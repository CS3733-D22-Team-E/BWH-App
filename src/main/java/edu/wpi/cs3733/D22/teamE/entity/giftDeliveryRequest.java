package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;

public class giftDeliveryRequest extends serviceRequest {
  private String patientName;
  private String gift;

  public giftDeliveryRequest(
      String serviceRequestID,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      LocalDate requestDate,
      LocalDate deliveryDate,
      String patientName,
      String gift) {
    super(
        serviceRequestID,
        String.valueOf(Type.GIFTREQUEST),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    this.patientName = patientName;
    this.gift = gift;
  }

  public giftDeliveryRequest() {
    super(String.valueOf(Type.GIFTREQUEST));
    this.patientName = "";
    this.gift = ""; // TODO: Maybe make default something else later
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public String getGift() {
    return gift;
  }

  public void setGift(String gift) {
    this.gift = gift;
  }
}
