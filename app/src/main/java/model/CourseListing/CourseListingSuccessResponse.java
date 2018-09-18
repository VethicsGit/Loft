
package model.CourseListing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseListingSuccessResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private CourseListingData data;

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

    public CourseListingData getData() {
        return data;
    }

    public void setData(CourseListingData data) {
        this.data = data;
    }

}
