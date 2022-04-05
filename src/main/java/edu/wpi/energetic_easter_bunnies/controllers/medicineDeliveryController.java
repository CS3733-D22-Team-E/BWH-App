package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.medicineDelivery;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class medicineDeliveryController extends serviceRequestPageController
    implements Initializable {

  @FXML ComboBox<String> floor;
  @FXML ComboBox<String> room;
  @FXML ComboBox<String> medicine;
  @FXML TextField amount;
  @FXML DatePicker date;
  @FXML TextField time;
  @FXML CheckBox mon;
  @FXML CheckBox tues;
  @FXML CheckBox wed;
  @FXML CheckBox thurs;
  @FXML CheckBox fri;
  @FXML CheckBox sat;
  @FXML CheckBox sun;
  @FXML Button resetButton;

  medicineDelivery medicineDeliveryRequest = new medicineDelivery();

  ObservableList<String> floors =
      FXCollections.observableArrayList(
          "Ground Floor", "First Floor", "Second Floor", "Third Floor");
  ObservableList<String> rooms = FXCollections.observableArrayList("101", "102", "104", "105");
  ObservableList<String> medicines =
      FXCollections.observableArrayList(
          "Halothane", "Isoflurane", "Propofol", "midazolam", "ibuprofen");

  public medicineDeliveryController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    floor.setItems(floors);
    room.setItems(rooms);
    medicine.setItems(medicines);
  }

  @FXML
  public void submitButton(ActionEvent event) {
    try {
      // medicalEquipmentRequest.setLocation(building.getValue() + floor.getValue());
      medicineDeliveryRequest.setFloorID(String.valueOf(floor.getItems()));
      medicineDeliveryRequest.setRoomID(room.getValue());
      medicineDeliveryRequest.setAmount(amount.getText());
      medicineDeliveryRequest.setMedicine(String.valueOf(medicine.getItems()));
      // medicineDeliveryRequest.setDeliveryDate(String.valueOf(date.getValue()));
      medicineDeliveryRequest.setDeliveryTime(time.getText());
      medicineDeliveryRequest.setMon(mon.isSelected());
      medicineDeliveryRequest.setTues(tues.isSelected());
      medicineDeliveryRequest.setWed(wed.isSelected());
      medicineDeliveryRequest.setThurs(thurs.isSelected());
      medicineDeliveryRequest.setFri(fri.isSelected());
      medicineDeliveryRequest.setSat(sat.isSelected());
      medicineDeliveryRequest.setSun(sun.isSelected());
      medicineDeliveryRequest.setOtherNotes(notes.getText());

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  @FXML
  private void resetButton(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    medicine.getSelectionModel().clearSelection();
    amount.clear();
    date.getEditor().clear();
    time.clear();
    mon.setSelected(false);
    tues.setSelected(false);
    wed.setSelected(false);
    thurs.setSelected(false);
    fri.setSelected(false);
    sat.setSelected(false);
    sun.setSelected(false);
    notes.clear();
  }
}
