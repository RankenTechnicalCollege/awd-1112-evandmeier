package edu.ranken.emeier.hot4;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.ranken.emeier.hot4.models.Article;

public class TechNewsApp extends Application {

    // constants
    private final String TAG = TechNewsApp.class.getSimpleName();
    public static final String GET_URL = "https://www.npr.org/feeds/1019/feed.json";
    public static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    public static final int NOTIFICATION_ID = 0;
    public static final int JOB_ID = 0;
    public static final String DEFAULT_IMAGE = "https://via.placeholder.com/300";

    // fields
    private NotificationManager mNotifyManager;
    private JobScheduler mScheduler;
    private String mLastUpdated;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize fields
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        // create notification channel
        createNotificationChannel();

        // schedule the job to run
        scheduleJob();

        // to avoid getting a duplicate notification, we need to
        // retrieve the time stamp from the most recent article
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                GET_URL,
                null,
                (JSONObject response) -> {
                    try {
                        JSONArray items = response.getJSONArray("items");
                        JSONObject item = items.getJSONObject(0);

                        mLastUpdated = item.getString("date_published");
                        Log.i(TAG, mLastUpdated);
                    } catch (JSONException e) {
                        mLastUpdated = "Default";
                        Log.i(TAG, mLastUpdated);
                    }
                },
                (VolleyError error) -> {
                    mLastUpdated = "Default";
                    Log.i(TAG, mLastUpdated);
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        Log.d(TAG, "App started!");
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel =
                    new NotificationChannel(
                            PRIMARY_CHANNEL_ID,
                            getString(R.string.channel_name),
                            NotificationManager.IMPORTANCE_HIGH
                    );

            channel.setDescription(getString(R.string.channel_description));
            mNotifyManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(Article article) {
        Log.i(TAG, String.format("Last Updated: %s , Date Published: %s", mLastUpdated, article.getDatePublished()));
        // if the time stamps are the same, we know that it isn't a new article
        if (mLastUpdated != null && mLastUpdated.equals(article.getDatePublished())) {
            return;
        }

        // build the url for the intent
        String url = article.getArticleUrl();
        Uri webpage = Uri.parse(url);

        Intent contentIntent = new Intent(Intent.ACTION_VIEW, webpage);
        PendingIntent pendingContentIntent = PendingIntent.getActivity(
                this,
                NOTIFICATION_ID,
                contentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.npr_logo)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(article.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(article.getSummary())
                        .setBigContentTitle(article.getTitle()))
                .setContentIntent(pendingContentIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
        mLastUpdated = article.getDatePublished();
    }

    public void scheduleJob() {
        ComponentName serviceName =
                new ComponentName(getPackageName(), TechNewsService.class.getName());

        JobInfo.Builder builder =
                new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(15 * 60 % 1000)
                .setPersisted(true);

        mScheduler.schedule(builder.build());
    }
}
