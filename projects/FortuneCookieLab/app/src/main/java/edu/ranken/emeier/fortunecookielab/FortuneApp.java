package edu.ranken.emeier.fortunecookielab;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class FortuneApp extends Application {

    // constants
    private static final String TAG = "FortuneApp";
    public static final String API_URL = "http://iwt.ranken.edu/ExampleWebServices/FortuneCookie/GetFortuneJSON";
    public static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    public static final int NOTIFICATION_ID = 0;
    public static final int JOB_ID = 0;
    public static final String NEW_FORTUNE_ACTION = "edu.ranken.emeier.fortunecookielab.NEW_FORTUNE_ACTION";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_LAST_UPDATED = "last_updated";

    // fields
    private NotificationManager mNotifyManager;
    private JobScheduler mScheduler;
    private String mLastUpdated;
    
    @Override
    public void onCreate() {
        super.onCreate();

        // create fields
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        // create notification channel
        createNotificationChannel();
        scheduleJob();

        Log.i(TAG, "App Started");
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel =
                    new NotificationChannel(
                            PRIMARY_CHANNEL_ID,
                            getString(R.string.primary_channel_name),
                            NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription(getString(R.string.primary_channel_description));
            mNotifyManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(String message, String lastUpdated) {
        if (mLastUpdated != null && mLastUpdated == lastUpdated) {
            return;
        }

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingContentIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
        .setSmallIcon(R.drawable.fortunecookie2)
        .setContentTitle("New Fortune!")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setContentIntent(pendingContentIntent)
        .setAutoCancel(true);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
        mLastUpdated = lastUpdated;
    }

    public void scheduleJob() {
        ComponentName serviceName =
                new ComponentName(getPackageName(), NotificationJobService.class.getName());

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(15 * 60 * 1000)
                .setPersisted(true);
//                .setMinimumLatency(3000)
//                .setOverrideDeadline(5000);

        mScheduler.schedule(builder.build());
    }
}
