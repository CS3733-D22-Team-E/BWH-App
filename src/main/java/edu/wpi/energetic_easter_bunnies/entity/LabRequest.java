package edu.wpi.energetic_easter_bunnies.entity;

public class LabRequest extends serviceRequest {

  private enum timeFrame {
    ASAP,
    oneDay,
    oneWeek
  }

  private String labRequestType;
  private String building;
  private String floor;

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
}
