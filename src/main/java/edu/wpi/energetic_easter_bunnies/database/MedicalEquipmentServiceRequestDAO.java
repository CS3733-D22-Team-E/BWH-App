package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.MedicalEquipmentRequest;
import edu.wpi.energetic_easter_bunnies.entity.ServiceRequest;
import java.util.List;

public interface MedicalEquipmentServiceRequestDAO {
  List<MedicalEquipmentRequest> getAllMedicalEquipmentServiceRequests();

  ServiceRequest getMedicalEquipmentServiceRequest(int numID);

  void updateMedicalEquipmentServiceRequest(MedicalEquipmentRequest medicalEquipmentRequest);

  void deleteMedicalEquipmentServiceRequest(MedicalEquipmentRequest medicalEquipmentRequest);
}
