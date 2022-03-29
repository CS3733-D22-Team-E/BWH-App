package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import entity.sanitationRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class sanitationServiceController extends ServiceRequestPageController {

  @FXML TextField locationField;
  @FXML ToggleGroup biohazardGroup;
  @FXML ToggleGroup urgencyGroup;
  @FXML ToggleGroup sizeGroup;

  sanitationRequest request = new sanitationRequest();

  public sanitationServiceController() {}

  @FXML
  public void submitButton(ActionEvent event) {
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
          request.setUrgency(sanitationRequest.Critical.Critical);
          break;
        case "NotCritical":
          request.setUrgency(sanitationRequest.Critical.NotCritical);
          break;
      }

      String loc = locationField.getText();
      if (loc.isEmpty()) throw new NullPointerException();
      request.setLocation(loc);

      request.setAdditionalNotes(notes.getText());
    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }
}
