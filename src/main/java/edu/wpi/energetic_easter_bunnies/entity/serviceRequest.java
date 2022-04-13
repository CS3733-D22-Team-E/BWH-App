package edu.wpi.energetic_easter_bunnies.entity;

import java.time.LocalDate;
import java.util.Random;

public class serviceRequest {
  public enum Type {
    SANITATION_REQ {
      @Override
      public String toString() {
        return "SANITATION_REQ";
      }
    },
    MED_EQUIP_REQ {
      @Override
      public String toString() {
        return "MED_EQUIP_REQ";
      }
    },
    LAB_REQUEST {
      @Override
      public String toString() {
        return "LAB_REQUEST";
      }
    },
    MEAL_DELIV_REQ {
      @Override
      public String toString() {
        return "MEAL_DELIV_REQ";
      }
    },
    LANG_INTERP_REQ {
      @Override
      public String toString() {
        return "LANG_INTERP_REQ";
      }
    },
    MED_DELIV_REQ {
      @Override
      public String toString() {
        return "MED_DELIV_REQ";
      }
    },
    SERVICEREQUEST {
      @Override
      public String toString() {
        return "SERVICEREQUEST";
      }
    }
  }

  private LocalDate requestDate; // TODO: Implement later
  private LocalDate deliveryDate; // TODO: Implement later
  private String serviceRequestID;
  private String otherNotes;
  private String floorID;
  private String roomID;
  private boolean isUrgent;
  private Type requestType;
  private int xCoord;
  private int yCoord;

  private String requestStatus;
  private String staffAssignee;

  public Type getRequestType() {
    return requestType;
  }

  public void setRequestType(Type requestType) {
    this.requestType = requestType;
  }

  public serviceRequest(
      String serviceRequestID,
      String serviceRequestType,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      LocalDate requestDate,
      LocalDate deliveryDate) {
    this.serviceRequestID = serviceRequestID;
    this.requestType = Type.valueOf(serviceRequestType);
    this.otherNotes = otherNotes;
    this.floorID = floorID;
    this.roomID = roomID;
    this.isUrgent = isUrgent;
    this.requestStatus = requestStatus;
    this.staffAssignee = staffAssignee;
    this.requestDate = requestDate;
    this.deliveryDate = deliveryDate;
  }

  public serviceRequest(String serviceRequestType) {
    this.serviceRequestID = generateRandomID(6);
    this.requestType = Type.valueOf(serviceRequestType);
    this.otherNotes = "";
    this.floorID = "";
    this.roomID = "";
    this.isUrgent = false;
    this.requestStatus = "";
    this.staffAssignee = "";
  }

  public String getServiceRequestID() {
    return serviceRequestID;
  }

  public void setServiceRequestID(String serviceRequestID) {
    this.serviceRequestID = serviceRequestID;
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

  public static String generateRandomID(int length) {
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder result = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      char randChar = alphabet.charAt(random.nextInt(alphabet.length()));
      result.append(randChar);
    }
    return result.toString();
  }

  public LocalDate getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(LocalDate requestDate) {
    this.requestDate = requestDate;
  }

  public LocalDate getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(LocalDate deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public boolean isUrgent() {
    return isUrgent;
  }

  public int getxCoord() {
    return xCoord;
  }

  public void setxCoord(int xCoord) {
    this.xCoord = xCoord;
  }

  public int getyCoord() {
    return yCoord;
  }

  public void setyCoord(int yCoord) {
    this.yCoord = yCoord;
  }
<<<<<<< HEAD
=======

  @Override
  public String toString() {
    return getRequestType().toString() + " : {" + getServiceRequestID() + "}";
  }
>>>>>>> Joey_Working_Branch
}
