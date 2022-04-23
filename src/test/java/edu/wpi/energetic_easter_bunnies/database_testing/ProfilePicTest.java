package edu.wpi.energetic_easter_bunnies.database_testing;

import edu.wpi.cs3733.D22.teamE.database.Employee;
import edu.wpi.cs3733.D22.teamE.database.ProfilePictureManager;
import edu.wpi.cs3733.D22.teamE.database.daos.DAO;
import edu.wpi.cs3733.D22.teamE.database.daos.EmployeeDAOImpl;
import java.io.IOException;
import java.sql.SQLException;

public class ProfilePicTest {

  public static void main(String[] args) throws IOException, SQLException {
    byte[] bytes = ProfilePictureManager.toByte("wwong2");
    DAO<Employee> employeeDAO = new EmployeeDAOImpl();
    Employee wong = new Employee("wwong2", "Wilson Wong", "Nurse", 69000.0, "FDEPT00101", true, 0);
    Employee staff = employeeDAO.get("staff");
    ProfilePictureManager.savePersonalPicture(staff, bytes);
    ProfilePictureManager.savePersonalPicture(wong, bytes);
    ProfilePictureManager.getPersonalPicture(wong);
  }
}
