package edu.wpi.cs3733.D22.teamE.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystem;
import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
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
  @FXML TextField submitButton;
  @FXML TextField resetButton;


  @FXML TableColumn tableEmployeeName;
  @FXML TableColumn tablePosition;
  @FXML TableColumn tableSalary;
  @FXML TableColumn tableUsername;

  DAOSystem system;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    system = DAOSystemSingleton.INSTANCE.getSystem();

    system.getAllEmployee();
    
    Employee employee = new Employee()

  }
}
