package edu.wpi.energetic_easter_bunnies.entity;

public class mealDeliveryRequest extends serviceRequest {

  private String entreeType;
  private String beverageType;
  private String dessertType;

  private int deliveryTime;

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

  public void setDeliveryTime(int time) {
    this.deliveryTime = time;
  }

  public int getDeliveryTime() {
    return this.deliveryTime;
  }
}
