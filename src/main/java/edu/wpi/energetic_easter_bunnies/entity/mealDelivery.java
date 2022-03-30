package edu.wpi.energetic_easter_bunnies.entity;

public class MealDelivery {

  private String entreeType;
  private String beverageType;
  private String dessertType;
  private String otherNotes;

  private int roomNumber;
  private int deliveryTimeHour;
  private int deliveryTimeMin;

  private boolean asap;

  public MealDelivery() {}

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

  public void setRoomNumber(int num) {
    this.roomNumber = num;
  }

  public int getRoomNumber() {
    return this.roomNumber;
  }

  public void setDeliveryTime(int hour, int min) {
    this.deliveryTimeHour = hour;
    this.deliveryTimeMin = min;
  }

  public int getDeliveryTime() {
    return this.deliveryTimeHour + this.deliveryTimeMin;
  }

  public void setASAP(boolean status) {
    this.asap = status;
  }

  public boolean getASAP() {
    return this.asap;
  }
}
