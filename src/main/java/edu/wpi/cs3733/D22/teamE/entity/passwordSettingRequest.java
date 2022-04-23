package edu.wpi.cs3733.D22.teamE.entity;

public class passwordSettingRequest {
  private String newPassword;
  private String confirmNewPassword;

  public passwordSettingRequest(String newPassword, String confirmNewPassword) {
    this.newPassword = newPassword;
    this.confirmNewPassword = confirmNewPassword;
  }

  public passwordSettingRequest() {
    this.newPassword = "";
    this.confirmNewPassword = "";
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmNewPassword() {
    return confirmNewPassword;
  }

  public void setConfirmNewPassword(String Password) {
    this.confirmNewPassword = Password;
  }
}
