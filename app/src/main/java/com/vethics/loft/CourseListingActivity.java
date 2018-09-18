package com.vethics.loft;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import fragment.FavouritesFragment;
import model.CourseListing.CourseListingData;
import model.CourseListing.CourseListingSuccessResponse;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.SessionManager;

public class CourseListingActivity extends AppCompatActivity {
    String strCategoryId, strSubCategory, strFilterLevel, strFilterLanguage, strCategory;
    private final String TAG = getClass().getCanonicalName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private String strStudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        if (i.getExtras() != null) {
            strCategoryId = i.getStringExtra("categoryId");
            Log.e(TAG, "categoryId : " + strCategoryId);
        }
        SharedPreferences spLogin = getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");
        FavouritesFragment favouritesFragment = FavouritesFragment.newInstance(strCategoryId);
        getSupportFragmentManager().beginTransaction().replace(R.id.courseContainer, favouritesFragment).commit();
        //fetchCategoryCourses();
    }

    private void fetchCategoryCourses() {
        final ProgressDialog loading = new ProgressDialog(CourseListingActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);

        Call<CourseListingSuccessResponse> call1 = apiService.common_search_filter(strStudentId, "0", strCategoryId, strSubCategory, "development", "", "", "", "");
        call1.enqueue(new Callback<CourseListingSuccessResponse>() {

            @Override
            public void onResponse(@NonNull Call<CourseListingSuccessResponse> call, @NonNull retrofit2.Response<CourseListingSuccessResponse> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData().getResults()));
                    CourseListingSuccessResponse categoryDetails = response.body();
                    String strStatus = categoryDetails.getStatus();

                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = categoryDetails.getMessage();
                        ArrayList<CourseListingData> courseListingData = new ArrayList<>();
                        courseListingData.add(categoryDetails.getData());
                        FavouritesFragment favouritesFragment = FavouritesFragment.newInstance(strCategoryId);
                        getSupportFragmentManager().beginTransaction().replace(R.id.courseContainer, favouritesFragment).commit();
                    }
                } else {
                    Log.e(TAG, "Response Error:" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    /*try {
                        Gson gson = new Gson();
                        UserDetailsError userDetailsError = gson.fromJson(response.errorBody().string(), UserDetailsError.class);
                        Toast.makeText(getActivity(.this, userDetailsError.getMessage().getUserId(), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }

            @Override
            public void onFailure(@NonNull Call<CourseListingSuccessResponse> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                Log.e(TAG, "categoryData : " + t.getMessage());
            }
        });
    }

}
