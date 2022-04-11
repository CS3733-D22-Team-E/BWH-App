package edu.wpi.energetic_easter_bunnies.entity;

import java.time.LocalDate;

public class sanitationRequest extends serviceRequest {

  public enum Size {
    Light {
      public String toString() {
        return "Light";
      }
    },
    Medium {
      public String toString() {
        return "Medium";
      }
    },
    Heavy {
      public String toString() {
        return "Heavy";
      }
    }
  }

  public enum Biohazard {
    Yes {
      public String toString() {
        return "Yes";
      }
    },
    No {
      public String toString() {
        return "No";
      }
    },
    Unsure {
      public String toString() {
        return "Unsure";
      }
    }
  }

  Size sizeOfCleaning;
  Biohazard biohazardOnSite;

  public sanitationRequest(String serviceRequestID, String otherNotes, String floorID, String roomID, boolean isUrgent, String requestStatus, String staffAssignee, LocalDate requestDate, LocalDate deliveryDate, Size sizeOfCleaning, Biohazard biohazardOnSite) {
    super(serviceRequestID, String.valueOf(Type.SANITATION_REQ), otherNotes, floorID, roomID, isUrgent, requestStatus, staffAssignee, requestDate, deliveryDate);
    this.sizeOfCleaning = sizeOfCleaning;
    this.biohazardOnSite = biohazardOnSite;
  }

  public sanitationRequest() { //TODO: Talk wtih Colin about how he wants to initialize in the blank constructor
    super();
    this.sizeOfCleaning = Size.Light;
    this.biohazardOnSite = Biohazard.No;
  }

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

  public String getSizeValue() {
    return sizeOfCleaning.toString();
  }

  public void setBiohazardOnSite(Biohazard biohazardOnSite) {
    this.biohazardOnSite = biohazardOnSite;
  }
}
