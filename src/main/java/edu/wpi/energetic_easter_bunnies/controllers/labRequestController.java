package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.daos.LabRequestDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.labRequest;
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
import javafx.scene.control.cell.PropertyValueFactory;

public class labRequestController extends serviceRequestPageController {

  @FXML ComboBox<String> floor;
  @FXML ComboBox<String> room;
  @FXML ComboBox<String> labRequestType;
  @FXML ComboBox<String> timeFrameComboBox;

  @FXML TableView<labRequest> requestsTable;

  @FXML TableColumn<labRequest, String> tableLabRequestType;
  @FXML TableColumn<labRequest, String> tableStaffAssignee;
  @FXML TableColumn<labRequest, String> tableLocNodeID;
  @FXML TableColumn<labRequest, String> tableTimeFrame;
  @FXML TableColumn<labRequest, String> tableRequestStatus;
  @FXML TableColumn<labRequest, String> tableOtherNotes;
  ObservableList<labRequest> tableList;

  LocationDAOImpl locationDB;
  LabRequestDAOImpl labRequestDB;
  labRequest labReq = new labRequest();

  public labRequestController() {}

  public void initialize(URL location, ResourceBundle resources) {
    try {
      super.initialize(location, resources);
      labRequestType.getItems().addAll("Blood Sample", "Urine Sample", "X-Ray", "CAT Scan", "MRI");
      timeFrameComboBox.getItems().addAll("ASAP", "<1 day", "<1 week");

      locationDB = new LocationDAOImpl();
      populateLocationComboBoxes();

      labRequestDB = new LabRequestDAOImpl();
      populateLabRequestTable();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void populateLocationComboBoxes() {
    List<Location> locations = locationDB.getAllLocations();
    List<String> floors = new ArrayList<>();
    HashMap<String, ArrayList<String>> floorToRooms = new HashMap<>();

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
      roomsOnFloor.add(l.getNodeID());
      floorToRooms.put(floor, roomsOnFloor);
    }
    floor.setItems(FXCollections.observableArrayList(floors));
    floor
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<String> roomsToDisplay =
                    FXCollections.observableArrayList((floorToRooms.get(newValue)));
                room.setItems(roomsToDisplay);
              }
            });
  }

  private void populateLabRequestTable() {
    ObservableList<labRequest> labRequests = populateLabRequestList();
    tableLabRequestType.setCellValueFactory(
        new PropertyValueFactory<labRequest, String>("labRequestType"));
    tableStaffAssignee.setCellValueFactory(
        new PropertyValueFactory<labRequest, String>("staffAssignee"));
    tableLocNodeID.setCellValueFactory(new PropertyValueFactory<labRequest, String>("roomID"));
    tableTimeFrame.setCellValueFactory(new PropertyValueFactory<labRequest, String>("timeFrame"));
    tableRequestStatus.setCellValueFactory(
        new PropertyValueFactory<labRequest, String>("requestStatus"));
    tableOtherNotes.setCellValueFactory(new PropertyValueFactory<labRequest, String>("otherNotes"));

    requestsTable.setItems(labRequests);
  }

  protected ObservableList<labRequest> populateLabRequestList() {
    List<labRequest> list = labRequestDB.getAllLabRequests();
    tableList = FXCollections.observableArrayList();
    for (labRequest l : list) {
      tableList.add(l);
    }
    return tableList;
  }

  @Override
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      labReq.setLabRequestType(labRequestType.getValue());
      labReq.setTimeFrame(timeFrameComboBox.getValue());
      labReq.setFloorID(floor.getValue());
      labReq.setRoomID(room.getValue());
      labReq.setOtherNotes(notes.getText());
      labReq.setRequestStatus(requestStatus.getText());
      labReq.setStaffAssignee(staffAssignee.getText());
      labSendToDB(labReq);

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  private void labSendToDB(labRequest labReq) throws SQLException {
    labRequestDB.addLabRequest(labReq);
    tableList.add(labReq);
  }

  @FXML
  private void resetButton(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    labRequestType.getSelectionModel().clearSelection();
    timeFrameComboBox.getSelectionModel().clearSelection();
    requestStatus.clear();
    staffAssignee.clear();
    notes.clear();
  }
}
