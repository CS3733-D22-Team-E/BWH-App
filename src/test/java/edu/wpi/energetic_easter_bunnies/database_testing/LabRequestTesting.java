package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.cs3733.D22.teamE.database.daos.DAO;
import edu.wpi.cs3733.D22.teamE.database.daos.LabRequestDAOImpl;
import edu.wpi.cs3733.D22.teamE.entity.labRequest;
import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class LabRequestTesting {

  @Test
  public void testAddLabRequest() throws SQLException {
    DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest =
        new labRequest(
            "1", "1", "1", "1", "1", false, "1", "1", "1", LocalDate.now(), LocalDate.now());
    labRequestDAO.add(labRequest);
    assertTrue(labRequestDAO.getAll().contains(labRequest));
  }

  @Test
  public void testDeleteLabRequest() throws SQLException {
    DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest =
        new labRequest(
            "2", "1", "1", "1", "1", false, "1", "1", "1", LocalDate.now(), LocalDate.now());
    labRequestDAO.add(labRequest);
    assertTrue(labRequestDAO.getAll().contains(labRequest));
    labRequestDAO.delete(labRequest);
    assertFalse(labRequestDAO.getAll().contains(labRequest));
  }

  @Test
  public void testUpdateLabRequest() throws SQLException {
    LabRequestDAOImpl labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest =
        new labRequest(
            "3", "1", "1", "1", "1", false, "1", "1", "1", LocalDate.now(), LocalDate.now());
    labRequestDAO.add(labRequest);
    labRequestDAO.updateLabServiceRequest(labRequest, "2");
    assertTrue(labRequest.getRequestStatus() == "2");
  }
}
