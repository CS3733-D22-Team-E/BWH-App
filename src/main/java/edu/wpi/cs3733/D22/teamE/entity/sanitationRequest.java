package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;
import java.util.Arrays;

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

  public sanitationRequest(
      String serviceRequestID,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      LocalDate requestDate,
      LocalDate deliveryDate,
      Size sizeOfCleaning,
      Biohazard biohazardOnSite) {
    super(
        serviceRequestID,
        String.valueOf(Type.SANITATION_REQ),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    this.sizeOfCleaning = sizeOfCleaning;
    this.biohazardOnSite = biohazardOnSite;
  }

  public
  sanitationRequest() { // TODO: Talk wtih Colin about how he wants to initialize in the blank
    // constructor
    super(String.valueOf(Type.SANITATION_REQ));
    this.sizeOfCleaning = Size.Light;
    this.biohazardOnSite = Biohazard.No;
  }

  public Size getSizeOfCleaning() {
    return sizeOfCleaning;
  }

  public void setSizeOfCleaning(Size sizeOfCleaning) {
    this.sizeOfCleaning = sizeOfCleaning;
  }

  public void setSizeOfCleaning(String sizeOfCleaning) {
    if (!Arrays.toString(Size.values()).contains(sizeOfCleaning))
      throw new RuntimeException(
          "Size of Cleaning must be one of " + Arrays.toString(Size.values()));
    else this.sizeOfCleaning = Size.valueOf(sizeOfCleaning);
  }

  public Biohazard getBiohazardOnSite() {
    return biohazardOnSite;
  }

  public void setBiohazardOnSite(Biohazard biohazardOnSite) {
    this.biohazardOnSite = biohazardOnSite;
  }

  public void setBiohazardOnSite(String biohazardOnSite) {
    if (!Arrays.toString(Biohazard.values()).contains(biohazardOnSite))
      throw new RuntimeException(
          "Biohazard on Site must be one of " + Arrays.toString(Biohazard.values()));
    else this.biohazardOnSite = Biohazard.valueOf(biohazardOnSite);
  }
}
