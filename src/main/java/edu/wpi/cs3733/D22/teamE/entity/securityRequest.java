package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class securityRequest extends serviceRequest {
  private String securityRequestType;
  private String timeFrame;

  private List<String> timeVals =
      new ArrayList<>() {
        {
          add("ASAP");
          add("<1 day");
          add("<1 week");
        }
      };

  public securityRequest(
      String securityRequestID,
      String securityRequestType,
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
        securityRequestID,
        String.valueOf(Type.LAB_REQUEST),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    setSecurityRequestType(securityRequestType);
    setTimeFrame(timeFrame);
  }

  public securityRequest() {
    super(String.valueOf(Type.LAB_REQUEST));
    this.securityRequestType = "";
    this.timeFrame = "";
  }

  public String getSecurityRequestType() {
    return securityRequestType;
  }

  public void setSecurityRequestType(String securityRequestType) {
    this.securityRequestType = securityRequestType;
  }

  public void setTimeFrame(String timeFrame) {
    if (!timeVals.contains(timeFrame))
      throw new RuntimeException("TimeFrame must be one of " + timeVals);
    else this.timeFrame = timeFrame;
  }

  public String getTimeFrame() {
    return timeFrame;
  }
}
