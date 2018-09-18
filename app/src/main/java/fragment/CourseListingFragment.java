package fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vethics.loft.CourseDetailsActivity;
import com.vethics.loft.R;

import java.io.IOException;
import java.util.ArrayList;

import adapter.FavouritesCoursesAdapter;
import interfaces.OnRecyclerViewItemClickListner;
import model.CourseListing.CourseListingData;
import model.CourseListing.CourseListingError;
import model.CourseListing.CourseListingResult;
import model.CourseListing.CourseListingSuccessResponse;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.SessionManager;

public class CourseListingFragment extends DialogFragment {
    RecyclerView rvFavourites;
    FloatingActionButton fabFilter;
    String strCategoryId = "", strSubCategory = "", strSearchKeyword = "", strLevelId = "", strLanguageId = "", strSortBy = "", strSortType = "";
    int strPageno;
    ArrayList<CourseListingData> courseListingData;
    ArrayList<CourseListingResult> courseListingResult;
    FavouritesCoursesAdapter favouritesCoursesAdapter;
    private final String TAG = getClass().getCanonicalName();
    private String strStudentId;


    public CourseListingFragment newInstance(String strCategoryId, int strPageNo, String strSearchKeyword, String strLevelId, String strLanguageId, String strSortBy, String strSortType) {
        CourseListingFragment courseListingFragment = new CourseListingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("strCategoryId", strCategoryId);
        bundle.putInt("strPageNo", strPageNo);
        bundle.putString("strSearchKeyword", strSearchKeyword);
        bundle.putString("strLevelId", strLevelId);
        bundle.putString("strLanguageId", strLanguageId);
        bundle.putString("strSortBy", strSortBy);
        bundle.putString("strSortType", strSortType);
        courseListingFragment.setArguments(bundle);
        return courseListingFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course_listing, container, false);
        rvFavourites = (RecyclerView) v.findViewById(R.id.rv_course_listing);
        fabFilter = (FloatingActionButton) v.findViewById(R.id.fab_course_listing_filter);
        SharedPreferences spLogin = getActivity().getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");
        if (getArguments() != null) {
            strCategoryId = getArguments().getString("strCategoryId");
            strPageno = getArguments().getInt("strPageno");
            strSearchKeyword = getArguments().getString("strSearchKeyword");
            strLevelId = getArguments().getString("strLevelId");
            strLanguageId = getArguments().getString("strLanguageId");
            strSortBy = getArguments().getString("strSortBy");
            strSortType = getArguments().getString("strSortType");
        } else {
            strCategoryId = "";
            strSubCategory = "";
            strPageno = 0;
            strSearchKeyword = "";
            strLevelId = "";
            strLanguageId = "";
            strSortBy = "";
            strSortType = "";
        }

//        fetchInitialCategoryCourses();

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDiag();
            }
        });
        return v;
    }

 /*   private void fetchInitialCategoryCourses() {
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);

        Call<CourseListingSuccessResponse> call1 = apiService.common_search_filter(strStudentId, String.valueOf(strPageno), strCategoryId, strSubCategory, strSearchKeyword, strLevelId, strLanguageId, strSortBy, strSortType);
        call1.enqueue(new Callback<CourseListingSuccessResponse>() {

            @Override
            public void onResponse(@NonNull Call<CourseListingSuccessResponse> call, @NonNull retrofit2.Response<CourseListingSuccessResponse> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    courseListingResult.clear();
                    //Log.e(TAG+"Response Success : ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData().getResults()));
                    CourseListingSuccessResponse categoryDetails = response.body();
                    String strStatus = categoryDetails.getStatus();
                    courseListingData.add(categoryDetails.getData());
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = categoryDetails.getMessage();
                        courseListingResult.addAll(categoryDetails.getData().getResults());

                        favouritesCoursesAdapter = new FavouritesCoursesAdapter(getActivity(), courseListingResult, new OnRecyclerViewItemClickListner() {
                            @Override
                            public void onItemClick(int position) {
                                Intent i = new Intent(getActivity(), CourseDetailsActivity.class);
                                i.putExtra("courseId", courseListingResult.get(position).getCourseId());
                                startActivity(i);
                                getActivity().overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                                //Toast.makeText(getActivity(), "JKLJBNik IK OIK ", Toast.LENGTH_SHORT).show();
                            }
                        });
                        rvFavourites.setAdapter(favouritesCoursesAdapter);
                    }
                } else {
                    //Log.e(TAG+"Response Error:", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    try {
                        Gson gson = new Gson();
                        CourseListingError userDatailsError = gson.fromJson(response.errorBody().string(), CourseListingError.class);
                        Toast.makeText(getActivity(), userDatailsError.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
*/
}
