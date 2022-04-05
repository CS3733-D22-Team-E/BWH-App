package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnection;
import edu.wpi.energetic_easter_bunnies.entity.labRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LabRequestDAOImpl implements LabRequestDAO {

  static Connection connection = DBConnection.getConnection();
  List<labRequest> labRequests;

  public LabRequestDAOImpl() throws SQLException {
    labRequests = new ArrayList<>();
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM LAB_REQUEST ORDER BY LAB_REQUESTID DESC";
    ResultSet rs = statement.executeQuery(query);
    // int numID = 0; //TODO: Assign Medical Requests an ID value
    while (rs.next()) {
      String labReqID = rs.getString("LAB_REQUESTID");
      String labRedType = rs.getString("LAB_REQUEST_TYPE");
      String timeFrame = rs.getString("TIMEFRAME");
      String staffAssignee = rs.getString("STAFFASSIGNEE");
      String locNodeID = rs.getString("LOCATIONID");
      String requestStatus = rs.getString("REQUESTSTATUS");
      String otherNotes = rs.getString("OTHERNOTES");

      labRequest LabRequest =
          new labRequest(
              labReqID, labRedType, timeFrame, staffAssignee, locNodeID, requestStatus, otherNotes);
      labRequests.add(LabRequest);
      // numID++;
    }
    rs.close();
  }

  /**
   * Return all lab requests
   *
   * @return a list of all lab requests
   */
  @Override
  public List<labRequest> getAllLabRequests() {
    return labRequests;
  }

  /**
   * Add a new lab request to the DB
   *
   * @param labRequest lab request to add
   * @throws SQLException
   */
  @Override
  public void addLabRequest(labRequest labRequest) throws SQLException {
    labRequests.add(labRequest);

    Statement statement = connection.createStatement();
    String query =
        "INSERT INTO LAB_REQUEST VALUES ('"
            + labRequest.getServiceRequestID()
            + "','"
            + labRequest.getLabRequestType()
            + "','"
            + labRequest.getStaffAssignee()
            + "','"
            + labRequest.getLocNodeID()
            + "','"
            + labRequest.getTimeFrame()
            + "','"
            + labRequest.getRequestStatus()
            + "','"
            + labRequest.getOtherNotes()
            + "')";
    System.out.println(query);
    statement.executeUpdate(query);
  }

  /**
   * Update request status for a lab request
   *
   * @param labRequest lab request to update
   * @param newRequestStatus new request status for the lab request
   * @throws SQLException
   */
  @Override
  public void updateLabServiceRequest(labRequest labRequest, String newRequestStatus)
      throws SQLException {

    // Updating status of request
    labRequest.setRequestStatus(newRequestStatus);

    // Update status of request in the db
    Statement statement = connection.createStatement();
    String query =
        "UPDATE LAB_REQUEST SET REQUESTSTATUS = '"
            + newRequestStatus
            + "' WHERE LAB_REQUESTID = '"
            + labRequest.getServiceRequestID()
            + "'";
    statement.executeUpdate(query);
  }

  /**
   * Deletes lab request from DB
   *
   * @param labRequest lab request to delete
   * @throws SQLException
   */
  @Override
  public void deleteLabRequest(labRequest labRequest) throws SQLException {

    // Deleting the lab request from the array list
    labRequests.remove(labRequest);

    // Remove lab request in the db
    Statement statement = connection.createStatement();
    String query =
        "DELETE FROM LAB_REQUEST WHERE LAB_REQUESTID = ('"
            + labRequest.getServiceRequestID()
            + "')";
    statement.executeUpdate(query);
  }
}
