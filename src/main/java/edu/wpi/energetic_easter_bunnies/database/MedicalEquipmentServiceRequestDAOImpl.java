package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.MedicalEquipmentRequest;
import edu.wpi.energetic_easter_bunnies.entity.ServiceRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentServiceRequestDAOImpl implements MedicalEquipmentServiceRequestDAO {
  List<MedicalEquipmentRequest> medicalRequests;

  public MedicalEquipmentServiceRequestDAOImpl() throws SQLException {
    medicalRequests = new ArrayList<>();
    String url = "jdbc:derby:myDB;";
    Connection connection = DriverManager.getConnection(url);
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM MEDEQUIPREQ ORDER BY REQUESTDATE DESC";
    ResultSet rs = statement.executeQuery(query);
    //int numID = 0; //TODO: Assign Medical Requests an ID value
    while (rs.next()) {
      String reqDate = rs.getString("REQUUESTDATE");
      String deliveryDate = rs.getString("DELIVERYDATE");
      boolean isUrgent = rs.getBoolean("ISURGENT");
      String equipment = rs.getString("EQUIP");
      int equipQuantity = rs.getInt("EQUIPQUANTITY");
      String staffAssignee = rs.getString("STAFFASSIGNEE");
      String locNodeID = rs.getString("LOCNODEID");
      String floor = rs.getString("FLOOR");
      String requestStatus = rs.getString("REQUESTSTATUS");
      String otherNotes = rs.getString("OTHERNOTES");

      MedicalEquipmentRequest equipRequest = new MedicalEquipmentRequest(otherNotes, floor, locNodeID, isUrgent, requestStatus, staffAssignee, equipment, equipQuantity, reqDate, deliveryDate);
      medicalRequests.add(equipRequest);
      //numID++;
    }
    rs.close();
  }

  public List<MedicalEquipmentRequest> getAllMedicalEquipmentServiceRequests() {
    return medicalRequests;
  }

  public MedicalEquipmentRequest getMedicalEquipmentServiceRequest(int numID) {
    return medicalRequests.get(numID);
  } //TODO: Add ID Value as extra parameter

  public void updateMedicalEquipmentServiceRequest(MedicalEquipmentRequest medicalEquipmentRequest) {}

  public void deleteMedicalEquipmentServiceRequest(MedicalEquipmentRequest medicalEquipmentRequest) {}
}
