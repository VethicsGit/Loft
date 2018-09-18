package model.RegisterResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Chirag on 1/12/2018.
 */

public class RegisterError {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private RegiserErrorMessage message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RegiserErrorMessage getMessage() {
        return message;
    }

    public void setMessage(RegiserErrorMessage message) {
        this.message = message;
    }

}
