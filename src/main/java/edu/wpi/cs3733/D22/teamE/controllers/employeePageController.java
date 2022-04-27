package edu.wpi.cs3733.D22.teamE.controllers;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamE.RSAEncryption;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import edu.wpi.cs3733.D22.teamE.entity.accounts.Account;
import edu.wpi.cs3733.D22.teamE.entity.accounts.adminAccount;
import edu.wpi.cs3733.D22.teamE.entity.accounts.staffAccount;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class employeePageController implements Initializable {

  @FXML TextField firstName;
  @FXML TextField lastName;
  @FXML TextField position;
  @FXML TextField employeeStatus;
  @FXML TextField salary;
  @FXML TextField Username;
  @FXML TextField Password;
  @FXML JFXButton submitButton;
  @FXML JFXButton resetButton;

  @FXML TableColumn tableEmployeeName;
  @FXML TableColumn tablePosition;
  @FXML TableColumn tableSalary;
  @FXML TableColumn tableUsername;

  DAOSystem system;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    system = DAOSystemSingleton.INSTANCE.getSystem();
    system.getAllEmployee();
  }

  public void submitButton(ActionEvent event) {
    try {
      Employee employee =
          new Employee(
              firstName.getText() + lastName.getText(),
              firstName.getText() + " " + lastName.getText(),
              position.getText(),
              Double.parseDouble(salary.getText()),
              "FDEPT00201",
              true,
              0);
      system.update(employee);
      Account account;
      if (position.getText() != "admin") {
        account =
            new staffAccount(
                Username.getText(),
                employee.getEmployeeID(),
                1,
                RSAEncryption.generatePasswordHASH(Password.getText()),
                firstName.getText(),
                lastName.getText(),
                position.getText());
      } else {
        account =
            new adminAccount(
                Username.getText(),
                employee.getEmployeeID(),
                3,
                RSAEncryption.generatePasswordHASH(Password.getText()),
                firstName.getText(),
                lastName.getText(),
                position.getText());
      }
      system.update(employee);
      system.update(account);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void resetButton(ActionEvent event) {
    firstName.clear();
    lastName.clear();
    position.clear();
    employeeStatus.clear();
    Username.clear();
    Password.clear();
    salary.clear();
  }
}
