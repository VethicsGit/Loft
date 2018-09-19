package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vethics.loft.R;

import adapter.ChapterAdapter;

public class Tab1Fragment extends android.support.v4.app.Fragment {


RecyclerView rv_chepter;
ChapterAdapter chapterAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.tab1,container,false);




            rv_chepter=view.findViewById(R.id.rv_chepter);


        return view;
    }
}
