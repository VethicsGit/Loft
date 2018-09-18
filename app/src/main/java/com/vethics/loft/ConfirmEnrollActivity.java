package com.vethics.loft;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfirmEnrollActivity extends AppCompatActivity {
    ImageView ivConfirmEnrollCourseImage;
    TextView tvConfirmEnrollCourseTitle, tvConfirmEnrollCourseDuration, tvConfirmEnrollCoursePrice;
    Button btnConfirmEnroll;
    String strCoursePrice, strCourseId, strCourseName, strCourseImageUrl, strCourseDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_enroll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmEnrollActivity.this, CourseDetailsActivity.class);
                startActivity(i);
                finish();
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        ivConfirmEnrollCourseImage = (ImageView) findViewById(R.id.iv_confirm_enroll_course_image);
        tvConfirmEnrollCourseTitle = (TextView) findViewById(R.id.tv_confirm_enroll_course_title);
        tvConfirmEnrollCourseDuration = (TextView) findViewById(R.id.tv_confirm_enroll_course_duration);
        tvConfirmEnrollCoursePrice = (TextView) findViewById(R.id.tv_confirm_enroll_course_price);
        btnConfirmEnroll = (Button) findViewById(R.id.btn_confirm_enroll);

        Intent i = getIntent();
        strCourseId = i.getExtras().getString("courseid");
        strCourseName = i.getExtras().getString("coursename");
        strCoursePrice = i.getExtras().getString("courseprice");
        strCourseDuration = i.getExtras().getString("courseduration");
        strCourseImageUrl = i.getExtras().getString("courseimage");

        GlideApp.with(this).load(strCourseImageUrl).error(R.mipmap.ic_launcher).into(ivConfirmEnrollCourseImage);
        tvConfirmEnrollCourseTitle.setText(strCourseName);
        if (strCoursePrice.equalsIgnoreCase("0")) {
            tvConfirmEnrollCoursePrice.setText("Free");
        } else {
            tvConfirmEnrollCoursePrice.setText(getString(R.string.rupee) + strCoursePrice);
        }

        tvConfirmEnrollCourseDuration.setText(strCourseDuration);
        btnConfirmEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmEnrollActivity.this, StripePaymentActivity.class);
                i.putExtra("courseprice", strCoursePrice);
                i.putExtra("courseid", strCourseId);
                i.putExtra("coursename", strCourseName);
                i.putExtra("courseimage", strCourseImageUrl);
                startActivity(i);
            }
        });

    }

}
