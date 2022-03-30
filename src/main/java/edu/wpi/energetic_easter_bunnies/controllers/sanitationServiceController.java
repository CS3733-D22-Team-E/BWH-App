package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.sanitationRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class sanitationServiceController extends serviceRequestPageController {

  @FXML TextField locationField;
  @FXML ToggleGroup biohazardGroup;
  @FXML ToggleGroup urgencyGroup;
  @FXML ToggleGroup sizeGroup;

  public sanitationServiceController() {}

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

      if (locationField.getText().isEmpty() || locationField.getText().isBlank())
        throw new NullPointerException();
    } catch (NullPointerException error) {
      System.out.println(error.getMessage());
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }
}
