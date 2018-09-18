package com.vethics.loft;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.realm.Realm;
import model.KeyModel;
import utils.EncryptedFileDataSourceFactory;

public class ViewDownloadedVideoActivity extends AppCompatActivity {
    String strVideoFileName;
    int id;
    public static final String AES_ALGORITHM = "AES";
    public static final String AES_TRANSFORMATION = "AES/CTR/NoPadding";

    private static final String ENCRYPTED_FILE_NAME = "encrypted.mp4";

    private Cipher mCipher;
    private SecretKeySpec mSecretKeySpec;
    private IvParameterSpec mIvParameterSpec;

    private File mEncryptedFile;

    private SimpleExoPlayerView mSimpleExoPlayerView;
    Realm realm;
    private SimpleExoPlayer player;

    private boolean playWhenReady;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private boolean mExoPlayerFullscreen = false;
    private int mResumeWindow;
    private long mResumePosition;
    private ImageView mFullScreenIcon;
    FrameLayout mFullScreenButton;
    private Dialog mFullScreenDialog;
    private ImageView ivExoBookmarkIcon;
    private final String TAG = getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_downloaded_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSimpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.sepv_downloaded_video);
        Intent i = getIntent();
        strVideoFileName = i.getStringExtra("videoFileName");
        id = i.getIntExtra("id", 0);
        realm = Realm.getDefaultInstance();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void playVideo() {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        mSimpleExoPlayerView.setPlayer(player);
        Log.e(TAG, "id : " + id);

        KeyModel keyModel = realm.where(KeyModel.class).equalTo("id", id).findAll().last();
        Log.e(TAG, "key : " + keyModel.getKey().toString());
        Log.e(TAG, "iv : " + keyModel.getIv().toString());
        Log.e(TAG, "key model : " + keyModel.toString());

        mSecretKeySpec = new SecretKeySpec(keyModel.getKey(), AES_ALGORITHM);
        mIvParameterSpec = new IvParameterSpec(keyModel.getIv());
        try {
            mCipher = Cipher.getInstance(AES_TRANSFORMATION);
            mCipher.init(Cipher.DECRYPT_MODE, mSecretKeySpec, mIvParameterSpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String courseTitle = keyModel.getCoursename();
        /*File dirFiles = new File(getExternalFilesDir("downloads"), "/");
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.addAll(Arrays.asList(dirFiles.list()));
        Log.e("files", stringArrayList.toString());*/
        mEncryptedFile = new File(getExternalFilesDir("downloads/" + courseTitle), strVideoFileName);

        DataSource.Factory dataSourceFactory = new EncryptedFileDataSourceFactory(mCipher, mSecretKeySpec, mIvParameterSpec, bandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        try {
            Uri uri = Uri.fromFile(mEncryptedFile);
            MediaSource videoSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
            player.prepare(videoSource);
            player.setPlayWhenReady(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void initFullscreenButton() {

        PlaybackControlView controlView = findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        ivExoBookmarkIcon = (ImageView) controlView.findViewById(R.id.exo_bookmark_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen) {
                    openFullscreenDialog();
                } else {
                    closeFullscreenDialog();
                }
                //mExoPlayerFullscreen = !mExoPlayerFullscreen;
            }
        });

        ivExoBookmarkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewDownloadedVideoActivity.this, "Bookmarked!" + player.getCurrentPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFullscreenDialog() {
        player.setPlayWhenReady(player.getPlayWhenReady());
        ((ViewGroup) mSimpleExoPlayerView.getParent()).removeView(mSimpleExoPlayerView);
        mFullScreenDialog.addContentView(mSimpleExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //mSimpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        //getSupportActionBar().hide();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(ViewDownloadedVideoActivity.this, R.mipmap.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog() {
        player.setPlayWhenReady(player.getPlayWhenReady());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mFullScreenDialog.dismiss();
        ((ViewGroup) mSimpleExoPlayerView.getParent()).removeView(mSimpleExoPlayerView);
        ((FrameLayout) findViewById(R.id.main_media_frame)).addView(mSimpleExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //mSimpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        //getSupportActionBar().show();
        mExoPlayerFullscreen = false;
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(ViewDownloadedVideoActivity.this, R.mipmap.ic_fullscreen_expand));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            //initializePlayer();
            playVideo();
            initFullscreenDialog();
            initFullscreenButton();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            //initializePlayer();
            playVideo();
            initFullscreenDialog();
            initFullscreenButton();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
