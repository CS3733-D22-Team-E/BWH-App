package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.database.daos.LocationDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.*;
import edu.wpi.cs3733.D22.teamE.entity.Location;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Is an abstract class meant to be the super class for each service request page. Inherits from
 * contains sideMenu
 */
public abstract class serviceRequestPageController {

  @FXML TextField notes;
  @FXML JFXButton submitButton;
  @FXML JFXButton resetButton;
  @FXML JFXComboBox<String> requestStatus;
  @FXML JFXComboBox<String> staffAssignee;
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

  @FXML Label notStarted;
  @FXML Label processing;
  @FXML Label LL2requests;
  @FXML Label LL1requests;
  @FXML Label requests1;
  @FXML Label requests2;
  @FXML Label requests3;
  @FXML Label requests4;
  @FXML Label requests5;

  @FXML JFXButton tableViewButton;

  LocationDAOImpl locationDB;
  HashMap<String, String> roomNameToRoomID;
  HashMap<String, String> roomIDToRoomName;

  DAOSystem system;

  serviceRequestPageController() {}

  public void initialize(URL url, ResourceBundle rb) {
    system = DAOSystemSingleton.INSTANCE.getSystem();
    requestStatus.getItems().addAll("To Do", "Processing", "Complete");
    populateEmployeeComboBox();

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

  protected void populateEmployeeComboBox() {
    ArrayList<Employee> employees = (ArrayList<Employee>) system.getAllEmployee();
    ArrayList<String> employeeNames = new ArrayList<>();
    for (Employee curEmployee : employees) {
      employeeNames.add(curEmployee.getName());
    }
    staffAssignee.setItems(FXCollections.observableArrayList(employeeNames));
  }

  @FXML
  public void displayTableView(ActionEvent event) {
    pageControl.loadCenter("statusPage.fxml", (Stage) tableViewButton.getScene().getWindow());
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

  public void setInfographicsCount(String requestType) {
    List<RequestInterface> requests = system.getAllServiceRequests();

    int notStartedCount = 0;
    int processingCount = 0;
    int requestsLL2Count = 0;
    int requestsLL1Count = 0;
    int requests1Count = 0;
    int requests2Count = 0;
    int requests3Count = 0;
    int requests4Count = 0;
    int requests5Count = 0;

    for (RequestInterface request : requests) {
      if (request.getRequestType().toString().equals(requestType)) {
        if (request.getRequestStatus().equals("To Do")) {
          notStartedCount++;
        } else if (request.getRequestStatus().equals("Processing")) {
          processingCount++;
        }

        switch (request.getFloorID()) {
          case "L2":
            requestsLL2Count++;
            break;
          case "L1":
            requestsLL1Count++;
            break;
          case "1":
            requests1Count++;
            break;
          case "2":
            requests2Count++;
            break;
          case "3":
            requests3Count++;
            break;
          case "4":
            requests4Count++;
            break;
          case "5":
            requests5Count++;
            break;
        }
      }
    }

    notStarted.setText(Integer.toString(notStartedCount));
    processing.setText(Integer.toString(processingCount));

    LL2requests.setText(Integer.toString(requestsLL2Count));
    LL1requests.setText(Integer.toString(requestsLL1Count));
    requests1.setText(Integer.toString(requests1Count));
    requests2.setText(Integer.toString(requests2Count));
    requests3.setText(Integer.toString(requests3Count));
    requests4.setText(Integer.toString(requests4Count));
    requests5.setText(Integer.toString(requests5Count));
  }
}
