package edu.wpi.energetic_easter_bunnies.database;

public abstract class Equipment {
  private final String equipmentID;
  private boolean isInUse;
  private boolean isClean;
  private String cleanLocation;
  private String storageLocation;
  private int numID;

  public Equipment(
      String equipmentID,
      boolean isInUse,
      boolean isClean,
      String cleanLocation,
      String storageLocation,
      int numID) {
    this.equipmentID = equipmentID;
    this.isInUse = isInUse;
    this.isClean = isClean;
    this.cleanLocation = cleanLocation;
    this.storageLocation = storageLocation;
    this.numID = numID;
  }

  public String getEquipmentID() {
    return equipmentID;
  }

  public boolean getIsInUse() {
    return isInUse;
  }

  public int getNumID() {
    return numID;
  }

  public void setNumID(int numID) {
    this.numID = numID;
  }

  public void setIsInUse(boolean inUse) {
    isInUse = inUse;
  }

  public boolean getIsClean() {
    return isClean;
  }

  public void setIsClean(boolean clean) {
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

  public abstract void use();
}
