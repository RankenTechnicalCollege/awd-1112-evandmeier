package edu.ranken.emeier.pockgit;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import edu.ranken.emeier.pockgit.data.PockGitDatabase;
import edu.ranken.emeier.pockgit.data.entity.Commit;
import edu.ranken.emeier.pockgit.data.entity.Repo;
import edu.ranken.emeier.pockgit.data.repository.PockGItRepository;
import edu.ranken.emeier.pockgit.ui.CommitNotificationService;
import edu.ranken.emeier.pockgit.ui.activity.MainActivity;

public class PockGitApp extends Application {

    // constants
    private static final String TAG = PockGitApp.class.getSimpleName();
    public static final String AUTHORIZE_URL = "https://github.com/login/oauth/authorize";
    public static final String TOKEN_URL = "https://github.com/login/oauth/access_token";
    public static final String API_BASE_URL = "http://api.github.com";
    public static final String CLIENT_ID = "9764f3fe7e84579b8f56";
    public static final String CLIENT_SECRET = "b2c9c68767df547dd5dab218eda81a7382d634a9";
    public static final String REDIRECT_URL = "pockgit://callback";
    public static final String PRIMARY_CHANNEL_ID = "primary_channel_id";
    public static final int NOTIFICATION_ID = 0;
    public static final int JOB_ID = 0;

    // fields
    public static RequestQueue mRequestQueue;
    private PockGitDatabase mDatabase;
    private PockGItRepository mRepository;
    private NotificationManager mNotifyManager;
    private JobScheduler mScheduler;
    private List<Repo> mRepos;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize fields
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        mRequestQueue = Volley.newRequestQueue(this);
        mDatabase = PockGitDatabase.buildDatabase(this);
        mRepository = new PockGItRepository(this);
        mRepos = mRepository.getRepos().getValue();

        // create notification channel
        createNotificationChannel();

        // schedule the job to run
        scheduleJob();
    }

    public PockGitDatabase getDatabase() { return mDatabase; }
    public PockGItRepository getRepository() { return mRepository; }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel =
                    new NotificationChannel(
                            PRIMARY_CHANNEL_ID,
                            "Repository Notifications",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            channel.setDescription("Notifications that occur when there is either a new commit or pull request.");
            mNotifyManager.createNotificationChannel(channel);
        }
    }

    public void scheduleJob() {
        ComponentName serviceName =
                new ComponentName(getPackageName(), CommitNotificationService.class.getName());

        JobInfo.Builder builder =
                new JobInfo.Builder(JOB_ID, serviceName)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPeriodic(15 * 60 * 1000)
                        .setPersisted(true);

        mScheduler.schedule(builder.build());
    }

    public void sendNotification(Commit commit) {
        // check if the commit already exists in the database
        if (mRepository.getCommitById(commit.getId()) != null) {
            return;
        }

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingContextIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("There is a new commit in one of your repositories!")
                .setContentText(String.format("%s : %s", commit.getCommitterName(), commit.getCommitMessage()))
                .setContentIntent(pendingContextIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
    }
}
