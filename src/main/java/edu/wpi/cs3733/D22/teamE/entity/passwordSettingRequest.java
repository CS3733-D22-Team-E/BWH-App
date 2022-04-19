package edu.wpi.cs3733.D22.teamE.entity;


public class passwordSettingRequest extends serviceRequest{
    private String newPassword;
    private String confirmNewPassword;
    public passwordSettingRequest(
            String newPassword,
            String confirmNewPassword
            ) {
        super(newPassword,
                confirmNewPassword);
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }


    public passwordSettingRequest() {
        super(String.valueOf(Type.MED_EQUIP_REQ));
        this.newPassword = "";
        this.confirmNewPassword = "";
    }
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String Password) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String Password) {
        this.confirmNewPassword = Password;
    }
}