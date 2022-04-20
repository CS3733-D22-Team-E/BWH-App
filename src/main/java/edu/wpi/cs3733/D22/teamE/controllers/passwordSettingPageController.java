package edu.wpi.cs3733.D22.teamE.controllers;

import edu.wpi.cs3733.D22.teamE.PopUp;
import edu.wpi.cs3733.D22.teamE.RSAEncryption;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.daos.AccountDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.DAO;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.entity.passwordSettingRequest;
import edu.wpi.cs3733.D22.teamE.pageControlFacade;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class passwordSettingPageController extends containsSideMenu {
  @FXML TextField newPassword;
  @FXML TextField confirmNewPassword;
  @FXML Button returnButton;
  passwordSettingRequest passwordSettingRequest = new passwordSettingRequest();

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
    if (newPassword.getText() != confirmNewPassword.getText()) {
      PopUp.createWarning(
          "Warning : Confirmation Doesn't Match Password!", drawer.getScene().getWindow());
    } else {
      try {
        Account account = AccountsManager.getInstance().getAccount();
        account.setPasswordHash(RSAEncryption.generatePasswordHASH(newPassword.getText()));
        DAO<Account> accountDAO = new AccountDAOImpl();
        accountDAO.delete(account);
        accountDAO.update(account);
      } catch (Exception e) {
        e.printStackTrace();
        PopUp.createWarning("Error: password change not successful", drawer.getScene().getWindow());
      }
      PopUp.createWarning(
          "Congrats! you have reset your password successfully", drawer.getScene().getWindow());
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

  @FXML
  public void returnButton(ActionEvent event) throws IOException {
    Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    pageControlFacade.loadPage("profilePage.fxml", thisStage);
    // pageControlFacade.loadPage("helpPage.fxml", thisStage);
  }
}
