package com.vethics.loft;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import adapter.SettingsViewPagerAdapter;
import fragment.ContactUsFragment;
import fragment.FaqsFragment;
import utils.AndroidUtils;

public class SupportActivity extends AppCompatActivity {
    TabLayout tabSupport;
    ViewPager vpSupport;

    private Typeface mTypeface = null;

    public Typeface getTypeface() {
        return mTypeface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vpSupport = (ViewPager) findViewById(R.id.vp_support);
        tabSupport = (TabLayout) findViewById(R.id.tab_support);
        tabSupport.setTabGravity(TabLayout.GRAVITY_FILL);
        setupViewPager(vpSupport);
        if (getIntent().getExtras() != null)
            vpSupport.setCurrentItem(getIntent().getExtras().getInt("extra_name"));

        tabSupport.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    AndroidUtils.hideSoftKeyboard(SupportActivity.this);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    AndroidUtils.hideSoftKeyboard(SupportActivity.this);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    AndroidUtils.hideSoftKeyboard(SupportActivity.this);
                }
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        SettingsViewPagerAdapter adapter = new SettingsViewPagerAdapter(getSupportFragmentManager());
        ContactUsFragment contactUsFragment = new ContactUsFragment();
        adapter.addFragment(contactUsFragment, "Contact Us");
        FaqsFragment faqsFragment = new FaqsFragment();
        adapter.addFragment(faqsFragment, "FAQs");
        viewPager.setAdapter(adapter);
        tabSupport.setupWithViewPager(vpSupport);
    }

}
