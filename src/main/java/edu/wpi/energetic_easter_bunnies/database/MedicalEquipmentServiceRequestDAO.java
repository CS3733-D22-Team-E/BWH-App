package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.medicalEquipmentRequest;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
import java.util.List;

public interface MedicalEquipmentServiceRequestDAO {
  List<medicalEquipmentRequest> getAllMedicalEquipmentServiceRequests();

  serviceRequest getMedicalEquipmentServiceRequest(int numID);

  void updateMedicalEquipmentServiceRequest(medicalEquipmentRequest medicalEquipmentRequest);

  void deleteMedicalEquipmentServiceRequest(medicalEquipmentRequest medicalEquipmentRequest);
}
