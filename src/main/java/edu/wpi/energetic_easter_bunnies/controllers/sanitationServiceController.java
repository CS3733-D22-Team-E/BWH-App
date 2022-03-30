package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.SanitationRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class sanitationServiceController extends ServiceRequestPageController {

  @FXML TextField locationField;
  @FXML ToggleGroup biohazardGroup;
  @FXML ToggleGroup urgencyGroup;
  @FXML ToggleGroup sizeGroup;

  public sanitationServiceController() {}

  @FXML
  public void submitButton(ActionEvent event) {
    SanitationRequest request = new SanitationRequest();
    try {
      RadioButton selectBiohazard = (RadioButton) biohazardGroup.getSelectedToggle();
      switch (selectBiohazard.getText()) {
        case "Yes":
          request.setBiohazardOnSite(SanitationRequest.Biohazard.Yes);
          break;
        case "No":
          request.setBiohazardOnSite(SanitationRequest.Biohazard.No);
          break;
        case "Unsure":
          request.setBiohazardOnSite(SanitationRequest.Biohazard.Unsure);
          break;
      }

      RadioButton selectSize = (RadioButton) sizeGroup.getSelectedToggle();
      switch (selectSize.getText()) {
        case "Light":
          request.setSizeOfCleaning(SanitationRequest.Size.Light);
          break;
        case "Medium":
          request.setSizeOfCleaning(SanitationRequest.Size.Medium);
          break;
        case "Heavy":
          request.setSizeOfCleaning(SanitationRequest.Size.Heavy);
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
