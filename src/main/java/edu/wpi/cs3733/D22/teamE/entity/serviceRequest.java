package edu.wpi.cs3733.D22.teamE.entity;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class serviceRequest implements RequestInterface {

  @Override
  public int getNumID() {
    return 0;
  }

  @Override
  public void setNumID(int num) {

  }

  @Override
  public Location getLocation() {
    return DAOSystemSingleton.INSTANCE.getSystem().getLocation(roomID);
  }

  @Override
  public void setLocation(String NodeID) throws NullPointerException {
    Location loc = DAOSystemSingleton.INSTANCE.getSystem().getLocation(NodeID);
    if (loc != null) {
      setRoomID(loc.getNodeID());
      setFloorID(loc.getFloor());
    }
  }

  @Override
  public void setLocation(Location location) throws NullPointerException {
    setRoomID(location.getNodeID());
    setFloorID(location.getFloor());
  }

  @Override
  public void setLocation(int xcoord, int ycoord) throws NullPointerException {
    Location loc = DAOSystemSingleton.INSTANCE.getSystem().getLocation(xcoord, ycoord);
    if (loc != null) {
      setRoomID(loc.getNodeID());
      setFloorID(loc.getFloor());
    }
  }

  @Override
  public double getXCoord() {
    return getLocation().getXcoord();
  }

  @Override
  public double getYCoord() {
    return getLocation().getYcoord();
  }

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
    SECURITY_REQ {
      @Override
      public String toString() {
        return "SECURITY_REQ";
      }
    },
    SERVICEREQUEST {
      @Override
      public String toString() {
        return "SERVICEREQUEST";
      }
    },
    GIFT_REQUEST {
      @Override
      public String toString() {
        return "GIFT_REQUEST";
      }
    },
    FACILITIES_REQ {
      @Override
      public String toString() {
        return "FACILITIES_REQ";
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

  public void setRequestType(String requestType) {
    this.requestType = Type.valueOf(requestType);
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
    this.serviceRequestID = RequestInterface.generateRandomID(6);
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
    // todo : verify correct floorID
    this.floorID = floorID;
  }

  public String getRoomID() {
    return roomID;
  }

  public void setRoomID(String roomID) {
    // todo : verify correct roomID
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

  public void setIsUrgent(boolean urgent) {
    isUrgent = urgent;
  }

  public void setIsUrgent(String urgent) {
    boolean t = Boolean.parseBoolean(urgent);
    if (!t && !urgent.equals("false"))
      throw new RuntimeException("IsUrgent must be one of {true, false}");
    else isUrgent = t;
  }

  public String getRequestStatus() {
    return requestStatus;
  }

  public void setRequestStatus(String requestStatus) {
    if (!(requestStatus.equals("Processing")
        || requestStatus.equals("Complete")
        || requestStatus.equals("To Do")))
      throw new RuntimeException("RequestStatus must be one of {To Do, Processing, Complete}");
    else this.requestStatus = requestStatus;
  }

  public String getStaffAssignee() {
    return staffAssignee;
  }

  public void setStaffAssignee(String staffAssignee) {
    // todo : implement assignee verification
    this.staffAssignee = staffAssignee;
  }

  public LocalDate getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(LocalDate requestDate) {
    this.requestDate = requestDate;
  }

  public void setRequestDate(String requestDate) {
    try {
      this.requestDate = LocalDate.parse(requestDate);
    } catch (DateTimeParseException e) {
      throw new RuntimeException("Date must be formatted as yyyy-mm-dd");
    }
  }

  public LocalDate getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(LocalDate deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    try {
      this.deliveryDate = LocalDate.parse(deliveryDate);
    } catch (DateTimeParseException e) {
      throw new RuntimeException("Date must be formatted as yyyy-mm-dd");
    }
  }

  @Override
  public String toString() {
    return getRequestType().toString() + " : {" + getServiceRequestID() + "}";
  }
}
