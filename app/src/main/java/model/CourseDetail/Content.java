
package model.CourseDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("course_media_content_id")
    @Expose
    private String courseMediaContentId;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("file_path")
    @Expose
    private String filePath;
    @SerializedName("course_topic_id")
    @Expose
    private String courseTopicId;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("file_type")
    @Expose
    private String fileType;

    public String getCourseMediaContentId() {
        return courseMediaContentId;
    }

    public void setCourseMediaContentId(String courseMediaContentId) {
        this.courseMediaContentId = courseMediaContentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCourseTopicId() {
        return courseTopicId;
    }

    public void setCourseTopicId(String courseTopicId) {
        this.courseTopicId = courseTopicId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
