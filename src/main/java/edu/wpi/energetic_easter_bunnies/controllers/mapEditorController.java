package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class mapEditorController implements Initializable {

  FXMLLoader loader = new FXMLLoader();
  Parent root;
  @FXML MenuBar menuBar;

  @FXML ComboBox<String> floor;
  @FXML JFXButton BigAddLocation;
  @FXML JFXButton BigUpdateLocation;
  @FXML JFXButton BigDeleteLocation;
  @FXML Pane AddLocationPane;
  @FXML TextField addShortName;
  @FXML TextField addLongName;
  @FXML ComboBox<String> addNodeType;
  @FXML Button smallAddLocation;
  @FXML Pane UpdateLocationPane;
  @FXML TextField updateShortName;
  @FXML TextField updateLongName;
  @FXML ComboBox<String> updateNodeType;
  @FXML Button smallUpdateLocation;
  @FXML Pane deleteLocationPane;
  @FXML Button smallDeleteLocation;
  @FXML AnchorPane mapBox;

  LocationDAOImpl db;

  ObservableList<String> floors = FXCollections.observableArrayList("1", "2", "3", "L1", "L2");

  public mapEditorController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    floor.setItems(floors);
  }

  // Switching Panes
  @FXML
  public void BigAddLocationButton(ActionEvent event) {
    paneSwitch("add");
  }

  @FXML
  public void BigUpdateLocationButton(ActionEvent event) {
    paneSwitch("update");
  }

  @FXML
  public void BigDeleteLocationButton(ActionEvent event) {
    paneSwitch("delete");
  }

  // New Locations, Update Location, Delete Location
  @FXML
  public void smallAddLocationButton(ActionEvent event) throws SQLException {
    Location location = new Location();
    db.addLocation(location);
  }

  @FXML
  public void smallUpdateLocationButton(ActionEvent event) {
    // db.updateLocation();
  }

  @FXML
  public void smallDeleteLocationButton(ActionEvent event) {
    // db.deleteLocation();
  }

  // Switching Floors
  private void switchMap(String floor) {

    // Clear and add back mapBox CSS class
    mapBox.getStyleClass().clear();
    mapBox.getStyleClass().add("mapBox");

    switch (floor) {
      case "1":
        mapBox.getStyleClass().add("floor1Map");
        break;
      case "2":
        mapBox.getStyleClass().add("floor2Map");
        break;
      case "3":
        mapBox.getStyleClass().add("floor3Map");
        break;
      case "L1":
        mapBox.getStyleClass().add("floorL1Map");
        break;
      case "L2":
        mapBox.getStyleClass().add("floorL2Map");
        break;
      default:
        mapBox.getStyleClass().add("floorDefaultMap");
        break;
    }
  }

  @FXML
  public void floor(ActionEvent event) throws IOException {

    switchMap(floor.getValue().toString());
  }

  public void paneSwitch(String pane) {
    AddLocationPane.setVisible(false);
    UpdateLocationPane.setVisible(false);
    deleteLocationPane.setVisible(false);
    switch (pane) {
      case "add":
        AddLocationPane.setVisible(true);
        break;
      case "update":
        UpdateLocationPane.setVisible(true);
        break;
      case "delete":
        deleteLocationPane.setVisible(true);
        break;
      default:
        break;
    }
  }

  @FXML
  public void mapClick(MouseEvent event) throws IOException {
    Circle c = new Circle();
    c.setRadius(8);
    c.setCenterX(event.getX());
    c.setCenterY(event.getY());
    c.getStyleClass().add("locationDot");
    mapBox.getChildren().add(c);
  }

  @FXML
  public void mealDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/mealDeliveryPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void languageButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/languagePage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void medicalEquipmentButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/medicalEquipmentPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void medicineDeliveryButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/medicineDelivery.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  public void exitButton(ActionEvent event) throws IOException {
    System.exit(0);
  }

  @FXML
  public void sanitationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/sanitationPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void labRequestButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/labRequestPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void homeButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/defaultPage.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }

  @FXML
  public void locationButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) menuBar.getScene().getWindow();

    URL url = Main.class.getResource("view/map.fxml");
    if (url != null) {
      loader.setLocation(url);
      root = loader.load();

      thisStage.setScene(new Scene(root));
    } else {
      System.out.println("Path Doesn't Exist");
    }
  }
}
