package edu.ranken.emeier.pockgit.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

import edu.ranken.emeier.pockgit.R;
import edu.ranken.emeier.pockgit.data.entity.Account;
import edu.ranken.emeier.pockgit.PockGitApp;
import edu.ranken.emeier.pockgit.data.repository.PockGItRepository;
import edu.ranken.emeier.pockgit.ui.adapter.AccountListAdapter;

public class MainActivity extends AppCompatActivity {

    // constants
    private static final String TAG = MainActivity.class.getSimpleName();

    // fields
    private PockGItRepository mRepository;
    private AccountListAdapter mAdapter;
    private LiveData<List<Account>> mAccounts;
    private String mOwnerAvatar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        // bind widgets
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // initialize fields
        PockGitApp app = (PockGitApp) getApplication();
        mRepository = app.getRepository();
        mAccounts = mRepository.getAccounts();
        mAdapter = new AccountListAdapter(this, mAccounts.getValue());

        // setup recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        // listen for data changes via LiveData and an Observer
        mAccounts.observe(this, (List<Account> accounts) -> {
            mAdapter.setAccounts(accounts);
        });

        // handle the returning intent from the browser
        Intent intent = getIntent();
        if (intent != null) {
            Uri uri = getIntent().getData();

            if (uri != null && uri.toString().startsWith(PockGitApp.REDIRECT_URL)) {
                String code = uri.getQueryParameter("code");

                mRepository.linkAccount(code);
            }
        }
    }

    public void linkAccount(View view) {
        // build the URI that the user will be directed to
        Uri uri = Uri.parse(PockGitApp.AUTHORIZE_URL)
                    .buildUpon()
                    .appendQueryParameter("client_id", PockGitApp.CLIENT_ID)
                    .appendQueryParameter("scope", "repo")
                    .appendQueryParameter("redirect_url", PockGitApp.REDIRECT_URL)
                    .build();

        // convert the URI into a URL
        String url = uri.toString();

        // start intent to open browser
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}