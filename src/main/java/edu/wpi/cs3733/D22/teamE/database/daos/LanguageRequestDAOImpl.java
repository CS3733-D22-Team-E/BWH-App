package edu.wpi.cs3733.D22.teamE.database.daos;

import edu.wpi.cs3733.D22.teamE.database.AccountsManager;
import edu.wpi.cs3733.D22.teamE.entity.languageInterpreterRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageRequestDAOImpl implements DAO<languageInterpreterRequest> {
  static Connection connection = AccountsManager.getInstance().getConnection();
  List<languageInterpreterRequest> languageRequests;

  public LanguageRequestDAOImpl() throws SQLException {
    languageRequests = new ArrayList<>();
    String query = "SELECT * FROM LANGUAGEREQUEST ORDER BY LAN_INTERP_REQ_ID DESC";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      String langInterpReqID = rs.getString("LAN_INTERP_REQ_ID");
      java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
      java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
      String status = rs.getString("STATUS");
      String staffAssignee = rs.getString("ASSIGNEE");
      boolean isUrgent = rs.getBoolean("ISURGENT");
      String roomID = rs.getString("ROOMID");
      String floorID = rs.getString("FLOOR");
      String language = rs.getString("LANGUAGE");
      String otherNotes = rs.getString("OTHERNOTES");

      languageInterpreterRequest request =
          new languageInterpreterRequest(
              langInterpReqID,
              otherNotes,
              floorID,
              roomID,
              isUrgent,
              status,
              staffAssignee,
              language,
              requestDate.toLocalDate(),
              deliveryDate.toLocalDate());
      languageRequests.add(request);
    }
    rs.close();
  }

  @Override
  public List<languageInterpreterRequest> getAll() {
    languageRequests = new ArrayList<>();

    try {
      String query = "SELECT * FROM LANGUAGEREQUEST ORDER BY LAN_INTERP_REQ_ID DESC";
      PreparedStatement statement = connection.prepareStatement(query);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        String langInterpReqID = rs.getString("LAN_INTERP_REQ_ID");
        java.sql.Date requestDate = rs.getDate("REQUEST_DATE");
        java.sql.Date deliveryDate = rs.getDate("DELIVERY_DATE");
        String status = rs.getString("STATUS");
        String staffAssignee = rs.getString("ASSIGNEE");
        boolean isUrgent = rs.getBoolean("ISURGENT");
        String roomID = rs.getString("ROOMID");
        String floorID = rs.getString("FLOOR");
        String language = rs.getString("LANGUAGE");
        String otherNotes = rs.getString("OTHERNOTES");

        languageInterpreterRequest request =
            new languageInterpreterRequest(
                langInterpReqID,
                otherNotes,
                floorID,
                roomID,
                isUrgent,
                status,
                staffAssignee,
                language,
                requestDate.toLocalDate(),
                deliveryDate.toLocalDate());
        languageRequests.add(request);
      }
      rs.close();
    } catch (SQLException e) {
      System.out.println("Get All Failed!");
      e.printStackTrace();
    }

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
      String query = "INSERT INTO LANGUAGEREQUEST VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, item.getServiceRequestID());
      statement.setDate(2, Date.valueOf(item.getRequestDate()));
      statement.setDate(3, Date.valueOf(item.getDeliveryDate()));
      statement.setString(4, item.getRequestStatus());
      statement.setString(5, item.getStaffAssignee());
      statement.setBoolean(6, item.getIsUrgent());
      statement.setString(7, item.getRoomID());
      statement.setString(8, item.getFloorID());
      statement.setString(9, item.getLanguage());
      statement.setString(10, item.getOtherNotes());

      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Add Language Request failed!"); // TODO: Come up with a better catch block
    }
  }

  @Override
  public void delete(languageInterpreterRequest request) {
    languageRequests.remove(request);

    String query = "DELETE FROM LANGUAGEREQUEST WHERE LAN_INTERP_REQ_ID = (?)";
    PreparedStatement statement;
    try {
      statement = connection.prepareStatement(query);
      statement.setString(1, request.getServiceRequestID());
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
