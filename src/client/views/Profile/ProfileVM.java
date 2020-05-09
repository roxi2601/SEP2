package client.views.Profile;


import client.core.ModelFactory;
import client.model.VegSearchModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.transferObjects.Profile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class ProfileVM {
    private VegSearchModel model;
    private StringProperty subsLabel;

    public ProfileVM() throws IOException, NotBoundException {
        this.model = ModelFactory.getInstance().getModel();
        this.subsLabel=new SimpleStringProperty();
    }

    public StringProperty getSubsLabel() {
        return subsLabel;
    }

    public void subscribe() throws RemoteException {
        subsLabel.set(model.getNumberOfSubscriptions());
    }
    public void unsubscribe() throws RemoteException {
        subsLabel.set(model.getNumberOfSubscriptions());
    }
    public Profile getLoggedProfile()
    {
       return model.getLoggedProfile();
    }
    public Profile getProfile(String username)
        throws FileNotFoundException, SQLException, RemoteException
    {
        return  model.getProfile(username);
    }
}
