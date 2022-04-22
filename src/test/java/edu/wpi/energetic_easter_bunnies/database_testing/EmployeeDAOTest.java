package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.cs3733.D22.teamE.database.Employee;
import edu.wpi.cs3733.D22.teamE.database.daos.DAO;
import edu.wpi.cs3733.D22.teamE.database.daos.EmployeeDAOImpl;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class EmployeeDAOTest {
  @Test
  public void testAddLabRequest() throws SQLException {
    DAO<Employee> employeeDAO = new EmployeeDAOImpl();
    Employee Employee = new Employee("1", "1", "1", 100.0, "1", true, 1);
    employeeDAO.update(Employee);
    assertTrue(employeeDAO.getAll().contains(Employee));
  }

  @Test
  public void testDeleteLabRequest() throws SQLException {
    DAO<Employee> employeeDAO = new EmployeeDAOImpl();
    Employee Employee = new Employee("1", "1", "1", 100.0, "1", true, 1);
    employeeDAO.update(Employee);
    assertTrue(employeeDAO.getAll().contains(Employee));
    employeeDAO.delete(Employee);
    assertFalse(employeeDAO.getAll().contains(Employee));
  }
}
