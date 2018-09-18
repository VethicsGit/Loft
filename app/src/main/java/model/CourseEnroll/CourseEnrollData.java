
package model.CourseEnroll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseEnrollData {

    @SerializedName("subscribed_id")
    @Expose
    private String subscribedId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getSubscribedId() {
        return subscribedId;
    }

    public void setSubscribedId(String subscribedId) {
        this.subscribedId = subscribedId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
