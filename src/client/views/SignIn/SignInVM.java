package client.views.SignIn;

import client.core.ModelFactory;
import client.core.ViewHandler;
import client.model.VegSearchModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.transferObjects.Profile;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SignInVM {

  private StringProperty errorLabel;
  private StringProperty passwordRepeatField;
  private StringProperty passwordCreationField;
  private StringProperty usernameCreationField;
  private StringProperty descriptionArea;
  private StringProperty picApprovedLabel;
  private ViewHandler vh = ViewHandler.getInstance();
  private VegSearchModel model = ModelFactory.getInstance().getModel();

  public SignInVM() throws IOException, NotBoundException, SQLException
  {

    errorLabel = new SimpleStringProperty();
    passwordRepeatField = new SimpleStringProperty();
    passwordCreationField = new SimpleStringProperty();
    usernameCreationField = new SimpleStringProperty();
    descriptionArea = new SimpleStringProperty();
    picApprovedLabel = new SimpleStringProperty();
  }
  public void cancel() throws IOException, SQLException, NotBoundException {
    usernameCreationField.setValue("");
    descriptionArea.setValue("");
    passwordCreationField.setValue("");
    passwordRepeatField.setValue("");
    errorLabel.setValue("");
    picApprovedLabel.setValue("");
  }

  public boolean signUp(File picFile,byte[] bytes)
      throws IOException, SQLException, NotBoundException
  {
    if(model.signUp(usernameCreationField.getValue(),passwordCreationField.getValue(),picFile,bytes, getDescriptionArea().getValue()))
    {
      ViewHandler.getInstance().openLogIn();
      errorLabel.setValue("");
    }
    else
    {
      errorLabel.setValue("Your profile couldn't be created, change username");
    }
    usernameCreationField.setValue("");
    descriptionArea.setValue("");
    passwordCreationField.setValue("");
    passwordRepeatField.setValue("");
    return true;
  }

  public StringProperty getErrorLabel()
  {
    return errorLabel;
  }

  public StringProperty getPasswordRepeatField()
  {
    return passwordRepeatField;
  }

  public StringProperty getDescriptionArea()
  {
    return descriptionArea;
  }

  public StringProperty getPasswordCreationField()
  {
    return passwordCreationField;
  }

  public boolean save(String oldUsername,String newUsername, String password,
      File picFile,byte[] bytes, String description, ArrayList<Profile> subs)
      throws IOException, SQLException
  {
    boolean temp =  model.editProfile(oldUsername, newUsername, password, picFile, bytes,description, subs);
    if(temp)
    {
     vh.openProfile(model.getProfile(newUsername));
    }
    return temp;
  }
  public StringProperty getUsernameCreationField()
  {
    return usernameCreationField;
  }
}
