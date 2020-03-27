package edu.ranken.emeier.fortunecookielab;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NotificationJobService extends JobService {

    // fields
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, FortuneApp.API_URL, null,
                (JSONObject response) -> {
                    jobFinished(params, false);
                    try {
                        String message = response.getString("Fortune");
                        String lastUpdated = response.getString("LastUpdated");

                        Intent broadcastIntent = new Intent(FortuneApp.NEW_FORTUNE_ACTION);
                        broadcastIntent.putExtra(FortuneApp.EXTRA_MESSAGE, message);
                        broadcastIntent.putExtra(FortuneApp.EXTRA_LAST_UPDATED, lastUpdated);

                        LocalBroadcastManager
                                .getInstance(NotificationJobService.this)
                                .sendBroadcast(broadcastIntent);

                        FortuneApp app = (FortuneApp) getApplication();
                        app.sendNotification(message, lastUpdated);
                    } catch (Exception e) {
                        showToast(e.getMessage());
                        jobFinished(params, false);
                    } finally {
                        jobFinished(params, false);
                    }
                },
                (VolleyError error) -> {
                    showToast(error.getMessage());
                    jobFinished(params, false);
                });

        mRequestQueue.add(request);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mRequestQueue.stop();
        showToast("Job stopped!");
        return true;
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
