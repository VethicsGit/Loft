
package model.CourseDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topic {

    @SerializedName("course_topic_id")
    @Expose
    private String courseTopicId;
    @SerializedName("course_module_id")
    @Expose
    private String courseModuleId;
    @SerializedName("course_topic_name")
    @Expose
    private String courseTopicName;
    @SerializedName("course_topic_description")
    @Expose
    private String courseTopicDescription;
    @SerializedName("course_topic_documentation")
    @Expose
    private Object courseTopicDocumentation;
    @SerializedName("contents")
    @Expose
    private List<Content> contents = null;

    public String getCourseTopicId() {
        return courseTopicId;
    }

    public void setCourseTopicId(String courseTopicId) {
        this.courseTopicId = courseTopicId;
    }

    public String getCourseModuleId() {
        return courseModuleId;
    }

    public void setCourseModuleId(String courseModuleId) {
        this.courseModuleId = courseModuleId;
    }

    public String getCourseTopicName() {
        return courseTopicName;
    }

    public void setCourseTopicName(String courseTopicName) {
        this.courseTopicName = courseTopicName;
    }

    public String getCourseTopicDescription() {
        return courseTopicDescription;
    }

    public void setCourseTopicDescription(String courseTopicDescription) {
        this.courseTopicDescription = courseTopicDescription;
    }

    public Object getCourseTopicDocumentation() {
        return courseTopicDocumentation;
    }

    public void setCourseTopicDocumentation(Object courseTopicDocumentation) {
        this.courseTopicDocumentation = courseTopicDocumentation;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

}
