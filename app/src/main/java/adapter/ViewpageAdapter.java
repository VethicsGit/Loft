package adapter;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import fragment.Tab1Fragment;
import fragment.Tab2Fragment;

public class ViewpageAdapter extends FragmentPagerAdapter {


    public ViewpageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        if (position == 0)
        {
            Tab1Fragment tab1= new Tab1Fragment();

        }else
        {
            Tab2Fragment tab2Fragment= new Tab2Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
