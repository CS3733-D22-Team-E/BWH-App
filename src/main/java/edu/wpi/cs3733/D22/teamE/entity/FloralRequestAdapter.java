package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;

public class FloralRequestAdapter implements RequestInterface {
  private FloralServiceRequest r;

  public FloralRequestAdapter(FloralServiceRequest r) {
    this.r = r;
  }

  public FloralServiceRequest getRequest() {
    return r;
  }

  public String getFlower() {
    return r.getFlower();
  }

  public void setFlower(String flower) {
    r.setFlower(flower);
  }

  public String getDeliveryTime() {
    return r.getDeliveryTime();
  }

  public void setDeliveryTime(String deliveryTime) {
    r.setDeliveryTime(deliveryTime);
  }

  @Override
  public String toString() {
    return "FLORAL_REQ" + " : {" + getServiceRequestID() + "}";
  }

  @Override
  public serviceRequest.Type getRequestType() {
    return serviceRequest.Type.SERVICEREQUEST;
  }

  @Override
  public void setRequestType(serviceRequest.Type requestType) {}

  @Override
  public String getServiceRequestID() {
    return r.getServiceRequestID();
  }

  @Override
  public void setServiceRequestID(String requestID) {
    r.setServiceRequestID(requestID);
  }

  @Override
  public LocalDate getRequestDate() {
    return r.getRequestDate();
  }

  @Override
  public void setRequestDate(LocalDate requestDate) {
    r.setRequestDate(requestDate);
  }

  @Override
  public LocalDate getDeliveryDate() {
    return r.getDeliveryDate();
  }

  @Override
  public void setDeliveryDate(LocalDate deliveryDate) {
    r.setDeliveryDate(deliveryDate);
  }

  @Override
  public String getRequestStatus() {
    return r.getRequestStatus();
  }

  @Override
  public void setRequestStatus(String status) {
    r.setRequestStatus(status);
  }

  @Override
  public boolean getIsUrgent() {
    return r.getIsUrgent();
  }

  @Override
  public void setIsUrgent(boolean urgent) {
    r.setIsUrgent(urgent);
  }

  @Override
  public String getStaffAssignee() {
    return r.getStaffAssignee();
  }

  @Override
  public void setStaffAssignee(String assignee) {
    r.setStaffAssignee(assignee);
  }

  @Override
  public String getRoomID() {
    return r.getRoomID();
  }

  @Override
  public void setRoomID(String roomID) {
    r.setRoomID(roomID);
  }

  @Override
  public String getFloorID() {
    return r.getFloorID();
  }

  @Override
  public void setFloorID(String floor) {
    r.setFloorID(floor);
  }

  @Override
  public String getOtherNotes() {
    return r.getOtherNotes();
  }

  @Override
  public void setOtherNotes(String otherNotes) {
    r.setOtherNotes(otherNotes);
  }

  @Override
  public double getxCoord() {
    return 0;
  }

  @Override
  public double getyCoord() {
    return 0;
  }
}
