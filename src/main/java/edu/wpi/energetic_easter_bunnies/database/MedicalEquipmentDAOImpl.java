package edu.wpi.energetic_easter_bunnies.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentDAOImpl implements MedicalEquipmentDAO {
  static Connection connection = DBConnection.getConnection();
  List<Equipment> equipmentList;

  public MedicalEquipmentDAOImpl() throws SQLException {
    equipmentList = new ArrayList<>();
    // String url = "jdbc:derby:myDB;";
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM EQUIPMENT ORDER BY ID DESC";
    ResultSet rs = statement.executeQuery(query);
    int numID = 0;
    while (rs.next()) {
      String equipmentID = rs.getString("EQUIPMENTID");
      String med_EquipmentID = rs.getString("MED_EQUIPMENTID");
      boolean isInUse = rs.getBoolean("isInUse");
      boolean isClean = rs.getBoolean("isClean");
      String cleanLocation = rs.getString("cleanLocationID");
      String storageLocation = rs.getString("storageLocationID");
      String currentLocation = rs.getString("currentLocationID");
      String equipmentType = rs.getString("equipmentType");

      Equipment equipment =
          new MedicalEquipment(
              equipmentID,
              med_EquipmentID,
              isInUse,
              isClean,
              cleanLocation,
              storageLocation,
              currentLocation,
              equipmentType,
              numID);
      equipmentList.add(equipment);
      numID++;
    }
    rs.close();
  }

  @Override
  public List<Equipment> getAllMedicalEquipment() {
    return equipmentList;
  }

  @Override
  public Equipment getEquipment(int numID) {
    return null;
  }

  @Override
  public void updateEquipment(Equipment equipment) throws SQLException {
    equipmentList.add(equipment);

    Statement statement = connection.createStatement();
    String query = "";
  }
}
