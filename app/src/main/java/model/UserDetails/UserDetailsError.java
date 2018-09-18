
package model.UserDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailsError {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private UserDetailsErrorMessage message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDetailsErrorMessage getMessage() {
        return message;
    }

    public void setMessage(UserDetailsErrorMessage message) {
        this.message = message;
    }

}
