package adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vethics.loft.R;

import java.util.ArrayList;
import java.util.Random;

import interfaces.OnRecyclerViewItemClickListner;
import model.CategoryResponse.CategorySuccessData;

public class CourseCategoryAdapter extends RecyclerView.Adapter<CourseCategoryAdapter.MyViewHolder> {
    private Context context;
    private String[] text = {"Mathematics", "Chemistry", "Physics", "Sciene", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Sciene", "English", "Gujarati", "Sanskrit", "Hindi", "Maths", "Chemistry", "Physics", "Sciene", "English", "Gujarati", "Sanskrit", "Hindi"};

    int[] drawables = {R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search, R.drawable.ic_app_math, R.drawable.ic_app_bio, R.drawable.ic_app_filter, R.drawable.ic_app_search};

    private final OnRecyclerViewItemClickListner listener;
    ArrayList<CategorySuccessData> categoryData;

    public CourseCategoryAdapter(Context context, ArrayList<CategorySuccessData> categoryData, OnRecyclerViewItemClickListner listener) {
        this.context = context;
        this.listener = listener;
        this.categoryData = categoryData;
    }

    @Override
    public CourseCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_course_categories_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CourseCategoryAdapter.MyViewHolder holder, final int position) {

        Random r = new Random();
        int red = r.nextInt(255 + 1);
        int green = r.nextInt(255 + 1);
        int blue = r.nextInt(255 + 1);

        holder.imageView.setImageResource(drawables[position]);
        holder.cvCategory.setBackgroundColor(Color.rgb(red, green, blue));
        holder.tvCourseName.setText(categoryData.get(position).getCategoryName());

        holder.cvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvCourseName;
        CardView cvCategory;

        MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            cvCategory = (CardView) itemView.findViewById(R.id.cv_category);
            tvCourseName = (TextView) itemView.findViewById(R.id.tv_course_name);

        }
    }
}
