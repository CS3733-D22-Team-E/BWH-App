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
    setClean(false);
  }

  public boolean checkIsClean() {
    return super.isClean();
  }
}
