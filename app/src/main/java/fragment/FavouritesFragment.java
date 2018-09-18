package fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vethics.loft.CourseDetailsActivity;
import com.vethics.loft.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.FavouritesCoursesAdapter;
import adapter.PopularCoursesAdapter;
import interfaces.OnLoadMoreListener;
import interfaces.OnRecyclerViewItemClickListner;
import model.CourseListing.CourseListingData;
import model.CourseListing.CourseListingError;
import model.CourseListing.CourseListingResult;
import model.CourseListing.CourseListingSuccessResponse;
import model.FavCourse.FavCourseList;
import model.PopulrCourse.Datum;
import model.PopulrCourse.PopularCourse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.API;
import utils.APIClient;
import utils.SessionManager;

public class FavouritesFragment extends Fragment {

    RecyclerView rvFavourites;
    ArrayList<CourseListingData> courseListingData;
    ArrayList<CourseListingResult> courseListingResult;
    OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    ProgressBar pbLoadMore;
    FavouritesCoursesAdapter favouritesCoursesAdapter;
    Handler handler;
    String strCategoryId = "", strSubCategory = "", strSearchKeyword = "", strLevelId = "", strLanguageId = "", strSortBy = "", strSortType = "";
    int strPageno = 0;
    FloatingActionButton fabFilter;
    private final String TAG = getClass().getCanonicalName();
    private String strStudentId;

    public static FavouritesFragment newInstance(String strCategoryId) {
        FavouritesFragment favouritesFragment = new FavouritesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("strCategoryId", strCategoryId);
        favouritesFragment.setArguments(bundle);
        return favouritesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);
//        ((DashBoardActivity) getActivity()).getSupportActionBar().setTitle("Favourites");
       Toast.makeText(getContext(), "In Favorite Fragment", Toast.LENGTH_SHORT).show();
         rvFavourites = (RecyclerView) v.findViewById(R.id.rv_favourites);
        pbLoadMore = (ProgressBar) v.findViewById(R.id.pb_load_more);
        pbLoadMore.setVisibility(View.GONE);



        SharedPreferences spLogin = getActivity().getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");

        fabFilter = (FloatingActionButton) v.findViewById(R.id.fab_filter);
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        handler = new Handler();
        if (getArguments() != null)
            strCategoryId = getArguments().getString("strCategoryId");
        else strCategoryId = "1";
        courseListingData = new ArrayList<>();
        courseListingResult = new ArrayList<>();
        final LinearLayoutManager pgridLayoutManager = new LinearLayoutManager(getActivity());
        rvFavourites.setLayoutManager(pgridLayoutManager);
        rvFavourites.setNestedScrollingEnabled(false);
        fetchInitialCategoryCourses();

        rvFavourites.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    totalItemCount = pgridLayoutManager.getItemCount();
                    lastVisibleItem = pgridLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            }
        });

        onLoadMoreListener = new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //pbLoadMore.setVisibility(View.VISIBLE);
                //courseListingResult.addAll(courseListingResult);
                courseListingResult.add(null);
                favouritesCoursesAdapter.notifyItemInserted(courseListingResult.size() - 1);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        courseListingResult.remove(courseListingResult.size() - 1);
                        favouritesCoursesAdapter.notifyItemRemoved(courseListingResult.size());
                        strPageno = strPageno + 1;
//                        fetchCategoryCourses();
                       int start = courseListingResult.size();
                        int end = start + 5;

                        for (int i = start + 1; i <= end; i++) {
                        courseListingResult.add(courseListingResult.get(1));
                        favouritesCoursesAdapter.notifyItemInserted(courseListingResult.size());
                         }
                        favouritesCoursesAdapter.notifyDataSetChanged();
                        loading = false;
                        pbLoadMore.setVisibility(View.VISIBLE);
                        //Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT).show();
                    }
                });
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2000);
            }
        };
        return v;
    }

    private void fetchInitialCategoryCourses() {
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();


        API api = APIClient.getClient().create(API.class);
        Call<FavCourseList> call = api.Fav_course("30");
        call.enqueue(new Callback<FavCourseList>() {
            @Override
            public void onResponse(Call<FavCourseList> call, Response<FavCourseList> response) {
                loading.dismiss();
                Log.e("fav_list_response ",response.body().toString());

                if (response.isSuccessful()) {

                    Log.e("Course", "response" + response);

                    FavCourseList favCourseList = response.body();
                    String strStatus = favCourseList.getStatus();
                    if (strStatus.equalsIgnoreCase("success")) {
                        String strMessage = favCourseList.getMessage();

                        Toast.makeText(getContext(), "response" + strMessage, Toast.LENGTH_LONG).show();

                        List<model.FavCourse.Datum> datumList = favCourseList.getData();

//                        for (int i = 0; i < datumList.size(); i++) {
//
//                                Datum datumList1 = datumList.get(i);

                        favouritesCoursesAdapter = new FavouritesCoursesAdapter(datumList, getContext());
                        rvFavourites.setAdapter(favouritesCoursesAdapter);


                    }
                }
            }


            @Override
            public void onFailure(Call<FavCourseList> call, Throwable t) {

            }
        });



     /*   API apiService = APIClient.getClient().create(API.class);

        Call<CourseListingSuccessResponse> call1 = apiService.common_search_filter(strStudentId, String.valueOf(strPageno), strCategoryId, strSubCategory, strSearchKeyword, strLevelId, strLanguageId, strSortBy, strSortType);
        call1.enqueue(new Callback<CourseListingSuccessResponse>() {

            @Override
            public void onResponse(@NonNull Call<CourseListingSuccessResponse> call, @NonNull retrofit2.Response<CourseListingSuccessResponse> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ArrayList<CourseListingResult> courseListingResults = new ArrayList<>();
                    courseListingResult.clear();
                    courseListingResults.clear();
                    //Log.e(TAG+"Response Success : ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData().getResults()));
                    CourseListingSuccessResponse categoryDetails = response.body();
                    String strStatus = categoryDetails.getStatus();
                    courseListingData.add(categoryDetails.getData());
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = categoryDetails.getMessage();
                        courseListingResults.addAll(categoryDetails.getData().getResults());
                        courseListingResult.addAll(categoryDetails.getData().getResults());
                        *//*for (int i = 0; i < courseListingResults.size(); i++) {
                            if (categoryDetails.getData().getResults().get(i).getIsFavourite().equalsIgnoreCase("yes")) {
                                courseListingResult.add(categoryDetails.getData().getResults().get(i));
                            }
                        }*//*
                        favouritesCoursesAdapter = new FavouritesCoursesAdapter(getActivity(), courseListingResult, new OnRecyclerViewItemClickListner() {
                            @Override
                            public void onItemClick(int position) {
                                Intent i = new Intent(getActivity(), CourseDetailsActivity.class);
                                i.putExtra("courseId", courseListingResult.get(position).getCourseId());
                                startActivity(i);
                                getActivity().finish();
                                getActivity().overridePendingTransition(0, R.anim.slide_in_right);
                            }
                        });
                        rvFavourites.setAdapter(favouritesCoursesAdapter);
                    }
                } else {
                    Log.e(TAG + "Response Error : ", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    // try {
                    //     Gson gson = new Gson();
                    //     //CourseListingError courseListingError = gson.fromJson(response.errorBody().string(), CourseListingError.class);
                    //     //Toast.makeText(getActivity(), courseListingError.getMessage(), Toast.LENGTH_SHORT).show();
                    // } catch (IOException e) {
                    //     e.printStackTrace();
                    // }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CourseListingSuccessResponse> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
            }
        });
    }*/




       /* private void fetchCategoryCourses () {
            courseListingResult.remove(courseListingResult.size() - 1);
            favouritesCoursesAdapter.notifyItemRemoved(courseListingResult.size());
            API apiService = APIClient.getClient().create(API.class);

            Call<CourseListingSuccessResponse> call1 = apiService.common_search_filter(strStudentId, String.valueOf(strPageno), strCategoryId, strSubCategory, strSearchKeyword, strLevelId, strLanguageId, strSortBy, strSortType);
            call1.enqueue(new Callback<CourseListingSuccessResponse>() {
                @Override
                public void onResponse(@NonNull Call<CourseListingSuccessResponse> call, @NonNull retrofit2.Response<CourseListingSuccessResponse> response) {

                    if (response.isSuccessful()) {
                        Log.e(TAG + "Response Success : ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData().getResults()));
                        CourseListingSuccessResponse categoryDetails = response.body();
                        String strStatus = categoryDetails.getStatus();

                        if (strStatus.equalsIgnoreCase("Success")) {
                            String strMessage = categoryDetails.getMessage();
                            ArrayList<CourseListingResult> courseListingResults = new ArrayList<>();
                            courseListingResults.addAll(categoryDetails.getData().getResults());
                            for (int i = 0; i < courseListingResults.size(); i++) {
                                if (categoryDetails.getData().getResults().get(i).getIsFavourite().equalsIgnoreCase("yes")) {
                                    courseListingResult.add(categoryDetails.getData().getResults().get(i));
                                }
                            }
                            courseListingResult.addAll(categoryDetails.getData().getResults());
                            favouritesCoursesAdapter.notifyItemInserted(courseListingResult.size());
                            favouritesCoursesAdapter.notifyDataSetChanged();
                            pbLoadMore.setVisibility(View.GONE);
                            loading = false;
                        }
                    } else {
                        pbLoadMore.setVisibility(View.GONE);
                        Log.e(TAG + "Response Error : ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
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
                    t.printStackTrace();
                    Log.e(TAG + "categoryData", t.getMessage());
                }
            });
        }

        private void showDiag () {
            final View dialogView = View.inflate(getActivity(), R.layout.custom_filter_dialog, null);

            final Dialog dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(dialogView);

            ImageView imageView = (ImageView) dialog.findViewById(R.id.closeDialogImg);
            final Spinner spnrFilterCategories = (Spinner) dialog.findViewById(R.id.spnr_filter_categories);
            spnrFilterCategories.setVisibility(View.GONE);
            final Spinner spnrFilterSubCategories = (Spinner) dialog.findViewById(R.id.spnr_filter_sub_categories);
            final Spinner spnrFilterLevel = (Spinner) dialog.findViewById(R.id.spnr_filter_level);
            final Spinner spnrFilterLanguage = (Spinner) dialog.findViewById(R.id.spnr_filter_language);

            final ArrayList<String> listSubCategory = new ArrayList<>();
            listSubCategory.add("Select Sub Category");
            for (int i = 0; i < courseListingData.get(0).getSubCategories().size(); i++) {
                listSubCategory.add(courseListingData.get(0).getSubCategories().get(i).getSubCategoryName());
            }
            if (getActivity() != null) {
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, android.R.id.text1, listSubCategory);
                spnrFilterSubCategories.setAdapter(stringArrayAdapter);
            }

            ArrayList<String> listLevel = new ArrayList<>();
            listLevel.add("Select Level");
            for (int i = 0; i < courseListingData.get(0).getLevels().size(); i++) {
                listLevel.add(courseListingData.get(0).getLevels().get(i).getCourseLevel());
            }
            if (getActivity() != null) {
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, android.R.id.text1, listLevel);
                spnrFilterLevel.setAdapter(stringArrayAdapter);
            }

            ArrayList<String> listLanguage = new ArrayList<>();
            listLanguage.add("Select Language");
            for (int i = 0; i < courseListingData.get(0).getLanguages().size(); i++) {
                listLanguage.add(courseListingData.get(0).getLanguages().get(i).getLanguageName());
            }
            if (getActivity() != null) {
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, android.R.id.text1, listLanguage);
                spnrFilterLanguage.setAdapter(stringArrayAdapter);
            }

            spnrFilterSubCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    strSubCategory = adapterView.getItemAtPosition(i).toString();
                    if (strSubCategory.equalsIgnoreCase("Select Sub Category")) {
                        strSubCategory = "";
                    } else {
                        strSubCategory = courseListingData.get(0).getSubCategories().get(i - 1).getSubCategoryId();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spnrFilterLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    strLevelId = adapterView.getItemAtPosition(i).toString();
                    if (strLevelId.equalsIgnoreCase("Select Level")) {
                        strLevelId = "";
                    } else {
                        strLevelId = courseListingData.get(0).getLevels().get(i - 1).getCourseLevelId();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spnrFilterLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    strLanguageId = adapterView.getItemAtPosition(i).toString();
                    if (strLanguageId.equalsIgnoreCase("Select Language")) {
                        strLanguageId = "";
                    } else {
                        strLanguageId = courseListingData.get(0).getLanguages().get(i - 1).getLanguageId();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            Button btnFilterApply = (Button) dialog.findViewById(R.id.btn_filter_apply);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        dialog.dismiss();
                    } else {
                        revealShow(dialogView, false, dialog);
                    }
                }
            });

            btnFilterApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //strSubCategory = spnrFilterSubCategories.getSelectedItem().toString();
                    strPageno = 0;
                    loading = false;
                *//*strSubCategory = "";
                strLevelId = spnrFilterLevel.getSelectedItem().toString();
                strLanguageId = "1";// spnrFilterLanguage.getSelectedItem().toString();
                String strCategory = spnrFilterCategories.getSelectedItem().toString();*//*
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        dialog.dismiss();
                    } else {
                        revealShow(dialogView, false, dialog);
                    }
                    fetchInitialCategoryCourses();
                }
            });

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        dialog.show();
                    } else {
                        revealShow(dialogView, true, null);
                    }
                }
            });

            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == KeyEvent.KEYCODE_BACK) {
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                            dialog.dismiss();
                        } else {
                            revealShow(dialogView, false, dialog);
                        }
                        return true;
                    }

                    return false;
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            dialog.show();
        }

        private void revealShow (View dialogView,boolean b, final Dialog dialog){

            final View view = dialogView.findViewById(R.id.dialog);

            int w = view.getWidth();
            int h = view.getHeight();

            int endRadius = (int) Math.hypot(w, h);
            Log.e(TAG + "hypot", "Math.hypot(" + w + "," + h + ")=" + Math.hypot(w, h));
            int cx = (int) (fabFilter.getX() + (fabFilter.getWidth() / 2));
            int cy = (int) (fabFilter.getY() + (fabFilter.getHeight() / 2));

            if (b) {
                Animator revealAnimator = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);
                    view.setVisibility(View.VISIBLE);
                    revealAnimator.setDuration(500);
                    revealAnimator.start();
                }

            } else {
                Animator anim = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            dialog.dismiss();
                            view.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.setDuration(500);
                    anim.start();
                }

            }*/

        }

    }