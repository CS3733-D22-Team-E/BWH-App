package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.database.Location;
import edu.wpi.energetic_easter_bunnies.database.daos.LocationDAOImpl;
import edu.wpi.energetic_easter_bunnies.database.daos.MedicalEquipmentServiceRequestDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
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
import javafx.scene.control.cell.PropertyValueFactory;

public class medicalEquipmentController extends serviceRequestPageController {

  @FXML ComboBox<String> floor;
  @FXML ComboBox<String> room;
  @FXML ComboBox<String> equipmentType;
  @FXML ComboBox<Integer> equipmentQuantity;
  @FXML TextField deliveryDate;
  @FXML TextField deliveryTime;
  @FXML CheckBox isUrgent;
  @FXML TableView<medicalEquipmentRequest> medRequestTable;

  @FXML TableColumn<medicalEquipmentRequest, String> tableDeliveryDate;
  @FXML TableColumn<medicalEquipmentRequest, String> tableDeliveryTime;
  @FXML TableColumn<medicalEquipmentRequest, Boolean> tableIsUrgent;
  @FXML TableColumn<medicalEquipmentRequest, String> tableEquipmentType;
  @FXML TableColumn<medicalEquipmentRequest, Integer> tableEquipmentQuantity;
  @FXML TableColumn<medicalEquipmentRequest, String> tableStaffAssignee;
  @FXML TableColumn<medicalEquipmentRequest, String> tableRoomID;
  @FXML TableColumn<medicalEquipmentRequest, String> tableFloorID;
  @FXML TableColumn<medicalEquipmentRequest, String> tableRequestStatus;
  @FXML TableColumn<medicalEquipmentRequest, String> tableOtherNotes;

  MedicalEquipmentServiceRequestDAOImpl medEquipmentDB;
  medicalEquipmentRequest medicalEquipmentRequest = new medicalEquipmentRequest();

  LocationDAOImpl locationDB;

  public medicalEquipmentController() {}

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    super.initialize(url, rb);
    try {
      locationDB = new LocationDAOImpl();
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
                    ObservableValue<? extends String> observable,
                    String oldValue,
                    String newValue) {
                  ObservableList<String> roomsToDisplay =
                      FXCollections.observableArrayList((floorToRooms.get(newValue)));
                  room.setItems(roomsToDisplay);
                }
              });

      medEquipmentDB = new MedicalEquipmentServiceRequestDAOImpl();
      ObservableList<medicalEquipmentRequest> medicalEquipmentRequests = populateMedEquipList();
      tableDeliveryDate.setCellValueFactory(
          new PropertyValueFactory<medicalEquipmentRequest, String>("deliveryDate"));
      tableDeliveryTime.setCellValueFactory(
          new PropertyValueFactory<medicalEquipmentRequest, String>("deliveryTime"));
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
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  protected ObservableList<medicalEquipmentRequest> populateMedEquipList() {
    List<medicalEquipmentRequest> list = medEquipmentDB.getAllMedicalEquipmentServiceRequests();
    ObservableList<medicalEquipmentRequest> tableList = FXCollections.observableArrayList();
    for (medicalEquipmentRequest m : list) {
      tableList.add(m);
    }
    return tableList;
  }

  @FXML
  public void submitButton(ActionEvent event) {
    try {
      medicalEquipmentRequest.setFloorID(floor.getValue());
      medicalEquipmentRequest.setRoomID(room.getValue());
      medicalEquipmentRequest.setEquipment(equipmentType.getValue());
      medicalEquipmentRequest.setEquipmentQuantity(equipmentQuantity.getValue());
      medicalEquipmentRequest.setRequestStatus(requestStatus.getText());
      medicalEquipmentRequest.setStaffAssignee(staffAssignee.getText());
      medicalEquipmentRequest.setDeliveryDate(deliveryDate.getText());
      medicalEquipmentRequest.setDeliveryTime(deliveryTime.getText());
      medicalEquipmentRequest.setUrgent(isUrgent.isSelected());
      medicalEquipmentRequest.setOtherNotes(notes.getText());
      medSendToDB(medicalEquipmentRequest);

    } catch (NullPointerException | SQLException error) {
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
    deliveryDate.clear();
    deliveryTime.clear();
    isUrgent.setSelected(false);
    requestStatus.clear();
    staffAssignee.clear();
    notes.clear();
  }

  private void medSendToDB(medicalEquipmentRequest medEquipmentRequest) throws SQLException {
    medEquipmentDB.addMedEquipReq(medEquipmentRequest);
  }
}
