package com.fooddelivery.delivery.dto.request;

public class ChangePasswordRequest {
    private String userId;
    private String oldPassword;
    private String newPassword;

    public ChangePasswordRequest() {}

    public ChangePasswordRequest(String userId, String oldPassword, String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
