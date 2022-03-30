package edu.wpi.energetic_easter_bunnies.controllers;

import edu.wpi.energetic_easter_bunnies.PopUpWarning;
import edu.wpi.energetic_easter_bunnies.entity.languageInterpreterRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;



public class languageInterpreterRequestController extends ServiceRequestPageController {
    FXMLLoader loader = new FXMLLoader();
    Parent root;

    @FXML ChoiceBox floorSelection;
    @FXML ChoiceBox roomSelection;
    @FXML ChoiceBox languageSelection;
    @FXML TextField noteField;
    @FXML ChoiceBox startSelection;
    @FXML ChoiceBox endSelection;

    languageInterpreterRequest languageInterpreterRequest = new languageInterpreterRequest();

    public languageInterpreterRequestController() {}

    @FXML
    public void submitButton(ActionEvent event) {
        try {
            //languageInterpreterRequest.setfloor(floorSelection.getValue());
            //languageInterpreterRequest.setRoomNumber(roomSelection.getValue());
            //languageInterpreterRequest.setLang(languageSelection.getValue());
            //languageInterpreterRequest.setStartTime(startSelection.getValue());
            //languageInterpreterRequest.setEndTime(endSelection.getValue());
            languageInterpreterRequest.setOtherNotes(notes.getText());
        } catch (NullPointerException error) {
            System.out.println("Error : Some Value is NULL");
            PopUpWarning.createWarning("Warning : A required value was not filled");
        }
    }
}
