package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.database.*;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.MedicalEquipmentDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.MedicalEquipmentServiceRequestDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
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

public class medicalEquipmentController extends serviceRequestPageController {

  @FXML ComboBox<String> floor;
  @FXML ComboBox<String> room;
  @FXML ComboBox<String> equipmentType;
  @FXML ComboBox<Integer> equipmentQuantity;
  @FXML DatePicker deliveryDate;
  @FXML CheckBox isUrgent;
  @FXML TableView<medicalEquipmentRequest> medRequestTable;

  @FXML TableColumn<medicalEquipmentRequest, String> tableDeliveryDate;
  @FXML TableColumn<medicalEquipmentRequest, String> tableRequestDate;
  @FXML TableColumn<medicalEquipmentRequest, Boolean> tableIsUrgent;
  @FXML TableColumn<medicalEquipmentRequest, String> tableEquipmentType;
  @FXML TableColumn<medicalEquipmentRequest, Integer> tableEquipmentQuantity;
  @FXML TableColumn<medicalEquipmentRequest, String> tableStaffAssignee;
  @FXML TableColumn<medicalEquipmentRequest, String> tableRoomID;
  @FXML TableColumn<medicalEquipmentRequest, String> tableFloorID;
  @FXML TableColumn<medicalEquipmentRequest, String> tableRequestStatus;
  @FXML TableColumn<medicalEquipmentRequest, String> tableOtherNotes;

  MedicalEquipmentServiceRequestDAOImpl medEquipmentServiceRequestDB;
  MedicalEquipmentDAOImpl medEquipmentDB;
  medicalEquipmentRequest medicalEquipmentRequest = new medicalEquipmentRequest();
  LocationDAOImpl locationDB;

  ObservableList<medicalEquipmentRequest> tableList;

  public medicalEquipmentController() {}

  /**
   * Initializes the page by populating the location combo boxes, equipment combo boxes, and the
   * medical equipment request service table
   */
  public void initialize(URL url, ResourceBundle rb) {
    super.initialize(url, rb);
    try {
      locationDB = new LocationDAOImpl();
      populateLocationComboBoxes();

      medEquipmentDB = new MedicalEquipmentDAOImpl();
      populateEquipComboBoxes();

      medEquipmentServiceRequestDB = new MedicalEquipmentServiceRequestDAOImpl();
      populateMedEquipRequestTable();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Populates the location combo boxes by first grabbing all the floors of the hospital and then
   * mapping them to an ArrayList of all the rooms on that floor. The room combo box selections are
   * determined based on the selection from the floor combo box.
   */
  private void populateLocationComboBoxes() {
    List<Location> locations = locationDB.getAll();
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

  /**
   * Populates the equipment combo boxes, which are equipmentType and equipmentQuantity. Similarly
   * to populateLocationComboBoxes(), equipmentQuantity's selections are determined based on the
   * selection for equipmentType. equipmentQuantity's selections will be Integers from 1 to however
   * many Equipment objects of that type are available, or it will have no selections if none are
   * available.
   */
  private void populateEquipComboBoxes() {
    ArrayList<MedicalEquipment> allEquipment =
        (ArrayList<MedicalEquipment>) medEquipmentDB.getAll();
    ArrayList<String> equipmentNames = new ArrayList<String>();
    HashMap<String, Integer> equipNameToQuantity = new HashMap<>();
    for (MedicalEquipment e : allEquipment) {
      if (e.checkIsClean() && !e.isInUse()) {
        String curEquipName = e.getEquipmentType();
        if (!equipmentNames.contains(curEquipName)) {
          equipmentNames.add(e.getEquipmentType());
          equipNameToQuantity.put(curEquipName, 1);
        } else {
          equipNameToQuantity.put(curEquipName, equipNameToQuantity.get(curEquipName) + 1);
        }
      }
    }
    equipmentType.setItems(FXCollections.observableArrayList(equipmentNames));
    equipmentType
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ArrayList<Integer> quantityNums = new ArrayList<Integer>();
                for (int i = 1; i <= equipNameToQuantity.get(newValue); i++) {
                  quantityNums.add(i);
                }
                ObservableList<Integer> quantitySelections =
                    FXCollections.observableArrayList(quantityNums);
                equipmentQuantity.setItems(quantitySelections);
              }
            });
  }

  /**
   * Populates the medicalEquipmentRequest table from the database. Sets the cell values for each
   * column to be the corresponding variable in medicalEquipmentRequest.
   */
  private void populateMedEquipRequestTable() {
    ObservableList<medicalEquipmentRequest> medicalEquipmentRequests = populateMedEquipList();
    tableDeliveryDate.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("deliveryDate"));
    tableRequestDate.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("requestDate"));
    tableIsUrgent.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, Boolean>("isUrgent"));
    tableEquipmentType.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("equipment"));
    tableEquipmentQuantity.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, Integer>("equipmentQuantity"));
    tableStaffAssignee.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("staffAssignee"));
    tableRoomID.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("roomID"));
    tableFloorID.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("floorID"));
    tableRequestStatus.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("requestStatus"));
    tableOtherNotes.setCellValueFactory(
        new PropertyValueFactory<medicalEquipmentRequest, String>("otherNotes"));

    medRequestTable.setItems(medicalEquipmentRequests);
  }

  /**
   * Gets the full list of medical equipment requests currently in the database.
   *
   * @return list of medicalEquipmentRequest objects in the database
   */
  protected ObservableList<medicalEquipmentRequest> populateMedEquipList() {
    List<medicalEquipmentRequest> list = medEquipmentServiceRequestDB.getAll();
    tableList = FXCollections.observableArrayList();
    for (medicalEquipmentRequest m : list) {
      tableList.add(m);
    }
    return tableList;
  }

  @FXML
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      medicalEquipmentRequest.setFloorID(floor.getValue());
      medicalEquipmentRequest.setRoomID(room.getValue());
      medicalEquipmentRequest.setEquipment(equipmentType.getValue());
      medicalEquipmentRequest.setEquipmentQuantity(equipmentQuantity.getValue());
      medicalEquipmentRequest.setRequestStatus(requestStatus.getText());
      medicalEquipmentRequest.setStaffAssignee(staffAssignee.getText());
      medicalEquipmentRequest.setDeliveryDate(deliveryDate.getValue());
      medicalEquipmentRequest.setRequestDate(LocalDate.now());
      medicalEquipmentRequest.setUrgent(isUrgent.isSelected());
      medicalEquipmentRequest.setOtherNotes(notes.getText());
      medSendToDB(medicalEquipmentRequest);

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  @FXML
  private void resetButton(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    equipmentType.getSelectionModel().clearSelection();
    equipmentQuantity.getSelectionModel().clearSelection();
    deliveryDate.getEditor().clear();
    isUrgent.setSelected(false);
    requestStatus.clear();
    staffAssignee.clear();
    notes.clear();
  }

  private void medSendToDB(medicalEquipmentRequest medEquipmentRequest) throws SQLException {
    medEquipmentServiceRequestDB.addMedEquipReq(medEquipmentRequest);
    tableList.add(medEquipmentRequest);
    List<MedicalEquipment> equipmentUsed =
        medEquipmentDB.getMedicalEquipments(
            medEquipmentRequest.getEquipment(),
            medEquipmentRequest.getEquipmentQuantity(),
            medEquipmentRequest.getRoomID(),
            medEquipmentRequest.getServiceRequestID());

    if (medEquipmentRequest.getRequestStatus().equals("Done")) {
      medEquipmentDB.sendToCleaning(equipmentUsed);
    }
  }
}
