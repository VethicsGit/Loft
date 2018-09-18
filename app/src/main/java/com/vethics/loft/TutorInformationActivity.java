package com.vethics.loft;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import customview.FontChangeCrawler;
import fragment.FavouritesFragment;
import model.ForgotPassword.ForgotPasswordError;
import model.TutorProfile.TutorSuccessAllCourse;
import model.TutorProfile.TutorSuccessData;
import model.TutorProfile.TutorSuccessResponse;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.AndroidUtils;

public class TutorInformationActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvTutorProfileStudentCount, tvTutorProfileCoursesCount, tvTutorName, tvTutorRatings, tvTutorPersonalBio, tvTutorDesignation;
    RatingBar rbTutorRatings;
    ImageView ivTutorFbLink, ivTutorGplusLink, ivTutorLinkedinLink;
    String strTutorId;
    private final String TAG = getClass().getCanonicalName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            strTutorId = intent.getStringExtra("tutorId");
        }
        if (AndroidUtils.hasConnection(TutorInformationActivity.this)) {
            fetchTutorProfile();
        } else {
            finish();
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "font/avenirltstd_light.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        tvTutorProfileStudentCount = (TextView) findViewById(R.id.tv_tutor_profile_student_count);
        tvTutorProfileCoursesCount = (TextView) findViewById(R.id.tv_tutor_profile_courses_count);
        tvTutorName = (TextView) findViewById(R.id.tv_tutor_name);
        tvTutorDesignation = (TextView) findViewById(R.id.tv_tutor_designation);
        tvTutorRatings = (TextView) findViewById(R.id.tv_tutor_rating);
        rbTutorRatings = (RatingBar) findViewById(R.id.rb_tutor_rating);
        tvTutorPersonalBio = (TextView) findViewById(R.id.tv_tutor_personal_bio);

        ivTutorFbLink = (ImageView) findViewById(R.id.iv_tutor_fb_link);
        ivTutorGplusLink = (ImageView) findViewById(R.id.iv_tutor_gplus_link);
        ivTutorLinkedinLink = (ImageView) findViewById(R.id.iv_tutor_linkedin_link);

        FavouritesFragment fragment = new FavouritesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_frame, fragment, "Favourites").commit();

        //fetchTutorProfile();

        ivTutorFbLink.setOnClickListener(this);
        ivTutorGplusLink.setOnClickListener(this);
        ivTutorLinkedinLink.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(this);
    }

    private void fetchTutorProfile() {
        final ProgressDialog loading = new ProgressDialog(TutorInformationActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        if (strTutorId == null)
            strTutorId = "2";
        API apiService = APIClient.getClient().create(API.class);
        Call<TutorSuccessResponse> call1 = apiService.fetch_tutor_info(strTutorId);
        call1.enqueue(new Callback<TutorSuccessResponse>() {

            @Override
            public void onResponse(@NonNull Call<TutorSuccessResponse> call, @NonNull retrofit2.Response<TutorSuccessResponse> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    TutorSuccessResponse tutorsuccessresonse = response.body();
                    String strStatus = tutorsuccessresonse.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        TutorSuccessData tutorSuccessData = tutorsuccessresonse.getData();
                        tvTutorName.setText(tutorSuccessData.getFirstName() + " " + tutorSuccessData.getLastName());
                        tvTutorDesignation.setText(tutorSuccessData.getEmail());
                        tvTutorProfileStudentCount.setText(Html.fromHtml(tutorSuccessData.getTotalStudents() + "\n" + "<br><font color=\"#ffd461\">" + getResources().getString(R.string.students) + "</font>"));
                        tvTutorProfileCoursesCount.setText(Html.fromHtml(tutorSuccessData.getTotalCourses() + "\n" + "<br><font color=\"#ffd461\">" + getResources().getString(R.string.courses) + "</font>"));

                        List<TutorSuccessAllCourse> tutorSuccessAllCourseArrayList = tutorSuccessData.getAllCourses();

                    }
                } else {
                    Log.e(TAG, "Response Error : " + response.code());
                    try {
                        Gson gson = new Gson();
                        ForgotPasswordError forgotpasswordError = gson.fromJson(response.errorBody().string(), ForgotPasswordError.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TutorSuccessResponse> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                Log.e(TAG, "forgotpasswordData : " + t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == ivTutorFbLink) {
            Toast.makeText(this, "Facebook Clicked!", Toast.LENGTH_SHORT).show();
        } else if (view == ivTutorGplusLink) {
            Toast.makeText(this, "GPlus Clicked!", Toast.LENGTH_SHORT).show();
        } else if (view == ivTutorLinkedinLink) {
            Toast.makeText(this, "Linkedin Clicked!", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}
