
package model.CourseDetailsbyCourseId;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("category_url_friendly_name")
    @Expose
    private String categoryUrlFriendlyName;
    @SerializedName("category_description")
    @Expose
    private String categoryDescription;

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

    public String getCategoryUrlFriendlyName() {
        return categoryUrlFriendlyName;
    }

    public void setCategoryUrlFriendlyName(String categoryUrlFriendlyName) {
        this.categoryUrlFriendlyName = categoryUrlFriendlyName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

}
