package edu.wpi.energetic_easter_bunnies.entity;

import java.time.LocalDate;

public class mealDeliveryRequest extends serviceRequest {

  private String entreeType;
  private String beverageType;
  private String dessertType;

  private int roomNumber;
  private LocalDate deliveryDate;
  private int deliveryTime;

  private boolean asap;

  public mealDeliveryRequest() {}

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

  public void setDeliveryDate(LocalDate date) {
    this.deliveryDate = date;
  }

  public LocalDate getDeliveryDate() {
    return this.deliveryDate;
  }

  public void setDeliveryTime(int time) {
    this.deliveryTime = time;
  }

  public int getDeliveryTime() {
    return this.deliveryTime;
  }

  public void setASAP(boolean status) {
    this.asap = status;
  }

  public boolean getASAP() {
    return this.asap;
  }
}
