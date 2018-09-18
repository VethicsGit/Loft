package model.LoginResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chirag on 1/11/2018.
 */

public class LoginError {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private LoginErrorMessage message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginErrorMessage getMessage() {
        return message;
    }

    public void setMessage(LoginErrorMessage message) {
        this.message = message;
    }

}
