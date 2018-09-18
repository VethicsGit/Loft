package service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.vethics.loft.DashBoardActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.realm.Realm;
import model.KeyModel;
import utils.Constants;
import utils.SessionManager;

/**
 * Created by Chirag on 2/7/2018.
 */

public class DownloaderService extends Service {
    public static final String AES_ALGORITHM = "AES";
    public static final String AES_TRANSFORMATION = "AES/CTR/NoPadding";

    private Cipher mCipher;

    Uri uri;
    String url1, courseTitle;
    private static int NOTIFY_ID = 1337;
    private static int FOREGROUND_ID = 1338;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyManager;

    private Intent progressIntent, completeIntent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null && "stopService".equalsIgnoreCase(intent.getAction())) {
//            mNotifyManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//            mNotifyManager.cancel(FOREGROUND_ID);
            stopForeground(true);
            stopSelf();
            Log.e("Service : ", "Stopped");
        } else {
            url1 = intent.getStringExtra("videoUrl");//"https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
            courseTitle = intent.getStringExtra("courseTitle");
            uri = Uri.parse(url1);
            startForeground(FOREGROUND_ID, buildForegroundNotification(uri.getLastPathSegment()));
            progressIntent = new Intent(Constants.BROADCAST_ACTION);
            completeIntent = new Intent(Constants.BROADCAST_COMPLETE_ACTION);
            File mEncryptedFile = new File(getExternalFilesDir("downloads/" + courseTitle), uri.getLastPathSegment());
            SecureRandom secureRandom = new SecureRandom();
            final byte[] key = new byte[16];
            final byte[] iv = new byte[16];
            secureRandom.nextBytes(key);
            secureRandom.nextBytes(iv);

            SecretKeySpec mSecretKeySpec = new SecretKeySpec(key, AES_ALGORITHM);
            IvParameterSpec mIvParameterSpec = new IvParameterSpec(iv);

            Log.e("secret key1 : ", key.toString());
            Log.e("secret iv1 : ", iv.toString());

            try {
                mCipher = Cipher.getInstance(AES_TRANSFORMATION);
                mCipher.init(Cipher.DECRYPT_MODE, mSecretKeySpec, mIvParameterSpec);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                new DownloadAndEncryptFileTask(getApplicationContext(), url1, courseTitle, mEncryptedFile, mCipher, key, iv).execute();
           /* Cipher encryptionCipher = Cipher.getInstance(AES_TRANSFORMATION);
            encryptionCipher.init(Cipher.ENCRYPT_MODE, mSecretKeySpec, mIvParameterSpec);
            URL url = new URL(url1);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            final int lenghtOfFile = connection.getContentLength();
            Log.e("length :", "" + lenghtOfFile);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("server error: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
            }

            //Displays the progress bar for the first time.
            //mBuilder.setProgress(100, 0, false);
            //mNotifyManager.notify(9648, mBuilder.build());

            InputStream inputStream = connection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(mEncryptedFile);
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
                progressIntent.putExtra("filename", Uri.parse(url1).getLastPathSegment());
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(progressIntent);
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
                    //person.setId(nextID);
                    person.setCoursename(courseTitle);
                    person.setFilename(Uri.parse(url1).getLastPathSegment());
                    person.setKey(key);
                    person.setIv(iv);
                    person.setPath(getApplicationContext().getExternalFilesDir("downloads") + "/" + Uri.parse(url1).getLastPathSegment());
                    person.setFileSize(Integer.parseInt(String.valueOf(lenghtOfFile / 1024)));
                    person.setDownloaded("true");
                }
            });

            inputStream.close();
            cipherOutputStream.close();
            connection.disconnect();

            completeIntent.putExtra("mKey", mKey);
            completeIntent.putExtra("iv", iv);
            completeIntent.putExtra("filename", Uri.parse(url1).getLastPathSegment());
            completeIntent.putExtra("filesize", Integer.parseInt(String.valueOf(lenghtOfFile / 1024)));
            raiseNotification(intent, mEncryptedFile, null);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(completeIntent);*/
            } catch (Exception e) {
                raiseNotification(intent, null, e);
                e.printStackTrace();
            }
        }

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("Task Removed : ", "true");
        super.onTaskRemoved(rootIntent);
    }

    public void raiseNotification(Intent inbound, File output, Exception e) {

        if (e == null) {
            // Create an Intent for the activity you want to start
            Intent resultIntent = new Intent(this, DashBoardActivity.class);
            resultIntent.putExtra("fragment", 3);
            // Create the TaskStackBuilder and add the intent, which inflates the back stack
            // TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            //stackBuilder.addNextIntentWithParentStack(resultIntent);
            // Get the PendingIntent containing the entire back stack
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            //stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                mBuilder = new NotificationCompat.Builder(getApplicationContext(), "Download Notification");
                mBuilder.setContentTitle("Download completed")
                        .setContentText(uri.getLastPathSegment())
                        .setContentIntent(resultPendingIntent)
                        .setSmallIcon(android.R.drawable.stat_sys_download_done)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setChannelId("Download Notification");
            } else {
                // The id of the channel.
                String id = "download_video_channel";
                // The user-visible name of the channel.
                CharSequence name = "Download Notification";//getApplicationContext().getString(R.string.channel_name);
                // The user-visible description of the channel.
                String description = "This is notification for your downloads";//getApplicationContext().getString(R.string.channel_description);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(id, name, importance);
                // Configure the notification channel.
                mChannel.setDescription(description);
                mChannel.enableLights(true);
                // Sets the notification light color for notifications posted to this
                // channel, if the device supports this feature.
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotifyManager.createNotificationChannel(mChannel);
                mBuilder = new NotificationCompat.Builder(getApplicationContext(), id);
                mBuilder.setContentTitle("Download Completed")
                        .setContentText(uri.getLastPathSegment())
                        .setContentIntent(resultPendingIntent)
                        .setSmallIcon(android.R.drawable.stat_sys_download_done)
                        .setTicker("Download Completed")
                        .setChannelId(id);
            }
            /*Intent outbound = new Intent(Intent.ACTION_VIEW);
            Uri outputUri =
                    FileProvider.getUriForFile(this, AUTHORITY, output);

            outbound.setDataAndType(outputUri, inbound.getType());
            outbound.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            PendingIntent pi = PendingIntent.getActivity(this, 0,
                    outbound, PendingIntent.FLAG_UPDATE_CURRENT);

            b.setContentIntent(pi);*/
        } else {
            mBuilder.setContentTitle("Download failed!")
                    .setContentText(e.getMessage())
                    .setSmallIcon(android.R.drawable.stat_notify_error)
                    .setTicker("Download failed!");
        }
        mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(NOTIFY_ID, mBuilder.build());
    }

    private Notification buildForegroundNotification(String filename) {
        NotificationCompat.Builder b = new NotificationCompat.Builder(this, "");

        b.setOngoing(true)
                .setContentTitle("Downloading...")
                .setContentText(filename)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setTicker("Downloading...");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mNotifyManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(getApplicationContext(), "Download Notification");
            mBuilder.setContentTitle("Downloading...")
                    .setContentText(filename)
                    .setSmallIcon(android.R.drawable.stat_sys_download)
                    .setChannelId("Download Notification");
        } else {
            mNotifyManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            // The id of the channel.
            String id = "download_video_channel";
            // The user-visible name of the channel.
            CharSequence name = "Download Notification";//getApplicationContext().getString(R.string.channel_name);
            // The user-visible description of the channel.
            String description = "This is the notification for your downloads";//getApplicationContext().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotifyManager.createNotificationChannel(mChannel);
            mBuilder = new NotificationCompat.Builder(getApplicationContext(), id);
            mBuilder.setContentTitle("Downloading...")
                    .setContentText(filename)
                    .setSmallIcon(android.R.drawable.stat_sys_download)
                    .setChannelId(id);
        }

        return mBuilder.build();
    }

    public class DownloadAndEncryptFileTask extends AsyncTask<String, String, String> {

        private String mUrl;
        private File mFile;
        private Cipher mCipher;
        private byte[] mKey, mIv;
        //private DownloaderService downloaderService;
        private Realm realm;
        private String strCourseTitle;
        private SharedPreferences sharedPreferences;
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
            //downloaderService = new DownloaderService();
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
                Log.v(getClass().getCanonicalName(), "Downloading...");
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
            raiseNotification(null, mFile, null);
            stopForeground(true);
            Log.e(getClass().getCanonicalName(), "Download Completed.");

            /*completeIntent.putExtra("mKey", mKey);
            completeIntent.putExtra("iv", iv);
            completeIntent.putExtra("filename", Uri.parse(mUrl).getLastPathSegment());
            completeIntent.putExtra("filesize", Integer.parseInt(String.valueOf(lenghtOfFile / 1024)));*/
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(completeIntent);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                downloadAndEncrypt();
            } catch (Exception e) {
                Log.e(getClass().getCanonicalName(), "Download Failed.");
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
