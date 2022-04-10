package edu.wpi.energetic_easter_bunnies.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.sanitationRequest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class sanitationServiceController extends serviceRequestPageController {

  @FXML JFXComboBox<String> floor;
  @FXML JFXComboBox<String> room;
  @FXML RadioButton mediumSelect;
  @FXML RadioButton heavySelect;
  @FXML RadioButton lightSelect;
  @FXML RadioButton notUrgent;
  @FXML RadioButton Urgent;
  @FXML RadioButton bioNo;
  @FXML RadioButton bioUnsure;
  @FXML RadioButton bioYes;
  @FXML JFXHamburger burger;
  @FXML JFXDrawer drawer;
  @FXML ToggleGroup biohazardGroup;
  @FXML ToggleGroup urgencyGroup;
  @FXML ToggleGroup sizeGroup;

  public sanitationServiceController() {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
  }

  @FXML
  public void submitButton(ActionEvent event) {
    sanitationRequest request = new sanitationRequest();
    try {
      RadioButton selectBiohazard = (RadioButton) biohazardGroup.getSelectedToggle();
      switch (selectBiohazard.getText()) {
        case "Yes":
          request.setBiohazardOnSite(sanitationRequest.Biohazard.Yes);
          break;
        case "No":
          request.setBiohazardOnSite(sanitationRequest.Biohazard.No);
          break;
        case "Unsure":
          request.setBiohazardOnSite(sanitationRequest.Biohazard.Unsure);
          break;
      }

      RadioButton selectSize = (RadioButton) sizeGroup.getSelectedToggle();
      switch (selectSize.getText()) {
        case "Light":
          request.setSizeOfCleaning(sanitationRequest.Size.Light);
          break;
        case "Medium":
          request.setSizeOfCleaning(sanitationRequest.Size.Medium);
          break;
        case "Heavy":
          request.setSizeOfCleaning(sanitationRequest.Size.Heavy);
          break;
      }

      RadioButton selectUrgency = (RadioButton) urgencyGroup.getSelectedToggle();
      switch (selectUrgency.getText()) {
        case "Critical":
          request.setUrgent(true);
          break;
        case "NotCritical":
          request.setUrgent(false);
          break;
      }

      request.setOtherNotes(notes.getText());

      if (staffAssignee.getText().isEmpty() || staffAssignee.getText().isBlank())
        throw new NullPointerException();
      else request.setStaffAssignee(staffAssignee.getText());

      request.setRequestStatus("To Do");

      request.setFloorID(floor.getValue());
      request.setRoomID(room.getValue());

    } catch (NullPointerException error) {
      System.out.println(error.getMessage());
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  @FXML
  public void resetFields(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    sizeGroup.selectToggle(lightSelect);
    biohazardGroup.selectToggle(bioNo);
    urgencyGroup.selectToggle(notUrgent);
    notes.clear();
    staffAssignee.clear();
  }
}
