package com.vethics.loft;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SlideAdapter  extends PagerAdapter{


    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context)
    {
        this.context=context;

    }
    public String[] slide_desc={
            "     TAKE THE WORD'S BEST\n" +
                    "COURSES ONLINE",
            "UNDERSTANDING & LEARNING\n" +
                    "MASTER NEW SKILLS",
            "     LERNING IN NEW WAY,IN\n" +
                    "PATH WAYS"
    };

    @Override
    public int getCount() {
        return slide_desc.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_tab,container,false);

        TextView slidDescription=(TextView) view.findViewById(R.id.desc);


        slidDescription.setText(slide_desc[position]);

        container.addView(view);


        return view;
    }

    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);



    }
}
