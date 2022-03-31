package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentServiceRequestDAOImpl implements MedicalEquipmentServiceRequestDAO {
  List<medicalEquipmentRequest> medicalRequests;

  public MedicalEquipmentServiceRequestDAOImpl() throws SQLException {
    medicalRequests = new ArrayList<>();
    String url = "jdbc:derby:myDB;";
    Connection connection = DriverManager.getConnection(url);
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM MED_EQUIP_REQ ORDER BY REQUESTDATE DESC";
    ResultSet rs = statement.executeQuery(query);
    // int numID = 0; //TODO: Assign Medical Requests an ID value
    while (rs.next()) {
      String medEquipReqID = rs.getString("ID");
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

  public medicalEquipmentRequest getMedicalEquipmentServiceRequest(int numID) {
    return medicalRequests.get(numID);
  } // TODO: Add ID Value as extra parameter

  public void updateMedicalEquipmentServiceRequest(
      medicalEquipmentRequest medicalEquipmentRequest) {}

  public void deleteMedicalEquipmentServiceRequest(
      medicalEquipmentRequest medicalEquipmentRequest) {}
}
