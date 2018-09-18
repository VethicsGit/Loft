
package model.UpdateProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileError {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private UpdateProfileErrorMessage message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateProfileErrorMessage getMessage() {
        return message;
    }

    public void setMessage(UpdateProfileErrorMessage message) {
        this.message = message;
    }

}
