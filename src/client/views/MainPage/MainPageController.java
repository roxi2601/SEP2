package client.views.MainPage;

import client.core.ModelFactory;
import client.core.ViewHandler;
import client.core.ViewModelFactory;
import client.model.VegSearchModel;
import client.views.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shared.transferObjects.Profile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class MainPageController implements ViewController
{
  @FXML
  private Button searchButton;
  @FXML
  private TextField searchField;
  @FXML
  private ListView ingredientList;
  @FXML
  private ImageView recipePic;
  @FXML
  private Button likeButton;
  @FXML
  private Label likeLabel;
  @FXML
  private TextField commentField;
  @FXML
  private TextField reportField;
  @FXML
  private ListView CommentsList;
  @FXML
  private ImageView userPic;
  @FXML
  private Hyperlink userLink;

  private MainPageVM vm= ViewModelFactory.getInstance().getMainPageVM();
  private ViewHandler vh = ViewHandler.getInstance();
 // private VegSearchModel model = ModelFactory.getInstance().getModel();

  public MainPageController() throws IOException, NotBoundException
  {
  }

  public void onUserLink(ActionEvent actionEvent)
      throws FileNotFoundException, SQLException, RemoteException
  {
    vm.openProfile();

  }

  public void onLikeButton(ActionEvent actionEvent)
  {
  }

  public void onCommentButton(ActionEvent actionEvent)
  {
  }

  public void onReportButton(ActionEvent actionEvent) throws IOException, NotBoundException {
    vm.report();
  }

  public void onSearchButton(ActionEvent actionEvent)
  {
  }

  public void onViewProfileButton(ActionEvent actionEvent)
  {
    vm.viewMyProfile();
  }

  public void onLogoutButton(ActionEvent actionEvent)
  {
    vm.logOut();
  }

  @Override public void init(Profile profile)
  {
    userLink.textProperty().bindBidirectional(vm.getUserLink());
    userLink.setText("Roksanka");
    Image image = new Image("file:rabbit.jpg");
    Image image1 = new Image("file:look.png");
    Image image2 = new Image("file:heart.png");
    Image image3 = new Image("file:carrotLogo.png");
    userPic.setImage(image);
    searchButton.setEffect(new ImageInput(image1));
    likeButton.setEffect(new ImageInput(image2));
    recipePic.setImage(image3);

  }
}
