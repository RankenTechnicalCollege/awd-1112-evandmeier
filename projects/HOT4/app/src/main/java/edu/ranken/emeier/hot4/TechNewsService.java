package edu.ranken.emeier.hot4;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import edu.ranken.emeier.hot4.models.Article;


public class TechNewsService extends JobService {

    // fields
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                TechNewsApp.GET_URL,
                null,
                (JSONObject response) -> {
                    jobFinished(jobParameters, false);

                    try {
                        JSONObject item = response.getJSONArray("items").getJSONObject(0);
                        String imageUrl;
                        try {
                            imageUrl = item.getString("image");
                        } catch (Exception e) {
                            // if the there is not an image associated with a given article
                            imageUrl = TechNewsApp.DEFAULT_IMAGE;
                        }

                        Article article = new Article(
                                item.getString("title"),
                                item.getJSONObject("author").getString("name"),
                                item.getString("summary").substring(0, 40),
                                item.getString("date_published"),
                                item.getString("url"),
                                imageUrl
                        );

                        TechNewsApp app = (TechNewsApp) getApplication();
                        app.sendNotification(article);
                    } catch (Exception e) {
                        showToast(e.getMessage());
                        jobFinished(jobParameters, false);
                    } finally {
                        jobFinished(jobParameters, false);
                    }
                },
                (VolleyError error) -> {
                    showToast(error.getMessage());
                    jobFinished(jobParameters, false);
                }
        );

        mRequestQueue.add(request);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        mRequestQueue.stop();
        showToast(getString(R.string.job_stopped));
        return true;
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
