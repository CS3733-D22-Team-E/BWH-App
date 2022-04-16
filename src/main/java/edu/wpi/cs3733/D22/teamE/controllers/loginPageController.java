package edu.wpi.cs3733.D22.teamE.controllers;

import static edu.wpi.cs3733.D22.teamE.RSAEncryption.validatePassword;
import static edu.wpi.cs3733.D22.teamE.entity.loginPage.verifyUser;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import edu.wpi.cs3733.D22.teamE.Main;
import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.Employee;
import edu.wpi.cs3733.D22.teamE.database.daos.AccountDAOImpl;
import edu.wpi.cs3733.D22.teamE.database.daos.DAO;
import edu.wpi.cs3733.D22.teamE.database.daos.EmployeeDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class loginPageController implements Initializable {
  private @FXML JFXTextField usernameField;

  private @FXML JFXPasswordField passwordField;

  @FXML Label invalidWarning;

  @FXML
  public void submitLogin(ActionEvent event) {
    if (verifyUser(getUsername(), getPassword())) {

      FXMLLoader loader = new FXMLLoader();

      URL url = Main.class.getResource("view/defaultPage.fxml");
      if (url != null) {
        loader.setLocation(url);
        Parent newRoot = null;
        try {
          newRoot = loader.load();
          passwordField.getScene().setRoot(newRoot);
        } catch (IOException | NullPointerException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Path Doesn't Exist");
      }
    } else {
      invalidWarning.setVisible(true);
    }
  }

  public void submitLoginNoParam() {
    if (verifyUser(getUsername(), getPassword())) {

      FXMLLoader loader = new FXMLLoader();

      URL url = Main.class.getResource("view/defaultPage.fxml");
      if (url != null) {
        loader.setLocation(url);
        Parent newRoot = null;
        try {
          newRoot = loader.load();
          passwordField.getScene().setRoot(newRoot);
        } catch (IOException | NullPointerException e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Path Doesn't Exist");
      }
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
      DAO<Account> accountDAO = new AccountDAOImpl();
      Account account = accountDAO.get(username);
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    RequiredFieldValidator validator = new RequiredFieldValidator();
    validator.setMessage("Input Required");
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
          if (event.getCode() == KeyCode.ENTER) passwordField.requestFocus();
        });
    passwordField.setOnKeyReleased(
        event -> {
          if (event.getCode() == KeyCode.ENTER) submitLoginNoParam();
        });
  }
}
