package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;
import java.util.Arrays;

public class languageInterpreterRequest extends serviceRequest {
  // private String languageSelected; //TODO: never used?

  // private LocalDate startDate; TODO: Ask Frank if these are needed since both can be represented
  // with requestDate and deliveryDate
  // private LocalDate endDate;

  Language langforInterpreter;

  public enum Language {
    En,
    Es,
    FR,
    CN
  }

  public languageInterpreterRequest() {
    super(String.valueOf(Type.LANG_INTERP_REQ));
    langforInterpreter = Language.En; // TODO: Default Language?
  }

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

  public String getLanguageValue() {
    return langforInterpreter.toString();
  }

  public void setLang(Language langforInterpreter) {
    this.langforInterpreter = langforInterpreter;
  }

  public void setLang(String langforInterpreter) {
    if (!Arrays.toString(Language.values()).contains(langforInterpreter))
      throw new RuntimeException("language must be one of " + Arrays.toString(Language.values()));
    else this.langforInterpreter = Language.valueOf(langforInterpreter);
  }
}
