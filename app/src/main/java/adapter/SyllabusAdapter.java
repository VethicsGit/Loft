package adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vethics.loft.CourseDetailsActivity;
import com.vethics.loft.R;

import java.util.List;

import customview.FontChangeCrawler;
import model.CourseDetailsbyCourseId.Content;
import model.CourseDetailsbyCourseId.Module;
import model.CourseDetailsbyCourseId.Topic;

/**
 * Created by Chirag on 1/19/2018.
 */

public class SyllabusAdapter extends BaseAdapter {
    private Context context;
    private CourseDetailsActivity activity;
    private List<Module> moduleList;
    private LayoutInflater layoutInflater;
    private String videoPath;
    private TextView textView;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public SyllabusAdapter(Context context, List<Module> moduleList) {
        this.context = context;
        this.activity = (CourseDetailsActivity) context;
        this.moduleList = moduleList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return moduleList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int finalK;
        FontChangeCrawler fontChanger = new FontChangeCrawler(context.getAssets(), "font/avenirltstd_light.otf");
        fontChanger.replaceFonts((ViewGroup) activity.findViewById(android.R.id.content));
        List<Topic> topicList = moduleList.get(position).getTopics();

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.custom_syllabus_list, parent, false);
            viewHolder.tvSyllabusMainHeaderTitle = (TextView) convertView.findViewById(R.id.tv_syllabus_main_header_title);
            viewHolder.tvSyllabusMainHeaderDuration = (TextView) convertView.findViewById(R.id.tv_syllabus_main_header_duration);
            viewHolder.llSyllabusSubContent = (LinearLayout) convertView.findViewById(R.id.ll_syllabus_sub_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvSyllabusMainHeaderTitle.setText(moduleList.get(position).getCourseModuleName());
        viewHolder.tvSyllabusMainHeaderDuration.setText(position + ":" + position);
        viewHolder.llSyllabusSubContent.removeAllViews();

        for (int j = 0; j < topicList.size(); j++) {
            //layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View v = layoutInflater.inflate(R.layout.custom_syllabus_sub_content, parent, false);
            textView = (TextView) v.findViewById(R.id.tv_syllabus_sub_content_title);
            final TextView textView1 = (TextView) v.findViewById(R.id.tv_syllabus_sub_content_duration);
            View line_view_lower = (View) v.findViewById(R.id.line_view_lower);
            View line_view_upper = (View) v.findViewById(R.id.line_view_upper);
            textView.setText(topicList.get(j).getCourseTopicName());
            textView1.setText(position + "m" + ":" + j + "s");
            if (j == topicList.size() - 1) {
                line_view_lower.setVisibility(View.GONE);
            }
            if (j == 0) {
                line_view_upper.setVisibility(View.GONE);
            }

            final List<Content> contentList = topicList.get(j).getContents();

            for (int k = 0; k < contentList.size(); k++) {
                videoPath = contentList.get(k).getFilePath();
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        v.setBackgroundColor(Color.BLUE);
                    /*    activity.closeDrawer();
                        activity.videoUrl = "http://demo.vethics.in/loft/" + videoPath;
                        activity.initializePlayer();*/
                    }
                });
            }
            viewHolder.llSyllabusSubContent.addView(v);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tvSyllabusMainHeaderTitle, tvSyllabusMainHeaderDuration;
        LinearLayout llSyllabusSubContent;
    }
}
