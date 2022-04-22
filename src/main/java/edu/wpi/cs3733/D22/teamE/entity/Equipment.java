package edu.wpi.cs3733.D22.teamE.entity;

import edu.wpi.cs3733.D22.teamE.database.DBConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

  public int getNumID() {
    return numID;
  }

  public void setNumID(int numID) {
    this.numID = numID;
  }

  @Override
  public Location getLocation() {
    return null;
  }

  @Override
  public void setLocation(String NodeID) {
  }

  @Override
  public void setLocation(Location location) {
  }

  @Override
  public void setLocation(int xcoord, int ycoord) {

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
}
