package com.vethics.loft;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import adapter.ViewpageAdapter;

public class CourseDetailPage extends AppCompatActivity {

    ViewPager viewPager;
    ViewpageAdapter viewpageAdapter;
    TabLayout tabLayout ;
    RecyclerView rv_chepter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.course_detail_page);


        ViewPager viewPager=findViewById(R.id.v_pager);
        ViewpageAdapter viewpageAdapter = new ViewpageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewpageAdapter);
        TabLayout tabLayout=findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);



    }
}
