package model.CourseListing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import model.CourseDetailsbyCourseId.Language;
import model.CourseDetailsbyCourseId.SubCategory;

public class CourseListingData {

    @SerializedName("course_image_path")
    @Expose
    private String courseImagePath;
    @SerializedName("course_url")
    @Expose
    private String courseUrl;
    @SerializedName("subCategories")
    @Expose
    private List<SubCategory> subCategories = null;
    @SerializedName("results")
    @Expose
    private List<CourseListingResult> results = null;
    @SerializedName("categories")
    @Expose
    private List<CourseListingCategory> categories = null;
    @SerializedName("levels")
    @Expose
    private List<CourseListingLevel> levels = null;
    @SerializedName("languages")
    @Expose
    private List<Language> languages = null;

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

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public List<CourseListingResult> getResults() {
        return results;
    }

    public void setResults(List<CourseListingResult> results) {
        this.results = results;
    }

    public List<CourseListingCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<CourseListingCategory> categories) {
        this.categories = categories;
    }

    public List<CourseListingLevel> getLevels() {
        return levels;
    }

    public void setLevels(List<CourseListingLevel> levels) {
        this.levels = levels;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

}
