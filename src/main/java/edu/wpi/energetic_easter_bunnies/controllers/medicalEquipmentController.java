package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.database.MedicalEquipmentServiceRequestDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class medicalEquipmentController extends serviceRequestPageController
    implements Initializable {

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

  public medicalEquipmentController() {}

  public void initialize(URL url, ResourceBundle rb) {
    try {
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
      medicalEquipmentRequest.setDeliveryDate(deliveryDate.getText());
      medicalEquipmentRequest.setDeliveryTime(deliveryTime.getText());
      medicalEquipmentRequest.setUrgent(isUrgent.isSelected());
      medicalEquipmentRequest.setOtherNotes(notes.getText());
      sendToDB(medicalEquipmentRequest);

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
