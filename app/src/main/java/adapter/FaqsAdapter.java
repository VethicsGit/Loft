package adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vethics.loft.R;
import com.vethics.loft.SupportActivity;

import java.util.ArrayList;

import customview.FontChangeCrawler;
import customview.JustifiedTextView;

/**
 * Created by Chirag on 1/22/2018.
 */

public class FaqsAdapter extends RecyclerView.Adapter<FaqsAdapter.FaqsViewHolder> {
    private Context context;
    private int mExpandedPosition = -1;
    private ArrayList<String> questionArrayList, answerArrayList;
    private RecyclerView recyclerView;
    private SupportActivity activity;

    public FaqsAdapter(Context context, RecyclerView recyclerView, ArrayList<String> questionArrayList, ArrayList<String> answerArrayList) {
        this.context = context;
        this.questionArrayList = questionArrayList;
        this.answerArrayList = answerArrayList;
        this.activity = (SupportActivity) context;
        this.recyclerView = recyclerView;
    }

    @Override
    public FaqsAdapter.FaqsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.custom_faqs_list_items, parent, false);
        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "font/avenirltstd_light.otf");
        fontChanger.replaceFonts((ViewGroup) v);
        return new FaqsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FaqsViewHolder holder, int position) {

        holder.questions_tv.setText(questionArrayList.get(holder.getAdapterPosition()));
//        holder.view.loadData("<html><body><font size = \"2\"> <p align=\"justify\">" + answerArrayList.get(holder.getAdapterPosition()) + "</p></font></body></html>", "text/html", "utf-8");
        //holder.jtv = new JustifiedTextView(context, answerArrayList.get(holder.getAdapterPosition()));
//        holder.llFaqsAnswer.addView(holder.jtv);
        //holder.jtv.setText(answerArrayList.get(holder.getAdapterPosition()));
        holder.answers_tv.setText(answerArrayList.get(holder.getAdapterPosition()));

        final boolean isExpanded = holder.getAdapterPosition() == mExpandedPosition;
        holder.llFaqsAnswer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        //holder.faqs_divider_view.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        if (isExpanded) {
            holder.questions_tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_up_black_24dp, 0);
        } else {
            holder.questions_tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down_black_24dp, 0);
        }

        holder.questions_tv.setActivated(isExpanded);
        holder.questions_tv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                recyclerView.smoothScrollToPosition(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return questionArrayList.size();
    }

    class FaqsViewHolder extends RecyclerView.ViewHolder {
        TextView questions_tv;
        TextView answers_tv;
        View faqs_divider_view;
        LinearLayout llFaqsAnswer;
        WebView view;
        JustifiedTextView jtv;

        FaqsViewHolder(View itemView) {
            super(itemView);
            questions_tv = (TextView) itemView.findViewById(R.id.faqs_question_textview);
            answers_tv = (TextView) itemView.findViewById(R.id.faqs_answer_textview);
            faqs_divider_view = (View) itemView.findViewById(R.id.faqs_divider_view);
            //view = (WebView) itemView.findViewById(R.id.webView);
            llFaqsAnswer = (LinearLayout) itemView.findViewById(R.id.ll_faqs_answer);
            //jtv = (JustifiedTextView) itemView.findViewById(R.id.faqs_answer_textview);
        }
    }
}
