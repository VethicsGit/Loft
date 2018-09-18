package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vethics.loft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralDownloadFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_general_download, container, false);
        SettingListFragment downloadsVideoListFragment = new SettingListFragment();
        getFragmentManager().beginTransaction().replace(R.id.downloadContainer, downloadsVideoListFragment).commit();
        return v;
    }

}
