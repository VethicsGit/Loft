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
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vethics.loft.CourseDetailsActivity;
import com.vethics.loft.CourseListingActivity;
import com.vethics.loft.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.CourseCategoryAdapter;
import adapter.FavouritesCoursesAdapter;
import adapter.OfferSliderPagerAdapter;
import adapter.PopularCoursesAdapter;
import interfaces.OnLoadMoreListener;
import interfaces.OnRecyclerViewItemClickListner;
import model.CategoryResponse.CategorySuccessData;
import model.CategoryResponse.CategorySuccessResponse;
import model.CourseListing.CourseListingData;
import model.CourseListing.CourseListingError;
import model.CourseListing.CourseListingResult;
import model.CourseListing.CourseListingSuccessResponse;
import model.PopulrCourse.Datum;
import model.PopulrCourse.PopularCourse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.API;
import utils.APIClient;
import utils.AndroidUtils;
import utils.SessionManager;

public class DashboardFragment extends Fragment {

    FloatingActionButton fabSearch;
    RecyclerView rvCategories, rvPopular;
    TabLayout tabDots;
    ViewPager vpOffersSlider;
    ArrayList<Drawable> offerStringArrayList;
    private int page_position;
    TextView tvCategoriesTag, tvCategoriesTagline, tvPopularTag, tvRecommendedTag;
    private static final int sColumnWidth = 150; // assume cell width of 120dp
    Handler handler;
    private final String TAG = getClass().getCanonicalName();
    ArrayList<CourseListingData> courseListingData;
    ArrayList<CourseListingResult> courseListingResult;
    private String strSearchKeyword;
    OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    ProgressBar pbLoadMore;
    FavouritesCoursesAdapter favouritesCoursesAdapter;
    PopularCoursesAdapter popularCoursesAdapter;
    int strPageno = 0;
    private String strStudentId;

    final Runnable update = new Runnable() {
        public void run() {
            if (page_position == offerStringArrayList.size() - 1) {
                page_position = 0;
            } else {
                page_position = page_position + 1;
            }
            vpOffersSlider.setCurrentItem(page_position, true);
            handler.postDelayed(update, 2000);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        ((DashBoardActivity) getActivity()).getSupportActionBar().setTitle("Dashboard");
        if (AndroidUtils.hasConnection(getActivity())) {
            fetchCategories();
            fetchPopularCourses();
        } else {
            Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

        fabSearch = (FloatingActionButton) view.findViewById(R.id.fab);
        rvCategories = (RecyclerView) view.findViewById(R.id.rv_course_categories);
        rvPopular = (RecyclerView) view.findViewById(R.id.rv_popular);
        vpOffersSlider = (ViewPager) view.findViewById(R.id.vp_offers_slider);
        tabDots = (TabLayout) view.findViewById(R.id.tab_dots);
        tvCategoriesTag = (TextView) view.findViewById(R.id.tv_categories_tag);
        tvCategoriesTagline = (TextView) view.findViewById(R.id.tv_categories_tagline);
        tvPopularTag = (TextView) view.findViewById(R.id.tv_favourites_course_title);
        tvRecommendedTag = (TextView) view.findViewById(R.id.tv_recommended_tag);

        SharedPreferences spLogin = getActivity().getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");
        offerStringArrayList = new ArrayList<>();
        offerStringArrayList.add(getResources().getDrawable(R.drawable.l1));
        offerStringArrayList.add(getResources().getDrawable(R.drawable.l2));
        offerStringArrayList.add(getResources().getDrawable(R.drawable.l3));
        offerStringArrayList.add(getResources().getDrawable(R.drawable.l4));
        OfferSliderPagerAdapter offerSliderPagerAdapter = new OfferSliderPagerAdapter(getActivity(), offerStringArrayList);
        vpOffersSlider.setAdapter(offerSliderPagerAdapter);
        tabDots.setupWithViewPager(vpOffersSlider);

        handler = new Handler();

        vpOffersSlider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vpOffersSlider.setCurrentItem(position);
                page_position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

      /*  GridLayoutManager pgridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false)*/;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rvPopular.setLayoutManager(linearLayoutManager);
      /*  PopularCoursesAdapter popularCoursesAdapter = new PopularCoursesAdapter(getActivity(), new OnRecyclerViewItemClickListner() {
            @Override
            public void onItemClick(int position) {

            }
        });*/




        API apiService = APIClient.getClient().create(API.class);
        Call<PopularCourse> call = apiService.Course_list("30");
        call.enqueue(new Callback<PopularCourse>() {
            @Override
            public void onResponse(Call<PopularCourse> call, Response<PopularCourse> response) {
                if (response.isSuccessful()) {

                    Log.e("Course", "response" + response);

                    PopularCourse popularCourse = response.body();
                    String strStatus = popularCourse.getStatus();
                    if (strStatus.equalsIgnoreCase("success")) {
                        String strMessage = popularCourse.getMessage();

                        Toast.makeText(getContext(),"response"+strMessage,Toast.LENGTH_LONG).show();

                        List<Datum> datumList = popularCourse.getData();

//                        for (int i = 0; i < datumList.size(); i++) {
//
//                                Datum datumList1 = datumList.get(i);


                            popularCoursesAdapter = new PopularCoursesAdapter(datumList, getContext());
                            rvPopular.setAdapter(popularCoursesAdapter);
                        }
                    }
                }


            @Override
            public void onFailure(Call<PopularCourse> call, Throwable t) {

            }
        });

        /*SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(rvPopular);
        ViewTreeObserver viewTreeObserver = rvPopular.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                calculateSize();
            }
        });*/

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiag();
            }
        });

        return view;
    }

    private void fetchPopularCourses() {



    }

    private void fetchCategories() {
        final ProgressDialog loading = new ProgressDialog(getActivity());
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        API apiService = APIClient.getClient().create(API.class);

        Call<CategorySuccessResponse> call1 = apiService.fetch_categories();
        call1.enqueue(new Callback<CategorySuccessResponse>() {

            @Override
            public void onResponse(@NonNull Call<CategorySuccessResponse> call, @NonNull retrofit2.Response<CategorySuccessResponse> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    //Log.e(TAG, "Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                    CategorySuccessResponse categoryDetails = response.body();
                    String strStatus = categoryDetails.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = categoryDetails.getMessage();
                        final ArrayList<CategorySuccessData> categoryData = categoryDetails.getData();

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
                        rvCategories.setLayoutManager(gridLayoutManager);
                        CourseCategoryAdapter videoAdapter = new CourseCategoryAdapter(getActivity(), categoryData, new OnRecyclerViewItemClickListner() {
                            @Override
                            public void onItemClick(int position) {
                                Intent i = new Intent(getActivity(), CourseListingActivity.class);
                                i.putExtra("categoryId", categoryData.get(position).getCategoryId());
                                getActivity().startActivity(i);
                            }
                        });
                        rvCategories.setAdapter(videoAdapter);
                    }

                } else {
                    Log.e(TAG, "Error : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    /*try {
                        Gson gson = new Gson();
                        UserDetailsError userDatailsError = gson.fromJson(response.errorBody().string(), UserDetailsError.class);
                        Toast.makeText(getActivity(.this, userDatailsError.getMessage().getUserId(), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }

            @Override
            public void onFailure(@NonNull Call<CategorySuccessResponse> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                Log.e(TAG, "categoryData : " + t.getMessage());
            }
        });
    }

   /* private void calculateSize() {
        int spanCount = (int) Math.floor(rvPopular.getHeight() / convertDPToPixels(sColumnWidth));
        ((GridLayoutManager) rvPopular.getLayoutManager()).setSpanCount(spanCount);
    }*/

    private float convertDPToPixels(int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return dp * logicalDensity;
    }

    private void showDiag() {

        final View dialogView = View.inflate(getActivity(), R.layout.custom_search_dialog, null);

        final Dialog dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        final EditText edtSearchCourse = (EditText) dialog.findViewById(R.id.edt_search_course);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.iv_search_course);
        final RecyclerView rvCourseListing = (RecyclerView) dialog.findViewById(R.id.rv_course_listing);
        courseListingData = new ArrayList<>();
        courseListingResult = new ArrayList<>();

        edtSearchCourse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                strSearchKeyword = edtSearchCourse.getText().toString().trim();
//                fetchInitialCategoryCourses(rvCourseListing);
            }
        });

        final LinearLayoutManager pgridLayoutManager = new LinearLayoutManager(getActivity());
        rvCourseListing.setLayoutManager(pgridLayoutManager);
        rvCourseListing.setNestedScrollingEnabled(false);
        rvCourseListing.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        strPageno = strPageno + 1;
                        fetchCategoryCourses();
                        Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        //fetchInitialCategoryCourses(rvCourseListing);
        //Button btnFilterApply = (Button) dialog.findViewById(R.id.btn_filter_apply);
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

//        btnFilterApply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

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

    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);
        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);
        Log.e(TAG + "hypot", "Math.hypot(" + w + "," + h + ")=" + Math.hypot(w, h));
        int cx = (int) (fabSearch.getX() + (fabSearch.getWidth() / 2));
        int cy = (int) (fabSearch.getY() + (fabSearch.getHeight() / 2));

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

        }

    }

  /*  private void fetchInitialCategoryCourses(final RecyclerView rvCourseListing) {

        API apiService = APIClient.getClient().create(API.class);

        Call<CourseListingSuccessResponse> call1 = apiService.common_search_filter(strStudentId, String.valueOf(strPageno), "", "", strSearchKeyword, "", "", "", "");
        call1.enqueue(new Callback<CourseListingSuccessResponse>() {

            @Override
            public void onResponse(@NonNull Call<CourseListingSuccessResponse> call, @NonNull retrofit2.Response<CourseListingSuccessResponse> response) {
                courseListingResult.clear();
                courseListingData.clear();
                favouritesCoursesAdapter = new FavouritesCoursesAdapter(getActivity(), courseListingResult, null);
                if (response.isSuccessful()) {
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
                    }
                } else {
                    //Log.e(TAG+"Response Error:", new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    try {
                        Gson gson = new Gson();
                        CourseListingError userDatailsError = gson.fromJson(response.errorBody().string(), CourseListingError.class);
                        //Toast.makeText(getActivity(), userDatailsError.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                rvCourseListing.setAdapter(favouritesCoursesAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<CourseListingSuccessResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Log.e(TAG, "categoryData : " + t.getMessage());
            }
        });
    }*/

    private void fetchCategoryCourses() {
        courseListingResult.remove(courseListingResult.size() - 1);
        favouritesCoursesAdapter.notifyItemRemoved(courseListingResult.size());
        API apiService = APIClient.getClient().create(API.class);

        Call<CourseListingSuccessResponse> call1 = apiService.common_search_filter(strStudentId, String.valueOf(strPageno), "", "", strSearchKeyword, "", "", "", "");
        call1.enqueue(new Callback<CourseListingSuccessResponse>() {
            @Override
            public void onResponse(@NonNull Call<CourseListingSuccessResponse> call, @NonNull retrofit2.Response<CourseListingSuccessResponse> response) {

                if (response.isSuccessful()) {
                    //  Log.e(TAG + "Response Success : ", new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData().getResults()));
                    CourseListingSuccessResponse categoryDetails = response.body();
                    String strStatus = categoryDetails.getStatus();

                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = categoryDetails.getMessage();
                        courseListingResult.addAll(categoryDetails.getData().getResults());
                        favouritesCoursesAdapter.notifyItemInserted(courseListingResult.size());
                        favouritesCoursesAdapter.notifyDataSetChanged();
                        loading = false;
                    }
                } else {
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

    @Override
    public void onResume() {
        handler.post(update);
        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(update);
        super.onPause();
    }

    @Override
    public void onStop() {
        handler.removeCallbacks(update);
        super.onStop();
    }
}
