package edu.wpi.cs3733.D22.teamE.entity;

import java.time.LocalDate;

public class languageInterpreterRequest extends serviceRequest {
  private String language;

  public languageInterpreterRequest() {
    super(String.valueOf(Type.LANG_INTERP_REQ));
    language = "";
  }

  public languageInterpreterRequest(
      String lanInterpID,
      String otherNotes,
      String floorID,
      String roomID,
      boolean isUrgent,
      String requestStatus,
      String staffAssignee,
      String language,
      LocalDate requestDate,
      LocalDate deliveryDate) {
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
    this.language = language;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
