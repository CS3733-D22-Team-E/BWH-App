package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.pageControlFacade;
import java.awt.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainController {
  public AnchorPane mainPane;

  @FXML Button sanitationButton;
  @FXML Button mealDeliveryButton;
  @FXML Button languageButton;
  @FXML Button medicalEquipmentButton;
  @FXML Button medicineDeliveryButton;
  @FXML Button labRequestButton;
  @FXML Button mapButton;

  @FXML private Button btnMode;
  @FXML private ImageView imgMode;
  @FXML private VBox parent;
  @FXML private Label title;

  private boolean isLightMode = true;

  public void changeMode(ActionEvent event) {
    isLightMode = !isLightMode;
    if (isLightMode) {
      setLightMode();
      System.out.println("It's work");
    } else {
      setDarkMode();
    }
  }

  private void setLightMode() {
    parent.getStylesheets().remove("styles/darkMode.css");
    parent.getStylesheets().add("styles/default.css");
    Image newImg = new Image("icons/ic_dark.png");
    imgMode.setImage(newImg);
  }

  private void setDarkMode() {
    parent.getStylesheets().remove("styles/default.css");
    parent.getStylesheets().add("styles/darkMode.css");
    Image newImg = new Image("view/icons/ic_light.png");
    imgMode.setImage(newImg);
  }

  pageControlFacade facade = new pageControlFacade();

  public mainController() {}

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    facade.loadPage("mealDeliveryPage.fxml", thisStage);
  }

  @FXML
  public void statusButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    facade.loadPage("statusPage.fxml", thisStage);
  }

  @FXML
  public void languageButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    facade.loadPage("languagePage.fxml", thisStage);
  }

  @FXML
  public void medicalEquipmentButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    facade.loadPage("medicalEquipmentPage.fxml", thisStage);
  }

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    facade.loadPage("medicineDelivery.fxml", thisStage);
  }

  @FXML
  public void exitButton(ActionEvent event) throws IOException {
    facade.exitApp();
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    facade.loadPage("sanitationPage.fxml", thisStage);
  }

  @FXML
  public void labRequestButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    facade.loadPage("labRequestPage.fxml", thisStage);
  }

  @FXML
  public void mapButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    facade.loadPage("map.fxml", thisStage);
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    facade.loadPage("defaultPage.fxml", thisStage);
  }
}
