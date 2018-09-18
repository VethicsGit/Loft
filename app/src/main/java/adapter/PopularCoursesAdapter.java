package adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
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
import com.vethics.loft.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.OnRecyclerViewItemClickListner;
import model.PopulrCourse.Datum;

/**
 * Created by Chirag on 12/29/2017.
 */

public class PopularCoursesAdapter extends RecyclerView.Adapter<PopularCoursesAdapter.MyViewHolder> {


    private List<Datum> datumList;
    private Context context;
    private String[] text = {"Mathematics", "Chemistry", "Physics", "Sciene", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Sciene", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Sciene", "English", "Gujarati", "Sanskrit", "Hindi"};

    private int[] image = {R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4};

    int[] drawables = {R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search};

    private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();
    private final ArrayList<Integer> likedPositions = new ArrayList<>();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);


    private OnRecyclerViewItemClickListner listener;

   /* public PopularCoursesAdapter(Context context, OnRecyclerViewItemClickListner listener) {
        this.context = context;
        this.listener = listener;
    }*/


   /* public PopularCoursesAdapter(List<Datum> datumList1, Context context) {

        this.datumList =  datumList1;
        this.context = context;
        this.listener = listener;
    }*/


    public PopularCoursesAdapter(List<Datum> datumList,Context context) {
        this.context=context;
        this.datumList = datumList;
    }

    public void add(Datum datum){
        if (datumList.isEmpty())
            datumList=new ArrayList<>();
        else
            datumList.add(datum);
            notifyDataSetChanged();
    }

    @Override
    public PopularCoursesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_popular_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        Datum datum = datumList.get(position);
        Glide.with(context).load(datum.getCourseImage()).into(holder.ivCourseBackground);
        //Glide.with(context).load  (R.drawable.dribble_gif).into(holder.ivCourseBackground);


        holder.tvPopularCourseTitle.setText(datum.getCourseTitle());

        LayerDrawable stars = (LayerDrawable) holder.rbCourseRatings.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.starColor), PorterDuff.Mode.SRC_ATOP);
        //stars.getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        holder.rbCourseRatings.setRating(Float.parseFloat(String.valueOf(datumList.get(position).getRating())));
        holder.rbCourseRatings.setStepSize(1);
        holder.tvCourseRatings.setText((Double.toString(datum.getRating())) );
//        holder.tvCourseReviewsCount.setText(datum.getTotalReviews());
        updateHeartButton(holder, false);

        holder.ivAddFavourite.setOnClickListener(new View.OnClickListener() {
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
                Log.e("liked positions :", likedPositions.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCourseBackground, ivAddFavourite;
        TextView tvPopularCourseCategoryTitle, tvPopularCourseTitle, tvSharePopularCourse, tvCourseRatings, tvCourseReviewsCount;
        RatingBar rbCourseRatings;

        MyViewHolder(View itemView) {
            super(itemView);
            ivCourseBackground = (ImageView) itemView.findViewById(R.id.iv_popular_course_background);
            ivAddFavourite = (ImageView) itemView.findViewById(R.id.iv_popular_add_favourite);
//            tvPopularCourseCategoryTitle = (TextView) itemView.findViewById(R.id.tv_popular_course_category_title);
            tvPopularCourseTitle = (TextView) itemView.findViewById(R.id.tv_popular_course_title);
            tvCourseRatings = (TextView) itemView.findViewById(R.id.tv_popular_course_rating);
            tvCourseReviewsCount = (TextView) itemView.findViewById(R.id.tv_popular_course_reviews_count);
            rbCourseRatings = (RatingBar) itemView.findViewById(R.id.rb_popular_course_rating);
            tvSharePopularCourse = (TextView) itemView.findViewById(R.id.tv_popular_share_popular_course);
        }
    }

    private void updateHeartButton(final MyViewHolder holder, boolean animated) {
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

    private void resetLikeAnimationState(MyViewHolder holder) {
        likeAnimations.remove(holder);
        /*holder.vBgLike.setVisibility(View.GONE);
        holder.ivLike.setVisibility(View.GONE);*/
    }
}
