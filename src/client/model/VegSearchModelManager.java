package client.model;

import client.network.Client;
import shared.transferObjects.Profile;
import shared.transferObjects.Recipe;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class VegSearchModelManager implements VegSearchModel {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Client client;
    private Profile loggedProfile;

    public VegSearchModelManager(Client client)
        throws IOException, NotBoundException, SQLException
    {
        this.client=client;

        client.startClient();
        client.addListener("NewNotification" ,this::onNewNotification);
    }

  private void onNewNotification(PropertyChangeEvent propertyChangeEvent)
  {
    support.firePropertyChange(propertyChangeEvent);
  }

  @Override
    public void report(String title, String message)
      throws RemoteException, SQLException
  {
        client.sendReport(title,loggedProfile.getUsername(),message);
    }

    @Override
    public void subscribe(String user) throws IOException, SQLException {
        client.subscribe(user,loggedProfile);
    }

    @Override
    public void unsubscribe(String user) throws IOException, SQLException {
        client.unsubscribe(user,loggedProfile);
    }

  @Override public boolean doIsubscribeIt(String user)
      throws RemoteException, FileNotFoundException, SQLException
  {
    return client.doIsubscribeIt(user,loggedProfile);
  }

  @Override
    public boolean addRecipe(String title, String description, ArrayList<String> ingredients, File picfile,byte[] bytes)
      throws IOException, SQLException
  {
       return client.addRecipe(title,description,loggedProfile.getUsername(),ingredients,picfile,bytes);
    }

    @Override public boolean logIn(String username, String password)
            throws IOException, SQLException {
        boolean logged = client.logIn(username,password);
        if(logged)
        {
            setLoggedProfile(client.getProfile(username));
        }
        return logged;
    }

    public Profile getLoggedProfile()
    {
        return loggedProfile;
    }

    @Override
    public Profile getProfile(String username)
            throws IOException, SQLException {
        return client.getProfile(username);
    }

  @Override public ArrayList<Profile> getProfiles(String username)
          throws IOException, SQLException {
    return client.getProfiles(username);
  }

    @Override public void delete() throws SQLException, RemoteException
    {
        client.delete(loggedProfile.getUsername());
    }

    @Override public ArrayList<Recipe> getRecipesByIngredient(String ingredient)
            throws SQLException, IOException
    {
        return client.getRecipesByIngredient(ingredient);
    }

    @Override public Recipe getRecipeByTitle(String title)
            throws SQLException, IOException
    {
        return client.getRecipeByTitle(title);
    }

    @Override public ArrayList<Recipe> getRecipesByUsername(String username)
            throws SQLException, IOException
    {
        return client.getRecipesByUsername(username);
    }

    @Override public ArrayList<Recipe> getRecipesByTitle(String title)
            throws SQLException, IOException
    {
        return client.getRecipesByTitle(title);
    }

  @Override public ArrayList<Recipe> getAllRecipes()
          throws SQLException, IOException
  {
    return client.getAllRecipes();
  }
    private void setLoggedProfile(Profile profile)
    {
        loggedProfile = profile;
    }
    @Override public boolean signUp(String username, String password,
        File picFile,byte[] bytes,String description)
        throws IOException, SQLException
    {
       return client.signUp(username, password, picFile,bytes,description);
    }

  @Override public boolean editProfile(String oldUsername, String newUsername,
      String password, File picFile,byte[] bytes, String description,
      ArrayList<Profile> subs) throws IOException, SQLException
  {
    return client.editProfile(oldUsername, newUsername, password, picFile,bytes, description, subs);
  }

  @Override
    public void addListener(String eventName, PropertyChangeListener listener) throws
      IOException, SQLException{
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName, listener);
    }
}
