
package model.CourseDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("instructor_id")
    @Expose
    private String instructorId;
    @SerializedName("role_id")
    @Expose
    private String roleId;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private Object zip;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("short_description")
    @Expose
    private Object shortDescription;
    @SerializedName("highest_qualification")
    @Expose
    private Object highestQualification;
    @SerializedName("profile_bio")
    @Expose
    private Object profileBio;
    @SerializedName("website_url")
    @Expose
    private Object websiteUrl;
    @SerializedName("google_plus_url")
    @Expose
    private Object googlePlusUrl;
    @SerializedName("twitter_url")
    @Expose
    private Object twitterUrl;
    @SerializedName("facebook_url")
    @Expose
    private Object facebookUrl;
    @SerializedName("linkedin_url")
    @Expose
    private Object linkedinUrl;
    @SerializedName("youtube_url")
    @Expose
    private Object youtubeUrl;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("ip_address")
    @Expose
    private String ipAddress;
    @SerializedName("name")
    @Expose
    private String name;

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getZip() {
        return zip;
    }

    public void setZip(Object zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Object getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(Object highestQualification) {
        this.highestQualification = highestQualification;
    }

    public Object getProfileBio() {
        return profileBio;
    }

    public void setProfileBio(Object profileBio) {
        this.profileBio = profileBio;
    }

    public Object getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(Object websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Object getGooglePlusUrl() {
        return googlePlusUrl;
    }

    public void setGooglePlusUrl(Object googlePlusUrl) {
        this.googlePlusUrl = googlePlusUrl;
    }

    public Object getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(Object twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public Object getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(Object facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public Object getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(Object linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public Object getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(Object youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
