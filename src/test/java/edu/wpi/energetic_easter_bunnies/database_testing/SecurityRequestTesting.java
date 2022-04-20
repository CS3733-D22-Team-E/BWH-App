package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityRequestTesting {
  /*
  @Test
  public void testAddSecurityRequest() throws SQLException {
    DAO<securityRequest> securityRequestDAO = new SecurityRequestDAOImpl();
    securityRequest securityRequest =
        new securityRequest(
            "1", "1", "ASAP", "1", "1", false, "1", "1", "1", LocalDate.now(), LocalDate.now());
    securityRequestDAO.update(securityRequest);
    assertTrue(securityRequestDAO.getAll().contains(securityRequest));
  }

  @Test
  public void testDeleteSecurityRequest() throws SQLException {
    DAO<securityRequest> securityRequestDAO = new SecurityRequestDAOImpl();
    securityRequest securityRequest =
        new securityRequest(
            "2", "1", "ASAP", "1", "1", false, "1", "1", "1", LocalDate.now(), LocalDate.now());
    securityRequestDAO.update(securityRequest);
    assertTrue(securityRequestDAO.getAll().contains(securityRequest));
    securityRequestDAO.delete(securityRequest);
    assertFalse(securityRequestDAO.getAll().contains(securityRequest));
  }

  @Test
  public void testUpdateSecurityRequest() throws SQLException {
    SecurityRequestDAOImpl securityRequestDAO = new SecurityRequestDAOImpl();
    securityRequest securityRequest =
        new securityRequest(
            "3", "1", "ASAP", "1", "1", false, "1", "1", "1", LocalDate.now(), LocalDate.now());
    securityRequestDAO.update(securityRequest);
    securityRequestDAO.updateSecurityServiceRequest(securityRequest, "Processing");
    assertEquals(securityRequest.getRequestStatus(), "Processing");
  }*/
}
