package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.*;

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
    labRequest request =
        new labRequest(
            "1",
            "1",
            "ASAP",
            "",
            "ePATI00103",
            false,
            "admin",
            "To Do",
            "1",
            LocalDate.now(),
            LocalDate.now());
    labRequestDAO.update(request);
    assertEquals(
        labRequestDAO.get(request.getServiceRequestID()).getServiceRequestID(),
        request.getServiceRequestID());
    // assertTrue(labRequestDAO.getAll().contains(request));
  }

  @Test
  public void testDeleteLabRequest() throws SQLException {
    DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest =
        new labRequest(
            "2",
            "1",
            "ASAP",
            "",
            "ePATI00103",
            false,
            "admin",
            "To Do",
            "1",
            LocalDate.now(),
            LocalDate.now());
    labRequestDAO.update(labRequest);
    assertEquals(
        labRequestDAO.get(labRequest.getServiceRequestID()).getServiceRequestID(),
        labRequest.getServiceRequestID());
    // assertTrue(labRequestDAO.getAll().contains(labRequest));
    assertFalse(labRequestDAO.getAll().contains(labRequest));
  }

  @Test
  public void testUpdateLabRequest() throws SQLException {
    LabRequestDAOImpl labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest =
        new labRequest(
            "3", "1", "ASAP", "1", "1", false, "1", "1", "1", LocalDate.now(), LocalDate.now());
    labRequestDAO.update(labRequest);
    labRequestDAO.updateLabServiceRequest(labRequest, "Processing");
    assertEquals(
        labRequestDAO.get(labRequest.getServiceRequestID()).getRequestStatus(), "Processing");
    // assertEquals(labRequest.getRequestStatus(), "Processing");
  }
}
