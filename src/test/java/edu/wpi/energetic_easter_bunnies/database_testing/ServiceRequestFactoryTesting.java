package edu.wpi.energetic_easter_bunnies.database_testing;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.wpi.cs3733.D22.teamE.entity.RequestInterface;
import edu.wpi.cs3733.D22.teamE.entity.ServiceRequestFactory;
import org.junit.jupiter.api.Test;

public class ServiceRequestFactoryTesting {
  @Test
  public void medServiceRequestFactoryTest() {
    ServiceRequestFactory factory = new ServiceRequestFactory();
    RequestInterface medReq = factory.getServiceRequest("MED_EQUIP_REQ");
    assertNotNull(medReq.getRequestStatus());
  }
}
