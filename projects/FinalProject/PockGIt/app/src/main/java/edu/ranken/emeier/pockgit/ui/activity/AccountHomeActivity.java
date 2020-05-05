package edu.ranken.emeier.pockgit.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import edu.ranken.emeier.pockgit.PockGitApp;
import edu.ranken.emeier.pockgit.R;
import edu.ranken.emeier.pockgit.data.entity.Repo;
import edu.ranken.emeier.pockgit.data.repository.PockGItRepository;
import edu.ranken.emeier.pockgit.ui.adapter.AccountListAdapter;
import edu.ranken.emeier.pockgit.ui.adapter.RepoListAdapter;
import edu.ranken.emeier.pockgit.utils.Utilities;

public class AccountHomeActivity extends AppCompatActivity {

    // constants
    private static final String TAG = AccountHomeActivity.class.getSimpleName();

    // fields
    private PockGItRepository mRepository;
    private RepoListAdapter mAdapter;
    private LiveData<List<Repo>> mRepos;
    private String mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_home);

        // set the title of the action bar to the current user's login
        if (getIntent() != null) {
            Intent intent = getIntent();
            mLogin = intent.getStringExtra(AccountListAdapter.EXTRA_USER_LOGIN);
        }

        // bind widgets
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // initialize fields
        PockGitApp app = (PockGitApp) getApplication();
        mRepository = app.getRepository();
        mRepos = mRepository.getRepos();
        mAdapter = new RepoListAdapter(this, mRepos.getValue());

        // setup recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        // listen for data to change via LiveData and an observer
        mRepos.observe(this, (List<Repo> repos) -> {
            mAdapter.setRepos(repos);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mLogin != null) {
            getSupportActionBar().setTitle(mLogin + " - Repositories");
        } else {
            getSupportActionBar().setTitle("Repositories");
        }
    }

    public void refreshRepos(View view) {
        mRepository.refreshRepos(mLogin);
        Utilities.showToast(this, String.format("Repos for '%s' have been updated!", mLogin));
    }
}