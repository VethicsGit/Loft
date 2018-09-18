
package model.ChangePasswordSuccess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordError {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private ChangePasswordErrorMessage message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ChangePasswordErrorMessage getMessage() {
        return message;
    }

    public void setMessage(ChangePasswordErrorMessage message) {
        this.message = message;
    }

}
