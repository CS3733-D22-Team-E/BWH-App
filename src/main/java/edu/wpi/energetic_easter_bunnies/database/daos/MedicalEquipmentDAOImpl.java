package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.DBConnection;
import edu.wpi.energetic_easter_bunnies.database.MedicalEquipment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalEquipmentDAOImpl implements MedicalEquipmentDAO {
  static Connection connection = DBConnection.getConnection();
  List<Equipment> equipmentList;

  public MedicalEquipmentDAOImpl() throws SQLException {
    equipmentList = new ArrayList<>();
    Statement statement = connection.createStatement();
    String query = "SELECT * FROM EQUIPMENT ORDER BY ID DESC";
    ResultSet rs = statement.executeQuery(query);
    int numID = 0;
    while (rs.next()) {
      String equipmentID = rs.getString("EQUIPMENTID");
      boolean isInUse = rs.getBoolean("isInUse");
      boolean isClean = rs.getBoolean("isClean");
      String cleanLocation = rs.getString("cleanLocation");
      String storageLocation = rs.getString("storageLocation");

      Equipment equipment =
          new MedicalEquipment(
              equipmentID, isInUse, isClean, cleanLocation, storageLocation, numID);

      equipmentList.add(equipment);
      numID++;
    }
    rs.close();
  }

  @Override
  public List<Equipment> getAllMedicalEquipment() {
    return null;
  }

  @Override
  public Equipment getEquipment(int numID) {
    return null;
  }

  @Override
  public void updateEquipment(Equipment equipment) {}

  @Override
  public void deleteEquipment(Equipment equipment) {}
}
