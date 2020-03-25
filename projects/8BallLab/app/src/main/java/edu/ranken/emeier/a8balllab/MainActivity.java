package edu.ranken.emeier.a8balllab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    // constants
    private static final String URL = "http://iwt.ranken.edu/ExampleWebServices/Magic8Ball";
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String ACTION_NOTIFICATION = BuildConfig.APPLICATION_ID + ".ACTION_UPDATE_NOTIFICATION";
    private static final int NOTIFICATION_ID = 0;

    // widgets
    private TextView mMessageView;

    // fields
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind widgets
        mMessageView = findViewById(R.id.text_message);

        // setup notification channel
        createNotificationChannel();
    }

    public void createNotificationChannel() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(PRIMARY_CHANNEL_ID, "8 Ball Notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Default channel for 8 Ball notifications.");

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void getPhrase(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL,
                (String response) -> {
                    mMessageView.setText(response);

                    // create intent for the standard notification
                    Intent notificationIntent = new Intent(this, MainActivity.class);
                    PendingIntent notificationPendingIntent =
                            PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

                    NotificationCompat.Builder notificationBuilder =
                            new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID);
                    notificationBuilder
                            .setContentTitle("NEW MESSAGE!")
                            .setContentText(response)
                            .setContentIntent(notificationPendingIntent)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setSmallIcon(R.drawable.ic_android);

                    mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
                },
                (VolleyError error) -> {
                    mMessageView.setText(R.string.error_message);
                });

        queue.add(request);
    }
}
