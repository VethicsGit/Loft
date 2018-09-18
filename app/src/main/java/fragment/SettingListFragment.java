package fragment;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.util.Util;
import com.vethics.loft.EmailSigninActivity;
import com.vethics.loft.R;
import com.vethics.loft.SettingAboutus;
import com.vethics.loft.SettingFeedback;
import com.vethics.loft.SettingPrivacy;
import com.vethics.loft.SettingTerms;
import com.vethics.loft.StudentProfileActivity;

import adapter.DownloadedVideoAdapter;
import broadcastreceiver.broadcastProgressReceiver;
import interfaces.OnRecyclerViewItemClickListner;
import io.realm.Realm;
import io.realm.RealmResults;
import model.KeyModel;
import service.DownloaderService;
import utils.AndroidUtils;
import utils.Constants;
import utils.SessionManager;

public class SettingListFragment extends Fragment  {
/*
    RecyclerView rvDownloads;
    private Realm realm;
    RealmResults<KeyModel> keyModelList;
    private final String TAG = getClass().getCanonicalName();
    ProgressBar pbDownloadVideo;
    TextView tvDownloadVideoName, tvProgressValue;
    LinearLayout llDownloadProgress;
    BroadcastReceiver broadcastReceiver;
    SharedPreferences sharedPreferences;
    ImageView ivDownloadedVideoDelete;
*/
LinearLayout setting_editprofile,setting_aboutus,setting_feedback,setting_privacypolice,setting_termsofuse,setting_invitefriends,setting_rateapp,setting_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloads_courses_list, container, false);




        setting_editprofile = (LinearLayout) view.findViewById(R.id.setting_editprofile);
        setting_aboutus = (LinearLayout) view.findViewById(R.id.setting_aboutus);
        setting_feedback = (LinearLayout) view.findViewById(R.id.setting_feedback);
        setting_privacypolice = (LinearLayout) view.findViewById(R.id.setting_privacypolice);
        setting_termsofuse = (LinearLayout) view.findViewById(R.id.setting_termsofuse);
        setting_invitefriends=(LinearLayout)view.findViewById(R.id.setting_invitefriends);
        setting_rateapp = (LinearLayout) view.findViewById(R.id.setting_rateapp);

        setting_logout = (LinearLayout) view.findViewById(R.id.setting_logout);


        setting_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), StudentProfileActivity.class);
                startActivity(intent);

            }
        });


        setting_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(getContext(), SettingAboutus.class);
                startActivity(intent);

            }
        });

        setting_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] TO = {""};
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);


                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");


                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        setting_privacypolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), SettingPrivacy.class);
                startActivity(intent);
            }
        });
        setting_termsofuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent  intent=new Intent(getContext(), SettingTerms.class);
                startActivity(intent);
            }
        });

        setting_invitefriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject test");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "extra text that you want to put");
                startActivity(Intent.createChooser(i,"Share via"));

            }
        });
        setting_rateapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
                }

            }
        });
        setting_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences=getContext().getSharedPreferences("",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.apply();


                Intent intent = new Intent(getContext(), EmailSigninActivity.class);
                startActivity(intent);
            }
        });

//        ((DashBoardActivity) getActivity()).getSupportActionBar().setTitle("Downloads");
      /*  setHasOptionsMenu(true);
        rvDownloads = (RecyclerView) v.findViewById(R.id.lv_downloads);
        pbDownloadVideo = (ProgressBar) v.findViewById(R.id.pb_download_video);
        tvDownloadVideoName = (TextView) v.findViewById(R.id.tv_download_video_name);
        tvProgressValue = (TextView) v.findViewById(R.id.tv_progress_value);
        llDownloadProgress = (LinearLayout) v.findViewById(R.id.ll_download_progress);
        ivDownloadedVideoDelete = (ImageView) v.findViewById(R.id.iv_downloaded_video_delete);
        realm = Realm.getDefaultInstance();
        llDownloadProgress.setVisibility(View.GONE);
        broadcastReceiver = new broadcastProgressReceiver();
        sharedPreferences = getActivity().getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);

        ivDownloadedVideoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DownloaderService.class);
                intent.setAction("stopService");
                getActivity().startService(intent);
                llDownloadProgress.setVisibility(View.GONE);
                loadDownloadCourseList();
            }
        });
        loadDownloadCourseList();
        return v;
    }

    private void loadDownloadCourseList() {
        keyModelList = realm.where(KeyModel.class).distinctValues("coursename").equalTo("student_id", sharedPreferences.getString(SessionManager.KEY_ID, "")).findAll();
        if (keyModelList.isEmpty()) {
            rvDownloads.setVisibility(View.GONE);
        } else {
            rvDownloads.setVisibility(View.VISIBLE);
        }
        Log.e(TAG, "Course List : " + keyModelList.toString());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvDownloads.setLayoutManager(linearLayoutManager);
        DownloadedVideoAdapter downloadedVideoAdapter = new DownloadedVideoAdapter(getActivity(), keyModelList, this, "course");
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
        loadDownloadCourseList();
    }

    private void updateProgressUI(Intent intent) {
        llDownloadProgress.setVisibility(View.VISIBLE);
        rvDownloads.setVisibility(View.VISIBLE);
        pbDownloadVideo.setProgress(intent.getIntExtra("progress", 0));
        tvProgressValue.setText(intent.getIntExtra("progress", 0) + "%");
        tvDownloadVideoName.setText(AndroidUtils.wordFirstCap(intent.getStringExtra("filename")));
        if (intent.getIntExtra("progress", 0) == 100) {
            llDownloadProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastProgressReceiver, new IntentFilter(Constants.BROADCAST_ACTION));
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastCompleteReceiver, new IntentFilter(Constants.BROADCAST_COMPLETE_ACTION));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getActivity(), "Resume...", Toast.LENGTH_SHORT).show();
        if ((Util.SDK_INT <= 23)) {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastProgressReceiver, new IntentFilter(Constants.BROADCAST_ACTION));
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastCompleteReceiver, new IntentFilter(Constants.BROADCAST_COMPLETE_ACTION));
        }

        *//*ActionBar actionBar = ((DashBoardActivity) getActivity()).getSupportActionBar();
        //actionBar.setTitle("First Fragment");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);*//*
    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity(), "Pause...", Toast.LENGTH_SHORT).show();
        if (Util.SDK_INT <= 23) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastProgressReceiver);
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastCompleteReceiver);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getActivity(), "Stop...", Toast.LENGTH_SHORT).show();
        if (Util.SDK_INT > 23) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastProgressReceiver);
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastCompleteReceiver);
        }
    }

    @Override
    public void onItemClick(int position) {*/
       /* DownloadsVideoListFragment downloadsVideoListFragment = new DownloadsVideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("coursename", keyModelList.get(position).getCoursename());
        downloadsVideoListFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.downloadContainer, downloadsVideoListFragment).addToBackStack("DownloadCourseList").commit();*/
        return view;
    }

}
