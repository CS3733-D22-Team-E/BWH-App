package edu.wpi.energetic_easter_bunnies.database_testing;

import edu.wpi.energetic_easter_bunnies.database.daos.ServiceRequestDAOImpl;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class ServiceRequestTests {

  @Test
  public void testAddLabRequest() throws SQLException {
    ServiceRequestDAOImpl serviceRequestDAO = new ServiceRequestDAOImpl();
    serviceRequestDAO.printAll();
  }
}
