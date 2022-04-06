package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.energetic_easter_bunnies.Main;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class mapEditorController implements Initializable {

  FXMLLoader loader = new FXMLLoader();
  Parent root;
  @FXML MenuBar menuBar;
  LocationDAOImpl db;

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
  @FXML Text deleteText;
  @FXML Button changePosition;

  int mouseX;
  int mouseY;
  String editMode;
  Location selectedLoc;

  ObservableList<String> floors = FXCollections.observableArrayList("1", "2", "3", "L1", "L2");
  ObservableList<String> nodes =
      FXCollections.observableArrayList(
          "PATI", "STOR", "DIRT", "HALL", "ELEV", "REST", "STAI", "DEPT", "LABS", "INFO", "CONF",
          "EXIT", "RETL", "SERV");

  public mapEditorController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    floor.setItems(floors);
    addNodeType.setItems(nodes);
    updateNodeType.setItems(nodes);

    try {
      db = new LocationDAOImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
    String nodeID =
        "e"
            + addNodeType.getValue()
            + "numonfloorWRONG"
            + floor.getValue(); // not correct need to check which num on floor
    int numID = 0;
    Location location =
        new Location(
            nodeID,
            mouseX,
            mouseY,
            floor.getValue().toString(),
            "Tower",
            addNodeType.getValue(),
            addLongName.getText().toString(),
            addShortName.getText().toString(),
            numID);
    db.addLocation(location);
  }

  @FXML
  public void smallUpdateLocationButton(ActionEvent event) throws SQLException {}

  @FXML
  public void smallDeleteLocationButton(ActionEvent event) throws SQLException {}

  // Display location on the map
  private void displayFloorLocations(List<Location> locationList) {

    double imageX = 535;
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    mapBox.getChildren().clear();

    List<Location> filteredLocations =
        locationList.stream()
            .filter(
                location -> {
                  if (Objects.equals(location.getFloor(), floor.getValue())) {
                    return true;
                  }
                  return false;
                })
            .collect(Collectors.toList());

    for (Location l : filteredLocations) {
      Circle c = new Circle();
      c.setRadius(8);
      c.setCenterX(l.getXcoord() * scaleFactor);
      c.setCenterY(l.getYcoord() * scaleFactor);

      // Conditional styling
      if (selectedLoc == l) {
        c.getStyleClass().add("selectedLocationDot");
      } else {
        c.getStyleClass().add("locationDot");
      }

      mapBox.getChildren().add(c);
    }
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

    // Reset values to default
    selectedLoc = null;
    smallUpdateLocation.setDisable(true);
    changePosition.setDisable(false);
    smallDeleteLocation.setDisable(true);
    smallAddLocation.setDisable(true);

    // Clear all locations dots when the floor is switched
    mapBox.getChildren().clear();
    switchMap(floor.getValue());
  }

  public void paneSwitch(String pane) {

    // Reset values to default
    selectedLoc = null;
    smallUpdateLocation.setDisable(true);
    changePosition.setDisable(false);
    smallDeleteLocation.setDisable(true);
    smallAddLocation.setDisable(true);

    // Only render existing dots if the delete or update functions are selected
    if (Objects.equals(pane, "delete") || Objects.equals(pane, "update")) {
      List<Location> locationList = db.getAllLocations();

      displayFloorLocations(locationList);
    }

    AddLocationPane.setVisible(false);
    UpdateLocationPane.setVisible(false);
    deleteLocationPane.setVisible(false);
    switch (pane) {
      case "add":
        editMode = "add";
        mapBox.getChildren().clear();
        AddLocationPane.setVisible(true);
        break;
      case "update":
        editMode = "update";
        UpdateLocationPane.setVisible(true);
        break;
      case "delete":
        editMode = "delete";
        deleteLocationPane.setVisible(true);
        break;
      default:
        break;
    }
  }

  @FXML
  public void mapClick(MouseEvent event) throws IOException {

    double imageX = 535;
    double coordinateX = 935;
    double scaleFactor = imageX / coordinateX;

    mouseX = (int) event.getX();
    mouseY = (int) event.getY();

    List<Location> locations = db.getAllLocations();

    // Handle moving the location dot on the map
    if (Objects.equals(editMode, "changePosition")) {

      // Filter out the old location dot
      List<Location> filteredLocations =
          locations.stream()
              .filter(
                  location -> {
                    if (selectedLoc == location) {
                      return false;
                    }
                    return true;
                  })
              .collect(Collectors.toList());
      // Re-render location dots
      displayFloorLocations(filteredLocations);

      // Add circle to map
      Circle c = new Circle();
      c.setRadius(8);
      c.setCenterX(mouseX);
      c.setCenterY(mouseY);
      c.getStyleClass().add("selectedLocationDot");
      mapBox.getChildren().add(c);

      // Update change position button text
      changePosition.setText("Change Position");

      // Change edit mode back to update
      editMode = "update";
    }

    // Get a selected location based on where the user clicked
    if (Objects.equals(editMode, "delete") || Objects.equals(editMode, "update")) {
      for (Location location : locations) {
        // If the user has selected a dot on the map
        int locationPadding = 7;
        if ((location.getXcoord() * scaleFactor >= mouseX - locationPadding
                && location.getXcoord() * scaleFactor <= mouseX + locationPadding)
            && (location.getYcoord() * scaleFactor >= mouseY - locationPadding
                && location.getYcoord() * scaleFactor <= mouseY + locationPadding)
            && Objects.equals(location.getFloor(), floor.getValue())) {

          // Update the current selected location variable
          selectedLoc = location;
          // Re-render dots to change the selected dot to red
          displayFloorLocations(locations);
        }
      }
    }

    if (Objects.equals(editMode, "delete")) {
      // Update text with location name
      deleteText.setText(selectedLoc.getShortName());
      // Enable the smallDeleteButton
      smallDeleteLocation.setDisable(false);
    } else if (Objects.equals(editMode, "update")) {
      // Update text fields with location data
      updateShortName.setText(selectedLoc.getShortName());
      updateLongName.setText(selectedLoc.getLongName());
      updateNodeType.setValue(selectedLoc.getNodeType());
      // Enable the smallUpdateButton and changePositionButton
      changePosition.setDisable(false);
      smallUpdateLocation.setDisable(false);
    } else if (Objects.equals(editMode, "add")) {
      // Clear past dots the user placed
      mapBox.getChildren().clear();

      // Place new dot
      Circle c = new Circle();
      c.setRadius(8);
      c.setCenterX(event.getX());
      c.setCenterY(event.getY());
      c.getStyleClass().add("selectedLocationDot");
      mapBox.getChildren().add(c);

      // Enable smallAddButton
      smallAddLocation.setDisable(false);
    }
  }

  @FXML
  public void changePositionButton(ActionEvent event) throws IOException {
    changePosition.setText("Cancel");
    editMode = "changePosition";
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
