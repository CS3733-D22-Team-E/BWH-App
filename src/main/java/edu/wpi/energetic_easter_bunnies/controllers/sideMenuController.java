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
import javafx.scene.layout.VBox;

public class sideMenuController implements Initializable, menuButtons {

  @FXML VBox root;

  @FXML
  public void exitButton(ActionEvent event) throws IOException {
    System.exit(0);
  }

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

  @Override
  public void initialize(URL location, ResourceBundle resources) {}
}
