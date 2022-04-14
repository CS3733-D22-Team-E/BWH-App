package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.pageButtons;
import edu.wpi.cs3733.D22.teamE.pageControlFacade;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class sideMenuController implements pageButtons {

  @FXML StackPane root;

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("medicineDelivery.fxml", thisStage);
  }

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("mealDeliveryPage.fxml", thisStage);
  }

  @FXML
  public void languageButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("languagePage.fxml", thisStage);
  }

  @FXML
  public void statusButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("statusPage.fxml", thisStage);
  }

  @FXML
  public void exitButton(ActionEvent event) throws IOException {
    pageControlFacade.exitApp();
  }

  @FXML
  public void medicalEquipmentButton(ActionEvent event) {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("medicalEquipmentPage.fxml", thisStage);
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("sanitationPage.fxml", thisStage);
  }

  @FXML
  public void labRequestButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("labRequestPage.fxml", thisStage);
  }

  @FXML
  public void mapButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("map.fxml", thisStage);
  }

  @Override
  public void aboutButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("aboutPage.fxml", thisStage);
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("defaultPage.fxml", thisStage);
  }

  @Override
  public void profButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("profilePage.fxml", thisStage);
  }

  @Override
  public void helpButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("helpPage.fxml", thisStage);
  }

  @Override
  public void dashboardButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("DashboardPage.fxml", thisStage);
  }
}
