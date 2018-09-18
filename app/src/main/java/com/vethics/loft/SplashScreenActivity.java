package com.vethics.loft;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import utils.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView ivSplashLogo;
    LinearLayout llSplash;
    SharedPreferences sharedPreferences;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ivSplashLogo = (ImageView) findViewById(R.id.iv_splash_logo);
        llSplash = (LinearLayout) findViewById(R.id.ll_splash);
        sharedPreferences = getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        /*Drawable leftDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_app_right_arrow);
        btnSignin.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);
        btnRegiter.setCompoundDrawablesWithIntrinsicBounds(null, null, leftDrawable, null);*/

        Animation zoomin = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.zoom_in);
        ivSplashLogo.startAnimation(zoomin);
        ivSplashLogo.setVisibility(View.VISIBLE);

        zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Animation bottomUp = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.bottom_up);
                llSplash.startAnimation(bottomUp);
                llSplash.setVisibility(View.VISIBLE);
                bottomUp.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (sharedPreferences.getBoolean(SessionManager.IS_LOGIN, false)) {
                            Intent i = new Intent(SplashScreenActivity.this, DashBoardActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(SplashScreenActivity.this, SigninActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
