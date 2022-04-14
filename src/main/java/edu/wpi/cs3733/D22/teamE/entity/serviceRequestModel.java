package edu.wpi.cs3733.D22.teamE.entity;

import javafx.beans.property.SimpleStringProperty;

public class serviceRequestModel {

  SimpleStringProperty ID,
      Status,
      Type,
      Assignee,
      requestDate,
      deliveryDate,
      floorID,
      roomID,
      notes,
      isUrgent;

  public serviceRequestModel(
      String ID,
      String Status,
      String Type,
      String Assignee,
      String requestDate,
      String deliveryDate,
      boolean isUrgent,
      String note) {
    this.ID = new SimpleStringProperty(ID);
    this.Status = new SimpleStringProperty(Status);
    this.Type = new SimpleStringProperty(Type);
    this.Assignee = new SimpleStringProperty(Assignee);
    this.requestDate = new SimpleStringProperty(requestDate);
    this.deliveryDate = new SimpleStringProperty(deliveryDate);
    this.isUrgent = new SimpleStringProperty(String.valueOf(isUrgent));
    this.notes = new SimpleStringProperty(note);
  }

  public String getNotes() {
    return notes.get();
  }

  public SimpleStringProperty notesProperty() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes.set(notes);
  }

  public String getID() {
    return ID.get();
  }

  public SimpleStringProperty IDProperty() {
    return ID;
  }

  public void setID(String ID) {
    this.ID.set(ID);
  }

  public String isIsUrgent() {
    return isUrgent.get();
  }

  public SimpleStringProperty isUrgentProperty() {
    return isUrgent;
  }

  public void setIsUrgent(boolean isUrgent) {
    this.isUrgent.set(String.valueOf(isUrgent));
  }

  public String getStatus() {
    return Status.get();
  }

  public SimpleStringProperty statusProperty() {
    return Status;
  }

  public void setStatus(String status) {
    this.Status.set(status);
  }

  public String getType() {
    return Type.get();
  }

  public SimpleStringProperty typeProperty() {
    return Type;
  }

  public void setType(String type) {
    this.Type.set(type);
  }

  public String getAssignee() {
    return Assignee.get();
  }

  public SimpleStringProperty assigneeProperty() {
    return Assignee;
  }

  public void setAssignee(String assignee) {
    this.Assignee.set(assignee);
  }

  public String getRequestDate() {
    return requestDate.get();
  }

  public SimpleStringProperty requestDateProperty() {
    return requestDate;
  }

  public void setRequestDate(String requestDate) {
    this.requestDate.set(requestDate);
  }

  public String getDeliveryDate() {
    return deliveryDate.get();
  }

  public SimpleStringProperty deliveryDateProperty() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate.set(deliveryDate);
  }

  public String getFloorID() {
    return floorID.get();
  }

  public SimpleStringProperty floorIDProperty() {
    return floorID;
  }

  public void setFloorID(String floorID) {
    this.floorID.set(floorID);
  }

  public String getRoomID() {
    return roomID.get();
  }

  public SimpleStringProperty roomIDProperty() {
    return roomID;
  }

  public void setRoomID(String roomID) {
    this.roomID.set(roomID);
  }
}
