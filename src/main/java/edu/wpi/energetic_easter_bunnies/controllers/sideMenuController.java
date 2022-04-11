package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.menuButtons;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/** Adds functionality to the side menu. */
public class sideMenuController implements Initializable, menuButtons {

  @FXML StackPane root;

  /**
   * Exits the program.
   *
   * @param event           Pressing the exit button
   * @throws IOException    ??
   */
  @FXML
  public void exitButton(ActionEvent event) throws IOException {
    System.exit(0);
  }

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
  public void statusButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/statusPage.fxml");
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
  }

  /**
   * Opens the Sanitation page from the side menu.
   * @param event             Pressing the Sanitation button
   * @throws IOException      ??
   */
  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();

    URL url = Main.class.getResource("view/sanitationPage.fxml");
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
}
