package edu.wpi.cs3733.D22.teamE.entity;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class locationModel {

  SimpleStringProperty nodeID;
  SimpleIntegerProperty xcoord, ycoord;
  SimpleStringProperty floor;
  SimpleStringProperty building;
  SimpleStringProperty nodeType;
  SimpleStringProperty longName, shortName;

  public locationModel(
      String nodeID,
      Integer xcoord,
      Integer ycoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.nodeID = new SimpleStringProperty(nodeID);
    this.xcoord = new SimpleIntegerProperty(xcoord);
    this.ycoord = new SimpleIntegerProperty(ycoord);
    this.floor = new SimpleStringProperty(floor);
    this.building = new SimpleStringProperty(building);
    this.nodeType = new SimpleStringProperty(nodeType);
    this.longName = new SimpleStringProperty(longName);
    this.shortName = new SimpleStringProperty(shortName);
  }

  public String getNodeID() {
    return nodeID.get();
  }

  public SimpleStringProperty nodeIDProperty() {
    return nodeID;
  }

  public int getXcoord() {
    return xcoord.get();
  }

  public SimpleIntegerProperty xcoordProperty() {
    return xcoord;
  }

  public int getYcoord() {
    return ycoord.get();
  }

  public SimpleIntegerProperty ycoordProperty() {
    return ycoord;
  }

  public String getFloor() {
    return floor.get();
  }

  public SimpleStringProperty floorProperty() {
    return floor;
  }

  public String getBuilding() {
    return building.get();
  }

  public SimpleStringProperty buildingProperty() {
    return building;
  }

  public String getNodeType() {
    return nodeType.get();
  }

  public SimpleStringProperty nodeTypeProperty() {
    return nodeType;
  }

  public String getLongName() {
    return longName.get();
  }

  public SimpleStringProperty longNameProperty() {
    return longName;
  }

  public String getShortName() {
    return shortName.get();
  }

  public SimpleStringProperty shortNameProperty() {
    return shortName;
  }
}
