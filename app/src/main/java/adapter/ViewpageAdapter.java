package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragment.Tab1Fragment;
import fragment.Tab2Fragment;

public class ViewpageAdapter extends FragmentPagerAdapter {

    CharSequence Title[];
    int NumbOfTabs;

   /* public ViewpageAdapter(FragmentManager fm) {
        super(fm);
    }*/

    public ViewpageAdapter(FragmentManager supportFragmentManager, CharSequence[] title, int numboftabs) {
        super(supportFragmentManager);
        this.Title = title;
        this.NumbOfTabs = numboftabs    ;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0)
        {
            Tab1Fragment tab1= new Tab1Fragment();
            return tab1;


        }else
        {
            Tab2Fragment tab2= new Tab2Fragment();
            return tab2;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


}
