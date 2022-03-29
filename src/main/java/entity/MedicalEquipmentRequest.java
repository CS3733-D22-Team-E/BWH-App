package entity;

public class MedicalEquipmentRequest {

  private String location;
  private String equipment;
  private int equipmentQuantity;
  // TODO: make method that takes in date and time as string and returns a date object
  private String deliveryDate;
  private String deliveryTime;
  private boolean isUrgent;
  private String otherNotes;

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getEquipment() {
    return equipment;
  }

  public void setEquipment(String equipment) {
    this.equipment = equipment;
  }

  public int getEquipmentQuantity() {
    return equipmentQuantity;
  }

  public void setEquipmentQuantity(int equipmentQuantity) {
    this.equipmentQuantity = equipmentQuantity;
  }

  public String getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(String deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public String getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(String deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public boolean isUrgent() {
    return isUrgent;
  }

  public void setUrgent(boolean urgent) {
    isUrgent = urgent;
  }

  public String getOtherNotes() {
    return otherNotes;
  }

  public void setOtherNotes(String otherNotes) {
    this.otherNotes = otherNotes;
  }
}
