package edu.wpi.energetic_easter_bunnies.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
  List<Employee> employees;

  public EmployeeDAOImpl() throws SQLException {
    employees = new ArrayList<>();
    String url = "jdbc:derby:myDB;";
    Connection connection = DriverManager.getConnection(url);
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM EMPLOYEES ORDER BY SALARY DESC";
    ResultSet rs = statement.executeQuery(query);
    int numID = 0;
    while (rs.next()) {
      String employeeID = rs.getString("EMPLOYEEID");
      String name = rs.getString("NAME");
      double salary = rs.getDouble("SALARY");
      String location = rs.getString("LOCATION");
      String position = rs.getString("POSITION");
      boolean available = rs.getBoolean("AVAILABLE");

      Employee employee =
          new Employee(employeeID, name, position, salary, location, available, numID);

      employees.add(employee);
      numID++;
    }
    rs.close();
  }

  @Override
  public List<Employee> getAllEmployees() {
    return employees;
  }

  @Override
  public Employee getEmployee(int numID) {
    return employees.get(numID);
  }

  @Override
  public void updateEmployee(Employee employee) {
    employees.add(employee);
  }

  @Override
  public void deleteEmployee(Employee employee) {
    employees.remove(employee);
  }
}
