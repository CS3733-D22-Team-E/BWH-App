package edu.wpi.energetic_easter_bunnies.database;

public class MedicalEquipment extends Equipment {

  public MedicalEquipment(
      String equipmentID,
      boolean isInUse,
      boolean isClean,
      String cleanLocation,
      String storageLocation,
      int numID) {
    super(equipmentID, isInUse, isClean, cleanLocation, storageLocation, numID);
  }

  @Override
  public void use() {
    setIsClean(false);
  }
}
