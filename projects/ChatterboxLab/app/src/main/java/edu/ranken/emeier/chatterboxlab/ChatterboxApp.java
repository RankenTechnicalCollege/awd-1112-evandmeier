package edu.ranken.emeier.chatterboxlab;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


public class ChatterboxApp extends Application {

    // constants
    public static final String TAG = ChatterboxApp.class.getSimpleName();
    public static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    public static final int NOTIFICATION_ID = 0;

    // fields
    private NotificationManager mNotifyManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // create fields
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // create notification channel
        createNotificationChannel();

        Log.i(TAG, "App Started!");
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

    public void sendNotification(String user, String message) {
        Intent contentIntent = new Intent(this, ChatroomActivity.class);
        PendingIntent pendingContentIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_message)
                        .setContentTitle("New Message!")
                        .setContentText(user + ": " + message)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setContentIntent(pendingContentIntent)
                        .setAutoCancel(true);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
    }
}
