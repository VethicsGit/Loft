/*
package service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import io.realm.Realm;
import model.KeyModel;
import utils.Constants;
import utils.SessionManager;

*/
/**
 * Created by Chirag on 2/8/2018.
 *//*


public class DownloadAndEncryptFileTask extends AsyncTask<String, String, String> {

    private String mUrl;
    private File mFile;
    private Cipher mCipher;
    private byte[] mKey, mIv;
    private DownloaderService downloaderService;
    private Realm realm;
    private String strCourseTitle;
    private SharedPreferences sharedPreferences;
    private Intent progressIntent, completeIntent;
    private WeakReference<Context> contextRef;

    DownloadAndEncryptFileTask(Context context, String url, String courseTitle, File file, Cipher cipher, byte[] key, byte[] iv) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("You need to supply a url to a clear MP4 file to download and encrypt, or modify the code to use a local encrypted mp4");
        }
        mUrl = url;
        this.strCourseTitle = courseTitle;
        mFile = file;
        mCipher = cipher;
        contextRef = new WeakReference<>(context);
        this.mKey = key;
        this.mIv = iv;
        downloaderService = new DownloaderService();
        progressIntent = new Intent(Constants.BROADCAST_ACTION);
        completeIntent = new Intent(Constants.BROADCAST_COMPLETE_ACTION);
        sharedPreferences = context.getSharedPreferences(SessionManager.PREF_NAME, Context.MODE_PRIVATE);
    }

    private void downloadAndEncrypt() throws Exception {
        final Context mContext = contextRef.get();
        URL url = new URL(mUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        final int lenghtOfFile = connection.getContentLength();
        Log.e("length :", "" + lenghtOfFile);
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("server error: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
        }

        InputStream inputStream = connection.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(mFile);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, mCipher);

        byte buffer[] = new byte[1024 * 1024];
        int bytesRead;
        long total = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            Log.e(getClass().getCanonicalName(), "reading from http..." + bytesRead);
            total += bytesRead;
            //publishProgress("" + (int) ((total * 100) / lenghtOfFile));
            cipherOutputStream.write(buffer, 0, bytesRead);
            progressIntent.putExtra("progress", (int) ((total * 100) / lenghtOfFile));
            progressIntent.putExtra("filename", Uri.parse(mUrl).getLastPathSegment());
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(progressIntent);
        }

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm1) {

                Number num = realm.where(KeyModel.class).max("id");
                int nextID;
                if (num == null) {
                    nextID = 1;
                } else {
                    nextID = num.intValue() + 1;
                }
                KeyModel person = realm1.createObject(KeyModel.class, nextID);
                person.setStudent_id(sharedPreferences.getString(SessionManager.KEY_ID, ""));
                person.setCoursename(strCourseTitle);
                person.setFilename(Uri.parse(mUrl).getLastPathSegment());
                person.setKey(mKey);
                person.setIv(mIv);
                person.setPath(mContext.getExternalFilesDir("downloads/" + strCourseTitle) + "/" + Uri.parse(mUrl).getLastPathSegment());
                person.setFileSize(Integer.parseInt(String.valueOf(lenghtOfFile / 1024)));
                person.setDownloaded("true");
            }
        });

        inputStream.close();
        cipherOutputStream.close();
        connection.disconnect();
        downloaderService.raiseNotification(null, mFile, null);
        downloaderService.stopForeground(true);
            */
/*completeIntent.putExtra("mKey", mKey);
            completeIntent.putExtra("iv", iv);
            completeIntent.putExtra("filename", Uri.parse(mUrl).getLastPathSegment());
            completeIntent.putExtra("filesize", Integer.parseInt(String.valueOf(lenghtOfFile / 1024)));*//*

        LocalBroadcastManager.getInstance(mContext).sendBroadcast(completeIntent);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            downloadAndEncrypt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}*/
