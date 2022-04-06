package edu.wpi.energetic_easter_bunnies.entity;

import java.time.LocalDate;

public class languageInterpreterRequest extends serviceRequest {
  private String lanInterpID;

  public languageInterpreterRequest() {
    super();
  }
  
  private String floorSelected;
  private String roomSelected;
  private String languageSelected;

  private LocalDate startDate;
  private LocalDate endDate;
  private int roomNumber;

  public languageInterpreterRequest() {
    super();
  }

  public void setFloorSelected(String floorSelected) {
    this.floorSelected = floorSelected;
  }
  
  Language langforInterpreter;


  public void setLanguageSelected(String languageSelected) {
     this.languageSelected = languageSelected;
  }

  // Language
  public Language getLang() {
    return langforInterpreter;
  }
}
