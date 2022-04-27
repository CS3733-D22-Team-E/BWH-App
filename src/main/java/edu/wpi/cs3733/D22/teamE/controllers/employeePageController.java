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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class employeePageController implements Initializable {

  @FXML TextField firstName;
  @FXML TextField lastName;
  @FXML TextField position;
  @FXML TextField employeeStatus;
  @FXML TextField salary;
  @FXML TextField phoneNumber;

  @FXML TextField Username;
  @FXML TextField Password;
  @FXML JFXButton submitButton;
  @FXML JFXButton resetButton;

  @FXML TableView<Employee> employeeTable;
  @FXML TableColumn<Employee, String> tableEmployeeName;
  @FXML TableColumn<Employee, String> tablePosition;
  @FXML TableColumn<Employee, Double> tableSalary;
  @FXML TableColumn<Employee, String> tableUsername;

  DAOSystem system;
  ObservableList<Employee> tableList;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    system = DAOSystemSingleton.INSTANCE.getSystem();
    populateEmployeeTable();
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

  private void populateEmployeeTable() {
    ObservableList<Employee> employees = populateEmployeeList();
    tableEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tablePosition.setCellValueFactory(new PropertyValueFactory<>("position"));
    tableSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    tableUsername.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
    employeeTable.setItems(employees);
  }

  protected ObservableList<Employee> populateEmployeeList() {
    List<Employee> list = system.getAllEmployee();
    tableList = FXCollections.observableArrayList();
    for (Employee l : list) {
      tableList.add(l);
    }
    return tableList;
  }
}
