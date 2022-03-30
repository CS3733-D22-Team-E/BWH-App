package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
import java.util.List;

public interface MedicalEquipmentServiceRequestDAO {
  List<serviceRequest> getAllMedicalEquipmentServiceRequests();

  serviceRequest getMedicalEquipmentServiceRequest(int numID);

  void updateMedicalEquipmentServiceRequest(serviceRequest serviceRequest);

  void deleteMedicalEquipmentServiceRequest(serviceRequest serviceRequest);
}
