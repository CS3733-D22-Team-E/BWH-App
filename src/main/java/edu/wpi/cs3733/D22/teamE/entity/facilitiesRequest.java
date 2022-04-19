package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;

public class facilitiesRequest extends serviceRequest {

  public facilitiesRequest(String serviceRequestType) {
    super(serviceRequestType);
  }

  private enum timeFrame {
    ASAP,
    oneDay,
    oneWeek
  }

  private String facilitiesRequestType;
  private String timeFrame;

  public facilitiesRequest(
      String facilitiesRequestID,
      String facilitiesRequestType,
      String timeFrame,
      String floorID,
      String roomID,
      boolean isUrgent,
      String staffAssignee,
      String requestStatus,
      String otherNotes,
      LocalDate requestDate,
      LocalDate deliveryDate) {
    super(
        facilitiesRequestID,
        String.valueOf(Type.FACILITIES_REQ),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    setFacilitiesRequestType(facilitiesRequestType);
    setTimeFrame(timeFrame);
  }

  public facilitiesRequest() {
    super(String.valueOf(Type.FACILITIES_REQ));
    this.facilitiesRequestType = "";
    this.timeFrame = "";
  }

  public String getFacilitiesReqType() {
    return facilitiesRequestType;
  }

  public void setFacilitiesRequestType(String facilitiesRequestType) {
    this.facilitiesRequestType = facilitiesRequestType;
  }

  public void setTimeFrame(String timeFrame) {
    this.timeFrame = timeFrame;
  }

  public String getTimeFrame() {
    return timeFrame;
  }
}
