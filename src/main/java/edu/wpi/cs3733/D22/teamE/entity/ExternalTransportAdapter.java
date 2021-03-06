package edu.wpi.cs3733.D22.teamE.entity;

import edu.wpi.cs3733.D22.teamZ.api.entity.ExternalTransportRequest;
import edu.wpi.cs3733.D22.teamZ.api.entity.RequestStatus;
import edu.wpi.cs3733.D22.teamZ.api.entity.TransportMethod;
import java.time.LocalDate;
import java.util.Arrays;

public class ExternalTransportAdapter implements RequestInterface {
  private ExternalTransportRequest r;

  public ExternalTransportAdapter(ExternalTransportRequest r) {
    this.r = r;
  }

  public ExternalTransportRequest getRequest() {
    return r;
  }

  public String getIssuerID() {
    return r.getIssuerID();
  }

  public void setIssuerID(String s) {
    if (!s.equals(getIssuerID())) throw new RuntimeException("Cannot Change IssuerID");
  }

  public String getTransportDestination() {
    return r.getTransportDestination();
  }

  public void setTransportDestination(String dest) {
    r.setTransportDestination(dest);
  }

  public String getPatientID() {
    return r.getPatientID();
  }

  public void setPatientID(String s) {
    if (!s.equals(getPatientID())) throw new RuntimeException("Cannot Change PatientID");
  }

  public String getTransportMethod() {
    return r.getTransportMethod().toString();
  }

  public LocalDate getDepartureDate() {
    return r.getDepartureDate();
  }

  public void setDepartureDate(LocalDate date) {
    r.setDepartureDate(date);
  }

  public void setTransportMethod(String method) {
    try {
      r.setTransportMethod(TransportMethod.valueOf(method));
    } catch (Exception e) {
      throw new RuntimeException(
          "Transport Method must be one of " + Arrays.toString(TransportMethod.values()));
    }
  }

  private boolean isUrgent = false;

  @Override
  public serviceRequest.Type getRequestType() {
    return serviceRequest.Type.SERVICEREQUEST;
  }

  @Override
  public void setRequestType(serviceRequest.Type requestType) {}

  @Override
  public String getServiceRequestID() {
    return r.getRequestID();
  }

  @Override
  public void setServiceRequestID(String requestID) {}

  @Override
  public LocalDate getRequestDate() {
    return r.getDepartureDate();
  }

  @Override
  public void setRequestDate(LocalDate requestDate) {}

  @Override
  public LocalDate getDeliveryDate() {
    return r.getDepartureDate();
  }

  @Override
  public void setDeliveryDate(LocalDate deliveryDate) {}

  @Override
  public String getRequestStatus() {
    return r.getStatus().toString();
  }

  @Override
  public void setRequestStatus(String status) {
    try {
      r.setStatus(RequestStatus.valueOf(status));
    } catch (Exception e) {
      throw new RuntimeException(
          "Status must be one of " + Arrays.toString(RequestStatus.values()));
    }
  }

  @Override
  public boolean getIsUrgent() {
    LocalDate n = LocalDate.now();
    return r.getDepartureDate().isBefore(n) || r.getDepartureDate().isEqual(n) || isUrgent;
  }

  @Override
  public void setIsUrgent(boolean urgent) {
    this.isUrgent = urgent;
  }

  @Override
  public String getStaffAssignee() {
    return r.getHandlerID();
  }

  @Override
  public void setStaffAssignee(String assignee) {
    r.setHandlerID(assignee);
  }

  @Override
  public int getNumID() {
    return 0;
  }

  @Override
  public void setNumID(int num) {}

  @Override
  public Location getLocation() {
    return null;
  }

  @Override
  public String getRoomID() {
    return "null";
  }

  @Override
  public void setRoomID(String roomID) {}

  @Override
  public String getFloorID() {
    return "null";
  }

  @Override
  public void setLocation(String NodeID) throws NullPointerException {}

  @Override
  public void setLocation(Location location) throws NullPointerException {}

  @Override
  public void setLocation(int xcoord, int ycoord) throws NullPointerException {}

  @Override
  public int getXCoord() {
    return 0;
  }

  @Override
  public int getYCoord() {
    return 0;
  }

  @Override
  public void setFloorID(String floor) {}

  @Override
  public String getOtherNotes() {
    return "null";
  }

  @Override
  public void setOtherNotes(String otherNotes) {}

  @Override
  public String toString() {
    return "TRANSPORT_REQ_EXT : " + getServiceRequestID();
  }
}
