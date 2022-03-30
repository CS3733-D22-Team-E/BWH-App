package edu.wpi.energetic_easter_bunnies.entity;

public class medicalEquipmentRequest extends serviceRequest {

  private String equipment;
  private int equipmentQuantity;
  // TODO: make method that takes in date and time as string and returns a date object
  private String deliveryDate;
  private String deliveryTime;

  public MedicalEquipmentRequest(
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      String equipment,
      int equipQuantity,
      String deliveryDate,
      String deliveryTime) {
    super(otherNotes, floorID, roomID, isUrgent, requestStatus, staffAssignee);
    this.equipment = equipment;
    this.equipmentQuantity = equipQuantity;
    this.deliveryDate = deliveryDate;
    this.deliveryTime = deliveryTime;
  }

  public MedicalEquipmentRequest() {
    super();
    this.equipment = "";
    this.equipmentQuantity = 0;
    this.deliveryDate = "";
    this.deliveryTime = "";
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
}
