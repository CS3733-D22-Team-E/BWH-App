package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;

public class giftDeliveryRequest extends serviceRequest {
  private String patientName;
  Gift gift;

  public enum Gift {
    BoardGame {
      public String toString() {
        return "Board Game";
      }
    },
    Book {
      public String toString() {
        return "Book";
      }
    },
    GetWellCard {
      public String toString() {
        return "Get Well Card";
      }
    },
    Movie {
      public String toString() {
        return "Movie";
      }
    },
    TeddyBear {
      public String toString() {
        return "Teddy Bear";
      }
    }
  }

  public giftDeliveryRequest(
      String serviceRequestID,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      LocalDate requestDate,
      LocalDate deliveryDate,
      String patientName,
      Gift gift) {
    super(
        serviceRequestID,
        String.valueOf(Type.GIFTREQUEST),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    this.patientName = patientName;
    this.gift = gift;
  }

  public giftDeliveryRequest() {
    super(String.valueOf(Type.GIFTREQUEST));
    this.patientName = "";
    this.gift = Gift.GetWellCard; // TODO: Maybe make default something else later
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public Gift getGift() {
    return gift;
  }

  public String getStringGift() {
    return gift.toString();
  }

  public void setGift(Gift gift) {
    this.gift = gift;
  }
}
