package adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.vethics.loft.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import interfaces.OnRecyclerViewItemClickListner;
import model.CourseListing.CourseListingResult;
import model.FavCourse.Datum;
import model.FavouriteSuccess.FavouriteSuccess;
import retrofit2.Call;
import retrofit2.Callback;
import utils.API;
import utils.APIClient;
import utils.SessionManager;

/**
 * Created by Chirag on 1/8/2018.
 */

public class FavouritesCoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String strStudentId;

    private List<Datum> datumList;
    private Context context;
    private ArrayList<CourseListingResult> courseListingResults;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROGRESS = 0;
    private OnRecyclerViewItemClickListner listner;
    private final String TAG = getClass().getCanonicalName();
    private SharedPreferences spLogin;

    private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();
    private final ArrayList<String> likedPositions = new ArrayList<>();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    private String[] text = {"Mathematics", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Mathematics", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Mathematics", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Mathematics", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Mathematics", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Mathematics", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Science", "English", "Gujarati", "Sanskrit", "Hindi"};

    private int[] image = {R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4};

    int[] drawables = {R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search};

 /*   public FavouritesCoursesAdapter(Context context, ArrayList<CourseListingResult> courseListingResults, OnRecyclerViewItemClickListner listner) {
        this.context = context;

        this.courseListingResults = courseListingResults;
        this.listner = listner;
        spLogin = context.getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");
    }*/

    public FavouritesCoursesAdapter(List<Datum> datumList, Context context) {
        this.context = context;
        this.datumList = datumList;
        spLogin = context.getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
        strStudentId = spLogin.getString(SessionManager.KEY_ID, "");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_favourites_list_item, parent, false);
            vh = new MyViewHolder(v);
            return vh;
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_progress_bar, parent, false);
            vh = new ProgressViewHolder(v);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //holder.setIsRecyclable(false);

        Datum datum = datumList.get(position);
        Glide.with(context).load(datum.getCourseImage()).into(((MyViewHolder) holder).ivCourseBackground);
        ((MyViewHolder) holder).tvPopularCourseTitle.setText(datum.getCourseTitle());

        ((MyViewHolder) holder).rbCourseRatings.setRating(datum.getRating());

        ((MyViewHolder) holder).tvCourseReviewsCount.setText("(" + datum.getTotalReviews() + ")");

        ((MyViewHolder) holder).llFavouriteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onItemClick(position);
            }
        });

    }
        /*if (holder instanceof MyViewHolder) {
            final int adapterPosition = holder.getAdapterPosition();
            Random r = new Random();
            int red = r.nextInt(4) + 1;


            Datum datum = datumList.get(position);
            Glide.with(context).load(datum.getCourseImage()).into(((MyViewHolder) holder).ivCourseBackground);
            ((MyViewHolder) holder).tvPopularCourseTitle.setText(datum.getCourseTitle());
//            Glide.with(context).load(image[position]).into(((MyViewHolder) holder).ivCourseBackground);
            //Glide.with(context).load(R.drawable.dribble_gif).into(((MyViewHolder)holder).ivCourseBackground);
            ((MyViewHolder) holder).tvPopularCourseCategoryTitle.setText(courseListingResults.get(position).getCategoryName());
            ((MyViewHolder) holder).tvPopularCourseTitle.setText(courseListingResults.get(position).getCourseTitle());

            LayerDrawable stars = (LayerDrawable) ((MyViewHolder) holder).rbCourseRatings.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.starColor), PorterDuff.Mode.SRC_ATOP);
            //stars.getDrawable(1).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
            ((MyViewHolder) holder).rbCourseRatings.setRating(red);
            ((MyViewHolder) holder).rbCourseRatings.setStepSize(1);

            ((MyViewHolder) holder).tvCourseRatings.setText(Double.toString(datum.getRating()));

            ((MyViewHolder) holder).tvCourseReviewsCount.setText("(" + courseListingResults.get(position).getCourseId() + ")");
            ((MyViewHolder) holder).llFavouriteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onItemClick(position);
                }
            });

            if (courseListingResults.get(position).getIsFavourite().equalsIgnoreCase("yes")) {
                itemStateArray.put(adapterPosition, true);
            } else {
                itemStateArray.put(adapterPosition, false);
            }

            if (!itemStateArray.get(adapterPosition, false)) {
                ((MyViewHolder) holder).ivAddFavourite.setImageResource(R.drawable.ic_app_unfav);
            } else {
                ((MyViewHolder) holder).ivAddFavourite.setImageResource(R.drawable.ic_app_fav);
            }

            ((MyViewHolder) holder).ivAddFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addtoFavourite(adapterPosition);
                    if (!itemStateArray.get(adapterPosition, false)) {
                        updateHeartButton((MyViewHolder) holder, true, adapterPosition);
                    } else {
                        updateHeartButton((MyViewHolder) holder, false, adapterPosition);
                    }
                    *//*if (!likedPositions.contains(courseListingResults.get(position).getCourseId())) {
                        likedPositions.add(courseListingResults.get(position).getCourseId());
                        updateHeartButton((MyViewHolder) holder, true, position);
                    } else {
                        likedPositions.remove(courseListingResults.get(position).getCourseId());
                        //holder.ivAddFavourite.setImageResource(R.drawable.ic_app_unfav);
                        updateHeartButton((MyViewHolder) holder, false, position);
                    }*//*
                    Log.e("liked positions : ", likedPositions.toString());
                }
            });
        } else {
            ((ProgressViewHolder) holder).pbLoadMore.setIndeterminate(true);
        }*/


        @Override
        public int getItemCount () {
            return datumList.size();
        }

        @Override
        public int getItemViewType ( int position){
            return datumList.get(position) != null ? VIEW_ITEM : VIEW_PROGRESS;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivCourseBackground, ivAddFavourite;
            TextView tvPopularCourseCategoryTitle, tvPopularCourseTitle, tvSharePopularCourse, tvCourseRatings, tvCourseReviewsCount;
            RatingBar rbCourseRatings;
            LinearLayout llFavouriteItem;

            MyViewHolder(View itemView) {
                super(itemView);
                ivCourseBackground = (ImageView) itemView.findViewById(R.id.iv_favourites_course_background);
                ivAddFavourite = (ImageView) itemView.findViewById(R.id.iv_favourites_add_favourite);
                tvPopularCourseCategoryTitle = (TextView) itemView.findViewById(R.id.tv_favourites_course_category_title);
                tvPopularCourseTitle = (TextView) itemView.findViewById(R.id.tv_favourites_course_title);
                tvCourseRatings = (TextView) itemView.findViewById(R.id.tv_favourites_course_rating);
                tvCourseReviewsCount = (TextView) itemView.findViewById(R.id.tv_favourites_course_reviews_count);
                rbCourseRatings = (RatingBar) itemView.findViewById(R.id.rb_favourites_course_rating);
                tvSharePopularCourse = (TextView) itemView.findViewById(R.id.tv_favourites_share_favourites_course);
                llFavouriteItem = (LinearLayout) itemView.findViewById(R.id.ll_favourite_item);
            }
        }

        class ProgressViewHolder extends RecyclerView.ViewHolder {
            ProgressBar pbLoadMore;

            ProgressViewHolder(View itemView) {
                super(itemView);
                pbLoadMore = (ProgressBar) itemView.findViewById(R.id.pb_load_more);
            }

        }

        private void addtoFavourite ( final int position){
            String strCourseId = courseListingResults.get(position).getCourseId();

        /*final ProgressDialog loading = new ProgressDialog(context);
        loading.setMessage("Please Wait..");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();*/

            API apiService = APIClient.getClient().create(API.class);
            Call<FavouriteSuccess> call1 = apiService.add_favourites(strCourseId, strStudentId);
            call1.enqueue(new Callback<FavouriteSuccess>() {

                @Override
                public void onResponse(@NonNull Call<FavouriteSuccess> call, @NonNull retrofit2.Response<FavouriteSuccess> response) {
                    //loading.dismiss();

                    if (response.isSuccessful()) {
                        Log.e(TAG, "Response Success : " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body().getData()));
                        FavouriteSuccess favouriteResponse = response.body();
                        String strStatus = favouriteResponse.getStatus();
                        String strMessage = favouriteResponse.getMessage();
                        if (strMessage.contains("Added")) {
                            itemStateArray.put(position, true);
                            //notifyDataSetChanged();
                        } else {
                            itemStateArray.put(position, false);
                            //notifyDataSetChanged();
                        }

                    } else {
                        Log.e(TAG, "Response Error : " + new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    /*try {
                        Gson gson = new Gson();
                        LoginError loginError = gson.fromJson(response.errorBody().string(), LoginError.class);
                        if (loginError.getMessage().getUserName() != null) {
                            tilLoginUsername.setError(loginError.getMessage().getUserName());
                            tilLoginUsername.setHintTextAppearance(R.style.MyErrorText);
                            tilLoginUsername.requestFocus();
                        } else if (loginError.getMessage().getPassword() != null) {
                            tilLoginPassword.setError(loginError.getMessage().getPassword());
                            tilLoginPassword.setHintTextAppearance(R.style.MyErrorText);
                            tilLoginPassword.requestFocus();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FavouriteSuccess> call, @NonNull Throwable t) {
                    //loading.dismiss();
                    t.printStackTrace();
                    Log.e(TAG, "Login CheckSocialSuccessData : " + t.getMessage());
                }
            });
        }

        private void updateHeartButton ( final MyViewHolder holder, boolean animated, int position){
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
                            holder.ivAddFavourite.setImageResource(R.drawable.ic_app_fav);
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
                if (!itemStateArray.get(position, false)) {
                    holder.ivAddFavourite.setImageResource(R.drawable.ic_app_fav);
                } else {
                    holder.ivAddFavourite.setImageResource(R.drawable.ic_app_unfav);
                }
            /*if (likedPositions.contains(courseListingResults.get(holder.getAdapterPosition()).getCourseId())) {
                holder.ivAddFavourite.setImageResource(R.drawable.ic_app_fav);
            } else {
                holder.ivAddFavourite.setImageResource(R.drawable.ic_app_unfav);
            }*/
            }
        }

        private void resetLikeAnimationState (MyViewHolder holder){
            likeAnimations.remove(holder);
        /*holder.vBgLike.setVisibility(View.GONE);
        holder.ivLike.setVisibility(View.GONE);*/
        }
    }
