package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vethics.loft.GlideApp;
import com.vethics.loft.R;

import java.util.ArrayList;

public class OfferSliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Activity activity;
    private ArrayList<Drawable> image_arraylist;

    public OfferSliderPagerAdapter(Activity activity, ArrayList<Drawable> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        View view = layoutInflater.inflate(R.layout.custom_offer_slider, container, false);
        ImageView im_slider = (ImageView) view.findViewById(R.id.iv_offer_slider);
        GlideApp.with(activity)
                .load(image_arraylist.get(position))
                .dontAnimate()
                .placeholder(R.drawable.ic_complete_icon)
                .error(R.drawable.ic_pending_icon)
                .into(im_slider);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}