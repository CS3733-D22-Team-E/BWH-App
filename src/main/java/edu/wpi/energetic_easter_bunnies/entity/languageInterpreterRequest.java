package edu.wpi.energetic_easter_bunnies.entity;

import java.time.LocalDate;

public class languageInterpreterRequest extends serviceRequest {
  private String lanInterpID;

  public languageInterpreterRequest() {
    super();
  }

  public enum Language {
    En,
    Es,
    FR,
    CN
  }

  Language langforInterpreter;

  public languageInterpreterRequest(
      String lanInterpID,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      Language langforInterpreter,
      LocalDate deliveryDate,
      LocalDate requestDate) {
    super(
        lanInterpID,
        String.valueOf(Type.LANG_INTERP_REQ),
        otherNotes,
        floorID,
        roomID,
        isUrgent,
        requestStatus,
        staffAssignee,
        requestDate,
        deliveryDate);
    this.langforInterpreter = langforInterpreter;
  }

  /*<<<<<<< HEAD
  =======
    public String getRoomSelected() {
      return this.roomSelected;
    }

    public void setLanguageSelected(String languageSelected) {
      this.languageSelected = languageSelected;
    }

  >>>>>>> parent of 1f427d6 (Merge pull request #17 from CS3733-D22-Team-E/language_request)*/
  // Language
  public Language getLang() {
    return langforInterpreter;
  }

  public void setLang(Language langforInterpreter) {
    this.langforInterpreter = langforInterpreter;
  }
}
