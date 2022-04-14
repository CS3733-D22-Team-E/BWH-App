package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.entity.languageInterpreterRequest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

public class languageInterpreterRequestController extends serviceRequestPageController
    implements Initializable {

  @FXML private ChoiceBox<String> languageSelection;
  private String[] langList = {"English", "Spanish", "Russian", "Mandarin Chinese"};

  @FXML TextField noteField;
  @FXML DatePicker startSelection;
  @FXML DatePicker endSelection;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);

    languageSelection.setValue("Select Language");
    languageSelection.getItems().addAll(langList);
  }

  languageInterpreterRequest languageInterpreterRequest = new languageInterpreterRequest();

  public languageInterpreterRequestController() {}

  @FXML
  public void submitButton(ActionEvent event) {
    try {
      /*      languageInterpreterRequest.setFloorSelected(floorSelection.getValue());
      languageInterpreterRequest.setLanguageSelected(languageSelection.getValue());
      languageInterpreterRequest.setRoomSelected(roomSelection.getValue());
      languageInterpreterRequest.setEndDate(endSelection.getValue());
      languageInterpreterRequest.setStartDate(startSelection.getValue());
      languageInterpreterRequest.setOtherNotes(notes.getText());*/

    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning("Warning : A required value was not filled", (Node) event.getSource());
    }
  }

  @FXML
  private void resetButton(ActionEvent event) {
    floor.getSelectionModel().clearSelection();
    languageSelection.getSelectionModel().clearSelection();
    room.getSelectionModel().clearSelection();
    floor.setValue("Select Floor");
    room.setValue("Select Room");
    languageSelection.setValue("Select Language");
    startSelection.getEditor().clear();
    endSelection.getEditor().clear();
    notes.clear();
  }
}