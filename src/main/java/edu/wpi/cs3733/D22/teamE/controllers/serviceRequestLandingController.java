package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXToggleButton;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class serviceRequestLandingController {
  @FXML VBox mainPane;

  @FXML Button sanitationButton;
  @FXML Button languageButton;
  @FXML Button equipmentButton;
  @FXML Button mealDeliveryButton;
  @FXML Button labRequestButton;
  @FXML Button giftDeliveryButton;
  @FXML Button facilitiesButton;
  @FXML Button floralDeliveryButton;
  @FXML Button medicineDeliveryButton;
  @FXML Button securityButton;
  @FXML Button api1Button;
  @FXML Button api2Button;
  @FXML JFXToggleButton seeAuthors;
  @FXML JFXToggleButton apis;

  @FXML Text floralName;
  @FXML Text api1Name;
  @FXML Text api2Name;

  @FXML Text sanitationAuthor;
  @FXML Text languageAuthor;
  @FXML Text equipmentAuthor;
  @FXML Text mealAuthor;
  @FXML Text labAuthor;
  @FXML Text giftAuthor;
  @FXML Text facilitiesAuthor;
  @FXML Text medicineAuthor;
  @FXML Text securityAuthor;
  @FXML Text floralAuthor;
  @FXML Text api1Author;
  @FXML Text api2Author;

  public serviceRequestLandingController() {}

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {
    pageControl.loadCenter("mealDeliveryPage.fxml", (Stage) mainPane.getScene().getWindow());
  }

  @FXML
  public void languageButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControl.loadCenter("languagePage.fxml", thisStage);
  }

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControl.loadCenter("medicineDelivery.fxml", thisStage);
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControl.loadCenter("sanitationPage.fxml", thisStage);
  }

  @FXML
  public void labRequestButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControl.loadCenter("labRequestPage.fxml", thisStage);
  }

  @FXML
  public void equipmentButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControl.loadCenter("medicalEquipmentPage.fxml", thisStage);
  }

  @FXML
  public void giftDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControl.loadCenter("giftPage.fxml", thisStage);
  }

  @FXML
  public void securityButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControl.loadCenter("securityPage.fxml", thisStage);
  }

  @FXML
  public void facilitiesButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) mainPane.getScene().getWindow();

    pageControl.loadCenter("facilitiesPage.fxml", thisStage);
  }

  @FXML
  public void floralDeliveryButton(ActionEvent event) throws IOException {
    //    Stage thisStage = (Stage) mainPane.getScene().getWindow();
    //
    //    pageControl.loadCenter("floralPage.fxml", thisStage);
  }

  @FXML
  public void api1Button(ActionEvent event) throws IOException {
    //    Stage thisStage = (Stage) mainPane.getScene().getWindow();
    //
    //    pageControl.loadCenter("api1.fxml", thisStage);
  }

  @FXML
  public void api2Button(ActionEvent event) throws IOException {
    //    Stage thisStage = (Stage) mainPane.getScene().getWindow();
    //
    //    pageControl.loadCenter("api1.fxml", thisStage);
  }

  @FXML
  public void seeAuthors(ActionEvent event) throws IOException {

    if (seeAuthors.isSelected()) {
      sanitationAuthor.setVisible(true);
      labAuthor.setVisible(true);
      equipmentAuthor.setVisible(true);
      giftAuthor.setVisible(true);
      medicineAuthor.setVisible(true);
      languageAuthor.setVisible(true);
      securityAuthor.setVisible(true);
      facilitiesAuthor.setVisible(true);
      mealAuthor.setVisible(true);

      if (apis.isSelected()) {
        floralAuthor.setVisible(true);
        api1Author.setVisible(true);
        api2Author.setVisible(true);
      }
    } else {
      sanitationAuthor.setVisible(false);
      labAuthor.setVisible(false);
      equipmentAuthor.setVisible(false);
      giftAuthor.setVisible(false);
      medicineAuthor.setVisible(false);
      languageAuthor.setVisible(false);
      securityAuthor.setVisible(false);
      facilitiesAuthor.setVisible(false);
      mealAuthor.setVisible(false);

      floralAuthor.setVisible(false);
      api1Author.setVisible(false);
      api2Author.setVisible(false);
    }
  }

  @FXML
  public void showApis(ActionEvent event) throws IOException {
    if (apis.isSelected()) {
      floralDeliveryButton.setVisible(true);
      floralName.setVisible(true);
      api1Button.setVisible(true);
      api1Name.setVisible(true);
      api2Button.setVisible(true);
      api2Name.setVisible(true);

      if (seeAuthors.isSelected()) {
        floralAuthor.setVisible(true);
        api1Author.setVisible(true);
        api2Author.setVisible(true);
      }
    } else {
      floralDeliveryButton.setVisible(false);
      floralName.setVisible(false);
      floralAuthor.setVisible(false);

      api1Button.setVisible(false);
      api1Name.setVisible(false);
      api1Author.setVisible(false);

      api2Button.setVisible(false);
      api2Name.setVisible(false);
      api2Author.setVisible(false);
    }
  }
}
