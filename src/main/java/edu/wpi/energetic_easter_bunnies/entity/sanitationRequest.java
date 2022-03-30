package edu.wpi.energetic_easter_bunnies.entity;

public class SanitationRequest extends ServiceRequest {

  public enum Size {
    Light,
    Medium,
    Heavy
  }

  public enum Biohazard {
    Yes,
    No,
    Unsure
  }

  Size sizeOfCleaning;
  Biohazard biohazardOnSite;

  public Size getSizeOfCleaning() {
    return sizeOfCleaning;
  }

  public void setSizeOfCleaning(Size sizeOfCleaning) {
    this.sizeOfCleaning = sizeOfCleaning;
  }

  public Biohazard getBiohazardOnSite() {
    return biohazardOnSite;
  }

  public void setBiohazardOnSite(Biohazard biohazardOnSite) {
    this.biohazardOnSite = biohazardOnSite;
  }
}
