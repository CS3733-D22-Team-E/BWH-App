package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.database.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements DAO<Employee> {
  static Connection connection = DBConnect.INSTANCE.getConnection();
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
  }

  @Override
  public void delete(Employee employee) {
    employees.remove(employee);
  }
}
