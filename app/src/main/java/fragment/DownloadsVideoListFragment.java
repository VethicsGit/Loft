package fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.util.Util;
import com.vethics.loft.R;
import com.vethics.loft.ViewDownloadedVideoActivity;

import adapter.DownloadedVideoAdapter;
import interfaces.OnRecyclerViewItemClickListner;
import io.realm.Realm;
import io.realm.RealmResults;
import model.KeyModel;
import utils.AndroidUtils;
import utils.Constants;

public class DownloadsVideoListFragment extends Fragment implements OnRecyclerViewItemClickListner {

    RealmResults<KeyModel> keyModelList;
    RecyclerView rvDownloads;
    ProgressBar pbDownloadVideo;
    TextView tvDownloadVideoName, tvProgressValue;
    private boolean _areLecturesLoaded = false;
    private Realm realm;
    LinearLayout llDownloadProgress;
    ImageView ivBackDownloadCourse;
    String strCourseName;
    private final String TAG = getClass().getCanonicalName();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_downloads, container, false);
//        ((DashBoardActivity) getActivity()).getSupportActionBar().setTitle("Downloads");
        setHasOptionsMenu(true);
        rvDownloads = (RecyclerView) v.findViewById(R.id.lv_downloads);
        pbDownloadVideo = (ProgressBar) v.findViewById(R.id.pb_download_video);
        tvDownloadVideoName = (TextView) v.findViewById(R.id.tv_download_video_name);
        tvProgressValue = (TextView) v.findViewById(R.id.tv_progress_value);
        llDownloadProgress = (LinearLayout) v.findViewById(R.id.ll_download_progress);
        ivBackDownloadCourse = (ImageView) v.findViewById(R.id.iv_back_download_course);
        Bundle bundle = getArguments();
        if (bundle != null) {
            strCourseName = bundle.getString("coursename");
        }
        llDownloadProgress.setVisibility(View.GONE);
        realm = Realm.getDefaultInstance();
        loadDownloadVideoList();

        ivBackDownloadCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingListFragment downloadsVideoListFragment = new SettingListFragment();
                getFragmentManager().beginTransaction().replace(R.id.downloadContainer, downloadsVideoListFragment).commit();
            }
        });
        return v;
    }

    private void loadDownloadVideoList() {
        //realm = Realm.getDefaultInstance();
        keyModelList = realm.where(KeyModel.class)
                .beginGroup()
                .equalTo("downloaded", "true")
                .and()
                .equalTo("coursename", strCourseName)
                .endGroup()
                .findAll();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvDownloads.setLayoutManager(linearLayoutManager);
        DownloadedVideoAdapter downloadedVideoAdapter = new DownloadedVideoAdapter(getActivity(), keyModelList, this, "video");
        rvDownloads.setAdapter(downloadedVideoAdapter);
    }

    private BroadcastReceiver broadcastProgressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateProgressUI(intent);
        }

    };

    private BroadcastReceiver broadcastCompleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateCompletedUI(intent);
        }

    };

    private void updateCompletedUI(final Intent intent) {
        llDownloadProgress.setVisibility(View.GONE);
        final String strFileName = intent.getStringExtra("filename");
        final int strFileSize = intent.getIntExtra("filesize", 0);
        final byte[] strKey = intent.getByteArrayExtra("key");
        final byte[] strIv = intent.getByteArrayExtra("iv");
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                KeyModel person = realm.where(KeyModel.class).equalTo("filename", strFileName).findFirst();
                person.setKey(strKey);
                person.setIv(strIv);
                person.setFileSize(strFileSize);
                person.setDownloaded("true");
                Log.e(TAG, "key data : " + strKey + " " + strIv + " added");
                loadDownloadVideoList();
            }
        });
    }

    private void updateProgressUI(Intent intent) {
        llDownloadProgress.setVisibility(View.VISIBLE);
        pbDownloadVideo.setProgress(intent.getIntExtra("progress", 0));
        tvProgressValue.setText(intent.getIntExtra("progress", 0));
        tvDownloadVideoName.setText(AndroidUtils.wordFirstCap(intent.getStringExtra("filename")));
        if (intent.getIntExtra("progress", 0) == 100) {
            llDownloadProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        getActivity().getMenuInflater().inflate(R.menu.menu_change_password, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            getActivity().registerReceiver(broadcastProgressReceiver, new IntentFilter(Constants.BROADCAST_ACTION));
            getActivity().registerReceiver(broadcastCompleteReceiver, new IntentFilter(Constants.BROADCAST_COMPLETE_ACTION));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(), "Resume...", Toast.LENGTH_SHORT).show();
        if ((Util.SDK_INT <= 23)) {
            getActivity().registerReceiver(broadcastProgressReceiver, new IntentFilter(Constants.BROADCAST_ACTION));
            getActivity().registerReceiver(broadcastCompleteReceiver, new IntentFilter(Constants.BROADCAST_COMPLETE_ACTION));
        }

        /*ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        //actionBar.setTitle("First Fragment");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);*/
    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity(), "Pause...", Toast.LENGTH_SHORT).show();
        if (Util.SDK_INT <= 23) {
            getActivity().unregisterReceiver(broadcastProgressReceiver);
            getActivity().unregisterReceiver(broadcastCompleteReceiver);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getActivity(), "Stop...", Toast.LENGTH_SHORT).show();
        if (Util.SDK_INT > 23) {
            getActivity().unregisterReceiver(broadcastProgressReceiver);
            getActivity().unregisterReceiver(broadcastCompleteReceiver);
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), ViewDownloadedVideoActivity.class);
        intent.putExtra("videoFileName", keyModelList.get(position).getFilename());
        getActivity().startActivity(intent);
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !_areLecturesLoaded) {
            loadDownloadVideoList();
            Toast.makeText(getActivity(), "cvvc", Toast.LENGTH_SHORT).show();
            _areLecturesLoaded = true;
        }
    }*/
}
