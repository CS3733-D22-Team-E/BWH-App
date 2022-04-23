package edu.wpi.cs3733.D22.teamE.entity;

import edu.wpi.cs3733.D22.teamE.entity.serviceRequest;
import java.time.LocalDate;

public class medicineDelivery extends serviceRequest {

  String medicine;
  String amount;
  String unit;
  String deliveryTime;

  String reocurringDays;
  boolean mon;
  boolean tues;
  boolean wed;
  boolean thurs;
  boolean fri;
  boolean sat;
  boolean sun;

  public medicineDelivery(
      String serviceRequestID,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      LocalDate requestDate,
      LocalDate deliveryDate,
      String medicine,
      String amount,
      String unit,
      String deliveryTime,
      boolean mon,
      boolean tues,
      boolean wed,
      boolean thurs,
      boolean fri,
      boolean sat,
      boolean sun) {
    super(
        serviceRequestID,
        String.valueOf(Type.MED_DELIV_REQ),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    this.medicine = medicine;
    this.amount = amount;
    this.unit = unit;
    this.deliveryTime = deliveryTime;
    this.mon = mon;
    this.tues = tues;
    this.wed = wed;
    this.thurs = thurs;
    this.fri = fri;
    this.sat = sat;
    this.sun = sun;
    this.reocurringDays = getRepeatingDays();
  }

  public medicineDelivery() {
    super(String.valueOf(Type.MED_DELIV_REQ));
    this.medicine = "";
    this.amount = "";
    this.unit = "";
    this.deliveryTime = "";
    this.mon = false;
    this.tues = false;
    this.wed = false;
    this.thurs = false;
    this.fri = false;
    this.sat = false;
    this.sun = false;
    this.reocurringDays = getRepeatingDays();
  }

  public String getMedicine() {
    return medicine;
  }

  public void setMedicine(String medicine) {
    this.medicine = medicine;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public String getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(String deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public boolean getMon() {
    return mon;
  }

  public void setMon(boolean mon) {
    this.mon = mon;
  }

  public boolean getTues() {
    return tues;
  }

  public void setTues(boolean tues) {
    this.tues = tues;
  }

  public boolean getWed() {
    return wed;
  }

  public void setWed(boolean wed) {
    this.wed = wed;
  }

  public boolean getThurs() {
    return thurs;
  }

  public void setThurs(boolean thurs) {
    this.thurs = thurs;
  }

  public boolean getFri() {
    return fri;
  }

  public void setFri(boolean fri) {
    this.fri = fri;
  }

  public boolean getSat() {
    return sat;
  }

  public void setSat(boolean sat) {
    this.sat = sat;
  }

  public boolean getSun() {
    return sun;
  }

  public void setSun(boolean sun) {
    this.sun = sun;
  }

  public String getReocurringDays() {
    return reocurringDays;
  }

  public void setReocurringDays(String reocurringDays) {
    this.reocurringDays = reocurringDays;
  }

  public String getRepeatingDays() {
    String result = "";

    if (sun) {
      result += "SUN ";
    }
    if (mon) {
      result += "MON ";
    }
    if (tues) {
      result += "TUES ";
    }
    if (wed) {
      result += "WED ";
    }
    if (thurs) {
      result += "THURS ";
    }
    if (fri) {
      result += "FRI ";
    }
    if (sat) {
      result += "SAT ";
    }

    return result; // TODO: Trim last parentheses if string is not empty
  }
}
