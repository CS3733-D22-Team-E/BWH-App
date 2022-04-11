package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnect;
import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentServiceRequestDAOImpl implements DAO<medicalEquipmentRequest> {
  static Connection connection = DBConnect.EMBEDDED_INSTANCE.getConnection();
  List<medicalEquipmentRequest> medicalRequests;

  public MedicalEquipmentServiceRequestDAOImpl() throws SQLException {
    medicalRequests = new ArrayList<>();
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM MED_EQUIP_REQ ORDER BY REQUESTDATE DESC";
    ResultSet rs = statement.executeQuery(query);
    while (rs.next()) {
      String medEquipReqID = rs.getString("MED_EQUIPMENTID");
      java.sql.Date reqDate = rs.getDate("REQUESTDATE");
      java.sql.Date deliveryDate = rs.getDate("DELIVERYDATE");
      boolean isUrgent = rs.getBoolean("ISURGENT");
      String equipment = rs.getString("EQUIP");
      int equipQuantity = rs.getInt("EQUIPQUANTITY");
      String staffAssignee = rs.getString("STAFFASSIGNEE");
      String locNodeID = rs.getString("LOCATIONID");
      String floor = rs.getString("FLOOR");
      String requestStatus = rs.getString("REQUESTSTATUS");
      String otherNotes = rs.getString("OTHERNOTES");

      medicalEquipmentRequest equipRequest =
          new medicalEquipmentRequest(
              medEquipReqID,
              otherNotes,
              floor,
              locNodeID,
              isUrgent,
              requestStatus,
              staffAssignee,
              equipment,
              equipQuantity,
              reqDate.toLocalDate(),
              deliveryDate.toLocalDate(),
              "");
      medicalRequests.add(equipRequest);
    }
    rs.close();
  }

  public List<medicalEquipmentRequest> getAll() {
    return medicalRequests;
  }

  public medicalEquipmentRequest get(String id) {
    for (medicalEquipmentRequest request : medicalRequests) {
      if (request.getServiceRequestID().equals(id)) return request;
    }

    System.out.println(
        "Medical Equipment Request with medical equipment request id " + id + " not found");
    throw new NullPointerException();
  }

  public void update(medicalEquipmentRequest request) {
    medicalRequests.add(request);

    try {
      Statement statement = connection.createStatement();
      String query =
          "INSERT INTO MED_EQUIP_REQ VALUES ('"
              + request.getServiceRequestID()
              + "','"
              + request.getDeliveryDate()
              + "','"
              + request.getRequestDate()
              + "',"
              + request.getIsUrgent()
              + ",'"
              + request.getEquipment()
              + "',"
              + request.getEquipmentQuantity()
              + ",'"
              + request.getStaffAssignee()
              + "','"
              + request.getRoomID()
              + "','"
              + request.getFloorID()
              + "','"
              + request.getRequestStatus()
              + "','"
              + request.getOtherNotes()
              + "')";
      statement.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println("SQL Error"); // TODO: Replace with something else
    }
  }

  public void delete(medicalEquipmentRequest request) { // TODO: Remove from DB table as well
    medicalRequests.remove(request);
  }
}
