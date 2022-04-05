package edu.wpi.energetic_easter_bunnies.entity;

public class labRequest extends serviceRequest {

  private enum timeFrame {
    ASAP,
    oneDay,
    oneWeek
  }

  private String labRequestType;
  private String building;
  private String floor;
  private String timeFrame;
  private String locNodeID;

  public labRequest(
      String labRequestID,
      String labRequestType,
      String timeFrame,
      String staffAssignee,
      String locNodeID,
      String requestStatus,
      String otherNotes) {
    setServiceRequestID(labRequestID);
    setLabRequestType(labRequestType);
    setTimeFrame(timeFrame);
    setStaffAssignee(staffAssignee);
    setLocNodeID(locNodeID);
    setRequestStatus(requestStatus);
    setOtherNotes(otherNotes);
  }

  public String getLabRequestType() {
    return labRequestType;
  }

  public void setLabRequestType(String labRequestType) {
    this.labRequestType = labRequestType;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public void setTimeFrame(String timeFrame) {
    this.timeFrame = timeFrame;
  }

  public String getTimeFrame() {
    return timeFrame;
  }

  public void setLocNodeID(String locNodeID) {
    this.locNodeID = locNodeID;
  }

  public String getLocNodeID() {
    return locNodeID;
  }
}
