package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.languageInterpreterRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class languageInterpreterRequestController extends serviceRequestPageController
    implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      super.initialize(location,resources);
       /*floorSelection.setValue("Select Floor");
      roomSelection.setValue("Select Room");
      languageSelection.setValue("Select Language");
      floorSelection.getItems().addAll(floorList);
      roomSelection.getItems().addAll(roomList);
      languageSelection.getItems().addAll(langList);*/
    }

  @FXML private ChoiceBox<String> floorSelection;

  private String[] floorList = {"Ground", "First Floor", "Second Floor", "Third Floor"};
  @FXML private ChoiceBox<String> roomSelection;
  private String[] roomList = {"101", "102", "103", "104"};

  @FXML private ChoiceBox<String> languageSelection;
  private String[] langList = {"English", "Spanish", "Russian", "Mandarin Chinese"};

  @FXML TextField noteField;
  @FXML DatePicker startSelection;
  @FXML DatePicker endSelection;

  languageInterpreterRequest languageInterpreterRequest = new languageInterpreterRequest();

  public languageInterpreterRequestController() {}

  @FXML
  public void submitButton(ActionEvent event) {
    try {
      /* languageInterpreterRequest.setFloorSelected(floorSelection.getValue());
      languageInterpreterRequest.setLanguageSelected(languageSelection.getValue());
      languageInterpreterRequest.setRoomSelected(roomSelection.getValue());
      languageInterpreterRequest.setEndDate(endSelection.getValue());
      languageInterpreterRequest.setStartDate(startSelection.getValue());
      languageInterpreterRequest.setOtherNotes(notes.getText());*/

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUpWarning.createWarning("Warning : A required value was not filled");
    }
  }

  @FXML
  private void resetButton(ActionEvent event) {
    floorSelection.getSelectionModel().clearSelection();
    languageSelection.getSelectionModel().clearSelection();
    roomSelection.getSelectionModel().clearSelection();
    floorSelection.setValue("Select Floor");
    roomSelection.setValue("Select Room");
    languageSelection.setValue("Select Language");
    startSelection.getEditor().clear();
    endSelection.getEditor().clear();
    notes.clear();
  }
}
