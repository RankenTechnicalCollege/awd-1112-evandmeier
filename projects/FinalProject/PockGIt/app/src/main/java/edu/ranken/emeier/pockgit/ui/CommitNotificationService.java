package edu.ranken.emeier.pockgit.ui;

import android.app.job.JobParameters;
import android.app.job.JobService;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.ranken.emeier.pockgit.PockGitApp;
import edu.ranken.emeier.pockgit.data.entity.Commit;
import edu.ranken.emeier.pockgit.data.entity.Repo;
import edu.ranken.emeier.pockgit.data.repository.PockGItRepository;

public class CommitNotificationService extends JobService {

    // fields
    private PockGItRepository mRepository;
    private LiveData<List<Repo>> mRepos;

    @Override
    public void onCreate() {
        super.onCreate();

        PockGitApp app = (PockGitApp) getApplication();
        mRepository = app.getRepository();
        mRepos = mRepository.getRepos();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        try {
            List<Repo> repos = mRepository.getRepos().getValue();
            for (Repo repo : repos) {
                if (repo.isCommitNotificationsEnabled()) {
                    mRepository.refreshCommitsForRepo(repo);
                }
            }
        } catch(Exception e) {
            // in this case, the application has just loaded and therefore doesn't have the full list of repos yes
        }

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}