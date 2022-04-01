package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.medicineDelivery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class medicineDeliveryController extends serviceRequestPageController {

  @FXML MenuButton floor;
  @FXML MenuButton room;
  @FXML MenuButton medicine;
  @FXML TextField amount;
  @FXML TextField date;
  @FXML TextField time;
  @FXML CheckBox mon;
  @FXML CheckBox tues;
  @FXML CheckBox wed;
  @FXML CheckBox thurs;
  @FXML CheckBox fri;
  @FXML CheckBox sat;
  @FXML CheckBox sun;

  medicineDelivery medicineDeliveryRequest = new medicineDelivery();

  public medicineDeliveryController() {}

  @FXML
  public void submitButton(ActionEvent event) {
    try {
      // medicalEquipmentRequest.setLocation(building.getValue() + floor.getValue());
      medicineDeliveryRequest.setFloorID(String.valueOf(floor.getItems()));
      medicineDeliveryRequest.setAmount(amount.getText());
      medicineDeliveryRequest.setMedicine(String.valueOf(medicine.getItems()));
      medicineDeliveryRequest.setDeliveryDate(date.getText());
      medicineDeliveryRequest.setDeliveryTime(time.getText());
      medicineDeliveryRequest.setMon(mon.isSelected());
      medicineDeliveryRequest.setTues(tues.isSelected());
      medicineDeliveryRequest.setWed(wed.isSelected());
      medicineDeliveryRequest.setThurs(thurs.isSelected());
      medicineDeliveryRequest.setFri(fri.isSelected());
      medicineDeliveryRequest.setSat(sat.isSelected());
      medicineDeliveryRequest.setSun(sun.isSelected());

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }
}
