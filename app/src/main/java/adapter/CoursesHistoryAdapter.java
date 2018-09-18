package adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vethics.loft.GlideApp;
import com.vethics.loft.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.OnRecyclerViewItemClickListner;
import model.MyCourses.MyCoursesSuccess;
import model.MyCourses.MyCoursesSuccessData;

/**
 * Created by Chirag on 1/10/2018.
 */

public class CoursesHistoryAdapter extends RecyclerView.Adapter<CoursesHistoryAdapter.MyViewHolder> {



    List<MyCoursesSuccessData>myCoursesSuccessData;
    private Context context;
    private String[] text = {"Mathematics", "Chemistry", "Physics", "Sciene", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Sciene", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Sciene", "English", "Gujarati", "Sanskrit", "Hindi"};

    private int[] image = {R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4};

    int[] drawables = {R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search};

    private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();
    private final ArrayList<Integer> likedPositions = new ArrayList<>();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

//    private final OnRecyclerViewItemClickListner listener;
    private final String TAG = getClass().getCanonicalName();
    private List<MyCoursesSuccessData> courseEnrollSuccessArrayList;

   /* public CoursesHistoryAdapter(Context context, List<MyCoursesSuccessData> courseEnrollSuccessArrayList, OnRecyclerViewItemClickListner listener) {
        this.context = context;
        this.listener = listener;
        this.courseEnrollSuccessArrayList = courseEnrollSuccessArrayList;
    }*/

    public CoursesHistoryAdapter(List<MyCoursesSuccessData> myCoursesSuccessDataList, Context context) {
        this.context = context;
        this.myCoursesSuccessData =myCoursesSuccessDataList;

    }

    @Override
    public CoursesHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_popular_list_item, parent, false);
        return new CoursesHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        MyCoursesSuccessData myCoursesSuccessData1 = myCoursesSuccessData.get(position);

//        GlideApp.with(context).load(courseEnrollSuccessArrayList.get(position).getCourseImage()).error(R.mipmap.ic_launcher).placeholder(R.mipmap.poweredby_vethics).into(holder.ivCourseBackground);
        Glide.with(context).load(myCoursesSuccessData1.getCourseImage()).into(holder.ivCourseBackground);

        holder.tvPopularCourseCategoryTitle.setText(courseEnrollSuccessArrayList.get(position).getCategoryName());
        holder.tvPopularCourseTitle.setText(myCoursesSuccessData1.getCourseTitle());

        LayerDrawable stars = (LayerDrawable) holder.rbCourseRatings.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.starColor), PorterDuff.Mode.SRC_ATOP);
        //stars.getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        holder.rbCourseRatings.setRating(4f);
        holder.rbCourseRatings.setStepSize(1);
        holder.tvCourseRatings.setText((Double.toString(Double.parseDouble(myCoursesSuccessData1.getRating()))));
        updateHeartButton(holder, false);

      /*  holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });*/
        holder.ivAddFavourite.setOnClickListener(new View.  OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!likedPositions.contains(holder.getAdapterPosition())) {
                    likedPositions.add(holder.getAdapterPosition());
                    updateHeartButton(holder, true);
                } else {
                    likedPositions.remove((Object) holder.getAdapterPosition());
                    //holder.ivAddFavourite.setImageResource(R.drawable.ic_heart_outline_grey);
                    updateHeartButton(holder, false);
                }
                Log.e(TAG, "Liked positions : " + likedPositions.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseEnrollSuccessArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView ivCourseBackground, ivAddFavourite;
        TextView tvPopularCourseCategoryTitle, tvPopularCourseTitle, tvSharePopularCourse, tvCourseRatings, tvCourseReviewsCount;
        RatingBar rbCourseRatings;

        MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv_popular_list_item);
            ivCourseBackground = (ImageView) itemView.findViewById(R.id.iv_popular_course_background);
            ivAddFavourite = (ImageView) itemView.findViewById(R.id.iv_popular_add_favourite);
//           tvPopularCourseCategoryTitle = (TextView) itemView.findViewById(R.id.tv_popular_course_category_title);
            tvPopularCourseTitle = (TextView) itemView.findViewById(R.id.tv_popular_course_title);
            tvCourseRatings = (TextView) itemView.findViewById(R.id.tv_popular_course_rating);
            tvCourseReviewsCount = (TextView) itemView.findViewById(R.id.tv_popular_course_reviews_count);
            rbCourseRatings = (RatingBar) itemView.findViewById(R.id.rb_popular_course_rating);
            tvSharePopularCourse = (TextView) itemView.findViewById(R.id.tv_popular_share_popular_course);
        }
    }

    private void updateHeartButton(final CoursesHistoryAdapter.MyViewHolder holder, boolean animated) {
        if (animated) {
            if (!likeAnimations.containsKey(holder)) {
                AnimatorSet animatorSet = new AnimatorSet();
                likeAnimations.put(holder, animatorSet);

                ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(holder.ivAddFavourite, "rotation", 0f, 360f);
                rotationAnim.setDuration(300);
                rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

                ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.ivAddFavourite, "scaleX", 0.2f, 1f);
                bounceAnimX.setDuration(300);
                bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

                ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.ivAddFavourite, "scaleY", 0.2f, 1f);
                bounceAnimY.setDuration(300);
                bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                bounceAnimY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        holder.ivAddFavourite.setImageResource(R.drawable.ic_heart_red);
                    }
                });

                animatorSet.play(rotationAnim);
                animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        resetLikeAnimationState(holder);
                    }
                });

                animatorSet.start();
            }
        } else {
            if (likedPositions.contains(holder.getAdapterPosition())) {
                holder.ivAddFavourite.setImageResource(R.drawable.ic_heart_red);
            } else {
                holder.ivAddFavourite.setImageResource(R.drawable.ic_heart_outline_grey);
            }
        }
    }

    private void resetLikeAnimationState(CoursesHistoryAdapter.MyViewHolder holder) {
        likeAnimations.remove(holder);
        /*holder.vBgLike.setVisibility(View.GONE);
        holder.ivLike.setVisibility(View.GONE);*/
    }
}
