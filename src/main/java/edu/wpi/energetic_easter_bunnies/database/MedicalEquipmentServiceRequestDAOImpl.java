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
    String query = "SELECT * FROM MED_EQUIP_REQ ORDER BY ID DESC";
    ResultSet rs = statement.executeQuery(query);
    int numID = 0;
    while (rs.next()) {

      MedicalEquipmentRequest equipRequest = new MedicalEquipmentRequest();

      medicalRequests.add(equipRequest);
      numID++;
    }
    rs.close();
  }

  public List<ServiceRequest> getAllMedicalEquipmentServiceRequests() {
    return null;
  }

  public ServiceRequest getMedicalEquipmentServiceRequest(int numID) {
    return null;
  }

  public void updateMedicalEquipmentServiceRequest(ServiceRequest serviceRequest) {}

  public void deleteMedicalEquipmentServiceRequest(ServiceRequest serviceRequest) {}
}
