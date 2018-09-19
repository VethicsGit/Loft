package com.vethics.loft;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import model.CourseDetail.CourseDetail;
import model.CourseDetail.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.API;
import utils.APIClient;

public class CourseDescriptionwithoutbuy extends AppCompatActivity {


      TextView course_desc_title;
    TextView course_desc_stars;
    TextView course_desc_enrolled;
    TextView course_desc;
    TextView course_desc_doller;
    TextView course_desc_chepter ;
    VideoView course_desc_video;
    String videofile  ="C:\\Users\\Kanika\\projects\\Loft\\app\\video";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_description_withoutbuy);





     course_desc_title=findViewById(R.id.course_desc_title);
        course_desc_stars=findViewById(R.id.course_desc_stars);
        course_desc_enrolled=findViewById(R.id.course_desc_enrolled);
        course_desc=findViewById(R.id.course_desc);
        course_desc_doller=findViewById(R.id.course_desc_doller);
        course_desc_chepter=findViewById(R.id.course_desc_chepter);
        course_desc_video=findViewById(R.id.course_desc_video);




        API api= APIClient.getClient().create(API.class);
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
                        String desc=courseDetails.getCourseDescription();
                        String video=courseDetails.getCourseIntroductionVideo();


                        course_desc_title.setText(title);
                        course_desc.setText(desc);
                        course_desc_video.setVideoPath(video);



                    }
                }

            }

            @Override
            public void onFailure(Call<CourseDetail> call, Throwable t) {

            }
        });


    }
}
