package utils;

import model.CategoryResponse.CategorySuccessResponse;
import model.ChangePasswordSuccess.ChangePasswordSuccess;
import model.CheckSocialLogin.CheckSocialError;
import model.CheckSocialLogin.CheckSocialSuccess;
import model.CourseDetail.CourseDetail;
import model.CourseDetailsbyCourseId.CourseDetailsSuccessResponse;
import model.CourseEnroll.CourseEnrollSuccess;
import model.CourseListing.CourseListingSuccessResponse;
import model.FavCourse.FavCourseList;
import model.FavouriteSuccess.FavouriteSuccess;
import model.ForgotPassword.ForgotPasswordSuccess;
import model.LoginResponse.LoginSuccess;
import model.MyCourses.MyCoursesSuccess;
import model.PopulrCourse.PopularCourse;
import model.RegisterResponse.RegisterSuccess;
import model.ResetPasssword.ResetPasswordSuccess;
import model.TutorProfile.TutorSuccessResponse;
import model.UpdateProfile.UpdateProfileSuccess;
import model.UserDetails.UserDetailsSuccess;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Chirag on 1/11/2018.
 */

public interface API {

    @FormUrlEncoded
    @POST("api/user/login")
    Call<LoginSuccess> login(@Field("user_name") String username,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("api/user/check_social_id")
    Call<CheckSocialSuccess> check_social_login(@Field("media_type") String media_type,
                                                @Field("media_id") String media_id);

    @FormUrlEncoded
    @POST("api/user/social_signin")
    Call<CheckSocialSuccess> social_login(@Field("media_type") String media_type,
                                          @Field("media_id") String media_id,
                                          @Field("name") String name,
                                          @Field("email") String email,
                                          @Field("gender") String gender,
                                          @Field("date_of_birth") String date_of_birth,
                                          @Field("profile_pic") String profile_pic);

    @FormUrlEncoded
    @POST("api/user/register")
    Call<RegisterSuccess> register(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("password") String password,
                                   @Field("gender") String gender,
                                   @Field("dob") String dob);

    @FormUrlEncoded
    @POST("api/user/forget_password")
    Call<ForgotPasswordSuccess> forgot_password(@Field("user_name") String user_name);

    @FormUrlEncoded
    @POST("api/user/reset_password")
    Call<ChangePasswordSuccess> change_password(@Field("user_name") String user_name,
                                                @Field("old_password") String old_password,
                                                @Field("password") String password,
                                                @Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST("api/user/reset_password/{token}")
    Call<ResetPasswordSuccess> reset_password(@Path("token") String token,
                                              @Field("password") String password,
                                              @Field("confirm_password") String confirm_password);

    @GET("api/user/{student_id}")
    Call<UserDetailsSuccess> user_details(@Path("student_id") String id);

    @Multipart
    @POST("api/user/{student_id}")
    Call<UpdateProfileSuccess> update_profile(@Path("student_id") String id,
                                              @Part("name") RequestBody first_name,
                                              @Part("gender") RequestBody gender,
                                              @Part("date_of_birth") RequestBody date_of_birth,
                                              @Part("address") RequestBody address,
                                              @Part("city") RequestBody city,
                                              @Part("country") RequestBody country,
                                              @Part("state") RequestBody state,
                                              @Part("email") RequestBody email,
                                              @Part MultipartBody.Part file);

    @GET("api/course/{course_id}/{student_id}")
    Call<CourseDetailsSuccessResponse> course_details_by_id(@Path("course_id") String course_id,
                                                            @Path("student_id") String student_id);

    @GET("api/Course/all_category")
    Call<CategorySuccessResponse> fetch_categories();

    @FormUrlEncoded
    @POST("api/Course/commonCoursesForAllFilter")
    Call<CourseListingSuccessResponse> common_search_filter(@Field("student_id") String student_id,
                                                            @Field("page") String page,
                                                            @Field("category_id") String category_id,
                                                            @Field("subcategory_id") String subcategory_id,
                                                            @Field("search_keyword") String search_keyword,
                                                            @Field("level_id") String level_id,
                                                            @Field("language_id") String language_id,
                                                            @Field("sort_by") String sort_by,
                                                            @Field("sort_type") String sort_type);

    @FormUrlEncoded
    @POST("api/User/instructorProfile")
    Call<TutorSuccessResponse> fetch_tutor_info(@Field("instructor_id") String instructor_id);

    @FormUrlEncoded
    @POST("api/course/addToFavouriteCourse")
    Call<FavouriteSuccess> add_favourites(@Field("course_id") String course_id,
                                          @Field("student_id") String student_id);

    /*Stripe Payment*/
    @FormUrlEncoded
    @POST("api/course/studentSubscribedToCourse")
    Call<CourseEnrollSuccess> make_payment_token(@Field("stripeToken") String token,
                                                 @Field("student_id") String student_id,
                                                 @Field("email") String email,
                                                 @Field("name") String name,
                                                 @Field("course_id") String course_id,
                                                 @Field("course_price") String course_price,
                                                 @Field("course_title") String course_title);

    @FormUrlEncoded
    @POST("api/course/studentSubscribedToCourse")
    Call<CourseEnrollSuccess> make_payment_without_token(@Field("student_id") String student_id,
                                                         @Field("email") String email,
                                                         @Field("name") String name,
                                                         @Field("course_id") String course_id,
                                                         @Field("course_price") String course_price,
                                                         @Field("course_title") String course_title);

    @FormUrlEncoded
    @POST("api/course/studentRateReviewCourse")
    Call<CheckSocialError> submit_rate_review(@Field("student_id") String student_id,
                                              @Field("course_id") String course_id,
                                              @Field("rating") int rating,
                                              @Field("review") String review);
    @FormUrlEncoded
    @POST("api/course/student_Subscribed_To_Course")
    Call<MyCoursesSuccess> get_subscribed_course_list(@Field("student_id") String student_id);



    @GET("api/course/popular_course_list/{student_id}")
    Call<PopularCourse> Course_list(@Path("student_id") String student_id);



    @GET("api/course/fav_course_list/{student_id}")
    Call<FavCourseList>Fav_course(@Path("student_id")String student_id);

    @GET("api/course/{student_id}")
    Call<CourseDetail> Course_detail();


    /*@FormUrlEncoded
    @POST("myaccount/update_user_mobile")
    Call<ResponseBody> updateprofile(@Field("user_id") String user_id,
                                     @Field("picture") String picture,
                                     @Field("firstname") String firstname,
                                     @Field("lastname") String lastname,
                                     @Field("subcaste") String subcaste,
                                     @Field("marital_status") String marital_status,
                                     @Field("dob") String dob,
                                     @Field("gender") String gender,
                                     @Field("con") String con,
                                     @Field("state") String state,
                                     @Field("city") String city,
                                     @Field("profession") String profession);

    @FormUrlEncoded
    @POST("Changepassword/change_password")
    Call<ModelChangePassword> changePass(@Field("cur_pass") String strCurrentpass, @Field("new_pass") String strNewpass, @Field("user_id") String userId);

    @GET("get_subcaste")
    Call<ResponseBody> get_subcaste();

    @GET("jobs/fetch_job_data")
    Call<List<JobListDatum>> fetch_job_data();

    @GET("jobs/fetch_job_data")
    Call<List<ModelPersonalJob>> fetch_job_data1(@Query("user_id") String user_id);

    @POST("Register/get_profession")
    Call<List<Profession>> get_profession();

    @POST("myaccount/get_country_mobile")
    Call<List<ModelCountry>> get_country();

    @GET("myaccount/get_states_mobile")
    Call<List<ModelState>> get_states(@Query("country_id") String strCountryId);

    @POST("myaccount/get_city_mobile")
    Call<List<ModelCity>> get_city(@Query("state_id") String strStateId);

    @POST("myaccount/get_user_info")
    Call<ModelProfile> userInfo(@Query("user_id") String userid);

    @GET("myaccount/get_user_info")
    Call<ModelProfileDetails> userInfo1(@Query("user_id") String userid);

    @GET("event/get_event")
    Call<List<EventList>> get_event();

    @GET("Event/getEventById")
    Call<ModelEventDetails> get_event_details(@Query("event_id") String eventid);

    @GET("Society/index")
    Call<List<Society>> get_society(@Query("user_id") String userid);

    @FormUrlEncoded
    @POST("society/join_society")
    Call<SocietyJoin> join_society(@Field("society_id") String societyId, @Field("join_user_id") String joinUseId, @Field("member_counter") String memberCounter, @Field("society_name") String societyName);

    @GET("family/get_relation")
    Call<ResponseBody> get_relation(@Query("user_id") String userid);

    @GET("periodicals")
    Call<List<Periodical>> periodicals();

    @FormUrlEncoded
    @POST("addnews/addnews_reg")
    Call<NewsPost> addnews_reg(@Field("user_id") String user_id,
                               @Field("news_img") String picture,
                               @Field("news_name") String firstname,
                               @Field("contact_no") String lastname,
                               @Field("contact_name") String subcaste,
                               @Field("start_date") String marital_status,
                               @Field("end_date") String dob,
                               @Field("time") String gender, @Field("venue") String venue);

    @GET("addnews/newslist")
    Call<List<ModelNewsList>> newslist();

    @GET("addnews/newslist")
    Call<List<ModelNewsList>> newslist1(@Query("user_id") String user_id);

    @GET("Society/get_society_by_user_id")
    Call<List<ModelPersonalSociety>> get_personal_society(@Query("user_id") String user_id);

    @GET("myaccount/get_data_from_table")
    Call<List<ModelProfessionSociety>> get_professional_Society(@Query("table_name") String table_name);

    @FormUrlEncoded
    @POST("Society/edit_society")
    Call<NewsPost> edit_society(
            @Field("society_id") String society_id,
            @Field("society_name") String society_name,
            @Field("reg_status") String reg_status,
            @Field("president_name") String president_name,
            @Field("president_contact_number") String president_contact_number,
            @Field("secretary_name") String secretary_name,
            @Field("secretary_contact_number") String secretary_contact_number,
            @Field("address") String address,
            @Field("country") String country,
            @Field("state") String state,
            @Field("city") String city,
            @Field("picture") String picture
    );

    @FormUrlEncoded
    @POST("Addnews/addnews_reg")
    Call<NewsPost> newslist_update(@Field("user_id") String user_id,
                                   @Field("news_id") String news_id,
                                   @Field("news_img") String picture,
                                   @Field("news_name") String firstname,
                                   @Field("contact_no") String lastname,
                                   @Field("contact_name") String subcaste,
                                   @Field("start_date") String marital_status,
                                   @Field("end_date") String dob,
                                   @Field("time") String gender, @Field("venue") String venue);

    @GET("jobs/get_area_of_work")
    Call<List<ModelAreaList>> get_area_of_work();

    @FormUrlEncoded
    @POST("jobs/add_jobs")
    Call<JobResponse> add_jobs(@Field("user_id") String user_id,
                               @Field("job_title") String picture,
                               @Field("area_of_work") String firstname,
                               @Field("department") String lastname,
                               @Field("designation") String subcaste,
                               @Field("qualification") String marital_status,
                               @Field("last_date_apply") String dob,
                               @Field("contact_name") String gender,
                               @Field("contact_email") String venue,
                               @Field("contact_no") String contact,
                               @Field("job_description") String desc);

    @FormUrlEncoded
    @POST("jobs/update_job")
    Call<JobResponse> update_job(@Field("user_id") String user_id,
                                 @Field("job_id") String job_id,
                                 @Field("job_title") String picture,
                                 @Field("area_of_work") String firstname,
                                 @Field("department") String lastname,
                                 @Field("designation") String subcaste,
                                 @Field("qualification") String marital_status,
                                 @Field("last_date_apply") String dob,
                                 @Field("contact_name") String gender,
                                 @Field("contact_email") String venue,
                                 @Field("contact_no") String contact,
                                 @Field("job_description") String desc,
                                 @Field("random") String random,
                                 @Field("updated_date") String updated_date04);


    @FormUrlEncoded
    @POST("myaccount/update_user_mobile")
    Call<ModelUpadateProfile> update_user(@Field("user_id") String user_id,
                                          @Field("firstname") String firstname,
                                          @Field("lastname") String lastname,
                                          @Field("mobile") String mobile,
                                          @Field("subcaste") String subcaste,
                                          @Field("other_subcast") String other_subcast,
                                          @Field("gotra_self") String gotra_self,
                                          @Field("gotra_mother") String gotra_mother,
                                          @Field("marital_status") String marital_status,
                                          @Field("dob") String dob,
                                          @Field("gender") String gender,
                                          @Field("profession") String profession,
                                          @Field("profession_others") String profession_others,
                                          @Field("organization") String Organization,
                                          @Field("area") String area,
                                          @Field("area_study_others") String area_study_others,
                                          @Field("prof_status_others") String prof_status_others,
                                          @Field("status_profession") String status_profession,
                                          @Field("profession_industry") String profession_industry,
                                          @Field("sub_category") String sub_category,
                                          @Field("designation") String designation,
                                          @Field("house_no") String house_no,

                                          @Field("pincode") String pincode,
                                          @Field("country") String country,
                                          @Field("state") String state,
                                          @Field("city") String city,
                                          @Field("contactno") String contactno,
                                          @Field("educational_qual") String educational_qual,
                                          @Field("institution") String institution,
                                          @Field("area_study") String area_study,
                                          @Field("status_of_education") String status_of_education,
                                          @Field("r_house_no") String r_house_no,
                                          @Field("r_area") String r_area,
                                          @Field("r_ward_no") String r_ward_no,
                                          @Field("r_constituency") String r_constituency,
                                          @Field("r_village") String r_village,
                                          @Field("r_tehsil") String r_tehsil,
                                          @Field("r_con") String r_con,
                                          @Field("r_state") String r_state,
                                          @Field("r_city") String r_city,
                                          @Field("p_house_no") String p_house_no,
                                          @Field("p_area") String p_area,
                                          @Field("p_ward_no") String p_ward_no,
                                          @Field("p_constituency") String p_constituency,
                                          @Field("p_village") String p_village,
                                          @Field("p_tehsil") String p_tehsil,
                                          @Field("p_con") String p_con,
                                          @Field("p_state") String p_state,
                                          @Field("p_city") String p_city,

                                          @Field("mobile2") String mobile2,

                                          @Field("landline") String landline,
                                          @Field("picture") String picture,
                                          @Field("subcaste1") String subcaste1);

    @FormUrlEncoded
    @POST("Family/add_Family")
    Call<ResponseBody> add_family(@Field("user_id") String userid, @Field("relation") String jsonArray);

    @FormUrlEncoded
    @POST("myaccount/filter")
    Call<List<ModelSearch>> search(@Field("user_id") String userid,
                                   @Field("member_id") String member_id,
                                   @Field("search") String search,
                                   @Field("country") String country,
                                   @Field("state") String state,
                                   @Field("city") String city,
                                   @Field("subcaste") String subcaste,
                                   @Field("profession") String profession,
                                   @Field("gender") String gender,
                                   @Field("educational_qualification") String educational_qualification,
                                   @Field("gotra_self") String gotra_self, @Field("marital_status") String marital);

    @FormUrlEncoded
    @POST("myaccount/filter")
    Call<ResponseBody> search1(@Field("user_id") String userid,
                               @Field("member_id") String member_id,
                               @Field("search") String search,
                               @Field("country") String country,
                               @Field("state") String state,
                               @Field("city") String city,
                               @Field("subcaste") String subcaste,
                               @Field("profession") String profession,
                               @Field("gender") String gender,
                               @Field("educational_qualification") String educational_qualification,
                               @Field("gotra_self") String gotra_self);

    @FormUrlEncoded
    @POST("Society/add_new_society")
    Call<ModeleventRegister> addSociety(@Field("user_id") String userid,
                                        @Field("society_name") String society_name,
                                        @Field("reg_status") String reg_status,
                                        @Field("president_name") String president_name,
                                        @Field("president_contact_number") String president_contact_number,
                                        @Field("secretary_name") String secretary_name,
                                        @Field("secretary_contact_number") String secretary_contact_number,
                                        @Field("address") String address,
                                        @Field("country") String country,
                                        @Field("state") String state,
                                        @Field("city") String city,
                                        @Field("picture") String picture);



    @Multipart
    @POST("Periodicals/register")
    Call<ResponseBody> register_periodical(@Part("user_id") RequestBody user_id,
                                           @Part("periodical_name") RequestBody periodical_name,
                                           @Part("registration_status") RequestBody registration_status,
                                           @Part("chief_editor") RequestBody chief_editor,
                                           @Part("contact_no") RequestBody contact_no,
                                           @Part("email") RequestBody email,
                                           @Part("office_address") RequestBody office_address,
                                           @Part("country") RequestBody country,
                                           @Part("state") RequestBody state,
                                           @Part("city") RequestBody city,
                                           @Part("pincode") RequestBody pincode,
                                           @Part("image_front") RequestBody image_front,
                                           @Part("image_back") RequestBody image_back, @Part("pdf") RequestBody file);


    @GET("myaccount/notifications")
    Call<List<ModelNotification>> notification(@Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("Jobs/delete_job")
    Call<NewsPost> job_delete(@Field("job_id") String job_id, @Field("user_id") String user_id);

    //    http://demo.vethics.in/swarnkar/mobile/Society/delete_society
    @FormUrlEncoded
    @POST("Society/delete_society")
    Call<NewsPost> delete_society(@Field("society_id") String society_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("api_add_programs.php")
    Call<ResponseBody> api_addprogram(@Field("cid") String cid,
                                      @Field("diseid") String diseid,
                                      @Field("name") String name,
                                      @Field("description") String description,
                                      @Field("img1") String img1,
                                      @Field("img2") String img2,
                                      @Field("img3") String img3);

    @Multipart
    @POST("uploadfiledemo.php")
    Call<ResponseBody> uploadfiledemo(@Part("name") String cid,
                                      @Part MultipartBody.Part image);

    @GET("api_gatepass2.php")
    Call<ModelSplash> api_getpass();
*/

}
