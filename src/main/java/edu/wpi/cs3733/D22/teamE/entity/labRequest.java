package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;

public class labRequest extends serviceRequest {

  private enum timeFrame {
    ASAP,
    oneDay,
    oneWeek
  }

  private String labRequestType;
  private String timeFrame;

  public labRequest(
      String labRequestID,
      String labRequestType,
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
        labRequestID,
        String.valueOf(Type.LAB_REQUEST),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    setLabRequestType(labRequestType);
    setTimeFrame(timeFrame);
  }

  public labRequest() {
    super(String.valueOf(Type.LAB_REQUEST));
    this.labRequestType = "";
    this.timeFrame = "";
  }

  public String getLabRequestType() {
    return labRequestType;
  }

  public void setLabRequestType(String labRequestType) {
    this.labRequestType = labRequestType;
  }

  public void setTimeFrame(String timeFrame) {
    this.timeFrame = timeFrame;
  }

  public String getTimeFrame() {
    return timeFrame;
  }
}
