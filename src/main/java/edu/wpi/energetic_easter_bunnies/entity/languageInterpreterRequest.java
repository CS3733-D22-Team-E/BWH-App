package edu.wpi.energetic_easter_bunnies.entity;

public class languageInterpreterRequest extends ServiceRequest {

  private String startDate;
  private String endDate;
  private int roomNumber;

  public languageInterpreterRequest() {
    super();
    this.startDate = "";
    this.endDate = "";
    this.roomNumber = 0;
  }

  public enum Floor {
    Ground,
    First,
    Second,
    Third
  }

  public enum Language {
    En,
    Es,
    FR,
    CN
  }

  Language langforInterpreter;
  Floor floorforLangInterpreter;

  public languageInterpreterRequest(String otherNotes, String floorID, String roomID, boolean isUrgent, String requestStatus, String staffAssignee, String startDate, String endDate, int roomNumber, Language langforInterpreter, Floor floorforLangInterpreter) {
    super(otherNotes, floorID, roomID, isUrgent, requestStatus, staffAssignee);
    this.startDate = startDate;
    this.endDate = endDate;
    this.roomNumber = roomNumber;
    this.langforInterpreter = langforInterpreter;
    this.floorforLangInterpreter = floorforLangInterpreter;
  }

  // Location
  public Floor getFloor() {
    return floorforLangInterpreter;
  }

  public void setFloor(Floor floorforLangInterpreter) {
    this.floorforLangInterpreter = floorforLangInterpreter;
  }

  public java.lang.Integer getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  // Time Period
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getEndDate() {
    return endDate;
  }

  // Language
  public Language getLang() {
    return langforInterpreter;
  }

  public void setLang(Language langforInterpreter) {
    this.langforInterpreter = langforInterpreter;
  }
}
