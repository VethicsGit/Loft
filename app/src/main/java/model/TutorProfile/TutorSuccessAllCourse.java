
package model.TutorProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorSuccessAllCourse {

    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("course_title")
    @Expose
    private String courseTitle;
    @SerializedName("course_short_title")
    @Expose
    private String courseShortTitle;
    @SerializedName("course_description")
    @Expose
    private String courseDescription;
    @SerializedName("course_url")
    @Expose
    private String courseUrl;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseShortTitle() {
        return courseShortTitle;
    }

    public void setCourseShortTitle(String courseShortTitle) {
        this.courseShortTitle = courseShortTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

}
