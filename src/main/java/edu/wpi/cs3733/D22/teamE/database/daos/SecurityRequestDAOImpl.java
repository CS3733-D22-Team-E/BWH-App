package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.entity.securityRequest;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SecurityRequestDAOImpl implements DAO<securityRequest> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<securityRequest> securityRequests;

  public SecurityRequestDAOImpl() throws SQLException {
    securityRequests = new ArrayList<>();
    String query = "SELECT * FROM SECURITYREQUEST ORDER BY SECURITY_REQUESTID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();
    // int numID = 0; //TODO: Assign Medical Requests an ID value
    while (rs.next()) {
      String securityReqID = rs.getString("SECURITY_REQUESTID");
      String securityReqType = rs.getString("SECURITY_REQUEST_TYPE");
      String timeFrame = rs.getString("TIMEFRAME");
      String staffAssignee = rs.getString("STAFFASSIGNEE");
      String locNodeID = rs.getString("LOCATIONID");
      String requestStatus = rs.getString("REQUESTSTATUS");
      String otherNotes = rs.getString("OTHERNOTES");
      String floorID = "";
      java.sql.Date deliveryDate = Date.valueOf(LocalDate.now());
      java.sql.Date requestDate = Date.valueOf(LocalDate.now());
      boolean isUrgent = false;

      securityRequest securityReq =
          new securityRequest(
              securityReqID,
              securityReqType,
              timeFrame,
              floorID,
              locNodeID,
              isUrgent,
              staffAssignee,
              requestStatus,
              otherNotes,
              requestDate.toLocalDate(),
              deliveryDate.toLocalDate());
      securityRequests.add(securityReq);
    }
    rs.close();
  }

  /**
   * Return all security requests
   *
   * @return a list of all security requests
   */
  @Override
  public List<securityRequest> getAll() {
    return securityRequests;
  }

  /**
   * Method to get a specific security request
   *
   * @param id The id of the specific security request wanted
   * @return the security request with given id
   */
  @Override
  public securityRequest get(String id) {
    for (securityRequest securityRequest : securityRequests) {
      if (securityRequest.getServiceRequestID().equals(id)) return securityRequest;
    }
    System.out.println("Security Request with id " + id + " not found");
    throw new NullPointerException();
  }

  /**
   * Add a new security request to the DB
   *
   * @param securityRequest request to add
   */
  @Override
  public void update(securityRequest securityRequest) {
    securityRequests.add(securityRequest);

    try {
      String query =
          "INSERT INTO SECURITYREQUEST VALUES ('"
              + securityRequest.getServiceRequestID()
              + "','"
              + securityRequest.getSecurityRequestType()
              + "','"
              + securityRequest.getTimeFrame()
              + "','"
              + securityRequest.getRoomID()
              + "','"
              + securityRequest.getFloorID()
              + "','"
              + securityRequest.isUrgent()
              + "','"
              + securityRequest.getStaffAssignee()
              + "','"
              + securityRequest.getRequestStatus()
              + "','"
              + securityRequest.getRequestDate()
              + "','"
              + securityRequest.getDeliveryDate()
              + "','"
              + securityRequest.getOtherNotes()
              + "')";
      PreparedStatement statement = connection.prepareStatement(query);
      System.out.println(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Update request status for a service request TODO find what to do with this function
   *
   * @param securityRequest request to update
   * @param newRequestStatus new request status for the security request
   * @throws SQLException
   */
  public void updateSecurityServiceRequest(securityRequest securityRequest, String newRequestStatus)
      throws SQLException {

    // Updating status of request
    securityRequest.setRequestStatus(newRequestStatus);

    // Update status of request in the db
    String query =
        "UPDATE SECURITYREQUEST SET REQUESTSTATUS = '"
            + newRequestStatus
            + "' WHERE SECURITY_REQUESTID = '"
            + securityRequest.getServiceRequestID()
            + "'";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.executeUpdate();
  }

  /**
   * Deletes security request from DB
   *
   * @param securityRequest security request to delete
   */
  @Override
  public void delete(securityRequest securityRequest) {

    // Deleting the security request from the array list
    securityRequests.remove(securityRequest);

    // Remove security request in the db
    try {
      String query =
          "DELETE FROM SECURITYREQUEST WHERE SECURITY_REQUESTID = ('"
              + securityRequest.getServiceRequestID()
              + "')";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
