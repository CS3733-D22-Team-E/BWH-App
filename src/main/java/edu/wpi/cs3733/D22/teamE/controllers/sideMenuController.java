package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamE.pageButtons;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class sideMenuController implements pageButtons {

  // TODO: do we need this?
  @FXML VBox root;
  @FXML JFXButton profileButton;

  @FXML
  public void profileButton(ActionEvent event) {
    profileButton.getStyleClass().clear();
    profileButton.getStyleClass().add(".sidebarSelectedButton");
  }

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("medicineDelivery.fxml", thisStage);
  }

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("mealDeliveryPage.fxml", thisStage);
  }

  @FXML
  public void languageButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("languagePage.fxml", thisStage);
  }

  @FXML
  public void statusButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("statusPage.fxml", thisStage);
  }

  @FXML
  public void exitButton(ActionEvent event) throws IOException {
    pageControl.exitApp();
  }

  @FXML
  public void medicalEquipmentButton(ActionEvent event) {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("medicalEquipmentPage.fxml", thisStage);
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("sanitationPage.fxml", thisStage);
  }

  @FXML
  public void labRequestButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("labRequestPage.fxml", thisStage);
  }

  @FXML
  public void mapButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("map.fxml", thisStage);
  }

  @Override
  public void aboutButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("aboutPage.fxml", thisStage);
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("defaultPage.fxml", thisStage);
  }

  @Override
  public void profButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("profilePage.fxml", thisStage);
  }

  @Override
  public void helpButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("helpPage.fxml", thisStage);
  }

  @Override
  public void dashboardButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControl.loadPage("DashboardPage.fxml", thisStage);
  }
}
