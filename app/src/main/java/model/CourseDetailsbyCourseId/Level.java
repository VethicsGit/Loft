
package model.CourseDetailsbyCourseId;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Level {

    @SerializedName("academic_level_id")
    @Expose
    private String academicLevelId;
    @SerializedName("academic_level")
    @Expose
    private String academicLevel;

    public String getAcademicLevelId() {
        return academicLevelId;
    }

    public void setAcademicLevelId(String academicLevelId) {
        this.academicLevelId = academicLevelId;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

}
