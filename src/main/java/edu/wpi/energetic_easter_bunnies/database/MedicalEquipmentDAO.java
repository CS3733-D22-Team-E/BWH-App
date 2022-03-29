package edu.wpi.energetic_easter_bunnies.database;

import java.util.List;

public interface MedicalEquipmentDAO {
  List<Equipment> getAllMedicalEquipment();

  Equipment getEquipment(int numID);

  void updateEquipment(Equipment equipment);

  void deleteEquipment(Equipment equipment);
}
