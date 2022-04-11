package edu.wpi.energetic_easter_bunnies.database;

import edu.wpi.energetic_easter_bunnies.entity.*;

public class ServiceRequestFactory {
  public serviceRequest getServiceRequest(String serviceRequestType) {
    if (serviceRequestType == null) return null;
    if (serviceRequestType.equalsIgnoreCase("MED_EQUIP_REQ")) return new medicalEquipmentRequest();
    else if (serviceRequestType.equalsIgnoreCase("LAB_REQUEST")) return new labRequest();
    else if (serviceRequestType.equalsIgnoreCase("LANG_INTERP_REQ"))
      return new languageInterpreterRequest();
    else if (serviceRequestType.equalsIgnoreCase("MEAL_DELIV_REQ"))
      return new mealDeliveryRequest();
    else if (serviceRequestType.equalsIgnoreCase("SANITATION_REQ")) return new sanitationRequest();
    else if (serviceRequestType.equalsIgnoreCase("MED_DELIV_REQ")) return new medicineDelivery();
    else if (serviceRequestType.equalsIgnoreCase("SERVICEREQUEST"))
      return new serviceRequest(String.valueOf(serviceRequest.Type.SERVICEREQUEST));
    return null;
  }
}
