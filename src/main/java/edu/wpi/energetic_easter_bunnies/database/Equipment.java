package edu.wpi.energetic_easter_bunnies.database;

public abstract class Equipment {
  private final String equipmentID;
  private boolean isInUse;
  private boolean isClean;
  private String cleanLocation;
  private String storageLocation;

  public Equipment(
      String equipmentID,
      boolean isInUse,
      boolean isClean,
      String cleanLocation,
      String storageLocation) {
    this.equipmentID = equipmentID;
    this.isInUse = isInUse;
    this.isClean = isClean;
    this.cleanLocation = cleanLocation;
    this.storageLocation = storageLocation;
  }

  public String getEquipmentID() {
    return equipmentID;
  }

  public boolean isInUse() {
    return isInUse;
  }

  public void setInUse(boolean inUse) {
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

  public abstract void use();
}
