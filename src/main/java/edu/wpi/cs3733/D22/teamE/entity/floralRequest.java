package edu.wpi.cs3733.D22.teamE.entity;

public class floralRequest extends serviceRequest {
  String flower;
  String deliveryTime;

  public floralRequest(FloralServiceRequest r) {
    super(
        r.getRequestID(),
        String.valueOf(Type.SERVICEREQUEST),
        r.getOtherNotes(),
        r.getFloor(),
        r.getRoomID(),
        r.isUrgent(),
        r.getStatus(),
        r.getAssignee(),
        r.getRequestDate(),
        r.getDeliveryDate());
    setFlower(r.getFlower());
    setDeliveryTime(r.getDeliveryTime());
  }

  public String getFlower() {
    return this.flower;
  }

  public void setFlower(String flower) {
    this.flower = flower;
  }

  public String getDeliveryTime() {
    return this.deliveryTime;
  }

  public void setDeliveryTime(String deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  @Override
  public String toString() {
    return "FLORAL_REQ" + " : {" + getServiceRequestID() + "}";
  }
}
