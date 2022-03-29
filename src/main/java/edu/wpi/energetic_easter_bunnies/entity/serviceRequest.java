package edu.wpi.energetic_easter_bunnies.entity;

import java.util.Date;

public abstract class serviceRequest {

  public enum Type {
    sanitationRequest,
    medicalEquipmentRequest,
    mealDeliveryRequest
  }

  private Date requestDate;
  private Date deliveryDate;

  private String otherNotes;
  private String floorID;
  private String roomID;
  private boolean isUrgent;
  private Type requestType;

  public Type getRequestType() {

    return requestType;
  }

  public void setRequestType(Type requestType) {
    this.requestType = requestType;
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

  public boolean isUrgent() {
    return isUrgent;
  }

  public void setUrgent(boolean urgent) {
    isUrgent = urgent;
  }
}
