package edu.wpi.energetic_easter_bunnies.database;

import java.util.List;

public interface EmployeeDAO {
  List<Employee> getAllEmployees();

  Employee getEmployee(int numID);

  void updateEmployee(Employee employee);

  void deleteEmployee(Employee employee);
}
