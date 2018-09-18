
package model.TutorProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TutorSuccessData {

    @SerializedName("instructor_id")
    @Expose
    private String instructorId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("short_description")
    @Expose
    private Object shortDescription;
    @SerializedName("profile_bio")
    @Expose
    private Object profileBio;
    @SerializedName("totalCourses")
    @Expose
    private String totalCourses;
    @SerializedName("totalStudents")
    @Expose
    private String totalStudents;
    @SerializedName("course_image_path")
    @Expose
    private String courseImagePath;
    @SerializedName("course_url")
    @Expose
    private String courseUrl;
    @SerializedName("allCourses")
    @Expose
    private List<TutorSuccessAllCourse> allCourses = null;

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Object getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(Object shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Object getProfileBio() {
        return profileBio;
    }

    public void setProfileBio(Object profileBio) {
        this.profileBio = profileBio;
    }

    public String getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(String totalCourses) {
        this.totalCourses = totalCourses;
    }

    public String getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(String totalStudents) {
        this.totalStudents = totalStudents;
    }

    public String getCourseImagePath() {
        return courseImagePath;
    }

    public void setCourseImagePath(String courseImagePath) {
        this.courseImagePath = courseImagePath;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public List<TutorSuccessAllCourse> getAllCourses() {
        return allCourses;
    }

    public void setAllCourses(List<TutorSuccessAllCourse> allCourses) {
        this.allCourses = allCourses;
    }

}
