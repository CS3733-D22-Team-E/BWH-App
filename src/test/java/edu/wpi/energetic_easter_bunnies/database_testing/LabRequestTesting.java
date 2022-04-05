package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.energetic_easter_bunnies.database.daos.LabRequestDAO;
import edu.wpi.energetic_easter_bunnies.database.daos.LabRequestDAOImpl;
import edu.wpi.energetic_easter_bunnies.entity.labRequest;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class LabRequestTesting {

  @Test
  public void testAddLabRequest() throws SQLException {
    LabRequestDAO labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest = new labRequest("1", "1", "1", "1", "1", "1", "1");
    labRequestDAO.addLabRequest(labRequest);
    assertTrue(labRequestDAO.getAllLabRequests().contains(labRequest));
  }

  @Test
  public void testDeleteLabRequest() throws SQLException {
    LabRequestDAO labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest = new labRequest("2", "1", "1", "1", "1", "1", "1");
    labRequestDAO.addLabRequest(labRequest);
    assertTrue(labRequestDAO.getAllLabRequests().contains(labRequest));
    labRequestDAO.deleteLabRequest(labRequest);
    assertFalse(labRequestDAO.getAllLabRequests().contains(labRequest));
  }

  @Test
  public void testUpdateLabRequest() throws SQLException {
    LabRequestDAO labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest = new labRequest("3", "1", "1", "1", "1", "1", "1");
    labRequestDAO.addLabRequest(labRequest);
    labRequestDAO.updateLabServiceRequest(labRequest, "2");
    assertTrue(labRequest.getRequestStatus() == "2");
  }
}
