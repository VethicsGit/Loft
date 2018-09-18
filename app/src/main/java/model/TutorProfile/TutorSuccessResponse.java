
package model.TutorProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorSuccessResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private TutorSuccessData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TutorSuccessData getData() {
        return data;
    }

    public void setData(TutorSuccessData data) {
        this.data = data;
    }

}
