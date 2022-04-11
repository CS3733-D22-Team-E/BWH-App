package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.menuButtons;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainController implements menuButtons {
  public FXMLLoader loader = new FXMLLoader();
  public Parent root;
  public AnchorPane mainPane;

  @FXML Button sanitationButton;
  @FXML Button mealDeliveryButton;
  @FXML Button languageButton;
  @FXML Button medicalEquipmentButton;
  @FXML Button medicineDeliveryButton;
  @FXML Button labRequestButton;
  @FXML Button mapButton;
  @FXML Button dashboardButton;

  public mainController() {}

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/mealDeliveryPage.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @FXML
  public void statusButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(Main.class.getResource("view/statusPage.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @Override
  public void profileButton(ActionEvent event) throws IOException {}

  @FXML
  public void languageButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/languagePage.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @FXML
  public void medicalEquipmentButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/medicalEquipmentPage.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/medicineDelivery.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @Override
  public void exitButton(ActionEvent event) throws IOException {
    System.exit(0);
  }

  @Override
  public void locationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/map.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/sanitationPage.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @FXML
  public void labRequestButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/labRequestPage.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @FXML
  public void mapButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/map.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @FXML
  public void dashboardButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/DashboardPage.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    loader.setLocation(Main.class.getResource("view/defaultPage.fxml"));
    root = loader.load();

    thisStage.getScene().setRoot(root);
  }
}
