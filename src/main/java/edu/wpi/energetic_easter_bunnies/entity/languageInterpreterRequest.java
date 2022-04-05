package edu.wpi.energetic_easter_bunnies.entity;

import java.time.LocalDate;

public class languageInterpreterRequest extends serviceRequest {
  private String lanInterpID;
  private String floorSelected;
  private String roomSelected;
  private String languageSelected;

  private LocalDate startDate;
  private LocalDate endDate;
  private int roomNumber;

  public languageInterpreterRequest() {
    super();
    // this.startDate = LocalDate.parse("");
    // this.endDate = LocalDate.parse("");
    this.roomNumber = 0;
  }

  public void setFloorSelected(String floorSelected) {
    this.floorSelected = floorSelected;
  }

  public String getFloorSelected(String floorSelected) {
    return this.floorSelected;
  }

  public void setRoomSelected(String roomSelected) {
    this.roomSelected = roomSelected;
  }

  public String getRoomSelected() {
    return this.roomSelected;
  }

  public void setLanguageSelected(String languageSelected) {
    this.languageSelected = languageSelected;
  }

  public String getLanguageSelected() {
    return this.languageSelected;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getStartDate() {
    return this.startDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalDate getEndDate() {
    return this.endDate;
  }
}
