package edu.wpi.energetic_easter_bunnies;

import edu.wpi.energetic_easter_bunnies.controllers.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {
  @FXML Button sanitationButton;
  @FXML Button mealDeliveryButton;
  @FXML Button languageButton;
  @FXML Button medicalEquipmentButton;
  @FXML Button medicineDeliveryButton;
  @FXML MenuBar menuBar;

  FXMLLoader loader = new FXMLLoader();
  Parent root;

  sanitationServiceController sanitationService = new sanitationServiceController();
  MedicalEquipmentController medicalEquipmentService = new MedicalEquipmentController();
  languageInterpreterRequestController languageInterpreterService = new languageInterpreterRequestController();

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @FXML
  private void mealDeliveryButton(ActionEvent event) throws IOException {
    Node node = mealDeliveryButton;
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/mealDeliveryPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  private void languageButton(ActionEvent event) throws IOException {
    Node node = languageButton;
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/languagePage.fxml"));
    root = loader.load();
    loader.setController(languageInterpreterService);


    thisStage.setScene(new Scene(root));
  }

  @FXML
  private void medicalEquipmentButton(ActionEvent event) throws IOException {
    Node node = medicalEquipmentButton;
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/medicalEquipmentPage.fxml"));
    root = loader.load();
    loader.setController(medicalEquipmentService);

    thisStage.setScene(new Scene(root));
  }

  @FXML
  private void medicineDeliveryButton(ActionEvent event) throws IOException {
    Node node = medicineDeliveryButton;
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/medicineDelivery.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }

  @FXML
  private void sanitationButton(ActionEvent event) throws IOException {
    Node node = sanitationButton;
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("view/sanitationPage.fxml"));
    root = loader.load();
    loader.setController(sanitationService);

    thisStage.setScene(new Scene(root));
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());

    loader.setLocation(getClass().getResource("view/defaultPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
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

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
