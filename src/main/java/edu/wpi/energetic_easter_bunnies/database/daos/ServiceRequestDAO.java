package edu.wpi.energetic_easter_bunnies.database.daos;

import edu.wpi.energetic_easter_bunnies.database.Equipment;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;

import java.util.List;

public interface ServiceRequestDAO {
    List<serviceRequest> getAllServiceRequests();

    serviceRequest getServiceRequest(int numID);

    void updateServiceRequest(serviceRequest request);

    void deleteServiceRequest(serviceRequest request);
}
