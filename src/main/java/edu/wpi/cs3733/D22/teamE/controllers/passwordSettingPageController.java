package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.PopUp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.sql.SQLException;
import edu.wpi.cs3733.D22.teamE.entity.passwordSettingRequest;
import javafx.scene.control.TextField;
public class passwordSettingPageController extends serviceRequestPageController{
    @FXML TextField newPassword;
    @FXML TextField confirmNewPassword;


    passwordSettingRequest  passwordSettingRequest = new passwordSettingRequest();
    public passwordSettingPageController() {}
    /**
     * Takes the inputs from the buttons, drop downs, text fields etc. and stores that data in the
     * mealDeliveryRequest object.
     *
     * @param event Pressing the submitButton
     */

    public void submitButton(ActionEvent event) throws SQLException {
        try {
            passwordSettingRequest.setNewPassword(newPassword.getText());
            passwordSettingRequest.setConfirmNewPassword(confirmNewPassword.getText());

        } catch (NullPointerException error) {
            System.out.println("Error : Some Value is NULL");
            PopUp.createWarning(
                    "Warning : A required value was not filled", drawer.getScene().getWindow());
        }
    }

    /**
     * clears all of the inputs on the page.
     *
     * @param event Pressing the resetButton
     */
    @FXML
    private void resetButton(ActionEvent event) {
        newPassword.clear();
        confirmNewPassword.clear();
    }
}


