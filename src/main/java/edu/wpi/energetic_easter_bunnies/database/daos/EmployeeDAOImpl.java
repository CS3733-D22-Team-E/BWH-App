package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.database.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements DAO<Employee> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<Employee> employees;

  public EmployeeDAOImpl() throws SQLException {
    employees = new ArrayList<>();
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM EMPLOYEES ORDER BY SALARY DESC";
    ResultSet rs = statement.executeQuery(query);
    int numID = 0;
    while (rs.next()) {
      String employeeID = rs.getString("EMPLOYEEID");
      String name = rs.getString("NAME");
      String locationID = rs.getString("LOCATIONID");
      String position = rs.getString("POSITION");
      boolean available = rs.getBoolean("AVAILABLE");
      double salary = rs.getDouble("SALARY");

      Employee employee =
          new Employee(employeeID, name, position, salary, locationID, available, numID);

      employees.add(employee);
      numID++;
    }
    rs.close();
  }

  @Override
  public List<Employee> getAll() {
    return employees;
  }

  @Override
  public Employee get(String id) {
    for (Employee employee : employees) {
      if (employee.getEmployeeID().equals(id)) return employee;
    }
    System.out.println("Employee with EmployeeID " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(Employee employee) {
    employees.add(employee);
    try {
      Statement statement = connection.createStatement();
      String query =
          "INSERT INTO EMPLOYEES (EMPLOYEEID, NAME , LOCATIONID, POSITION, AVAILABLE, SALARY) VALUES ('"
              + employee.getEmployeeID()
              + "','"
              + employee.getName()
              + "','"
              + employee.getLocation()
              + "','"
              + employee.getPosition()
              + "',"
              + employee.getAvailable()
              + ","
              + employee.getSalary()
              + ")"; // Insert into database; does not check if the employeeID already exists
      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(Employee employee) {
    employees.remove(employee);
    try {
      Statement statement = connection.createStatement();
      String query =
          "DELETE FROM EMPLOYEES WHERE EMPLOYEEID = ('" + employee.getEmployeeID() + "')";
      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
