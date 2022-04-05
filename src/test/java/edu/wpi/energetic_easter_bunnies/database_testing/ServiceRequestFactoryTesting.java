package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.wpi.energetic_easter_bunnies.database.ServiceRequestFactory;
import edu.wpi.energetic_easter_bunnies.entity.serviceRequest;
import org.junit.jupiter.api.Test;

public class ServiceRequestFactoryTesting {
  @Test
  public void medServiceRequestFactoryTest() {
    ServiceRequestFactory factory = new ServiceRequestFactory();
    serviceRequest medReq = factory.getServiceRequest("MED_EQUIP_REQ");
    assertNotNull(medReq.getRequestStatus());
  }
}
