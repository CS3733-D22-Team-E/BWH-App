package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements DAO<Employee> {
  static Connection connection = AccountsManager.getInstance().getConnection();
  List<Employee> employees;

  public EmployeeDAOImpl() throws SQLException {
    employees = new ArrayList<>();
    String query = "SELECT * FROM EMPLOYEES ORDER BY SALARY DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();
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
    employees = new ArrayList<>();

    try {
      String query = "SELECT * FROM EMPLOYEES ORDER BY SALARY DESC";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
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
    } catch (SQLException e) {
      System.out.println("Get All Failed!");
      e.printStackTrace();
    }

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
    if (connection == DBConnect.EMBEDDED_INSTANCE.getConnection()) {
      System.out.println("eb");
    } else {
      System.out.println("cb");
    }
    try {
      delete(employee);
    } catch (NullPointerException e) {
      System.out.println("Not Found");
    }
    employees.add(employee);
    try {
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
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void delete(Employee employee) {
    try {
      get(employee.getEmployeeID());
    } catch (NullPointerException e) {
      throw new NullPointerException(e.getMessage());
    }
    employees.remove(employee);
    try {
      String query =
          "DELETE FROM EMPLOYEES WHERE EMPLOYEEID = ('" + employee.getEmployeeID() + "')";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
