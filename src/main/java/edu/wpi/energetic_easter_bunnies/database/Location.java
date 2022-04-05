package edu.wpi.energetic_easter_bunnies.database;

public class Location {
  private final String nodeID;
  private Integer xcoord;
  private Integer ycoord;
  private String floor;
  private final String building;
  private String nodeType;
  private final String longName, shortName;
  private final int numID;

  public Location() {
    this.nodeID = "1";
    this.xcoord = 1;
    this.ycoord = 1;
    this.floor = "";
    this.building = "";
    this.nodeType = "";
    this.longName = "";
    this.shortName = "";
    this.numID = 1;
  }

  public Location(
      String nodeID,
      Integer xcoord,
      Integer ycoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName,
      int numID) {
    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
    this.numID = numID;
  }

  public String getNodeID() {
    return nodeID;
  }

  public Integer getXcoord() {
    return xcoord;
  }

  public void setXCoord(Integer newXCoord) {
    xcoord = newXCoord;
  }

  public void setYCoord(Integer newYCoord) {
    ycoord = newYCoord;
  }

  public Integer getYcoord() {
    return ycoord;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getBuilding() {
    return building;
  }

  public String getNodeType() {
    return nodeType;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
  }

  public String getLongName() {
    return longName;
  }

  public String getShortName() {
    return shortName;
  }

  public int getNumID() {
    return numID;
  }

  public String toString() {
    return "nodeID: "
        + nodeID
        + "\txcoord: "
        + xcoord
        + "\tycoord: "
        + ycoord
        + "\tfloor: "
        + floor
        + "\tbuilding: "
        + building
        + "\tnodeType: "
        + nodeType
        + "\tlong name: "
        + longName
        + "\tshort name: "
        + shortName;
  }
}
