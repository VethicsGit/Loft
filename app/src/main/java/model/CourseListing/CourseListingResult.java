package model.CourseListing;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseListingResult implements Parcelable {

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
    @SerializedName("course_image")
    @Expose
    private String courseImage;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("isFavourite")
    @Expose
    private String isFavourite;
    @SerializedName("isSubscribed")
    @Expose
    private String isSubscribed;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("total_reviews")
    @Expose
    private String totalReviews;

    private CourseListingResult(Parcel in) {
        courseId = in.readString();
        courseTitle = in.readString();
        courseShortTitle = in.readString();
        courseDescription = in.readString();
        courseUrl = in.readString();
        courseImage = in.readString();
        categoryId = in.readString();
        categoryName = in.readString();
        isFavourite = in.readString();
        isSubscribed = in.readString();
        rating = in.readString();
        totalReviews = in.readString();
    }

    public static final Creator<CourseListingResult> CREATOR = new Creator<CourseListingResult>() {
        @Override
        public CourseListingResult createFromParcel(Parcel in) {
            return new CourseListingResult(in);
        }

        @Override
        public CourseListingResult[] newArray(int size) {
            return new CourseListingResult[size];
        }
    };

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

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(String totalReviews) {
        this.totalReviews = totalReviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseId);
        dest.writeString(courseTitle);
        dest.writeString(courseShortTitle);
        dest.writeString(courseDescription);
        dest.writeString(courseUrl);
        dest.writeString(courseImage);
        dest.writeString(categoryId);
        dest.writeString(categoryName);
        dest.writeString(isFavourite);
        dest.writeString(isSubscribed);
        dest.writeString(rating);
        dest.writeString(totalReviews);
    }
}
