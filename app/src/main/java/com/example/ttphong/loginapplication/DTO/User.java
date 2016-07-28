package com.example.ttphong.loginapplication.DTO;

/**
 * Created by ttphong on 7/28/2016.
 */
public class User {

    public static final String DEFAULT_USERNAME = "thanhphong";
    public static final String DEFAULT_PASSWORD = "123456";
    public static final boolean DEFAULT_STATUS = false;
    private String mUserName;
    private String mPassWordHashing;
    private boolean mStatus;

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassWordHashing() {
        return mPassWordHashing;
    }

    public void setPassWordHashing(String passWordHashing) {
        mPassWordHashing = passWordHashing;
    }

    public boolean getStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }
}
