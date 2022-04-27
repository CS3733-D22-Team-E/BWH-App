package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.cs3733.D22.teamE.Texting;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.daos.DAO;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.EmployeeDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.pageControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

import static edu.wpi.cs3733.D22.teamE.RSAEncryption.validatePassword;

public class twoFacAuthPageController implements Initializable {

  private @FXML JFXTextField authenticationField;

  @FXML Label invalidWarning;

  DAOSystem db;

  @FXML
  public void twoFAButton(ActionEvent event) {
    if (verifyUser2FA(getAuthenticationCode())) {//verifyUser(getUsername(), getPassword()) || verifyUserRFID()) {
      pageControl.loadPage("BasePage.fxml", (Stage) authenticationField.getScene().getWindow());

    } else {
      invalidWarning.setVisible(true);
    }
  }

  private String getAuthenticationCode() {
    return authenticationField.getText();
  }

  private boolean verifyUserRFID() {
    //    System.out.println("In verifyUserRFID()");
    //    String data = com.readData();
    //    System.out.println("Arduino Data: " + data);
    //    if (!data.equals("")) {
    //      System.out.println("Access Granted.");
    //      return true;
    //    } else {
    //      System.out.println("Access Denied.");
    //      return false;
    //    }
    return false;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    RequiredFieldValidator validator = new RequiredFieldValidator();
    validator.setMessage("Input Required");
    try {
      db = new DAOSystem();
      authenticationField.getValidators().add(validator);
      authenticationField
          .focusedProperty()
          .addListener(
              (o, oldVal, newVal) -> {
                if (!newVal) authenticationField.validate();
              });

      invalidWarning.setVisible(false);
      authenticationField.setOnKeyReleased(
          event -> {
            if (event.getCode() == KeyCode.ENTER) {
              if (authenticationField.getText().isEmpty() || authenticationField.getText().isBlank())
                authenticationField.requestFocus();
              else twoFAButton(new ActionEvent());
            }
          });

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
