package edu.wpi.energetic_easter_bunnies.entity;

public abstract class serviceRequest {
  /*
    public enum Type {
      sanitationRequest,
      medicalEquipmentRequest,
      mealDeliveryRequest,
      translatorRequest,
      medicineRequest
    }
  */
  // private Date requestDate; //TODO: Implement later
  // private Date deliveryDate; //TODO: Implement later
  private String serviceRequestID;
  private String otherNotes;
  private String floorID;
  private String roomID;
  private boolean isUrgent;
  // private Type requestType;

  private String requestStatus;
  private String staffAssignee;

  // public Type getRequestType() { return requestType; }

  // public void setRequestType(Type requestType) { this.requestType = requestType; }

  public String getServiceRequestID() {
    return serviceRequestID;
  }

  public void setServiceRequestID(String serviceRequestID) {
    this.serviceRequestID = serviceRequestID;
  }

  public serviceRequest(
      String serviceRequestID,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee) {
    this.serviceRequestID = serviceRequestID;
    this.otherNotes = otherNotes;
    this.floorID = floorID;
    this.roomID = roomID;
    this.isUrgent = isUrgent;
    this.requestStatus = requestStatus;
    this.staffAssignee = staffAssignee;
  }

  public serviceRequest() {
    this.serviceRequestID = "";
    this.otherNotes = "";
    this.floorID = "";
    this.roomID = "";
    this.isUrgent = false;
    this.requestStatus = "";
    this.staffAssignee = "";
  }

  public String getFloorID() {
    return floorID;
  }

  public void setFloorID(String floorID) {
    this.floorID = floorID;
  }

  public String getRoomID() {
    return roomID;
  }

  public void setRoomID(String roomID) {
    this.roomID = roomID;
  }

  public String getOtherNotes() {
    return otherNotes;
  }

  public void setOtherNotes(String otherNotes) {
    this.otherNotes = otherNotes;
  }

  public boolean getIsUrgent() {
    return isUrgent;
  }

  public void setUrgent(boolean urgent) {
    isUrgent = urgent;
  }

  public String getRequestStatus() {
    return requestStatus;
  }

  public void setRequestStatus(String requestStatus) {
    this.requestStatus = requestStatus;
  }

  public String getStaffAssignee() {
    return staffAssignee;
  }

  public void setStaffAssignee(String staffAssignee) {
    this.staffAssignee = staffAssignee;
  }
}
