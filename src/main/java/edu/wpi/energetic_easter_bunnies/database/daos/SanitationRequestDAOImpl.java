package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.controllers.mainController;
import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.entity.sanitationRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanitationRequestDAOImpl implements DAO<sanitationRequest> {
  static Connection connection =
      DBConnect.valueOf(mainController.getDatabaseMode()).getConnection();
  List<sanitationRequest> sanitationRequests;

  public SanitationRequestDAOImpl() throws SQLException {
    sanitationRequests = new ArrayList<>();
    String query = "SELECT * FROM SANITATIONREQUEST ORDER BY SANITATION_REQ_ID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();

    while (rs.next()) {
      String sanitationReqID = rs.getString("SANITATION_REQ_ID");
      java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
      java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
      String status = rs.getString("STATUS");
      String staffAssignee = rs.getString("ASSIGNEE");
      boolean isUrgent = rs.getBoolean("ISURGENT");
      String roomID = rs.getString("ROOMID");
      String floorID = rs.getString("FLOOR");
      String sizeType = rs.getString("CLEANINGSIZE");
      String biohazardType = rs.getString("ISBIOHAZARD");
      String otherNotes = rs.getString("OTHERNOTES");

      sanitationRequest request =
          new sanitationRequest(
              sanitationReqID,
              otherNotes,
              floorID,
              roomID,
              isUrgent,
              status,
              staffAssignee,
              requestDate.toLocalDate(),
              deliveryDate.toLocalDate(),
              sanitationRequest.Size.valueOf(sizeType),
              sanitationRequest.Biohazard.valueOf(biohazardType));
      sanitationRequests.add(request);
    }
    rs.close();
  }

  @Override
  public List<sanitationRequest> getAll() {
    return sanitationRequests;
  }

  @Override
  public sanitationRequest get(String id) {
    for (sanitationRequest request : sanitationRequests) {
      if (request.getServiceRequestID().equals(id)) return request;
    }

    System.out.println("Sanitation Request with sanitation request id " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(sanitationRequest item) {
    sanitationRequests.add(item);

    try {
      String query = "INSERT INTO SANITATIONREQUEST VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, item.getServiceRequestID());
      statement.setDate(2, Date.valueOf(item.getRequestDate()));
      statement.setDate(3, Date.valueOf(item.getDeliveryDate()));
      statement.setString(4, item.getRequestStatus());
      statement.setString(5, item.getStaffAssignee());
      statement.setBoolean(6, item.getIsUrgent());
      statement.setString(7, item.getRoomID());
      statement.setString(8, item.getFloorID());
      statement.setString(9, item.getSizeValue());
      statement.setString(10, item.getBiohazardValue());
      statement.setString(11, item.getOtherNotes());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(
          "Add Sanitation Request failed!"); // TODO: Come up with a better catch block
    }
  }

  @Override
  public void delete(sanitationRequest item) {
    sanitationRequests.remove(item);
    String query = "DELETE FROM SANITATIONREQUEST WHERE SANITATION_REQ_ID = (?)";
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(query);
      statement.setString(1, item.getServiceRequestID());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
