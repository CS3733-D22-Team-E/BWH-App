package edu.wpi.energetic_easter_bunnies.database;

import java.sql.SQLException;
import java.util.List;

public interface MedicalEquipmentDAO {
  List<MedicalEquipment> getAllMedicalEquipment();

  List<MedicalEquipment> getMedicalEquipments(
      String equipmentType, int equipmentQuantity, String roomID, String MED_EQUIPMENTID)
      throws SQLException;

  void sendToCleaning(List<MedicalEquipment> equipments) throws SQLException;
}
