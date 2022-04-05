package edu.wpi.energetic_easter_bunnies.entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class serviceRequestModel {

  SimpleIntegerProperty ID;
  SimpleBooleanProperty isUrgent;
  SimpleStringProperty Status, Type, Assignee, requestDate, deliveryDate;

  public serviceRequestModel(
      Integer ID,
      String Status,
      String Type,
      String Assignee,
      String requestDate,
      String deliveryDate,
      boolean isUrgent) {
    this.ID = new SimpleIntegerProperty(ID);
    this.Status = new SimpleStringProperty(Status);
    this.Type = new SimpleStringProperty(Type);
    this.Assignee = new SimpleStringProperty(Assignee);
    this.requestDate = new SimpleStringProperty(requestDate);
    this.deliveryDate = new SimpleStringProperty(deliveryDate);
    this.isUrgent = new SimpleBooleanProperty(isUrgent);
  }

  public int getID() {
    return ID.get();
  }

  public SimpleIntegerProperty IDProperty() {
    return ID;
  }

  public void setID(int ID) {
    this.ID.set(ID);
  }

  public boolean isIsUrgent() {
    return isUrgent.get();
  }

  public SimpleBooleanProperty isUrgentProperty() {
    return isUrgent;
  }

  public void setIsUrgent(boolean isUrgent) {
    this.isUrgent.set(isUrgent);
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
}
