package edu.wpi.energetic_easter_bunnies.database;

import java.sql.SQLException;
import java.util.List;

public interface MedicalEquipmentDAO {
  List<Equipment> getAllMedicalEquipment();

  Equipment getEquipment(int numID);

  void updateEquipment(Equipment equipment) throws SQLException;
}
