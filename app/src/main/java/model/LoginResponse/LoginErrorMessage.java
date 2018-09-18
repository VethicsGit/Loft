package model.LoginResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chirag on 1/11/2018.
 */

public class LoginErrorMessage {
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("password")
    @Expose
    private String password;

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
