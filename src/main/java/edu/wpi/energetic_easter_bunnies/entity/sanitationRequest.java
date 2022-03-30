package edu.wpi.energetic_easter_bunnies.entity;

public class sanitationRequest extends serviceRequest {

  public enum Size {
    Light{
      public String toString() {
        return "Light";
      }
    },
    Medium{
      public String toString() {
        return "Medium";
      }
    },
    Heavy{
      public String toString() {
        return "Heavy";
      }
    }
  }

  public enum Biohazard {
    Yes{
      public String toString() {
        return "Yes";
      }
    },
    No{
      public String toString() {
        return "No";
      }
    },
    Unsure{
      public String toString() {
        return "Unsure";
      }
    }
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

  public String getBiohazardValue() {
    return biohazardOnSite.toString();
  }

  public String getSizeValue(){
    return sizeOfCleaning.toString();
  }

  public void setBiohazardOnSite(Biohazard biohazardOnSite) {
    this.biohazardOnSite = biohazardOnSite;
  }
}
