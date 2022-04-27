package edu.wpi.cs3733.D22.teamE.controllers;

import static edu.wpi.cs3733.D22.teamE.RSAEncryption.validatePassword;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.daos.DAO;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.EmployeeDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.pageControl;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class loginPageController implements Initializable {
  private @FXML JFXTextField usernameField;

  private @FXML JFXPasswordField passwordField;

  @FXML Label invalidWarning;

  private String validRFID = "9352CD1B";
  // private String invalidRFID = "";
  DAOSystem db;

  @FXML
  public void submitLogin(ActionEvent event) {
    if (verifyUser(getUsername(), getPassword()) || verifyUserRFID()) {
      // homePage
      pageControl.loadPage("homePage.fxml", (Stage) passwordField.getScene().getWindow());

    } else {
      invalidWarning.setVisible(true);
    }
  }

  private String getPassword() {
    return passwordField.getText();
  }

  private String getUsername() {
    return usernameField.getText();
  }

  private boolean verifyUser(String username, String password) {
    try {
      Account account = db.getAccount(username);
      System.out.println(account.getAccountID());
      System.out.println(account.getAuthorityLevel());
      if (!validatePassword(password, account.getPasswordHash())) {
        return false;
      } else {
        AccountsManager.getInstance().setAccount(account);
        DAO<Employee> employeeDAO = new EmployeeDAOImpl();
        if (account.getEmployeeID() != null && !Objects.equals(account.getEmployeeID(), "")) {
          try {
            Employee employee = employeeDAO.get(account.getEmployeeID());
            AccountsManager.getInstance().setEmployee(employee);
          } catch (NullPointerException e) {
            e.printStackTrace();
            return true;
          }
        }
      }
    } catch (SQLException
        | NullPointerException
        | IllegalBlockSizeException
        | BadPaddingException e) {
      e.printStackTrace();
      return false;
    }
    return true;
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
      usernameField.getValidators().add(validator);
      usernameField
          .focusedProperty()
          .addListener(
              (o, oldVal, newVal) -> {
                if (!newVal) usernameField.validate();
              });

      passwordField.getValidators().add(validator);
      passwordField
          .focusedProperty()
          .addListener(
              (o, oldVal, newVal) -> {
                if (!newVal) passwordField.validate();
              });
      invalidWarning.setVisible(false);
      usernameField.setOnKeyReleased(
          event -> {
            if (event.getCode() == KeyCode.ENTER) {
              if (passwordField.getText().isEmpty() || passwordField.getText().isBlank())
                passwordField.requestFocus();
              else submitLogin(new ActionEvent());
            }
          });
      passwordField.setOnKeyReleased(
          event -> {
            if (event.getCode() == KeyCode.ENTER) {
              if (usernameField.getText().isBlank() || passwordField.getText().isEmpty())
                usernameField.requestFocus();
              else submitLogin(new ActionEvent());
            }
          });
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
