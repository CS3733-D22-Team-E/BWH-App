package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import entity.sanitationRequest;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class sanitationServiceController {

  FXMLLoader loader = new FXMLLoader();
  Parent root;

  @FXML ToggleGroup biohazardGroup;
  @FXML ToggleGroup urgencyGroup;
  @FXML ToggleGroup sizeGroup;
  @FXML TextField locationField;
  @FXML TextField Notes;
  @FXML MenuBar menuBar;
  @FXML Button submitButton;

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

      request.setAdditionalNotes(Notes.getText());
    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  @FXML
  private void homeButton(ActionEvent event) throws IOException {
    Node node = (Node) event.getSource();
    Stage thisStage = (Stage) node.getScene().getWindow();

    loader.setLocation(getClass().getResource("../view/defaultPage.fxml"));
    root = loader.load();

    thisStage.setScene(new Scene(root));
  }
}
