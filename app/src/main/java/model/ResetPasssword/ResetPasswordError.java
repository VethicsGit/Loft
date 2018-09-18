
package model.ResetPasssword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordError {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private ResetPasswordErrorMessage message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResetPasswordErrorMessage getMessage() {
        return message;
    }

    public void setMessage(ResetPasswordErrorMessage message) {
        this.message = message;
    }

}
