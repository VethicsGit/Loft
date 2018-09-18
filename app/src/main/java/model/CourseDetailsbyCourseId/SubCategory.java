
package model.CourseDetailsbyCourseId;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory {

    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("sub_catgeory_url_friendly_name")
    @Expose
    private String subCatgeoryUrlFriendlyName;
    @SerializedName("sub_category_description")
    @Expose
    private String subCategoryDescription;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCatgeoryUrlFriendlyName() {
        return subCatgeoryUrlFriendlyName;
    }

    public void setSubCatgeoryUrlFriendlyName(String subCatgeoryUrlFriendlyName) {
        this.subCatgeoryUrlFriendlyName = subCatgeoryUrlFriendlyName;
    }

    public String getSubCategoryDescription() {
        return subCategoryDescription;
    }

    public void setSubCategoryDescription(String subCategoryDescription) {
        this.subCategoryDescription = subCategoryDescription;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

}
