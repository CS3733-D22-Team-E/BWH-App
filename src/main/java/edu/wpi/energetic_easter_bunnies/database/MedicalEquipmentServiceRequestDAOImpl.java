package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentServiceRequestDAOImpl implements MedicalEquipmentServiceRequestDAO {
  static Connection connection = DBConnection.getConnection();
  List<medicalEquipmentRequest> medicalRequests;

  public MedicalEquipmentServiceRequestDAOImpl() throws SQLException {
    medicalRequests = new ArrayList<>();
    // String url = "jdbc:derby:myDB;";
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM MED_EQUIP_REQ ORDER BY REQUESTDATE DESC";
    ResultSet rs = statement.executeQuery(query);
    // int numID = 0; //TODO: Assign Medical Requests an ID value
    while (rs.next()) {
      String medEquipReqID = rs.getString("MED_EQUIPMENTID");
      String reqDate = rs.getString("REQUESTDATE");
      String deliveryDate = rs.getString("DELIVERYDATE");
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
              reqDate,
              deliveryDate);
      medicalRequests.add(equipRequest);
      // numID++;
    }
    rs.close();
  }

  public List<medicalEquipmentRequest> getAllMedicalEquipmentServiceRequests() {
    return medicalRequests;
  }

  public medicalEquipmentRequest getMedicalEquipmentServiceRequest(String MedEquipReqID) {
    for (medicalEquipmentRequest request : medicalRequests) {
      if (request.getServiceRequestID().equalsIgnoreCase(MedEquipReqID)) return request;
    }
    return null; // TODO: Make a better return if ID is not found
  }

  public void updateMedicalEquipmentServiceRequest(medicalEquipmentRequest request) {}

  public void deleteMedicalEquipmentServiceRequest(medicalEquipmentRequest request) {}

  public void addMedEquipReq(medicalEquipmentRequest request) throws SQLException {
    medicalRequests.add(request);

    Statement statement = connection.createStatement();
    String query =
        "INSERT INTO MED_EQUIP_REQ VALUES ('"
            + request.getServiceRequestID()
            + "','"
            + request.getDeliveryDate()
            + "','"
            + request.getDeliveryTime()
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
    System.out.println(query);
    statement.executeUpdate(query);
  }
  /*
    public static void main(String[] args) throws SQLException {
      MedicalEquipmentServiceRequestDAOImpl db = new MedicalEquipmentServiceRequestDAOImpl();
      db.addMedEquipReq(
          new medicalEquipmentRequest(
              "1234", "note", "floor", "45", false, "inprogress", "me", "bed", 5, "1/12", "1/13"));
    }
  */
}
