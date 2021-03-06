package database;

import javafx.scene.image.Image;
import shared.transferObjects.Profile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ProfilesData {

    private static ProfilesData instance;

    public ProfilesData() throws SQLException
    {
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    public static synchronized ProfilesData getInstance() throws SQLException {
        if(instance==null)
        {
            instance = new ProfilesData();
        }
        return instance;
    }
    private Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
                "JJuu11@@");
    }

    public synchronized boolean create(String username, String password, File picFile ,byte[] bytes, String description, ArrayList<Profile>subscriptions)
        throws SQLException, IOException
    {
        if(bytes!=null)
        picFile = getPicFile(bytes,username);
        if (picFile==null)
        {
            return  false;
        }
        Connection connection = getConnection();
        FileInputStream fis  = new FileInputStream(picFile);
        try (connection)
        {
            PreparedStatement statement= connection.prepareStatement("INSERT INTO \"VegSearch\".Profile(username,password,picFile,description,subscriptions) VALUES (?,?,?,?,?);");
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setBinaryStream(3,fis,(int)picFile.length());
            statement.setString(4,description);
            Array array  = connection.createArrayOf("varchar",getSubsForDB(subscriptions));
            statement.setArray(5, array);
            statement.executeUpdate();
        }
        connection.close();
        return true;
    }

    public synchronized Profile getProfile(String searchedUsername) throws SQLException
    {
        Profile result = null;
        Connection connection = getConnection();

        try(connection)
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"VegSearch\".Profile WHERE username LIKE ?");
            statement.setString(1, searchedUsername);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                String username  = resultSet.getString("username");
                String password = resultSet.getString("password");
                byte[] imgBytes = resultSet.getBytes(3);
                //using private method from below
                File picFile = getPicFile(imgBytes,username);
                String description = resultSet.getString("description");
                Array subscriptions = resultSet.getArray(4);
                String[] subs = (String[])subscriptions.getArray();
                ArrayList<Profile> subscribers = getSubs(subs);

                result = new Profile(username,password,imgBytes,picFile,description,subscribers);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        connection.close();
        return result;
    }
    public synchronized ArrayList<Profile> getProfiles(String searchedUsername) throws SQLException
    {
        ArrayList<Profile> result = new ArrayList<>();
        Connection connection = getConnection();

        try(connection)
        {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"VegSearch\".Profile WHERE username LIKE ?");
            statement.setString(1, "%"+searchedUsername+"%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                String username  = resultSet.getString("username");
                String password = resultSet.getString("password");
                byte[] imgBytes = resultSet.getBytes(3);
                //using private method from below
                File picFile = getPicFile(imgBytes,username);
                String description = resultSet.getString("description");
                Array subscriptions = resultSet.getArray(4);
                String[] subs = (String[])subscriptions.getArray();
                ArrayList<Profile> subscribers = getSubs(subs);

                result.add(new Profile(username,password,imgBytes,picFile,description,subscribers ));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        connection.close();
        return result;
    }
    private synchronized File getPicFile(byte[] imgBytes, String username) throws IOException
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);
        BufferedImage image = ImageIO.read(bais);

        if(new File(username+"pic.jpg").createNewFile())
        {
            System.out.println("new file created");
        }
        File picFile =  new File(username+"pic.jpg");

        if(image!=null)
        ImageIO.write(image,"jpg",picFile);
        return picFile;
    }

    //here I'm transforming array of subscribers usernames(read from database) into ArrayList of Profiles
    private  ArrayList<Profile> getSubs(String[] subs) throws SQLException {
        ArrayList<Profile> subscribers = new ArrayList<>();
        for (int i = 0; i < subs.length ;i++) {
            Profile profile = getProfile(subs[i]);
            subscribers.add(profile);
        }
        return  subscribers;
    }
    //here I'm transforming ArrayList of Profiles into Array of usernames of subscribers
    // I will need it to put subscribers data into database
    private String[] getSubsForDB(ArrayList<Profile> subs)
    {
        String[] usernames = new String[subs.size()];
        for (int i = 0; i <subs.size() ; i++) {
            usernames[i] = subs.get(i).getUsername();
        }

        return  usernames;
    }
    public synchronized boolean update(String oldUsername,String newUsername, String password, File picFile,byte[] bytes, String description, ArrayList<Profile> subscriptions)
        throws SQLException, IOException
    {
            if(bytes!=null)
            picFile = getPicFile(bytes,newUsername);
            if(picFile==null){
                return false;
            }
        Connection connection = getConnection();
        FileInputStream fis = new FileInputStream(picFile);
        try (connection)
        {
            PreparedStatement statement= connection.prepareStatement("UPDATE \"VegSearch\".Profile SET username=?,password=?,picFile=?,description=?,subscriptions=? WHERE username=?");
            statement.setString(1,newUsername);
            statement.setString(2,password);
            statement.setBinaryStream(3,fis,(int)picFile.length());

            statement.setString(4,description);
            Array array  = connection.createArrayOf("varchar",getSubsForDB(subscriptions));
            statement.setArray(5, array);
            statement.setString(6,oldUsername);

            if(!oldUsername.equals(newUsername))
            {
                new File(oldUsername+"pic.jpg").delete();
            }

            statement.executeUpdate();
        }
        connection.close();
        return true;
    }
    public synchronized boolean delete(String username) throws SQLException
    {
        Connection connection = getConnection();
        try (connection)
        {
            PreparedStatement statement= connection.prepareStatement("DELETE FROM \"VegSearch\".Profile WHERE username =? ");
            statement.setString(1,username);
            statement.executeUpdate();
            new File(username+"pic.jpg").delete();
            RecipesData.getInstance().deleteRecipe(username);
        }
        connection.close();
        return true;
    }
}

