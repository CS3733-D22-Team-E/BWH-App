package edu.wpi.energetic_easter_bunnies;

import edu.wpi.energetic_easter_bunnies.controllers.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application implements menuButtons {
  @FXML Button sanitationButton;
  @FXML Button mealDeliveryButton;
  @FXML Button languageButton;
  @FXML Button medicalEquipmentButton;
  @FXML Button medicineDeliveryButton;
  @FXML Button labRequestButton;
  @FXML MenuBar menuBar;
  @FXML AnchorPane mainPane;


  public FXMLLoader loader = new FXMLLoader();
  public Parent root;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    loader.setLocation(getClass().getResource("view/defaultPage.fxml"));
    root = loader.load();

    primaryStage.setTitle("Application");
    primaryStage.setScene(new Scene(root));
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/mealDeliveryPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void languageButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/languagePage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void medicalEquipmentButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/medicalEquipmentPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/medicineDelivery.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @Override
  public void exitButton(ActionEvent event) throws IOException {
    System.exit(0);
  }

  @Override
  public void locationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/map.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/sanitationPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void labRequestButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/labRequestPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/defaultPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
