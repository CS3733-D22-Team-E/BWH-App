package edu.wpi.energetic_easter_bunnies.entity;

import java.time.LocalDate;

public class medicalEquipmentRequest extends serviceRequest {

  private String equipment;
  private int equipmentQuantity;
  private String deliveryTime;

  public medicalEquipmentRequest(
      String medEquipRequestID,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      String equipment,
      int equipQuantity,
      LocalDate requestDate,
      LocalDate deliveryDate,
      String deliveryTime) {
    super(
        medEquipRequestID,
        String.valueOf(Type.MED_EQUIP_REQ),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    this.equipment = equipment;
    this.equipmentQuantity = equipQuantity;
    this.deliveryTime = deliveryTime;
  }

  public medicalEquipmentRequest() {
    super(String.valueOf(Type.MED_EQUIP_REQ));
    this.equipment = "";
    this.equipmentQuantity = 0;
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

  public String getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(String deliveryTime) {
    this.deliveryTime = deliveryTime;
  }
}
