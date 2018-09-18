package com.vethics.loft;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import adapter.SettingsViewPagerAdapter;
import fragment.MyCoursesFragment;

public class MyCoursesActivity extends AppCompatActivity implements View.OnClickListener {
    TabLayout tabStudentProfile;
    ViewPager vpStudentProfile;
    ImageView ivStudentProfileEdit;
    ImageView ivStudentProfilePic;
    private MyCoursesFragment studentCourseHistoryFragment;
    private final String TAG = getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vpStudentProfile = (ViewPager) findViewById(R.id.vp_student_profile);
        tabStudentProfile = (TabLayout) findViewById(R.id.tab_student_profile);
        ivStudentProfileEdit = (ImageView) findViewById(R.id.iv_student_profile_edit);
        ivStudentProfilePic = (ImageView) findViewById(R.id.iv_profile_image);
        tabStudentProfile.setTabGravity(TabLayout.MODE_FIXED);

        tabStudentProfile.addTab(tabStudentProfile.newTab());
        tabStudentProfile.addTab(tabStudentProfile.newTab());
        tabStudentProfile.addTab(tabStudentProfile.newTab());
        setupViewPager(vpStudentProfile);
        tabStudentProfile.setupWithViewPager(vpStudentProfile);

        fetchMyCourse();

        tabStudentProfile.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*View imageView = (ImageView) tab.getCustomView().findViewById(R.id.view_bottom_arrow);
                imageView.setVisibility(View.VISIBLE);*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                /*View imageView = (ImageView) tab.getCustomView().findViewById(R.id.view_bottom_arrow);
                imageView.setVisibility(View.INVISIBLE);*/
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ivStudentProfileEdit.setOnClickListener(this);
    }

    private void fetchMyCourse() {

    }

    private void setupViewPager(ViewPager viewPager) {
        SettingsViewPagerAdapter adapter = new SettingsViewPagerAdapter(getSupportFragmentManager());
        studentCourseHistoryFragment = new MyCoursesFragment();
        adapter.addFragment(studentCourseHistoryFragment, "All");

        studentCourseHistoryFragment = new MyCoursesFragment();
        adapter.addFragment(studentCourseHistoryFragment, "Ongoing");

        studentCourseHistoryFragment = new MyCoursesFragment();
        adapter.addFragment(studentCourseHistoryFragment, "Completed");
        viewPager.setAdapter(adapter);
        //viewPager.setCurrentItem(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v == ivStudentProfileEdit) {
            Intent i = new Intent(MyCoursesActivity.this, StudentProfileActivity.class);
            startActivity(i);
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_student_profile, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } */
}

