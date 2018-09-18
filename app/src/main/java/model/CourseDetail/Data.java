
package model.CourseDetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("instructor_id")
    @Expose
    private String instructorId;
    @SerializedName("course_title")
    @Expose
    private String courseTitle;
    @SerializedName("course_short_title")
    @Expose
    private String courseShortTitle;
    @SerializedName("course_description")
    @Expose
    private String courseDescription;
    @SerializedName("course_language_id")
    @Expose
    private String courseLanguageId;
    @SerializedName("course_category_id")
    @Expose
    private String courseCategoryId;
    @SerializedName("course_sub_category_id")
    @Expose
    private String courseSubCategoryId;
    @SerializedName("course_level_id")
    @Expose
    private String courseLevelId;
    @SerializedName("course_image")
    @Expose
    private String courseImage;
    @SerializedName("course_introduction_video")
    @Expose
    private String courseIntroductionVideo;
    @SerializedName("course_welcome_message")
    @Expose
    private String courseWelcomeMessage;
    @SerializedName("course_complete_message")
    @Expose
    private String courseCompleteMessage;
    @SerializedName("course_duration")
    @Expose
    private String courseDuration;
    @SerializedName("course_url")
    @Expose
    private String courseUrl;
    @SerializedName("is_published")
    @Expose
    private String isPublished;
    @SerializedName("is_approved")
    @Expose
    private String isApproved;
    @SerializedName("is_deleted")
    @Expose
    private String isDeleted;
    @SerializedName("created_dt")
    @Expose
    private String createdDt;
    @SerializedName("updated_dt")
    @Expose
    private String updatedDt;
    @SerializedName("published_dt")
    @Expose
    private String publishedDt;
    @SerializedName("is_popular")
    @Expose
    private String isPopular;
    @SerializedName("isFavourite")
    @Expose
    private String isFavourite;
    @SerializedName("isSubscribed")
    @Expose
    private String isSubscribed;
    @SerializedName("modules")
    @Expose
    private List<Module> modules = null;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("sub_category")
    @Expose
    private SubCategory subCategory;
    @SerializedName("level")
    @Expose
    private Level level;
    @SerializedName("language")
    @Expose
    private Language language;
    @SerializedName("author")
    @Expose
    private Author author;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
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

    public String getCourseLanguageId() {
        return courseLanguageId;
    }

    public void setCourseLanguageId(String courseLanguageId) {
        this.courseLanguageId = courseLanguageId;
    }

    public String getCourseCategoryId() {
        return courseCategoryId;
    }

    public void setCourseCategoryId(String courseCategoryId) {
        this.courseCategoryId = courseCategoryId;
    }

    public String getCourseSubCategoryId() {
        return courseSubCategoryId;
    }

    public void setCourseSubCategoryId(String courseSubCategoryId) {
        this.courseSubCategoryId = courseSubCategoryId;
    }

    public String getCourseLevelId() {
        return courseLevelId;
    }

    public void setCourseLevelId(String courseLevelId) {
        this.courseLevelId = courseLevelId;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseIntroductionVideo() {
        return courseIntroductionVideo;
    }

    public void setCourseIntroductionVideo(String courseIntroductionVideo) {
        this.courseIntroductionVideo = courseIntroductionVideo;
    }

    public String getCourseWelcomeMessage() {
        return courseWelcomeMessage;
    }

    public void setCourseWelcomeMessage(String courseWelcomeMessage) {
        this.courseWelcomeMessage = courseWelcomeMessage;
    }

    public String getCourseCompleteMessage() {
        return courseCompleteMessage;
    }

    public void setCourseCompleteMessage(String courseCompleteMessage) {
        this.courseCompleteMessage = courseCompleteMessage;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseUrl() {
        return courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public String getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(String isPublished) {
        this.isPublished = isPublished;
    }

    public String getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(String isApproved) {
        this.isApproved = isApproved;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(String createdDt) {
        this.createdDt = createdDt;
    }

    public String getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(String updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getPublishedDt() {
        return publishedDt;
    }

    public void setPublishedDt(String publishedDt) {
        this.publishedDt = publishedDt;
    }

    public String getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(String isPopular) {
        this.isPopular = isPopular;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(String isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
