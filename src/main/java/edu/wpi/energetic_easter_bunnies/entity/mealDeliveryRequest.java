package edu.wpi.energetic_easter_bunnies.entity;

import java.time.LocalDate;

public class mealDeliveryRequest extends serviceRequest {

  private String entreeType;
  private String beverageType;
  private String dessertType;

  private int deliveryTime; // TODO: Talk with Phillip about whether or not this is still needed,
  // considering serviceRequest has a LocalDate DeliveryDate

  public mealDeliveryRequest(
      String serviceRequestID,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      LocalDate requestDate,
      LocalDate deliveryDate,
      String entreeType,
      String beverageType,
      String dessertType,
      int deliveryTime) {
    super(
        serviceRequestID,
        String.valueOf(Type.MEAL_DELIV_REQ),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    this.entreeType = entreeType;
    this.beverageType = beverageType;
    this.dessertType = dessertType;
    this.deliveryTime = deliveryTime;
  }

  public mealDeliveryRequest() {
    super(String.valueOf(Type.MEAL_DELIV_REQ));
    this.entreeType = "";
    this.beverageType = "";
    this.dessertType = "";
    this.deliveryTime = 0;
  }

  public void setEntreeType(String entree) {
    this.entreeType = entree;
  }

  public String getEntreeType() {
    return this.entreeType;
  }

  public void setBeverageType(String beverage) {
    this.entreeType = beverage;
  }

  public String getBeverageType() {
    return this.beverageType;
  }

  public void setDessertType(String dessert) {
    this.entreeType = dessert;
  }

  public String getDessertType() {
    return this.dessertType;
  }

  public void setDeliveryTime(int time) {
    this.deliveryTime = time;
  }

  public int getDeliveryTime() {
    return this.deliveryTime;
  }
}
