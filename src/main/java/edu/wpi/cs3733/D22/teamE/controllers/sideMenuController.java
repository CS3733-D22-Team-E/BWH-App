package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.ProfilePictureManager;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class sideMenuController implements Initializable {

  private BorderPane root;

  @FXML HBox dashboardBox;
  @FXML HBox submitServiceRequestBox;
  @FXML HBox statusPageBox;
  @FXML HBox mapBox;
  @FXML HBox profileBox;
  @FXML HBox aboutBox;
  @FXML HBox helpBox;
  @FXML VBox bufferVBox;
  @FXML HBox logoutBox;
  @FXML HBox exitBox;
  @FXML Circle profilePicture;

  public sideMenuController(BorderPane root) {
    this.root = root;
  }

  @Override
  public void initialize(URL location, ResourceBundle resourceBundle) {
    ProfilePictureManager.sidePanel = this;
    VBox.setVgrow(bufferVBox, Priority.ALWAYS);

    sideMenuButtonHandler(dashboardBox, "DashboardPage.fxml");
    sideMenuButtonHandler(submitServiceRequestBox, "serviceRequestLanding.fxml");
    sideMenuButtonHandler(statusPageBox, "statusPage.fxml");
    sideMenuButtonHandler(mapBox, "map.fxml");
    sideMenuButtonHandler(profileBox, "profilePage.fxml");
    sideMenuButtonHandler(aboutBox, "aboutPage.fxml");
    sideMenuButtonHandler(helpBox, "helpPage.fxml");
    sideMenuButtonHandler(logoutBox, "loginPage.fxml");
    sideMenuButtonHandler(exitBox, "");

    resetProfilePicture();
  }

  private void sideMenuButtonHandler(HBox sideMenuBox, String url) {
    highlightSideMenuButtonHandler(sideMenuBox);
    sideMenuButtonClickedHandler(sideMenuBox, url);
  }

  private void sideMenuButtonClickedHandler(HBox sideMenuBox, String url) {
    sideMenuBox.setOnMouseClicked(
        mouseEvent -> {
          if (sideMenuBox.equals(logoutBox)) {
            pageControl.loadPage(url, (Stage) root.getScene().getWindow());
          } else if (sideMenuBox.equals(exitBox)) {
            pageControl.exitApp(exitBox.getScene().getWindow());
          } else {
            pageControl.loadCenter(url, (Stage) root.getScene().getWindow());
          }
        });
  }

  public void resetProfilePicture() {
    Employee currentEmployee = AccountsManager.getInstance().getEmployee();
    Image image;
    try {
      InputStream is = ProfilePictureManager.getPersonalPicture(currentEmployee);
      image = new Image(is);
    } catch (Exception e) {
      URL url = Main.class.getResource("view/icons/profilepic.png");
      assert url != null;
      InputStream is = null;
      try {
        is = url.openStream();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      image = new Image(is);
    }
    profilePicture.setFill(new ImagePattern(image));
  }

  private void highlightSideMenuButtonHandler(HBox sideMenuBox) {
    // TODO: make CSS file change text color of button as well?
    sideMenuBox.setOnMouseEntered(
        mouseEvent -> {
          sideMenuBox.getStyleClass().clear();
          sideMenuBox.getStyleClass().add("sidebarSelectedButton");
          Label boxText = (Label) sideMenuBox.getChildren().get(1);
          boxText.getStyleClass().clear();
          boxText.getStyleClass().add("sidebarSelectedButton");
          root.getScene().setCursor(Cursor.HAND);
        });

    sideMenuBox.setOnMouseExited(
        mouseEvent -> {
          sideMenuBox.getStyleClass().clear();
          sideMenuBox.getStyleClass().add("sidebarButton");
          Label boxText = (Label) sideMenuBox.getChildren().get(1);
          boxText.getStyleClass().clear();
          boxText.getStyleClass().add("sidebarButton");
          root.getScene().setCursor(Cursor.DEFAULT);
        });
  }
}
