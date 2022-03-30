package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.entity.*;
import edu.wpi.energetic_easter_bunnies.menuButtons;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public abstract class ServiceRequestPageController implements menuButtons {

  FXMLLoader loader = new FXMLLoader();
  Parent root;

  @FXML TextField notes;
  @FXML Button submitButton;
  @FXML Button resetButton;
  @FXML MenuBar menuBar;
  @FXML TextField requestStatus;
  @FXML TextField staffAssignee;

  ServiceRequestPageController() {}

  public boolean sendToDB(serviceRequest request) {
    // todo : implement DB communication
    return true;
  }

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("../view/mealDeliveryPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void languageButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("../view/languagePage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void medicalEquipmentButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("../view/medicalEquipmentPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("../view/medicineDelivery.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @Override
  public void exitButton(ActionEvent event) throws IOException {
    System.exit(0);
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("../view/sanitationPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("../view/defaultPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  public void populateRequestTable() {
    // todo : get all service requests as list
    // todo : filter through to match MY type
    // todo : populate my table
  }
}
