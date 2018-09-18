
package model.CourseListing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseListingLevel {

    @SerializedName("course_level_id")
    @Expose
    private String courseLevelId;
    @SerializedName("course_level")
    @Expose
    private String courseLevel;

    public String getCourseLevelId() {
        return courseLevelId;
    }

    public void setCourseLevelId(String courseLevelId) {
        this.courseLevelId = courseLevelId;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

}
