package edu.wpi.energetic_easter_bunnies.database;

public class MedicalEquipment extends Equipment {
  private String med_equipmentID;
  private String equipmentType;

  public MedicalEquipment(
      String equipmentID,
      String med_equipmendID,
      boolean isInUse,
      boolean isClean,
      String cleanLocation,
      String storageLocation,
      String currentLocation,
      String equipmentType,
      int numID) {
    super(equipmentID, isInUse, isClean, cleanLocation, storageLocation, currentLocation, numID);
    this.med_equipmentID = med_equipmendID;
    this.equipmentType = equipmentType;
  }

  @Override
  public void use() {
    setisInUse(true);
    setClean(false);
  }

  public void cleanEquipment() {
    setisInUse(false);
    setClean(true);
    setCurrentLocation(getStorageLocation());
    setMed_equipmentID(null); // Hopefully won't break when entered into DB Table
  }

  public boolean checkIsClean() {
    return super.isClean();
  }

  public String getMed_equipmentID() {
    return med_equipmentID;
  }

  public void setMed_equipmentID(String med_equipmentID) {
    this.med_equipmentID = med_equipmentID;
  }

  public String getEquipmentType() {
    return equipmentType;
  }

  public void setEquipmentType(String equipmentType) {
    this.equipmentType = equipmentType;
  }
}
