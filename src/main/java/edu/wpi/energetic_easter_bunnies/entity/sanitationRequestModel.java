package edu.wpi.energetic_easter_bunnies.entity;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;

public class sanitationRequestModel extends serviceRequestModel {
  SimpleStringProperty sizeString, bioString;

  public sanitationRequestModel(
      String ID,
      String Status,
      String Type,
      String Assignee,
      String requestDate,
      String deliveryDate,
      boolean isUrgent,
      String size,
      String biohazard,
      String roomID,
      String floorID) {
    super(ID, Status, Type, Assignee, requestDate, deliveryDate, isUrgent);
    sizeString = new SimpleStringProperty(size);
    bioString = new SimpleStringProperty(biohazard);
    this.roomID = new SimpleStringProperty(roomID);
    this.floorID = new SimpleStringProperty(floorID);
  }

  public sanitationRequestModel(sanitationRequest r) {
    super(
        r.getServiceRequestID(),
        r.getRequestStatus(),
        r.getRequestType().toString(),
        r.getStaffAssignee(),
        String.valueOf(LocalDate.now()),
        "null",
        r.isUrgent());
    sizeString = new SimpleStringProperty(r.getSizeValue());
    bioString = new SimpleStringProperty(r.getBiohazardValue());
    this.roomID = new SimpleStringProperty(r.getRoomID());
    this.floorID = new SimpleStringProperty(r.getFloorID());
  }

  public String getSizeString() {
    return sizeString.get();
  }

  public SimpleStringProperty sizeStringProperty() {
    return sizeString;
  }

  public void setSizeString(String sizeString) {
    this.sizeString.set(sizeString);
  }

  public String getBioString() {
    return bioString.get();
  }

  public SimpleStringProperty bioStringProperty() {
    return bioString;
  }

  public void setBioString(String bioString) {
    this.bioString.set(bioString);
  }
}