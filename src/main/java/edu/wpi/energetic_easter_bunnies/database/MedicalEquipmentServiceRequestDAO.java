package edu.wpi.energetic_easter_bunnies.database;

import java.util.List;

public interface MedicalEquipmentServiceRequestDAO {
  List<ServiceRequest> getAllMedicalEquipmentServiceRequests();

  ServiceRequest getMedicalEquipmentServiceRequest(int numID);

  void updateMedicalEquipmentServiceRequest(ServiceRequest serviceRequest);

  void deleteMedicalEquipmentServiceRequest(ServiceRequest serviceRequest);
}
