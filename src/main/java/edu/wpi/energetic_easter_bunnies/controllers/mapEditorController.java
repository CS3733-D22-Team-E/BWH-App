package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.pageControlFacade;
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
  @FXML Text currentLocation;

  double imageX = 535;
  double coordinateX = 935;
  double scaleFactor = imageX / coordinateX;
  int mouseX;
  int mouseY;
  String editMode;
  Location selectedLoc;

  ObservableList<String> floors =
      FXCollections.observableArrayList("L1", "L2", "1", "2", "3", "4", "5");
  ObservableList<String> nodes =
      FXCollections.observableArrayList(
          "PATI", "STOR", "DIRT", "HALL", "ELEV", "REST", "STAI", "DEPT", "LABS", "INFO", "CONF",
          "EXIT", "RETL", "SERV");

  public mapEditorController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    floor.setItems(floors);
    addNodeType.setItems(nodes);
    floor.setValue("1");

    try {
      db = new LocationDAOImpl();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void fetchDB() throws SQLException {
    db = new LocationDAOImpl();
    List<Location> locationList = db.getAll();
    displayFloorLocations(locationList);
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

    List<Location> locationList = db.getAll();

    // Get node type count
    List<Location> filteredLocations =
        locationList.stream()
            .filter(
                location -> {
                  if (Objects.equals(location.getFloor(), floor.getValue())
                      && Objects.equals(location.getNodeType(), addNodeType.getValue())) {
                    return true;
                  }
                  return false;
                })
            .collect(Collectors.toList());

    int nodeTypeCount = filteredLocations.size() + 1;

    String nodeID =
        "e"
            + addNodeType.getValue()
            + ("000" + nodeTypeCount).substring(Integer.toString(nodeTypeCount).length())
            + ("00" + floor.getValue()).substring(floor.getValue().length());
    int numID = 0;
    Location location =
        new Location(
            nodeID,
            Math.toIntExact(Math.round(mouseX / scaleFactor)),
            Math.toIntExact(Math.round(mouseY / scaleFactor)),
            floor.getValue().toString(),
            "Tower",
            addNodeType.getValue(),
            addLongName.getText().toString(),
            addShortName.getText().toString(),
            numID);
    db.update(location);

    // Fetch and switch and to update pane
    fetchDB();
    paneSwitch("update");
  }

  @FXML
  public void smallUpdateLocationButton(ActionEvent event) throws SQLException {
    int locationPadding = 7;

    // Check if the location has been moved
    if ((selectedLoc.getXcoord() >= (mouseX - locationPadding) / scaleFactor
            && selectedLoc.getXcoord() <= (mouseX + locationPadding) / scaleFactor)
        && (selectedLoc.getYcoord() >= (mouseY - locationPadding) / scaleFactor
            && selectedLoc.getYcoord() <= (mouseY + locationPadding) / scaleFactor)) {
      db.updateCoord(selectedLoc, selectedLoc.getXcoord(), selectedLoc.getYcoord());
    } else {

      // Get a whole number from the scaled up mouseX and mouseY
      int newX = Math.toIntExact(Math.round(mouseX / scaleFactor));
      int newY = Math.toIntExact(Math.round(mouseY / scaleFactor));

      // Update the location in the db with the new coordinates
      db.updateCoord(selectedLoc, newX, newY);

      // Fetch the updates from the db
      fetchDB();
    }
  }

  @FXML
  public void smallDeleteLocationButton(ActionEvent event) throws SQLException {

    // Reset delete text
    deleteText.setText("Click on the location you would like to delete");
    // Remove the currently selected location from the db
    db.delete(selectedLoc);
    fetchDB();
  }

  // Display location on the map
  private void displayFloorLocations(List<Location> locationList) {

    // Remove all location dots
    mapBox.getChildren().clear();

    // Only show location dots on the currently selected floor
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
      case "4":
        mapBox.getStyleClass().add("floor4Map");
        break;
      case "5":
        mapBox.getStyleClass().add("floor5Map");
        break;
      case "L1":
        mapBox.getStyleClass().add("floorL1Map");
        break;
      case "L2":
        mapBox.getStyleClass().add("floorL2Map");
        break;
      default:
        mapBox.getStyleClass().add("floor1Map");
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

    // Only render existing dots if the delete or update functions are selected
    if (Objects.equals(editMode, "delete") || Objects.equals(editMode, "update")) {
      List<Location> locationList = db.getAll();
      displayFloorLocations(locationList);
    }
  }

  public void paneSwitch(String pane) {

    // Reset values to default
    selectedLoc = null;
    smallUpdateLocation.setDisable(true);
    changePosition.setDisable(true);
    smallDeleteLocation.setDisable(true);
    smallAddLocation.setDisable(true);
    deleteText.setText("Click on the location you would like to delete");
    currentLocation.setText("Click Location to Change, then Change Position Button");

    // Only render existing dots if the delete or update functions are selected
    if (Objects.equals(pane, "delete") || Objects.equals(pane, "update")) {
      List<Location> locationList = db.getAll();

      displayFloorLocations(locationList);
    }

    // Hide all panes
    AddLocationPane.setVisible(false);
    UpdateLocationPane.setVisible(false);
    deleteLocationPane.setVisible(false);

    // Only show the current pane
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

    // Set mouseX and mouseY to the current location of the cursor
    mouseX = (int) event.getX();
    mouseY = (int) event.getY();

    List<Location> locations = db.getAll();

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

    if (Objects.equals(editMode, "delete") && selectedLoc != null) {
      // Update text with location name
      deleteText.setText(selectedLoc.getShortName());
      // Enable the smallDeleteButton
      smallDeleteLocation.setDisable(false);
    } else if (Objects.equals(editMode, "update") && selectedLoc != null) {

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

    if (Objects.equals(changePosition.getText(), "Cancel")) {
      changePosition.setText("Change Position");
      editMode = "update";
    } else {
      changePosition.setText("Cancel");
      editMode = "changePosition";
    }
  }

  @FXML
  public void editorReturn(ActionEvent event) {
    Stage thisStage = (Stage) mapBox.getScene().getWindow();

    pageControlFacade.loadPage("map.fxml", thisStage);
  }
}
