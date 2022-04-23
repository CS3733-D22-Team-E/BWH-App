package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.entity.labRequest;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LabRequestDAOImpl implements DAO<labRequest> {

  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<labRequest> labRequests;

  public LabRequestDAOImpl() throws SQLException {
    labRequests = new ArrayList<>();
    String query = "SELECT * FROM LAB_REQUEST ORDER BY LAB_REQUESTID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();
    // int numID = 0; //TODO: Assign Medical Requests an ID value
    while (rs.next()) {
      String labReqID = rs.getString("LAB_REQUESTID");
      String labRedType = rs.getString("LAB_REQUEST_TYPE");
      String timeFrame = rs.getString("TIMEFRAME");
      String staffAssignee = rs.getString("STAFFASSIGNEE");
      String locNodeID = rs.getString("LOCATIONID");
      String requestStatus = rs.getString("REQUESTSTATUS");
      String otherNotes = rs.getString("OTHERNOTES");
      String floorID = "";
      java.sql.Date deliveryDate = Date.valueOf(LocalDate.now());
      java.sql.Date requestDate = Date.valueOf(LocalDate.now());
      boolean isUrgent = false;

      labRequest LabRequest =
          new labRequest(
              labReqID,
              labRedType,
              timeFrame,
              floorID,
              locNodeID,
              isUrgent,
              staffAssignee,
              requestStatus,
              otherNotes,
              requestDate.toLocalDate(),
              deliveryDate.toLocalDate());
      labRequests.add(LabRequest);
    }
    rs.close();
  }

  /**
   * Return all lab requests
   *
   * @return a list of all lab requests
   */
  @Override
  public List<labRequest> getAll() {
    return labRequests;
  }

  /**
   * Method to get a specific lab request
   *
   * @param id The id of the specific lab request wanted
   * @return
   */
  @Override
  public labRequest get(String id) {
    for (labRequest labRequest : labRequests) {
      if (labRequest.getServiceRequestID().equals(id)) return labRequest;
    }
    System.out.println("Lab Request with lab request id " + id + " not found");
    throw new NullPointerException();
  }

  /**
   * Add a new lab request to the DB
   *
   * @param labRequest lab request to add
   */
  @Override
  public void update(labRequest labRequest) {
    labRequests.add(labRequest);

    try {
      String query =
          "INSERT INTO LAB_REQUEST VALUES ('"
              + labRequest.getServiceRequestID()
              + "','"
              + labRequest.getLabRequestType()
              + "','"
              + labRequest.getStaffAssignee()
              + "','"
              + labRequest.getRoomID()
              + "','"
              + labRequest.getTimeFrame()
              + "','"
              + labRequest.getRequestStatus()
              + "','"
              + labRequest.getOtherNotes()
              + "')";
      PreparedStatement statement = connection.prepareStatement(query);
      System.out.println(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update request status for a lab request TODO find what to do with this function
   *
   * @param labRequest lab request to update
   * @param newRequestStatus new request status for the lab request
   * @throws SQLException
   */
  public void updateLabServiceRequest(labRequest labRequest, String newRequestStatus)
      throws SQLException {

    // Updating status of request
    labRequest.setRequestStatus(newRequestStatus);

    // Update status of request in the db
    String query =
        "UPDATE LAB_REQUEST SET REQUESTSTATUS = '"
            + newRequestStatus
            + "' WHERE LAB_REQUESTID = '"
            + labRequest.getServiceRequestID()
            + "'";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  /**
   * Deletes lab request from DB
   *
   * @param labRequest lab request to delete
   */
  @Override
  public void delete(labRequest labRequest) {

    // Deleting the lab request from the array list
    labRequests.remove(labRequest);

    // Remove lab request in the db
    try {
      String query =
          "DELETE FROM LAB_REQUEST WHERE LAB_REQUESTID = ('"
              + labRequest.getServiceRequestID()
              + "')";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
