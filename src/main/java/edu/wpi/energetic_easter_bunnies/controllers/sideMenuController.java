package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.pageControlFacade;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

<<<<<<< HEAD
/** Adds functionality to the side menu. */
public class sideMenuController implements Initializable, menuButtons {
=======
public class sideMenuController {
>>>>>>> origin/Joey_Working_Branch

  @FXML StackPane root;

  /**
   * Exits the program.
   *
   * @param event           Pressing the exit button
   * @throws IOException    ??
   */
  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {

<<<<<<< HEAD
  /**
   * Opens the map page from the side menu.
   * @param event             Pressing the Map button
   * @throws IOException      ??
   */
  @Override
  public void locationButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/map.fxml");
    if (url != null) {
      loader.setLocation(url);
      Parent newRoot = loader.load();

      try {
        root.getScene().setRoot(newRoot);
      } catch (NullPointerException e) {
        System.out.println("mainStage never sent to side Panel");
      }
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  /**
   * Opens the lab request page from the side menu.
   * @param event             Pressing the Lab Request button
   * @throws IOException      ??
   */
  @Override
  public void labRequestButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/labRequestPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      Parent newRoot = loader.load();

      try {
        root.getScene().setRoot(newRoot);
      } catch (NullPointerException e) {
        System.out.println("mainStage never sent to side Panel");
      }
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  /**
   * Opens the Status page from the side menu.
   * @param event             Pressing the Status Page button
   * @throws IOException      ??
   */
  @Override
=======
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("mealDeliveryPage.fxml", thisStage);
  }

  @FXML
>>>>>>> origin/Joey_Working_Branch
  public void statusButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("statusPage.fxml", thisStage);
  }

<<<<<<< HEAD
  /**
   * Opens the home page from the side menu.
   * @param event             Pressing the Home button
   * @throws IOException      ??
   */
  @Override
  public void homeButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/defaultPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      Parent newRoot = loader.load();

      try {
        root.getScene().setRoot(newRoot);
      } catch (NullPointerException e) {
        System.out.println("mainStage never sent to side Panel");
      }
    } else {
      System.out.println("Path Doesn't Exist");
    }
=======
  @FXML
  public void languageButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("languagePage.fxml", thisStage);
>>>>>>> origin/Joey_Working_Branch
  }

  /**
   * Opens the Sanitation page from the side menu.
   * @param event             Pressing the Sanitation button
   * @throws IOException      ??
   */
  @FXML
  public void medicalEquipmentButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("medicalEquipmentPage.fxml", thisStage);
  }

<<<<<<< HEAD
  /**
   * Opens the map page from the side menu.
   * @param event             Pressing the Map button
   * @throws IOException      ??
   */
  @Override
  public void mealDeliveryButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/mealDeliveryPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      Parent newRoot = loader.load();

      try {
        root.getScene().setRoot(newRoot);
      } catch (NullPointerException e) {
        System.out.println("mainStage never sent to side Panel");
      }
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  /**
   * Opens the Language Interpreter page from the side menu.
   * @param event             Pressing the Language Interpreter button
   * @throws IOException      ??
   */
  @Override
  public void languageButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/languagePage.fxml");
    if (url != null) {
      loader.setLocation(url);
      Parent newRoot = loader.load();

      try {
        root.getScene().setRoot(newRoot);
      } catch (NullPointerException e) {
        System.out.println("mainStage never sent to side Panel");
      }
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  /**
   * Opens the Medical Equipment page from the side menu.
   * @param event             Pressing the Medical Equipment button
   * @throws IOException      ??
   */
  @Override
  public void medicalEquipmentButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/medicalEquipmentPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      Parent newRoot = loader.load();

      try {
        root.getScene().setRoot(newRoot);
      } catch (NullPointerException e) {
        System.out.println("mainStage never sent to side Panel");
      }
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  /**
   * Opens the Medicine Delivery page from the side menu.
   * @param event             Pressing the Medicine Delivery button
   * @throws IOException      ??
   */
  @Override
  public void medicineDeliveryButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/medicineDelivery.fxml");
    if (url != null) {
      loader.setLocation(url);
      Parent newRoot = loader.load();

      try {
        root.getScene().setRoot(newRoot);
      } catch (NullPointerException e) {
        System.out.println("mainStage never sent to side Panel");
      }
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  /**
   * Opens the Profile page from the side menu.
   * @param event             Pressing the Profile button
   * @throws IOException      ??
   */
  @Override
  public void profileButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/profilePage.fxml");
    if (url != null) {
      loader.setLocation(url);
      Parent newRoot = loader.load();

      try {
        root.getScene().setRoot(newRoot);
      } catch (NullPointerException e) {
        System.out.println("mainStage never sent to side Panel");
      }
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  /**
   * Not being used atm.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {}
=======
  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {

    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("medicineDelivery.fxml", thisStage);
  }

  @FXML
  public void exitButton(ActionEvent event) throws IOException {
    pageControlFacade.exitApp();
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

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("defaultPage.fxml", thisStage);
  }
>>>>>>> origin/Joey_Working_Branch
}
