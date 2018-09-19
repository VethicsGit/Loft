package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vethics.loft.R;

public class Tab2Fragment extends android.support.v4.app.Fragment {

        ImageView dow_img,abt_course,share_course,q_a,announce,resource;
        TextView tv_dow_img,tv_abt_course,tv_share_course,tv_q_a,tv_announce,tv_resource;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab_2,container,false);



        dow_img=view.findViewById(R.id.dow_img);
        abt_course=view.findViewById(R.id.abt_course);
        share_course=view.findViewById(R.id.share_course);
        q_a=view.findViewById(R.id.q_a);
        announce=view.findViewById(R.id.announce);
        resource=view.findViewById(R.id.resource);
        tv_dow_img=view.findViewById(R.id.tv_dow_img);
        tv_abt_course=view.findViewById(R.id.tv_abt_course);
        tv_share_course=view.findViewById(R.id.tv_share_course);
        tv_q_a=view.findViewById(R.id.tv_q_a);
        tv_announce=view.findViewById(R.id.tv_announce);
        tv_resource=view.findViewById(R.id.tv_resource);




        return view;
    }
}
