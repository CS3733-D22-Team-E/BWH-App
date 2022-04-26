package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.RSAEncryption;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.entity.passwordSettingRequest;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class passwordSettingPageController {
  @FXML JFXTextField newPassword;
  @FXML JFXTextField confirmNewPassword;
  @FXML JFXButton submitButton;

  private DAOSystem db;

  public passwordSettingPageController() {
    try {
      db = new DAOSystem();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  /**
   * Takes the inputs from the buttons, drop downs, text fields etc. and stores that data in the
   * mealDeliveryRequest object.
   *
   * @param event Pressing the submitButton
   */
  public void submitButton(ActionEvent event) throws SQLException {
    try {
      passwordSettingRequest passwordSettingRequest = new passwordSettingRequest();
      passwordSettingRequest.setNewPassword(newPassword.getText());
      passwordSettingRequest.setConfirmNewPassword(confirmNewPassword.getText());

      submitPasswordChange(passwordSettingRequest);
    } catch (NullPointerException error) {
      System.out.println("Error : Some Value is NULL");
      PopUp.createWarning(
          "Warning : A required value was not filled", newPassword.getScene().getWindow());
    }
  }

  private void submitPasswordChange(passwordSettingRequest r) {

    String password = r.getNewPassword();
    String confirmation = r.getConfirmNewPassword();
    System.out.println(password);
    System.out.println(confirmation);
    if (!password.equals(confirmation)) {
      PopUp.createWarning(
          "Warning : Confirmation Doesn't Match Password!", newPassword.getScene().getWindow());
    } else {
      try {
        Account account = AccountsManager.getInstance().getAccount();
        account.setPasswordHash(RSAEncryption.generatePasswordHASH(r.getNewPassword()));
        db.update(account);
      } catch (Exception e) {
        e.printStackTrace();
        PopUp.createWarning(
            "Error: password change not successful", newPassword.getScene().getWindow());
      }
      PopUp.createWarning(
          "Congrats! you have reset your password successfully",
          newPassword.getScene().getWindow());
      newPassword.getScene().getWindow().hide();
    }
  }
}
