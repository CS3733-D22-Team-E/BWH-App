package edu.wpi.energetic_easter_bunnies.entity;

public class medicineDelivery extends serviceRequest {

    String medicine;
    String amount;
    String unit;
    String otherNotes;
    String deliveryDate;
    String deliveryTime;
    boolean mon;
    boolean tues;
    boolean wed;
    boolean thurs;
    boolean fri;
    boolean sat;
    boolean sun;


    public String getMedicine(){return medicine;}
    public  void setMedicine(String medicine){this.medicine = medicine;}
    public String getAmount(){return amount;}
    public void setAmount(String amount){this.amount =amount;}
    public String getUnit(){return unit;}
    public void setUnit(String unit){this.unit = unit;}
    public String getOtherNotes(){return otherNotes;}
    public void setOtherNotes(String setOtherNotes){this.otherNotes = otherNotes;}
    public String getDeliveryDate(){return deliveryDate;}
    public void setDeliveryDate(String deliveryDate){this.deliveryDate = deliveryDate;}
    public String getDeliveryTime(){return deliveryTime;}
    public  void setDeliveryTime(String deliveryTime){this.deliveryTime = deliveryTime;}
    public boolean getMon(){return mon;}
    public  void setMon(boolean mon){this.mon = mon;}
    public boolean getTues(){return tues;}
    public void setTues(boolean tues){this.tues = tues;}
    public boolean getWed(){return wed;}
    public void setWed(boolean wed){this.wed = wed;}
    public boolean getThurs(){return thurs;}
    public void setThurs(boolean thurs){this.thurs = thurs;}
    public boolean getFri(){return fri;}
    public void setFri(boolean fri){this.fri = fri;}
    public boolean getSat(){return sat;}
    public void setSat(boolean sat){this.sat = sat;}
    public boolean getSun(){return sun;}
    public void setSun(boolean sun){this.sun = sun;}
}
