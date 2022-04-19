package edu.wpi.energetic_easter_bunnies.database_testing;

public class LabRequestTesting {

  /*@Test
  public void testAddLabRequest() throws SQLException {
    DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest =
        new labRequest(
            "1", "1", "1", "1", "1", false, "1", "1", "1", LocalDate.now(), LocalDate.now());
    labRequestDAO.update(labRequest);
    assertTrue(labRequestDAO.getAll().contains(labRequest));
  }

  @Test
  public void testDeleteLabRequest() throws SQLException {
    DAO<labRequest> labRequestDAO = new LabRequestDAOImpl();
    labRequest labRequest =
        new labRequest(
            "2", "1", "1", "1", "1", false, "1", "1", "1", LocalDate.now(), LocalDate.now());
    labRequestDAO.update(labRequest);
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
    labRequestDAO.update(labRequest);
    labRequestDAO.updateLabServiceRequest(labRequest, "2");
    assertTrue(labRequest.getRequestStatus() == "2");
  }*/
}
