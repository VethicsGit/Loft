
package model.ForgotPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordError {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private ForgotPasswordMessage message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ForgotPasswordMessage getMessage() {
        return message;
    }

    public void setMessage(ForgotPasswordMessage message) {
        this.message = message;
    }

}
