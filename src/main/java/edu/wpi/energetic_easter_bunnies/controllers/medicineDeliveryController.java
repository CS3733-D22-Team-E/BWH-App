package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.database.medicineDelivery;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

/**
 * Controller Class for the Medicine Delivery Service Request. Inherits from the
 * serviceRequestController super class.
 */
public class medicineDeliveryController extends serviceRequestPageController
    implements Initializable {

  @FXML ComboBox<String> medicine;
  @FXML TextField amount;
  @FXML ComboBox<String> unit;
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

  /** Creating a medicineDeliveryRequest object to store the inputted data in. */
  medicineDelivery medicineDeliveryRequest = new medicineDelivery();

  /** Creating the ObservableList of medicines and units for the drop downs. */
  ObservableList<String> medicines =
      FXCollections.observableArrayList(
          "Halothane", "Isoflurane", "Propofol", "midazolam", "ibuprofen");

  ObservableList<String> units = FXCollections.observableArrayList("mg", "g", "mL");

  /** Constructor */
  public medicineDeliveryController() {}

  /**
   * Initializes the drops downs with the respective observable lists and the table columns with the
   * values from current service requests.
   *
   * @param location ??
   * @param resources ??
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    medicine.setItems(medicines);
    unit.setItems(units);
  }

  /**
   * Takes the inputs from the buttons, drop downs, text fields etc. and stores that data in the
   * mealDeliveryRequest object.
   *
   * @param event Pressing the submitButton
   */
  @FXML
  public void submitButton(ActionEvent event) {
    try {
      // medicalEquipmentRequest.setLocation(building.getValue() + floor.getValue());
      medicineDeliveryRequest.setFloorID(String.valueOf(floor.getItems()));
      medicineDeliveryRequest.setRoomID(room.getValue());
      medicineDeliveryRequest.setAmount(amount.getText());
      medicineDeliveryRequest.setMedicine(String.valueOf(medicine.getItems()));
      // medicineDeliveryRequest.setDeliveryDate(String.valueOf(date.getValue()));
      medicineDeliveryRequest.setUnit(String.valueOf(unit.getItems()));
      medicineDeliveryRequest.setDeliveryTime(time.getText());
      medicineDeliveryRequest.setMon(mon.isSelected());
      medicineDeliveryRequest.setTues(tues.isSelected());
      medicineDeliveryRequest.setWed(wed.isSelected());
      medicineDeliveryRequest.setThurs(thurs.isSelected());
      medicineDeliveryRequest.setFri(fri.isSelected());
      medicineDeliveryRequest.setSat(sat.isSelected());
      medicineDeliveryRequest.setSun(sun.isSelected());
      medicineDeliveryRequest.setOtherNotes(notes.getText());
      medicineDeliveryRequest.setRequestDate(LocalDate.now());

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  /**
   * clears all of the inputs on the page.
   *
   * @param event Pressing the resetButton
   */
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
