package edu.ranken.emeier.pockgit.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.ranken.emeier.pockgit.PockGitApp;
import edu.ranken.emeier.pockgit.R;
import edu.ranken.emeier.pockgit.data.entity.Commit;
import edu.ranken.emeier.pockgit.data.entity.Repo;
import edu.ranken.emeier.pockgit.data.repository.PockGItRepository;
import edu.ranken.emeier.pockgit.ui.adapter.CommitListAdapter;
import edu.ranken.emeier.pockgit.ui.adapter.RepoListAdapter;
import edu.ranken.emeier.pockgit.utils.Utilities;

public class RepositoryDetailsActivity extends AppCompatActivity {

    // constants
    private static final String TAG = RepositoryDetailsActivity.class.getSimpleName();

    // widgets
    private TextView mTextRepoOwner, mTextRepoDesc;
    private ImageView mImageRepoAvatar;
    private EditText mInputSearch;

    // fields
    private PockGItRepository mRepository;
    private Repo mRepo;
    private CommitListAdapter mAdapter;
    private LiveData<List<Commit>> mCommits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);

        // initialize fields
        PockGitApp app = (PockGitApp) getApplication();
        mRepository = app.getRepository();

        // load in the data from the intent
        Intent intent = getIntent();
        int id = intent.getIntExtra(RepoListAdapter.EXTRA_REPO_ID, -999);

        if (id == -999) {
            // do something
        } else {
            mRepo = mRepository.getRepoById(id);
            mRepository.refreshCommitsForRepo(mRepo);
            mCommits = mRepository.getCommmitsByRepoId(mRepo.getId());

            getSupportActionBar().setTitle(mRepo.getFullName());
        }

        // bind widgets
        mTextRepoOwner = findViewById(R.id.text_repo_owner);
        mTextRepoDesc = findViewById(R.id.text_repo_desc);
        mImageRepoAvatar = findViewById(R.id.image_owner_avatar);
        mInputSearch = findViewById(R.id.input_commit_search);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // setup RecyclerView
        mAdapter = new CommitListAdapter(this, mCommits.getValue());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        // set the values of the views
        mTextRepoOwner.setText(mRepo.getOwnerName());
        if (mRepo.getDescription().equals("null")) {
            mTextRepoDesc.setText("You have not provided a description for this repository!");
        } else {
            mTextRepoDesc.setText(mRepo.getDescription());
        }

        if (TextUtils.isEmpty(mRepo.getOwnerAvatar())) {
            mImageRepoAvatar.setImageResource(R.drawable.placeholder);
        } else {
            Picasso.get().load(mRepo.getOwnerAvatar()).resize(100, 100).into(mImageRepoAvatar);
        }

        // listen for data to change via LiveData and an observer
        mCommits.observe(this, (List<Commit> commits) -> {
            mAdapter.setCommits(commits);
        });
    }

    public void searchCommits(View view) {
        List<Commit> commits = mCommits.getValue();
        List<Commit> results = new ArrayList<>();
        if (!TextUtils.isEmpty(mInputSearch.getText())) {
            String query = mInputSearch.getText().toString().toLowerCase();

            for (Commit commit : commits) {
                if (commit.getCommitterName().toLowerCase().contains(query) ||
                    commit.getCommitMessage().toLowerCase().contains(query)) {
                    results.add(commit);
                }
            }

            mAdapter.setCommits(results);
        } else {
            mAdapter.setCommits(commits);
        }
    }

    public void refreshCommits(View view) {
        mRepository.refreshCommitsForRepo(mRepo);
        Utilities.showToast(this, String.format("Commits for '%s' have been refreshed!", mRepo.getFullName()));
    }
}
