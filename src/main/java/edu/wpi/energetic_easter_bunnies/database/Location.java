package edu.wpi.energetic_easter_bunnies.database;

public class Location {
  private final String nodeID;
  private final Integer xcoord, ycoord;
  private String floor;
  private final String building;
  private String nodeType;
  private final String longName, shortName;
  private final int numID;

  public Location() {
    this.nodeID = "";
    this.xcoord = 0;
    this.ycoord = 0;
    this.floor = "";
    this.building = "";
    this.nodeType = "";
    this.longName = "";
    this.shortName = "";
    this.numID = 0;
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
