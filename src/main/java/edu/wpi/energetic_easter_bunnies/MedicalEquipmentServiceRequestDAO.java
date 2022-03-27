package edu.wpi.energetic_easter_bunnies;

import java.util.List;

public interface MedicalEquipmentServiceRequestDAO {
  List<MedicalEquipmentServiceRequest> getAllMedicalEquipmentServiceRequests();

  MedicalEquipmentServiceRequest getMedicalEquipmentServiceRequest(int numID);

  void updateMedicalEquipmentServiceRequest(MedicalEquipmentServiceRequest serviceRequest);

  void deleteMedicalEquipmentServiceRequest(MedicalEquipmentServiceRequest serviceRequest);
}
