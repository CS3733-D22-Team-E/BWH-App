package edu.wpi.energetic_easter_bunnies.entity;

public abstract class serviceRequest {

  public enum Type {
    sanitationRequest,
    medicalEquipmentRequest,
    mealDeliveryRequest
  }

  // TODO : add location object proper

  private String otherNotes;
  private String location;
  private boolean isUrgent;
  private Type requestType;

  public Type getRequestType() {
    return requestType;
  }

  public void setRequestType(Type requestType) {
    this.requestType = requestType;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getOtherNotes() {
    return otherNotes;
  }

  public void setOtherNotes(String otherNotes) {
    this.otherNotes = otherNotes;
  }

  public boolean isUrgent() {
    return isUrgent;
  }

  public void setUrgent(boolean urgent) {
    isUrgent = urgent;
  }
}
