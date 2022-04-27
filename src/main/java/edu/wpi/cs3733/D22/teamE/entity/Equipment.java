package edu.wpi.cs3733.D22.teamE.entity;

import edu.wpi.cs3733.D22.teamE.database.daos.DAOSystemSingleton;

public abstract class Equipment implements EntityInterface {
  private final String equipmentID;
  private boolean isInUse;
  private boolean isClean;
  private String cleanLocation;
  private String storageLocation;
  private String currentLocation;
  private int numID;

  public Equipment(
      String equipmentID,
      boolean isInUse,
      boolean isClean,
      String cleanLocation,
      String storageLocation,
      String currentLocation,
      int numID) {
    this.equipmentID = equipmentID;
    this.isInUse = isInUse;
    this.isClean = isClean;
    this.cleanLocation = cleanLocation;
    this.storageLocation = storageLocation;
    this.currentLocation = currentLocation;
    this.numID = numID;
  }

  public String getEquipmentID() {
    return equipmentID;
  }

  public boolean isInUse() {
    return isInUse;
  }

  @Override
  public int getNumID() {
    return numID;
  }

  @Override
  public void setNumID(int numID) {
    this.numID = numID;
  }

  @Override
  public Location getLocation() {
    // String myLoc = (isInUse) ? currentLocation : (isClean) ? cleanLocation : storageLocation;
    return DAOSystemSingleton.INSTANCE.getSystem().getLocation(currentLocation);
  }

  @Override
  public void setLocation(String NodeID) throws NullPointerException {
    if (DAOSystemSingleton.INSTANCE.getSystem().getLocation(NodeID) != null) {
      this.currentLocation = NodeID;
    }
  }

  public String getRoomID() {
    return getLocation().getNodeID();
  }

  public String getFloorID() {
    return getLocation().getFloor();
  }

  @Override
  public void setLocation(Location location) throws NullPointerException {
    this.currentLocation = location.getNodeID();
  }

  @Override
  public void setLocation(int xcoord, int ycoord) throws NullPointerException {
    Location loc = DAOSystemSingleton.INSTANCE.getSystem().getLocation(xcoord, ycoord);
    if (loc != null) {
      this.currentLocation = loc.getNodeID();
    }
  }

  public void setisInUse(boolean inUse) {
    isInUse = inUse;
  }

  public boolean isClean() {
    return isClean;
  }

  public void setClean(boolean clean) {
    isClean = clean;
  }

  public String getCleanLocation() {
    return cleanLocation;
  }

  public void setCleanLocation(String cleanLocation) {
    this.cleanLocation = cleanLocation;
  }

  public String getStorageLocation() {
    return storageLocation;
  }

  public void setStorageLocation(String storageLocation) {
    this.storageLocation = storageLocation;
  }

  public String getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(String currentLocation) {
    this.currentLocation = currentLocation;
  }

  public abstract void use();

  @Override
  public double getXCoord() {
    return this.getLocation().getXCoord();
  }

  @Override
  public double getYCoord() {
    return this.getLocation().getYCoord();
  }

  @Override
  public String toString() {
    return this.getEquipmentID();
  }
}
