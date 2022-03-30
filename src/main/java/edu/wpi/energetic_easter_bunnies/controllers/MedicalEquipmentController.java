package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.MedicalEquipmentRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class MedicalEquipmentController extends ServiceRequestPageController {

  @FXML ComboBox<String> floor;
  @FXML ComboBox<String> room;
  @FXML ComboBox<String> equipmentType;
  @FXML ComboBox<Integer> equipmentQuantity;
  @FXML TextField deliveryDate;
  @FXML TextField deliveryTime;
  @FXML CheckBox isUrgent;

  MedicalEquipmentRequest medicalEquipmentRequest = new MedicalEquipmentRequest();

  public MedicalEquipmentController() {}

  @FXML
  public void submitButton(ActionEvent event) {
    try {
      // medicalEquipmentRequest.setLocation(building.getValue() + floor.getValue());
      medicalEquipmentRequest.setEquipment(equipmentType.getValue());
      medicalEquipmentRequest.setEquipmentQuantity(equipmentQuantity.getValue());
      medicalEquipmentRequest.setDeliveryDate(deliveryDate.getText());
      medicalEquipmentRequest.setDeliveryTime(deliveryTime.getText());
      medicalEquipmentRequest.setUrgent(isUrgent.isSelected());
      medicalEquipmentRequest.setOtherNotes(notes.getText());
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
    notes.clear();
  }
}
