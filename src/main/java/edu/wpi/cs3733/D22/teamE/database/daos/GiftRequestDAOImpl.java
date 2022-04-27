package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.DBConnect;
import edu.wpi.cs3733.D22.teamE.entity.giftDeliveryRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiftRequestDAOImpl implements DAO<giftDeliveryRequest> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<giftDeliveryRequest> giftRequests;

  public GiftRequestDAOImpl() throws SQLException {
    giftRequests = new ArrayList<>();
    String query = "SELECT * FROM GIFTREQUEST ORDER BY GIFT_REQ_ID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();

    while (rs.next()) {
      String giftReqID = rs.getString("GIFT_REQ_ID");
      java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
      java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
      String status = rs.getString("STATUS");
      String staffAssignee = rs.getString("ASSIGNEE");
      boolean isUrgent = rs.getBoolean("ISURGENT");
      String roomID = rs.getString("ROOMID");
      String floorID = rs.getString("FLOOR");
      String patientName = rs.getString("PATIENTNAME");
      String giftType = rs.getString("GIFTTYPE");
      String otherNotes = rs.getString("OTHERNOTES");

      giftDeliveryRequest request =
          new giftDeliveryRequest(
              giftReqID,
              otherNotes,
              floorID,
              roomID,
              isUrgent,
              status,
              staffAssignee,
              requestDate.toLocalDate(),
              deliveryDate.toLocalDate(),
              patientName,
              giftType);
      giftRequests.add(request);
    }
    rs.close();
  }

  @Override
  public List<giftDeliveryRequest> getAll() {
    giftRequests = new ArrayList<>();

    try {
      String query = "SELECT * FROM GIFTREQUEST ORDER BY GIFT_REQ_ID DESC";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        String giftReqID = rs.getString("GIFT_REQ_ID");
        java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
        java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
        String status = rs.getString("STATUS");
        String staffAssignee = rs.getString("ASSIGNEE");
        boolean isUrgent = rs.getBoolean("ISURGENT");
        String roomID = rs.getString("ROOMID");
        String floorID = rs.getString("FLOOR");
        String patientName = rs.getString("PATIENTNAME");
        String giftType = rs.getString("GIFTTYPE");
        String otherNotes = rs.getString("OTHERNOTES");

        giftDeliveryRequest request =
            new giftDeliveryRequest(
                giftReqID,
                otherNotes,
                floorID,
                roomID,
                isUrgent,
                status,
                staffAssignee,
                requestDate.toLocalDate(),
                deliveryDate.toLocalDate(),
                patientName,
                giftType);
        giftRequests.add(request);
      }
      rs.close();
    } catch (SQLException e) {
      System.out.println("Get All Failed!");
      e.printStackTrace();
    }

    return giftRequests;
  }

  @Override
  public giftDeliveryRequest get(String id) {
    for (giftDeliveryRequest request : giftRequests) {
      if (request.getServiceRequestID().equals(id)) return request;
    }

    System.out.println("Gift Delivery Request with service request id " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(giftDeliveryRequest item) {
    giftRequests.add(item);

    try {
      String query = "INSERT INTO GIFTREQUEST VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, item.getServiceRequestID());
      statement.setDate(2, Date.valueOf(item.getRequestDate()));
      statement.setDate(3, Date.valueOf(item.getDeliveryDate()));
      statement.setString(4, item.getRequestStatus());
      statement.setString(5, item.getStaffAssignee());
      statement.setBoolean(6, item.getIsUrgent());
      statement.setString(7, item.getRoomID());
      statement.setString(8, item.getFloorID());
      statement.setString(9, item.getPatientName());
      statement.setString(10, item.getGift());
      statement.setString(11, item.getOtherNotes());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Add Gift Request failed!"); // TODO: Come up with a better catch block
      e.printStackTrace();
    }
  }

  @Override
  public void delete(giftDeliveryRequest item) {
    giftRequests.remove(item);

    String query = "DELETE FROM GIFTREQUEST WHERE GIFT_REQ_ID = (?)";
    PreparedStatement statement;

    try {
      statement = connection.prepareStatement(query);
      statement.setString(1, item.getServiceRequestID());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
