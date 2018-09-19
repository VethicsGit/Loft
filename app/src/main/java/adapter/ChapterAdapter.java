package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vethics.loft.R;

import java.util.ArrayList;

public class ChapterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList datalist;
    private Context context;


    public ChapterAdapter(ArrayList data,Context context) {
        this.datalist=data;
        this.context=context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.tab_1_fragment,parent,false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



    class ViewHolder  extends RecyclerView.ViewHolder {
        TextView index,course_chep_name,course_categori;
      ImageView course_chep_download;

        public ViewHolder(View itemView) {
            super(itemView);

            index=itemView.findViewById(R.id.index);
            course_chep_name=itemView.findViewById(R.id.course_chep_name);
            course_categori=itemView.findViewById(R.id.course_categori);

            course_chep_download=itemView.findViewById(R.id.course_chep_download);
        }
    }
}
