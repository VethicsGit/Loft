
package model.CourseDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Module {

    @SerializedName("course_module_id")
    @Expose
    private String courseModuleId;
    @SerializedName("course_module_name")
    @Expose
    private String courseModuleName;
    @SerializedName("course_module_description")
    @Expose
    private String courseModuleDescription;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("topics")
    @Expose
    private List<Topic> topics = null;

    public String getCourseModuleId() {
        return courseModuleId;
    }

    public void setCourseModuleId(String courseModuleId) {
        this.courseModuleId = courseModuleId;
    }

    public String getCourseModuleName() {
        return courseModuleName;
    }

    public void setCourseModuleName(String courseModuleName) {
        this.courseModuleName = courseModuleName;
    }

    public String getCourseModuleDescription() {
        return courseModuleDescription;
    }

    public void setCourseModuleDescription(String courseModuleDescription) {
        this.courseModuleDescription = courseModuleDescription;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

}
