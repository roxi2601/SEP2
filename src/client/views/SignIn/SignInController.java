package client.views.SignIn;

import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.views.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shared.transferObjects.Profile;

import javax.swing.*;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class SignInController implements ViewController
{
  @FXML
  private Label picApprovedLabel;
  @FXML
  private Label errorLabel;
  @FXML
  private PasswordField passwordRepeatField;
  @FXML
  private PasswordField passwordCreationField;
  @FXML
  private TextField usernameCreationField;
  @FXML
  private TextArea descriptionArea;
  @FXML
  private ImageView userPic;

  private SignInVM vm = ViewModelFactory.getInstance().getSignInVM();

  private File picFile;

  public SignInController() throws IOException, NotBoundException, SQLException
  {
  }

  public void onChooseFileButton(ActionEvent actionEvent) throws IOException
  {
    JFileChooser fc =  new JFileChooser();
    fc.setDialogTitle("Choose your profile picture");
    fc.showDialog(null,"Select");
    fc.setVisible(true);

      if(fc.getSelectedFile()!=null)
      {
        picFile = fc.getSelectedFile();
        picApprovedLabel.setText("Your pic was added :)");
      }
      else
      {
        picFile = new File("file:rabbit.jpg");
      }

  }

  public void onSignUp2Button(ActionEvent actionEvent)
      throws FileNotFoundException, SQLException, RemoteException
  {
    if(usernameCreationField.getText().length()<3|| passwordCreationField.getText().length()<3)
    {
      errorLabel.setText("Username and password have to contain at least 3 signs ");
    }
    else if(!passwordCreationField.getText().equals(passwordRepeatField.getText()) )
    {
      errorLabel.setText("Passwords don't match :( , try to type them again");
      passwordCreationField.clear();
      passwordRepeatField.clear();
    }
    else
    vm.signUp(picFile);
  }

  public void onCancelButton(ActionEvent actionEvent)
  {
    ViewHandler.getInstance().openLogIn();
  }

  @Override public void init(Profile profile)
  {
    Image image = new Image("file:rabbit.jpg");
    userPic.setImage(image);

    errorLabel.textProperty().bind(vm.getErrorLabel());
    passwordCreationField.textProperty().bindBidirectional(vm.getPasswordCreationField());
    usernameCreationField.textProperty().bindBidirectional(vm.getUsernameCreationField());
    descriptionArea.textProperty().bindBidirectional(vm.getDescriptionArea());
  }
}
