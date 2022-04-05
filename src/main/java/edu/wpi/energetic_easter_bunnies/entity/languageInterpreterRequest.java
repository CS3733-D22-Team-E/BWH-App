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

  // Language
  public Language getLang() {
    return langforInterpreter;
  }

  public void setLang(Language langforInterpreter) {
    this.langforInterpreter = langforInterpreter;
  }
}
