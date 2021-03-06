package client.views.Profile;


import client.core.ModelFactory;
import client.core.ViewHandler;
import client.model.VegSearchModel;
import client.views.RecipeDemo.RecipeDemoVM;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.transferObjects.Profile;
import shared.transferObjects.Recipe;
import java.beans.PropertyChangeEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfileVM {

    private ObservableList<RecipeDemoVM> recipeDemoVMS;

    private ViewHandler vh = ViewHandler.getInstance();
    private VegSearchModel model;
    private StringProperty subsLabel;
    private StringProperty username;



    public ProfileVM() throws IOException, NotBoundException, SQLException
    {
        this.model = ModelFactory.getInstance().getModel();
        this.subsLabel=new SimpleStringProperty();
        this.username = new SimpleStringProperty();
        recipeDemoVMS = FXCollections.observableArrayList();
    }

    public void addRecipeDisplay(Recipe recipe)
    {
        RecipeDemoVM rd = new RecipeDemoVM(recipe);
        recipeDemoVMS.add(rd);
    }
    public ObservableList<RecipeDemoVM> getRecipeDemoVMS()
    {
        return recipeDemoVMS;
    }
    public ArrayList<Recipe> getRecipes(String username) throws SQLException, IOException
    {
        return model.getRecipesByUsername(username);
    }

    public void newRecipe() throws IOException, SQLException
    {
        vh.openNewRecipeView();
    }

    public StringProperty getSubsLabel() {
        return subsLabel;
    }
    public StringProperty getUsername()
    {
        return username;
    }

    public void subscribe() throws IOException, SQLException
    {
        model.subscribe(username.getValue());
    }
    public void unsubscribe() throws IOException, SQLException
    {
        model.unsubscribe(username.getValue());
    }

    //This method returns boolean saying if entering profile user already subscribe that profile so the button '(Un)subscribe' is set to proper value
    public boolean doIsubscribeIt()
        throws RemoteException, FileNotFoundException, SQLException
    {
      return model.doIsubscribeIt(username.getValue());
    }

    public Profile getLoggedProfile()
    {
       return model.getLoggedProfile();
    }

    public Profile getProfile(String username)
            throws IOException, SQLException {
        return  model.getProfile(username);
    }
    public void delete() throws SQLException, RemoteException
    {
        model.delete();
    }

}
