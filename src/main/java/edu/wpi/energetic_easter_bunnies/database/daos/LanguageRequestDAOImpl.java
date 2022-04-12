package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.entity.languageInterpreterRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageRequestDAOImpl implements DAO<languageInterpreterRequest> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<languageInterpreterRequest> languageRequests;

  public LanguageRequestDAOImpl() throws SQLException {
    languageRequests = new ArrayList<>();
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM LANGUAGEREQUEST ORDER BY LAN_INTERP_REQ_ID DESC";
    ResultSet rs = statement.executeQuery(query);
    while (rs.next()) {
      String langInterpReqID = rs.getString("LAN_INTERP_REQ_ID");
      String otherNotes = rs.getString("OTHERNOTES");
      String floorID = rs.getString("FLOOR");
      String roomID = rs.getString("DELIVERYLOCATIONID");
      boolean isUrgent = rs.getBoolean("ISURGENT");
      String status = rs.getString("STATUS");
      String staffAssignee = rs.getString("ASSIGNEE");
      java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
      java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
      String language = rs.getString("LANGUAGE");

      languageInterpreterRequest request =
          new languageInterpreterRequest(
              langInterpReqID,
              otherNotes,
              floorID,
              roomID,
              isUrgent,
              status,
              staffAssignee,
              languageInterpreterRequest.Language.valueOf(language),
              deliveryDate.toLocalDate(),
              requestDate.toLocalDate());
      languageRequests.add(request);
    }
    rs.close();
  }

  @Override
  public List<languageInterpreterRequest> getAll() {
    return languageRequests;
  }

  @Override
  public languageInterpreterRequest get(String id) {
    for (languageInterpreterRequest request : languageRequests) {
      if (request.getServiceRequestID().equals(id)) return request;
    }

    System.out.println(
        "Language Interpreter Request with language request id " + id + " not found");
    throw new NullPointerException();
  }

  @Override
  public void update(languageInterpreterRequest item) {
    languageRequests.add(item);

    try {
      String query = "INSERT INTO SANITATIONREQUEST VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, item.getServiceRequestID());
      statement.setDate(2, Date.valueOf(item.getRequestDate()));
      statement.setDate(3, Date.valueOf(item.getDeliveryDate()));
      statement.setString(4, item.getRequestStatus());
      statement.setString(5, item.getStaffAssignee());
      statement.setBoolean(6, item.getIsUrgent());
      statement.setString(7, item.getRoomID());
      statement.setString(8, item.getFloorID());
      statement.setString(9, item.getLanguageValue());
      statement.setString(10, item.getOtherNotes());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(
          "Add Sanitation Request failed!"); // TODO: Come up with a better catch block
    }
  }

  @Override
  public void delete(languageInterpreterRequest item) {
    languageRequests.remove(item);
  }
}
