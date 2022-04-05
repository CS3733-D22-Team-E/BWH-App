package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.Equipment;
import edu.wpi.energetic_easter_bunnies.database.MedicalEquipment;
import java.sql.SQLException;
import java.util.List;

public interface MedicalEquipmentDAO {
  List<Equipment> getAllMedicalEquipment();

  Equipment getEquipment(int numID);

  void updateEquipment(Equipment equipment);

  void deleteEquipment(Equipment equipment);
}
