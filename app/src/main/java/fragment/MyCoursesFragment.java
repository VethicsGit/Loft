package fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.vethics.loft.CourseDetailsActivity;
import com.vethics.loft.R;

import java.util.List;

import adapter.CoursesHistoryAdapter;
import interfaces.OnRecyclerViewItemClickListner;
import model.MyCourses.MyCoursesSuccess;
import model.MyCourses.MyCoursesSuccessData;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.SessionManager;

public class MyCoursesFragment extends Fragment {
    private final String TAG = getClass().getCanonicalName();
    RecyclerView rvMyCourse;
    private String strStudentId;
    private static final int sColumnWidth = 150; // assume cell width of 120dp

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_courses, container, false);
        rvMyCourse = (RecyclerView) v.findViewById(R.id.rv_my_course);

        SharedPreferences spLogin = getActivity().getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");

        GridLayoutManager pgridLayoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        rvMyCourse.setLayoutManager(pgridLayoutManager);
        rvMyCourse.setNestedScrollingEnabled(false);
        fetchSubscribedCourses();
        return v;
    }

    private void calculateSize() {
        int spanCount = (int) Math.floor(rvMyCourse.getWidth() / convertDPToPixels(sColumnWidth));
        ((GridLayoutManager) rvMyCourse.getLayoutManager()).setSpanCount(spanCount);
    }

    private float convertDPToPixels(int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return dp * logicalDensity;
    }

    public void fetchSubscribedCourses() {
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<MyCoursesSuccess> call1 = apiService.get_subscribed_course_list("30");
        call1.enqueue(new Callback<MyCoursesSuccess>() {

            @Override
            public void onResponse(@NonNull Call<MyCoursesSuccess> call, @NonNull final retrofit2.Response<MyCoursesSuccess> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    Log.e(TAG + "Response Success : ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                            MyCoursesSuccess myCoursesSuccess =response.body();
                            String strStatus = myCoursesSuccess.getStatus();
                            if (strStatus.equalsIgnoreCase("success")){
                                String strMessage = myCoursesSuccess.getMessage();

                                List <MyCoursesSuccessData>myCoursesSuccessDataList=myCoursesSuccess.getData();

                                CoursesHistoryAdapter coursesHistoryAdapter =new CoursesHistoryAdapter(myCoursesSuccessDataList,getContext());
                                rvMyCourse.setAdapter(coursesHistoryAdapter );

                            }
                  /*  CoursesHistoryAdapter popularCoursesAdapter = new CoursesHistoryAdapter(getActivity(), response.body().getData(), new OnRecyclerViewItemClickListner() {
                        @Override
                        public void onItemClick(int position) {
                            Intent i = new Intent(getActivity(), CourseDetailsActivity.class);
                            i.putExtra("courseId", response.body().getData().get(position).getCourseId());
                            startActivity(i);
                            getActivity().finish();
                            getActivity().overridePendingTransition(0, R.anim.slide_in_right);
                        }
                    });*/



                } else {
                    Log.e(TAG + "Response Error : ", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyCoursesSuccess> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
            }
        });
    }

}
