package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.cs3733.D22.teamE.database.daos.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.*;
import edu.wpi.cs3733.D22.teamE.entity.Location;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Is an abstract class meant to be the super class for each service request page. Inherits from
 * contains sideMenu
 */
public abstract class serviceRequestPageController {

  @FXML TextField notes;
  @FXML JFXButton submitButton;
  @FXML JFXButton resetButton;
  @FXML TextField requestStatus;
  @FXML TextField staffAssignee;
  @FXML JFXHamburger burger;
  @FXML JFXDrawer drawer;
  @FXML JFXComboBox<String> floor;
  @FXML JFXComboBox<String> room;

  @FXML TableView<serviceRequest> requestTable;
  @FXML TableColumn<serviceRequest, String> tableFloorID;
  @FXML TableColumn<serviceRequest, String> tableRoomID;
  @FXML TableColumn<serviceRequest, String> tableRequestStatus;
  @FXML TableColumn<serviceRequest, String> tableStaffAssignee;
  @FXML TableColumn<serviceRequest, String> tableOtherNotes;

  LocationDAOImpl locationDB;
  HashMap<String, String> roomNameToRoomID;
  HashMap<String, String> roomIDToRoomName;

  serviceRequestPageController() {}

  public void initialize(URL url, ResourceBundle rb) {
    try {
      populateLocationComboBoxes();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Initializes and returns a LocationDAOImpl object to be used for populating location combo boxes
   *
   * @return an initialized LocationDAOImpl object
   * @throws SQLException if there is an error accessing the database
   */
  private LocationDAOImpl initalizeLocationDAO() throws SQLException {
    locationDB = new LocationDAOImpl();
    return locationDB;
  }

  /**
   * Populates the location combo boxes by first grabbing all the floors of the hospital and then
   * mapping them to an ArrayList of all the rooms on that floor. The room combo box selections are
   * determined based on the selection from the floor combo box.
   */
  protected void populateLocationComboBoxes() throws SQLException {
    // Initializes the locationDB object
    initalizeLocationDAO();

    /*
      Sets up data structures for storing floor and room values.
      A hashmap with floor name keys and lists of room names as values is used to
      associate rooms with their corresponding floor
    */
    List<Location> locations = locationDB.getAll();
    List<String> floors = new ArrayList<>();
    HashMap<String, ArrayList<String>> floorToRooms = new HashMap<>();
    roomNameToRoomID = new HashMap<>();
    roomIDToRoomName = new HashMap<>();
    // Makes the room combo box hidden until a floor value is selected
    room.setVisible(false);

    // Populates location list and hashmap
    for (Location l : locations) {
      String floor = l.getFloor();
      if (!floors.contains(floor)) {
        floors.add(floor);
      }
      ArrayList<String> roomsOnFloor;
      if (!floorToRooms.containsKey(floor)) {
        roomsOnFloor = new ArrayList<>();
      } else {
        roomsOnFloor = floorToRooms.get(floor);
      }
      roomsOnFloor.add(l.getShortName());
      roomNameToRoomID.put(l.getShortName(), l.getNodeID());
      roomIDToRoomName.put(l.getNodeID(), l.getShortName());
      floorToRooms.put(floor, roomsOnFloor);
    }

    // Sets value of floor combo box to list of floor names
    floor.setItems(FXCollections.observableArrayList(floors));

    // Adds a listener to the floor combo box so that once a selection is made, the corresponding
    // room values
    // for that floor populate the room combo box
    floor
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                  ObservableList<String> roomsToDisplay =
                      FXCollections.observableArrayList((floorToRooms.get(newValue)));
                  room.setItems(roomsToDisplay);
                  room.setVisible(true);
                }
              }
            });
  }

  protected void populateRequestReports(String serviceRequestType) {
    switch (serviceRequestType) {
      case "Medical Equipment":
        break;
      case "Meal":
        break;
      case "Medicine":
        break;
      case "Floral":
        break;
      case "Lab":
        break;
      case "Language Interpreter":
        break;
      case "Facilities":
        break;
      case "Gift":
        break;
      case "Sanitation":
        break;
      case "Security":
        break;
    }
  }

  /** Sends the respective data to the respective data base. */
  public boolean sendToDB(serviceRequest request) {
    // todo : implement DB communication
    return true;
  }

  /** Populates the respective table with the respective data. */
  protected void populateRequestTable() {
    // todo : get all service requests as list
    // todo : filter through to match MY type
    // todo : populate my table

  }

  /*
  protected ObservableList<serviceRequest> populateServiceRequestList(ServiceRequestDAOImpl)
   */

  /**
   * Abstract method for submitting information from the page to the respective service request
   * object.
   *
   * @param event Pressing the submit button.
   * @throws SQLException ??
   */
  @FXML
  public abstract void submitButton(ActionEvent event) throws SQLException;
}
