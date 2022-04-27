package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.cs3733.D22.teamE.database.daos.DAO;
import edu.wpi.cs3733.D22.teamE.database.daos.EmployeeDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.Employee;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class EmployeeDAOTest {
  @Test
  public void testAddEmployee() throws SQLException {
    DAO<Employee> employeeDAO = new EmployeeDAOImpl();
    Employee Employee = new Employee("1", "1", "1", 100.0, "1", true, 1);
    employeeDAO.update(Employee);
    assertEquals(
        employeeDAO.get(Employee.getEmployeeID()).getEmployeeID(), Employee.getEmployeeID());
    // assertTrue(employeeDAO.getAll().contains(Employee));
  }

  @Test
  public void testEmployee() throws SQLException {
    DAO<Employee> employeeDAO = new EmployeeDAOImpl();
    Employee Employee = new Employee("1", "1", "1", 100.0, "1", true, 1);
    employeeDAO.update(Employee);
    assertEquals(
        employeeDAO.get(Employee.getEmployeeID()).getEmployeeID(), Employee.getEmployeeID());
    // assertTrue(employeeDAO.getAll().contains(Employee));
    employeeDAO.delete(Employee);
    assertFalse(employeeDAO.getAll().contains(Employee));
  }
}
