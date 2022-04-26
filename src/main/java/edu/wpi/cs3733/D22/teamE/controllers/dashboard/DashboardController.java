package edu.wpi.cs3733.D22.teamE.controllers.dashboard;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

  @FXML ImageView mapButton;
  @FXML ImageView requestStatusButton;
  @FXML ImageView profileButton;
  @FXML ImageView submitRequestButton;

  @FXML JFXButton ll2Button;
  @FXML JFXButton ll1Button;
  @FXML JFXButton firstFloorButton;
  @FXML JFXButton secondFloorButton;
  @FXML JFXButton thirdFloorButton;
  @FXML JFXButton fourthFloorButton;
  @FXML JFXButton fifthFloorButton;

  public DashboardController() {}

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    quickAccessButtonHandler();
  }

  private void quickAccessButtonHandler() {
    quickAccessButtonClickedHandler(mapButton, "map.fxml");
    quickAccessButtonClickedHandler(requestStatusButton, "statusPage.fxml");
    quickAccessButtonClickedHandler(profileButton, "profilePage.fxml");
    quickAccessButtonClickedHandler(submitRequestButton, "aboutPage.fxml");
  }

  private void quickAccessButtonClickedHandler(ImageView quickAccessButton, String url) {
    quickAccessButton.setOnMouseClicked(
        mouseEvent -> {
          pageControl.loadCenter(url, (Stage) quickAccessButton.getScene().getWindow());
        });
  }
}
