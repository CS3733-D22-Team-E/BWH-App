package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.pageControl;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class sideMenuController extends HeaderController {

  // TODO: do we need this?
  @FXML VBox root;

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
