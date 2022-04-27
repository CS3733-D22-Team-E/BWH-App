package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.pageControl;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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

  public sideMenuController(BorderPane root) {
    this.root = root;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    VBox.setVgrow(bufferVBox, Priority.ALWAYS);

    sideMenuButtonHandler(dashboardBox, "DashboardPage.fxml");
    sideMenuButtonHandler(submitServiceRequestBox, "DashboardPage.fxml");
    sideMenuButtonHandler(statusPageBox, "statusPage.fxml");
    sideMenuButtonHandler(mapBox, "map.fxml");
    sideMenuButtonHandler(profileBox, "profilePage.fxml");
    sideMenuButtonHandler(aboutBox, "aboutPage.fxml");
    sideMenuButtonHandler(helpBox, "helpPage.fxml");
    sideMenuButtonHandler(logoutBox, "");
    sideMenuButtonHandler(exitBox, "");
  }

  private void sideMenuButtonHandler(HBox sideMenuBox, String url) {
    highlightSideMenuButtonHandler(sideMenuBox);
    sideMenuButtonClickedHandler(sideMenuBox, url);
  }

  private void sideMenuButtonClickedHandler(HBox sideMenuBox, String url) {
    sideMenuBox.setOnMouseClicked(
        mouseEvent -> {
          if (sideMenuBox.equals(logoutBox)) {
            // TODO: fill will logout procedure
            pageControl.loadPage("loginPage.fxml", (Stage) root.getScene().getWindow());
          } else if (sideMenuBox.equals(exitBox)) {
            pageControl.exitApp();
          } else {
            pageControl.loadCenter(url, (Stage) root.getScene().getWindow());
          }
        });
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

  @FXML
  public void dashboardButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("DashboardPage.fxml", thisStage);
  }

  @FXML
  public void submitServiceRequestButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("medicineDelivery.fxml", thisStage);
  }

  @FXML
  public void statusButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("statusPage.fxml", thisStage);
  }

  @FXML
  public void mapButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("map.fxml", thisStage);
  }

  @FXML
  public void profileButton(ActionEvent event) {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("profilePage.fxml", thisStage);
  }

  @FXML
  public void aboutButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("aboutPage.fxml", thisStage);
  }

  @FXML
  public void helpButton(ActionEvent event) {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("helpPage.fxml", thisStage);
  }

  @FXML
  public void logoutButton(ActionEvent event) throws IOException {
    pageControl.exitApp();
  }

  @FXML
  public void exitButton(ActionEvent event) throws IOException {
    pageControl.exitApp();
  }
}
