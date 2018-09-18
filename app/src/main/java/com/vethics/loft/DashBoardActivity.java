package com.vethics.loft;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import adapter.DashBoardViewPagerAdapter;
import fragment.DashboardFragment;
import fragment.SettingListFragment;
import fragment.FavouritesFragment;
import fragment.MyCoursesFragment;
import utils.SessionManager;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

   LinearLayout vpDashboardContent;
    Toolbar toolbar;
    Fragment homeFragment, favouritesFragment, recentHistoryFragment, ongoingFragment;
    BottomNavigationView bottomNavigationView;
    RelativeLayout navigation_home,navigation_favourites,navigation_ongoing,navigation_recent;
    FloatingActionButton fabFilter;
    ImageView ivNavClose, ivNavProfile,home_img,blogs_img,debets_img,setting_img;
    TextView tvNavDashboard, tvNavSupport, tvNavTutorProfile, tvNavMyCourses, tvNavCourseDetails, tvNavStudentProfile, tvNavSetting, tvNavLogout,home_txt,favotite_txt,course_txt,setting_txt,title;
    private DrawerLayout drawer;

    SessionManager sessionManager;

    private final String TAG = getClass().getCanonicalName();

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     /*   if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Dashboard");*/

        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        toolbar.setNavigationIcon(R.drawable.ic_app_menu);

        title=findViewById(R.id.title);
        title.setText("Dashboard");

        final Toolbar.LayoutParams params =new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.MATCH_PARENT);
        params.gravity=Gravity.CENTER_HORIZONTAL;

       /* FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "font/avenirltstd_light.otf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));*/

        vpDashboardContent = findViewById(R.id.vp_dashboard_content);
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bn_dashboard);
        home_img=findViewById(R.id.home_img);
        blogs_img=findViewById(R.id.blogs_img);
        debets_img=findViewById(R.id.debets_img);
        setting_img=findViewById(R.id.setting_img);



        navigation_home =findViewById(R.id.navigation_home);
        navigation_favourites=findViewById(R.id.navigation_favourites);
        navigation_ongoing=findViewById(R.id.navigation_ongoing);
        navigation_recent=findViewById(R.id.navigation_recent);

        home_txt=findViewById(R.id.home_txt);
        favotite_txt=findViewById(R.id.favorite_txt);
        course_txt=findViewById(R.id.course_txt);
        setting_txt=findViewById(R.id.setting_txt);


        getSupportFragmentManager().beginTransaction().replace(R.id.vp_dashboard_content,new DashboardFragment()).commit();
        home_img.setImageDrawable(getResources().getDrawable(R.drawable.homepink));
        blogs_img.setImageDrawable(getResources().getDrawable(R.drawable.favouritegray));
        debets_img.setImageDrawable(getResources().getDrawable(R.drawable.coursegray));
        setting_img.setImageDrawable(getResources().getDrawable(R.drawable.settinggray));


        home_txt.setTextColor(Color.parseColor("#660E7A"));
        favotite_txt.setTextColor(Color.parseColor("#666666"));
        course_txt.setTextColor(Color.parseColor("#666666"));
        setting_txt.setTextColor(Color.parseColor("#666666"));



        navigation_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(DashBoardActivity.this, "home clicked", Toast.LENGTH_SHORT).show();

               /* DashBoardViewPagerAdapter adapter = new DashBoardViewPagerAdapter(getSupportFragmentManager());
                homeFragment = new DashboardFragment();
                adapter.addFragment(homeFragment);*/

              Fragment fragment = new DashboardFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.vp_dashboard_content,fragment);
                Log.e("fragment","homefragment");
                toolbar.setLayoutParams(params);

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                home_img.setImageDrawable(getResources().getDrawable(R.drawable.homepink));
                blogs_img.setImageDrawable(getResources().getDrawable(R.drawable.favouritegray));
                debets_img.setImageDrawable(getResources().getDrawable(R.drawable.coursegray));
                setting_img.setImageDrawable(getResources().getDrawable(R.drawable.settinggray));

                home_txt.setTextColor(Color.parseColor("#660E7A"));
                favotite_txt.setTextColor(Color.parseColor("#666666"));
                course_txt.setTextColor(Color.parseColor("#666666"));
                setting_txt.setTextColor(Color.parseColor("#666666"));

            }
        });


        navigation_favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(DashBoardActivity.this, "favorite clicked", Toast.LENGTH_SHORT).show();

              /*  Fragment fragment = new FavouritesFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.vp_dashboard_content,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

              FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
//              ft.addToBackStack("Dashboars");
              ft.replace(R.id.vp_dashboard_content,new FavouritesFragment()).commit();
              title.setText("favorite");
              fabFilter.setVisibility(View.INVISIBLE);





                home_img.setImageDrawable(getResources().getDrawable(R.drawable.homegray));
                blogs_img.setImageDrawable(getResources().getDrawable(R.drawable.favouritepink));
                debets_img.setImageDrawable(getResources().getDrawable(R.drawable.coursegray));
                setting_img.setImageDrawable(getResources().getDrawable(R.drawable.settinggray));


                home_txt.setTextColor(Color.parseColor("#666666"));
                favotite_txt.setTextColor(Color.parseColor("#660E7A"));
                course_txt.setTextColor(Color.parseColor("#666666"));
                setting_txt.setTextColor(Color.parseColor("#666666"));

            }
        });

        navigation_ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(DashBoardActivity.this, "ongoing clicked", Toast.LENGTH_SHORT).show();

               /* Fragment fragment = new MyCoursesFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.vp_dashboard_content,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
*/

                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
//                ft.addToBackStack("Dashboard");
                ft.replace(R.id.vp_dashboard_content,new MyCoursesFragment()).commit();

                title.setText("My Course");
                fabFilter.setVisibility(View.INVISIBLE);



                home_img.setImageDrawable(getResources().getDrawable(R.drawable.homegray));
                blogs_img.setImageDrawable(getResources().getDrawable(R.drawable.favouritegray));
                debets_img.setImageDrawable(getResources().getDrawable(R.drawable.coursepink));
                setting_img.setImageDrawable(getResources().getDrawable(R.drawable.settinggray));


                home_txt.setTextColor(Color.parseColor("#666666"));
                favotite_txt.setTextColor(Color.parseColor("#666666"));
                course_txt.setTextColor(Color.parseColor("#660E7A"));
                setting_txt.setTextColor(Color.parseColor("#666666"));

            }
        });

        navigation_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(DashBoardActivity.this, "recent clicked", Toast.LENGTH_SHORT).show();
                Fragment fragment = new SettingListFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.vp_dashboard_content,fragment);
                title.setText("Setting");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


                home_img.setImageDrawable(getResources().getDrawable(R.drawable.homegray));
                blogs_img.setImageDrawable(getResources().getDrawable(R.drawable.favouritegray));
                debets_img.setImageDrawable(getResources().getDrawable(R.drawable.coursegray));
                setting_img.setImageDrawable(getResources().getDrawable(R.drawable.settingpink));


                home_txt.setTextColor(Color.parseColor("#666666"));
                favotite_txt.setTextColor(Color.parseColor("#666666"));
                course_txt.setTextColor(Color.parseColor("#666666"));
                setting_txt.setTextColor(Color.parseColor("#660E7A"));


            }
        });
//        vpDashboardContent.addOnPageChangeListener(vpChangeListner);
//        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fabFilter = (FloatingActionButton) findViewById(R.id.fab_filter);
//        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(null);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                drawer.openDrawer(Gravity.START);
            }
        });

        ivNavClose = (ImageView) findViewById(R.id.iv_nav_close);
        ivNavProfile = (ImageView) findViewById(R.id.iv_nav_profile);
        tvNavDashboard = (TextView) findViewById(R.id.tv_nav_dashboard);
        tvNavSupport = (TextView) findViewById(R.id.tv_nav_support);
        tvNavTutorProfile = (TextView) findViewById(R.id.tv_nav_tutor_profile);
        tvNavMyCourses = (TextView) findViewById(R.id.tv_nav_mycourses);
        tvNavCourseDetails = (TextView) findViewById(R.id.tv_nav_course_details);
        tvNavStudentProfile = (TextView) findViewById(R.id.tv_nav_interaction_board);
        tvNavSetting = (TextView) findViewById(R.id.tv_nav_setting);
        tvNavLogout = (TextView) findViewById(R.id.tv_nav_logout);
//        setupViewPager(vpDashboardContent);

        if (getIntent().getExtras() == null) {
//            vpDashboardContent.setCurrentItem(getIntent().getIntExtra("fragment", 2));
            title.setText("Downloads");
            fabFilter.setVisibility(View.GONE);
        }

      /*  tvNavSetting.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_setting, 0, 0);
        tvNavLogout.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_logout, 0, 0);
*/
//        ivNavClose.setOnClickListener(this);
       /* ivNavProfile.setOnClickListener(this);
        tvNavDashboard.setOnClickListener(this);
        tvNavSupport.setOnClickListener(this);
        tvNavTutorProfile.setOnClickListener(this);
        tvNavMyCourses.setOnClickListener(this);
        tvNavCourseDetails.setOnClickListener(this);
        tvNavStudentProfile.setOnClickListener(this);
        tvNavSetting.setOnClickListener(this);
        tvNavLogout.setOnClickListener(this);*/
        sessionManager = new SessionManager(DashBoardActivity.this);
//        vpDashboardContent.setOffscreenPageLimit(1);
        fabFilter.setVisibility(View.GONE);
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiag();
            }
        });
    }
    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vpDashboardContent.setCurrentItem(0);
                    toolbar.setTitle("Dashboard");
                    fabFilter.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_favourites:
                    vpDashboardContent.setCurrentItem(1);
                    toolbar.setTitle("Favourites");
                    fabFilter.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_ongoing:
                    vpDashboardContent.setCurrentItem(2);
                    toolbar.setTitle("Ongoing");
                    fabFilter.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_recent:
                    vpDashboardContent.setCurrentItem(3);
                    toolbar.setTitle("Downloads");
                    fabFilter.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };
*/
   /* private MenuItem prevMenuItem;
    ViewPager.OnPageChangeListener vpChangeListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (prevMenuItem != null) {
                prevMenuItem.setChecked(false);
            } else {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
            }
            Log.d("page", "onPageSelected: " + position);
            bottomNavigationView.getMenu().getItem(position).setChecked(true);
            prevMenuItem = bottomNavigationView.getMenu().getItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };*/

    private void setupViewPager(ViewPager viewPager) {
        DashBoardViewPagerAdapter adapter = new DashBoardViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new DashboardFragment();
        favouritesFragment = new FavouritesFragment();
        ongoingFragment = new MyCoursesFragment();
        recentHistoryFragment = new SettingListFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(favouritesFragment);
        adapter.addFragment(ongoingFragment);
        adapter.addFragment(recentHistoryFragment);
        viewPager.setAdapter(adapter);
    }

    private void showDiag() {

        final View dialogView = View.inflate(this, R.layout.custom_filter_dialog, null);

        final Dialog dialog = new Dialog(this, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.closeDialogImg);
        final Spinner spnrFilterCategories = (Spinner) dialog.findViewById(R.id.spnr_filter_categories);
        final Spinner spnrFilterSubCategories = (Spinner) dialog.findViewById(R.id.spnr_filter_sub_categories);
        final Spinner spnrFilterLevel = (Spinner) dialog.findViewById(R.id.spnr_filter_level);
        final Spinner spnrFilterLanguage = (Spinner) dialog.findViewById(R.id.spnr_filter_language);
        Button btnFilterApply = (Button) dialog.findViewById(R.id.btn_filter_apply);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(dialogView, false, dialog);
            }
        });

        btnFilterApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strSubCategory = spnrFilterSubCategories.getSelectedItem().toString();
                String strFilterLevel = spnrFilterLevel.getSelectedItem().toString();
                String strFilterLanguage = spnrFilterLanguage.getSelectedItem().toString();
                String strCategory = spnrFilterCategories.getSelectedItem().toString();
            }
        });

       /* dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    revealShow(dialogView, false, dialog);
                    return true;
                }
                return false;
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();*/
    }

    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

    /*    final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);
        Log.e(TAG, "Math.hypot(" + w + "," + h + ")=" + Math.hypot(w, h));
        int cx = (int) (fabFilter.getX() + (fabFilter.getWidth() / 2));
        int cy = (int) (fabFilter.getY() + (fabFilter.getHeight() / 2));

        if (b) {
            Animator revealAnimator = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);
                view.setVisibility(View.VISIBLE);
                revealAnimator.setDuration(700);
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
                anim.setDuration(700);
                anim.start();
            }

        }
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.navigation_notifications) {
            Constants.commonAlertDialog("We are working on it. Will notify you soon!", DashBoardActivity.this);
            Intent i = new Intent(DashBoardActivity.this, CourseDetailsActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
      /*  if (v == ivNavClose) {
            drawer.closeDrawer(Gravity.START);
            Toast.makeText(DashBoardActivity.this, "Closed!", Toast.LENGTH_SHORT).show();
        } else if (v == ivNavProfile) {
            drawer.closeDrawer(Gravity.START);
            Intent i = new Intent(DashBoardActivity.this, StudentProfileActivity.class);
            startActivity(i);
        } else if (v == tvNavDashboard) {
            drawer.closeDrawer(Gravity.START);
            tvNavDashboard.setTextColor(getResources().getColor(R.color.colorWhite));
            tvNavSupport.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavTutorProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavMyCourses.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavCourseDetails.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavStudentProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            Toast.makeText(DashBoardActivity.this, "Dashboard!", Toast.LENGTH_SHORT).show();
        } else if (v == tvNavSupport) {
            drawer.closeDrawer(Gravity.START);
            tvNavDashboard.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavSupport.setTextColor(getResources().getColor(R.color.colorWhite));
            tvNavTutorProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavMyCourses.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavCourseDetails.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavStudentProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            Intent i = new Intent(DashBoardActivity.this, SupportActivity.class);
            startActivity(i);
            Toast.makeText(DashBoardActivity.this, "Support!", Toast.LENGTH_SHORT).show();
        } else if (v == tvNavTutorProfile) {
            drawer.closeDrawer(Gravity.START);
            tvNavDashboard.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavSupport.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavTutorProfile.setTextColor(getResources().getColor(R.color.colorWhite));
            tvNavMyCourses.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavCourseDetails.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavStudentProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            Toast.makeText(DashBoardActivity.this, "Tutor Profile!", Toast.LENGTH_SHORT).show();
        } else if (v == tvNavMyCourses) {
            drawer.closeDrawer(Gravity.START);
            tvNavDashboard.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavSupport.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavTutorProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavMyCourses.setTextColor(getResources().getColor(R.color.colorWhite));
            tvNavCourseDetails.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavStudentProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            Intent i = new Intent(DashBoardActivity.this, MyCoursesActivity.class);
            startActivity(i);
            Toast.makeText(DashBoardActivity.this, "My Courses!", Toast.LENGTH_SHORT).show();
        } else if (v == tvNavCourseDetails) {
            drawer.closeDrawer(Gravity.START);
            tvNavDashboard.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavSupport.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavTutorProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavMyCourses.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavCourseDetails.setTextColor(getResources().getColor(R.color.colorWhite));
            tvNavStudentProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            Toast.makeText(DashBoardActivity.this, "Course Details!", Toast.LENGTH_SHORT).show();
        } else if (v == tvNavStudentProfile) {
            drawer.closeDrawer(Gravity.START);
            tvNavDashboard.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavSupport.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavTutorProfile.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavMyCourses.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavCourseDetails.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNavStudentProfile.setTextColor(getResources().getColor(R.color.colorWhite));
            Intent i = new Intent(DashBoardActivity.this, WhiteBoardActivity.class);
            startActivity(i);
        } else if (v == tvNavSetting) {
            drawer.closeDrawer(Gravity.START);
            Intent i = new Intent(DashBoardActivity.this, SettingsActivity.class);
            startActivity(i);
            Toast.makeText(DashBoardActivity.this, "Settings!", Toast.LENGTH_SHORT).show();
        } else if (v == tvNavLogout) {
            drawer.closeDrawer(Gravity.START);
            sessionManager.logoutUser();
            boolean loggedIn = AccessToken.getCurrentAccessToken() == null;

            if (!loggedIn) {
                LoginManager.getInstance().logOut();
            }

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                        }
                    });
            Toast.makeText(DashBoardActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
        }*/
    }

}
