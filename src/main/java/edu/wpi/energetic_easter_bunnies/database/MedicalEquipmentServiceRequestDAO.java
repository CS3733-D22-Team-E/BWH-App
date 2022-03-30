package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.ServiceRequest;
import java.util.List;

public interface MedicalEquipmentServiceRequestDAO {
  List<ServiceRequest> getAllMedicalEquipmentServiceRequests();

  ServiceRequest getMedicalEquipmentServiceRequest(int numID);

  void updateMedicalEquipmentServiceRequest(ServiceRequest serviceRequest);

  void deleteMedicalEquipmentServiceRequest(ServiceRequest serviceRequest);
}
