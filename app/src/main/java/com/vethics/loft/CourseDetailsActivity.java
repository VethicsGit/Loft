package com.vethics.loft;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.stepstone.apprating.listener.RatingDialogListener;

import adapter.ViewpageAdapter;
import interfaces.OnRecyclerViewItemClickListner;
import model.CourseDetail.CourseDetail;
import model.CourseDetail.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.API;
import utils.APIClient;

public class CourseDetailsActivity extends AppCompatActivity implements View.OnClickListener, OnRecyclerViewItemClickListner, RatingDialogListener {

   /* Toolbar toolbar;
    boolean bTitleDescription = true, bPreknowledge = true, bInclusion = true, bDetails = true, bSyllabus = true, bTutorProfile = true, bReviews = true;
    TextView tvCourseRating;

    TextView tvCourseDetailTitle, tvCourseDuration, tvCourseDetailTitleDescription, tvCourseDetailReadMore, tvCourseDetailPreknowledgeTag, tvCourseDetailInclusionTag, tvCourseDetailsTag, tvCourseDetailSyllabusTag, tvCourseDetailTutorProfileTag, tvCourseDetailReviewsTag;

    TextView tvCourseDetailPreknowledge, tvCourseDetailInclusion, tvCourseDetails, tvCourseDetailsLevel, tvCourseDetailsLanguage, tvCourseDetailSyllabus, tvCourseDetailTutorProfile, tvCourseDetailReviews;

    ImageView ivTutorProfileImage;

    TextView tvNavSyllabusCourseTitle, tvNavSyllabusDone;
    ListView lvSyllabus;

    CardView cvCourseDetailPreknowledge, cvCourseDetailInclusion, cvCourseDetails, cvCourseDetailSyllabus, cvCourseDetailTutorProfile, cvCourseDetailReviews;
    String strCourseTitle;

    RecyclerView rvSimilar;

    RatingBar rbCourseDetailRating;

    Button btnCourseDetailBuy, btnCourseDetailPrice;
    private DrawerLayout drawer;

    MediaController mediaController;
    public String videoUrl = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet.mp4";

    NestedScrollView nsvCourseDetail;

    private File mEncryptedFile;

    private SimpleExoPlayerView mSimpleExoPlayerView;
    SimpleExoPlayer player;

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";

    private boolean playWhenReady;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private boolean mExoPlayerFullscreen = false;
    private int mResumeWindow;
    private long mResumePosition;

    private ImageView ivNavigationOpen;
    private ImageView mFullScreenIcon;
    FrameLayout mFullScreenButton;
    private Dialog mFullScreenDialog;
    private ImageView ivExoBookmarkIcon;
    private String strCourseId, strTutorId, strStudentId = "", strShareLink, strCoursePrice, strCourseImageUrl, strCourseDuration;
    private final String TAG = getClass().getCanonicalName();

    SharedPreferences spLogin;
    private Menu menu;

    boolean menuicon = false;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
*/

   /* TextView  course_desc_title;
    TextView course_desc_stars;
    TextView course_desc_enrolled;
    TextView course_desc;
    TextView course_desc_doller;
    TextView course_desc_chepter ;
    VideoView course_desc_video;
    String videofile  ="C:\\Users\\Kanika\\projects\\Loft\\app\\video";
*/


    String videofile  ="C:\\Users\\Kanika\\projects\\Loft\\app\\video";
   VideoView video_vv;
   TextView course_title,shorttitle;
   TabLayout tablayout;
   ViewPager v_pager;
   ViewpageAdapter viewpageAdapter;
   CharSequence Title[]={"Lectures","More"};
   int Numboftabs = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_descripton_buy);


     /*   course_desc_title=findViewById(R.id.course_desc_title);
        course_desc_stars=findViewById(R.id.course_desc_stars);
        course_desc_enrolled=findViewById(R.id.course_desc_enrolled);
        course_desc=findViewById(R.id.course_desc);
        course_desc_doller=findViewById(R.id.course_desc_doller);
        course_desc_chepter=findViewById(R.id.course_desc_chepter);
        course_desc_video=findViewById(R.id.course_desc_video);
*/
     video_vv=findViewById(R.id.video_vv);
     course_title =findViewById(R.id.course_title);
     shorttitle=findViewById(R.id.title);

     v_pager =findViewById(R.id.v_pager);



     viewpageAdapter= new ViewpageAdapter(getSupportFragmentManager(),Title,Numboftabs);
     v_pager.setAdapter(viewpageAdapter);

     tablayout =findViewById(R.id.tablayout);
     tablayout.setupWithViewPager(v_pager);


        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videofile,
                MediaStore.Images.Thumbnails.MINI_KIND);


        API api=APIClient.getClient().create(API.class);
        Call<CourseDetail> call=api.Course_detail();
        call.enqueue(new Callback<CourseDetail>() {
            @Override
            public void onResponse(Call<CourseDetail> call, Response<CourseDetail> response) {

                if (response.isSuccessful()){
                    CourseDetail courseDetail =response.body();
                    String strStatus = courseDetail.getStatus();
                    if (strStatus.equalsIgnoreCase("success")){
                        String strMessage = courseDetail.getMessage();

                        Toast.makeText(getApplicationContext(),"response" + strMessage, Toast.LENGTH_SHORT).show();

                        Data courseDetails= courseDetail.getData();
                       String title =courseDetails.getCourseTitle();
                       String shortTitle=courseDetails.getCourseShortTitle();
                       String video=courseDetails.getCourseIntroductionVideo();


                      course_title.setText(title);
                       shorttitle.setText(shortTitle);
                       video_vv.setVideoPath(video);



                    }
                }

            }

            @Override
            public void onFailure(Call<CourseDetail> call, Throwable t) {

                }
        });





       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            getWindow().setEnterTransition(explode);
        }
        btnCourseDetailBuy = (Button) findViewById(R.id.btn_course_detail_buy);
        Intent intent = getIntent();
        if (intent != null) {
            strCourseId = intent.getStringExtra("courseId");
        }
        spLogin = getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");
        if (AndroidUtils.hasConnection(CourseDetailsActivity.this)) {
            fetchCourseDetails();
        } else {
            finish();
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_app_menu_overflow));
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "font/avenirltstd_light.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        MyApp application = (MyApp) getApplication();
        ivNavigationOpen = (ImageView) findViewById(R.id.iv_navigation_open);
        mSimpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.vv_course_detail_preview);
        nsvCourseDetail = (NestedScrollView) findViewById(R.id.nsv_course_detail);
        tvCourseDetailTitle = (TextView) findViewById(R.id.tv_course_detail_title);
        tvCourseDuration = (TextView) findViewById(R.id.tv_course_duration);
        tvCourseDetailTitleDescription = (TextView) findViewById(R.id.tv_course_detail_title_description);
        tvCourseDetailReadMore = (TextView) findViewById(R.id.tv_course_detail_read_more);
        tvCourseRating = (TextView) findViewById(R.id.tv_favourites_course_rating);
        rbCourseDetailRating = (RatingBar) findViewById(R.id.rb_course_detail_rating);

        tvCourseDetailPreknowledgeTag = (TextView) findViewById(R.id.tv_course_detail_preknowledge_tag);
        tvCourseDetailInclusionTag = (TextView) findViewById(R.id.tv_course_detail_inclusion_tag);
        tvCourseDetailsTag = (TextView) findViewById(R.id.tv_course_details_tag);
        tvCourseDetailSyllabusTag = (TextView) findViewById(R.id.tv_course_detail_syllabus_tag);
        tvCourseDetailTutorProfileTag = (TextView) findViewById(R.id.tv_course_detail_tutor_profile_tag);
        tvCourseDetailReviewsTag = (TextView) findViewById(R.id.tv_course_detail_reviews_tag);

        ivTutorProfileImage = (ImageView) findViewById(R.id.iv_tutor_profile_image);
        tvCourseDetailPreknowledge = (TextView) findViewById(R.id.tv_course_detail_preknowledge);
        tvCourseDetailInclusion = (TextView) findViewById(R.id.tv_course_detail_inclusion);
        tvCourseDetails = (TextView) findViewById(R.id.tv_course_details_description);
        tvCourseDetailsLevel = (TextView) findViewById(R.id.tv_course_details_level);
        tvCourseDetailsLanguage = (TextView) findViewById(R.id.tv_course_details_language);
        tvCourseDetailSyllabus = (TextView) findViewById(R.id.tv_course_detail_syllabus);
        tvCourseDetailTutorProfile = (TextView) findViewById(R.id.tv_course_detail_tutor_profile);
        tvCourseDetailReviews = (TextView) findViewById(R.id.tv_course_detail_reviews);

        cvCourseDetailPreknowledge = (CardView) findViewById(R.id.cv_course_detail_preknowledge);
        cvCourseDetailInclusion = (CardView) findViewById(R.id.cv_course_detail_inclusion);
        cvCourseDetails = (CardView) findViewById(R.id.cv_course_details);
        cvCourseDetailSyllabus = (CardView) findViewById(R.id.cv_course_detail_syllabus);
        cvCourseDetailTutorProfile = (CardView) findViewById(R.id.cv_course_detail_tutor_profile);
        cvCourseDetailReviews = (CardView) findViewById(R.id.cv_course_detail_reviews);

        btnCourseDetailPrice = (Button) findViewById(R.id.btn_course_detail_price);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_course_detail_syllabus);

        tvNavSyllabusCourseTitle = (TextView) findViewById(R.id.tv_nav_syllabus_course_title);
        tvNavSyllabusDone = (TextView) findViewById(R.id.tv_nav_syllabus_done);
        lvSyllabus = (ListView) navigationView.findViewById(R.id.lv_syllabus);

        rvSimilar = (RecyclerView) findViewById(R.id.rv_similar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent i = new Intent(CourseDetailsActivity.this, CourseListingActivity.class);
                startActivity(i);*//*
                finish();
                overridePendingTransition(0, R.anim.slide_out_right);
            }
        });

        //tvNavSyllabusCourseTitle.setText("Course Title");
        tvNavSyllabusDone.setOnClickListener(this);

        *//*nsvCourseDetail.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!vvCourseDetails.isShown()) {
                    vvCourseDetails.pause();
                }
            }
        });*//*

        LayerDrawable stars = (LayerDrawable) rbCourseDetailRating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.starColor), PorterDuff.Mode.SRC_ATOP);
        //stars.getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        rbCourseDetailRating.setRating(4);
        rbCourseDetailRating.setStepSize(1);
        tvCourseRating.setText(String.valueOf(rbCourseDetailRating.getRating()));

        application.setTypeface(tvCourseDetailTitle);
        application.setTypeface(tvCourseDetailPreknowledgeTag);
        application.setTypeface(tvCourseDetailInclusionTag);
        application.setTypeface(tvCourseDetailsTag);
        application.setTypeface(tvCourseDetailSyllabusTag);
        application.setTypeface(tvCourseDetailTutorProfileTag);
        application.setTypeface(tvCourseDetailReviewsTag);

        application.setTypeface(tvCourseDuration);
        application.setTypeface(tvCourseDetailTitleDescription);
        application.setTypeface(tvCourseDetailReadMore);
        application.setTypeface(tvCourseDetailPreknowledge);
        application.setTypeface(tvCourseDetailInclusion);
        application.setTypeface(tvCourseDetails);
        application.setTypeface(tvCourseDetailSyllabus);
        application.setTypeface(tvCourseDetailTutorProfile);
        application.setTypeface(tvCourseDetailReviews);

        GridLayoutManager pgridLayoutManager = new GridLayoutManager(CourseDetailsActivity.this, 1, LinearLayoutManager.HORIZONTAL, false);
        rvSimilar.setLayoutManager(pgridLayoutManager);
        PopularCoursesAdapter popularCoursesAdapter = new PopularCoursesAdapter(CourseDetailsActivity.this, this);
        rvSimilar.setAdapter(popularCoursesAdapter);

        Drawable leftDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_app_plus);

        tvCourseDetailPreknowledgeTag.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);
        tvCourseDetailInclusionTag.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);
        //tvCourseDetailsTag.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);
        tvCourseDetailSyllabusTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_right_arrow, 0);
        tvCourseDetailTutorProfileTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_right_arrow, 0);
        tvCourseDetailReviewsTag.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);

        tvCourseDetailReadMore.setOnClickListener(this);
        tvCourseDetailPreknowledgeTag.setOnClickListener(this);
        tvCourseDetailInclusionTag.setOnClickListener(this);
        tvCourseDetailsTag.setOnClickListener(this);
        tvCourseDetailSyllabusTag.setOnClickListener(this);
        cvCourseDetailTutorProfile.setOnClickListener(this);
        tvCourseDetailReviewsTag.setOnClickListener(this);
        rbCourseDetailRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showRatingDialog();
                }
                return true;
            }
        });

        btnCourseDetailBuy.setOnClickListener(this);
        ivNavigationOpen.setOnClickListener(this);
    }

    private void fetchCourseDetails() {
        final ProgressDialog loading = new ProgressDialog(CourseDetailsActivity.this);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        //loading.show();

        API apiService = APIClient.getClient().create(API.class);
        Call<CourseDetailsSuccessResponse> call1 = apiService.course_details_by_id("1", strStudentId);
        call1.enqueue(new Callback<CourseDetailsSuccessResponse>() {

            @Override
            public void onResponse(@NonNull Call<CourseDetailsSuccessResponse> call, @NonNull retrofit2.Response<CourseDetailsSuccessResponse> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    //Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                    CourseDetailsSuccessResponse courseDetails = response.body();
                    String strStatus = courseDetails.getStatus();
                    if (strStatus.equalsIgnoreCase("Success")) {
                        String strMessage = courseDetails.getMessage();
                        CourseDetailsData courseDetailsData = courseDetails.getData();
                        strCourseTitle = courseDetailsData.getCourseTitle();
                        toolbar.setTitle(strCourseTitle);
                        if (courseDetailsData.getIsFavourite().equalsIgnoreCase("yes")) {
                            menu.getItem(0).setIcon(ContextCompat.getDrawable(CourseDetailsActivity.this, R.drawable.ic_app_fav));
                            menuicon = true;
                        } else {
                            menu.getItem(0).setIcon(ContextCompat.getDrawable(CourseDetailsActivity.this, R.drawable.ic_app_unfav));
                            menuicon = false;
                        }
                        strShareLink = courseDetailsData.getCourseUrl();
                        tvCourseDetailTitle.setText(strCourseTitle);
                        tvNavSyllabusCourseTitle.setText(strCourseTitle);
                        strCourseDuration = courseDetailsData.getCourseDuration();
                        tvCourseDuration.setText(Html.fromHtml("<font color=\"black\">Course Duration : </font>" + strCourseDuration));
                        tvCourseDetailTitleDescription.setText(Html.fromHtml(courseDetailsData.getCourseDescription()));
                        tvCourseDetails.setText(courseDetailsData.getCourseShortTitle());
                        Author author = courseDetailsData.getAuthor();
                        strTutorId = author.getInstructorId();
                        Log.e(TAG, "Tutor Id : " + strTutorId);
                        Glide.with(CourseDetailsActivity.this).load(author.getProfilePic()).into(ivTutorProfileImage);
                        tvCourseDetailTutorProfileTag.setText(Html.fromHtml("<html><font size = 2 color = black >Tutor :</font><br></html>" + author.getName()));
                        strCourseId = courseDetailsData.getCourseId();
                        strCoursePrice = "100";
                        //courseDetailsData.getCourseCategoryId(); //change this to price

                        strCourseImageUrl = courseDetailsData.getCourseImage();
                        if (strCoursePrice.equalsIgnoreCase("0")) {
                            btnCourseDetailPrice.setText("Free");
                        } else {
                            btnCourseDetailPrice.setText("Starting from \n" + getString(R.string.rupee) + strCoursePrice);
                        }

                        Level level = courseDetailsData.getLevel();
                        tvCourseDetailsLevel.setText(Html.fromHtml("<font color=\"black\">Level : </font>" + level.getAcademicLevel()));
                        Language language = courseDetailsData.getLanguage();
                        tvCourseDetailsLanguage.setText(Html.fromHtml("<font color=\"black\">Language : </font>" + language.getLanguageName()));

                        List<Module> moduleArrayList = courseDetailsData.getModules();

                        SyllabusAdapter favouritesCoursesAdapter = new SyllabusAdapter(CourseDetailsActivity.this, moduleArrayList);
                        lvSyllabus.setAdapter(favouritesCoursesAdapter);
                        lvSyllabus.setFastScrollEnabled(true);

                        if (!courseDetailsData.getCourseIntroductionVideo().isEmpty()) {
                            videoUrl = courseDetailsData.getCourseIntroductionVideo();
                            initializePlayer();
                            //playVideo(videoUrl);
                        } else {
                            videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4";// "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
                            initializePlayer();
                            initFullscreenDialog();
                            initFullscreenButton();
                            //playVideo(videoUrl);
                        }
                    }

                } else {
                    Log.e(TAG, "Response Error:" + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    *//*try {
                        Gson gson = new Gson();
                        UserDetailsError userDatailsError = gson.fromJson(response.errorBody().string(), UserDetailsError.class);
                        Toast.makeText(CourseDetailsActivity.this, userDatailsError.getMessage().getUserId(), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*//*
                }
            }

            @Override
            public void onFailure(@NonNull Call<CourseDetailsSuccessResponse> call, @NonNull Throwable t) {
                loading.dismiss();
                t.printStackTrace();
                Log.e(TAG, "courseDetailsData : " + t.getMessage());
            }
        });
    }

    public void closeDrawer() {
        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        }
    }

    public void playVideo(String videoUriPath) {

        Uri uri = Uri.parse(videoUriPath);
        Log.e(TAG, "video uri : " + videoUriPath);
        // vvCourseDetails.setVideoURI(uri);
        //vvCourseDetails.start();
        //vvCourseDetails.seekTo(900);

    }

    private boolean hasFile() {
        return mEncryptedFile != null
                && mEncryptedFile.exists()
                && mEncryptedFile.length() > 0;
    }

    private void downloadModule() {
        mEncryptedFile = new File(getExternalFilesDir("downloads/" + strCourseTitle), Uri.parse(videoUrl).getLastPathSegment());
        if (hasFile()) {
            Toast.makeText(this, "Video already downloaded!", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Video already downloaded!");
            return;
        }

        try {
            Intent intent = new Intent(getApplicationContext(), DownloaderService.class);
            intent.putExtra("videoUrl", videoUrl);
            intent.putExtra("courseTitle", strCourseTitle);
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        *//*Uri uri = Uri.parse(videoUrl);

        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .mkdirs();

        lastDownload =
                mgr.enqueue(new DownloadManager.Request(uri)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle("Downloading...")
                        .setDescription("Something useful. No, really.")
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                URLUtil.guessFileName(videoUrl, null, null)));*//*
    }

    public void initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mSimpleExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            mSimpleExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }
        player.setPlayWhenReady(playWhenReady);
        //player.seekTo(1000);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private void initFullscreenDialog() {
        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void initFullscreenButton() {

        PlaybackControlView controlView = findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        ivExoBookmarkIcon = (ImageView) controlView.findViewById(R.id.exo_bookmark_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen) {
                    openFullscreenDialog();
                } else {
                    closeFullscreenDialog();
                }
                //mExoPlayerFullscreen = !mExoPlayerFullscreen;
            }
        });

        ivExoBookmarkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CourseDetailsActivity.this, "Bookmarked!" + player.getCurrentPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFullscreenDialog() {
        player.setPlayWhenReady(player.getPlayWhenReady());
        ((ViewGroup) mSimpleExoPlayerView.getParent()).removeView(mSimpleExoPlayerView);
        mFullScreenDialog.addContentView(mSimpleExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //mSimpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        //getSupportActionBar().hide();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(CourseDetailsActivity.this, R.mipmap.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog() {
        player.setPlayWhenReady(player.getPlayWhenReady());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mFullScreenDialog.dismiss();
        ((ViewGroup) mSimpleExoPlayerView.getParent()).removeView(mSimpleExoPlayerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mSimpleExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen._170sdp)));
        //mSimpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        //getSupportActionBar().show();
        mExoPlayerFullscreen = false;
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(CourseDetailsActivity.this, R.mipmap.ic_fullscreen_expand));
    }

    private void updateFavourites() {
        MenuItem menuItem = menu.findItem(R.id.navigation_course_favourites);
        if (!menuicon) {
            menuItem.setIcon(ContextCompat.getDrawable(CourseDetailsActivity.this, R.drawable.ic_app_fav));
            menuicon = !menuicon;
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(CourseDetailsActivity.this, R.drawable.ic_app_unfav));
            menuicon = !menuicon;
        }
    }

    private void submitRateReview(int i, String s) {
        API apiService = APIClient.getClient().create(API.class);
        Call<CheckSocialError> call1 = apiService.submit_rate_review(strStudentId, strCourseId, i, s);
        call1.enqueue(new Callback<CheckSocialError>() {

            @Override
            public void onResponse(@NonNull Call<CheckSocialError> call, @NonNull retrofit2.Response<CheckSocialError> response) {
                //loading.dismiss();

                if (response.isSuccessful()) {
                    Toast.makeText(CourseDetailsActivity.this, "Thanks for your review!", Toast.LENGTH_SHORT).show();
                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<CheckSocialError> call, @NonNull Throwable t) {
                //loading.dismiss();
                t.printStackTrace();
                Log.e(TAG, "Login CheckSocialSuccessData : " + t.getMessage());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mediaController != null)
            mediaController.show(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_course_details, menu);
        //menu.getItem(0).setIcon(ContextCompat.getDrawable(CourseDetailsActivity.this, R.drawable.ic_app_unfav));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_download_module) {
            downloadModule();
        } else if (item.getItemId() == R.id.navigation_course_favourites) {
            updateFavourites();
        } else if (item.getItemId() == R.id.navigation_course_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, strShareLink);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            //initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            //initializePlayer();
            //initFullscreenDialog();
            //initFullscreenButton();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v == tvCourseDetailReadMore) {
            if (bTitleDescription) {
                tvCourseDetailTitleDescription.setMaxLines(Integer.MAX_VALUE);
                bTitleDescription = !bTitleDescription;
                tvCourseDetailReadMore.setText(getResources().getString(R.string.read_less));
                tvCourseDetailReadMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.collapse_arrow_24dp, 0);
            } else {
                tvCourseDetailTitleDescription.setMaxLines(3);
                bTitleDescription = !bTitleDescription;
                tvCourseDetailReadMore.setText(getResources().getString(R.string.read_more));
                tvCourseDetailReadMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.expand_arrow_24dp, 0);
            }
        } else if (v == tvCourseDetailPreknowledgeTag) {
            if (bPreknowledge) {
                tvCourseDetailPreknowledge.setMaxLines(12);
                bPreknowledge = !bPreknowledge;
                tvCourseDetailPreknowledgeTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            } else {
                tvCourseDetailPreknowledge.setMaxLines(3);
                bPreknowledge = !bPreknowledge;
                tvCourseDetailPreknowledgeTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            }
        } else if (v == tvCourseDetailInclusionTag) {
            if (bInclusion) {
                tvCourseDetailInclusion.setMaxLines(12);
                bInclusion = !bInclusion;
                tvCourseDetailInclusionTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            } else {
                tvCourseDetailInclusion.setMaxLines(3);
                bInclusion = !bInclusion;
                tvCourseDetailInclusionTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            }
        } else if (v == tvCourseDetailsTag) {
            if (bDetails) {
                tvCourseDetails.setMaxLines(12);
                bDetails = !bDetails;
                //tvCourseDetailsTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            } else {
                tvCourseDetails.setMaxLines(3);
                bDetails = !bDetails;
                //tvCourseDetailsTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            }
        } else if (v == tvCourseDetailSyllabusTag) {
            drawer.openDrawer(Gravity.END);
           *//* if (bSyllabus) {
                tvCourseDetailSyllabus.setMaxLines(12);
                bSyllabus = !bSyllabus;
                tvCourseDetailSyllabusTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            } else {
                tvCourseDetailSyllabus.setMaxLines(3);
                bSyllabus = !bSyllabus;
                tvCourseDetailSyllabusTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            }*//*
        } else if (v == ivNavigationOpen) {
            drawer.openDrawer(Gravity.END);
        } else if (v == cvCourseDetailTutorProfile) {
            Intent i = new Intent(CourseDetailsActivity.this, TutorInformationActivity.class);
            i.putExtra("tutorId", strTutorId);
            startActivity(i);
            *//*if (bTutorProfile) {
                tvCourseDetailTutorProfile.setMaxLines(12);
                bTutorProfile = !bTutorProfile;
                tvCourseDetailTutorProfileTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            } else {
                tvCourseDetailTutorProfile.setMaxLines(3);
                bTutorProfile = !bTutorProfile;
                tvCourseDetailTutorProfileTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            }*//*
        } else if (v == tvCourseDetailReviewsTag) {
            if (bReviews) {
                tvCourseDetailReviews.setMaxLines(12);
                bReviews = !bReviews;
                tvCourseDetailReviewsTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            } else {
                tvCourseDetailReviews.setMaxLines(3);
                bReviews = !bReviews;
                tvCourseDetailReviewsTag.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_app_plus, 0);
            }
        } else if (v == btnCourseDetailBuy) {

            Intent i = new Intent(CourseDetailsActivity.this, ConfirmEnrollActivity.class);
            i.putExtra("courseprice", strCoursePrice);
            i.putExtra("courseid", strCourseId);
            i.putExtra("coursename", strCourseTitle);
            i.putExtra("courseduration", strCourseDuration);
            i.putExtra("courseimage", strCourseImageUrl);
            startActivity(i);

        } else if (v == tvNavSyllabusDone) {
            closeDrawer();
        }
    }

    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                //.setNeutralButtonText("Later")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not good", "Quite Ok", "Very Good", "Excellent!!!"))
                .setDefaultRating(4)
                .setTitle("Rate this course")
                .setDescription("Please select some stars and give your feedback.")
                //.setDefaultComment("This app is pretty cool !")
                .setStarColor(R.color.starColor)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.starColor)
                .setDescriptionTextColor(R.color.colorLightGray)
                .setHint("Write your review here!")
                .setHintTextColor(R.color.colorLightGray)
                .setCommentTextColor(R.color.colorBlack)
                .setCommentBackgroundColor(R.color.review_border)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .create(CourseDetailsActivity.this)
                .show();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {
        if (!s.isEmpty()) {
            submitRateReview(i, s);
        } else {
            Toast.makeText(this, "Review is compulsory!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {
*/
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPositiveButtonClicked(int i, String s) {

    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onItemClick(int position) {

    }
}

   /* BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            //findViewById(R.id.start).setEnabled(true);
            Toast.makeText(ctxt, "Download Finished!", Toast.LENGTH_SHORT).show();
        }
    };

    BroadcastReceiver onNotificationClick = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Intent i = new Intent(ctxt, CourseDetailsActivity.class);
            startActivity(i);
            Toast.makeText(ctxt, "Ummmm...hi!", Toast.LENGTH_LONG).show();
        }
    }; */
