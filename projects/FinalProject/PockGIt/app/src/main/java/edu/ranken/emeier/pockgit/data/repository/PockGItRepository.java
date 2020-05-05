package edu.ranken.emeier.pockgit.data.repository;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ranken.emeier.pockgit.PockGitApp;
import edu.ranken.emeier.pockgit.data.PockGitDatabase;
import edu.ranken.emeier.pockgit.data.entity.Account;
import edu.ranken.emeier.pockgit.data.entity.Commit;
import edu.ranken.emeier.pockgit.data.entity.Repo;

import static android.util.Log.e;

public class PockGItRepository {

    // constants
    private static final String TAG = PockGItRepository.class.getSimpleName();

    private PockGitApp mApp;
    private PockGitDatabase mDatabase;
    private LiveData<List<Account>> mAccounts;
    private LiveData<List<Repo>> mRepos;
    private LiveData<List<Commit>> mCommits;

    public PockGItRepository(PockGitApp app) {
        mApp = app;
        mDatabase = app.getDatabase();
        mAccounts = mDatabase.getAccountDao().getAllAccounts();
        mRepos = mDatabase.getRepoDao().getAllRepos();
        mCommits = mDatabase.getCommitDao().getAllCommits();
    }

    public LiveData<List<Account>> getAccounts() {
        return mAccounts;
    }

    public LiveData<List<Repo>> getRepos() {
        return mRepos;
    }

    public LiveData<List<Commit>> getCommits() {
        return mCommits;
    }

    public Account getAccountByLogin(String login) {
        try {
            return new GetAccountByLoginTask().execute(login).get();
        } catch (Exception e) {
            return null;
        }
    }

    public LiveData<List<Commit>> getCommmitsByRepoId(int repoId) {
        return mDatabase.getCommitDao().getCommitsByRepoId(repoId);
    }

    public Repo getRepoById(int id) {
        try {
            return new GetRepoByIdTask().execute(id).get();
        } catch(Exception e) {
            return null;
        }
    }

    public void updateRepo(Repo repo) {
        new UpdateRepositoryTask().execute(repo);
    }

    public void deleteRepoByOwnerId(int ownerId) {
        new DeleteReposByOwnerIdTask().execute(ownerId);
    }

    public void deleteCommitsByRepoId(int repoId) {

    }

    /**
     *
     * @param code
     */
    public void linkAccount(String code) {
        Uri uri = Uri.parse(PockGitApp.TOKEN_URL)
                .buildUpon()
                .appendQueryParameter("client_id", PockGitApp.CLIENT_ID)
                .appendQueryParameter("client_secret", PockGitApp.CLIENT_SECRET)
                .appendQueryParameter("code", code)
                .build();

        String url = uri.toString();

        PockGitApp.mRequestQueue.add(new StringRequest(
                Request.Method.GET,
                url,
                (String response) -> {
                    String accessToken = response.substring(13, response.indexOf('&'));
                    Log.i(TAG, response);

                    // get user information based on accessToken
                    getAccountInfo(accessToken);
                },
                (VolleyError error) -> {
                    e(TAG, "VOLLEY ERROR (GetAccessToken)", error);
                }
        ));
    }

    public Commit getCommitById(String id) {
        try {
            return new GetCommitByIdTask().execute(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    public void refreshCommitsForRepo(Repo repo) {
        Uri uri = Uri.parse(PockGitApp.API_BASE_URL)
                .buildUpon()
                .scheme("https")
                .path("repos/" + repo.getOwnerName() + "/" + repo.getName() + "/commits")
                .build();

        String url = uri.toString();

        PockGitApp.mRequestQueue.add(new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                (JSONArray response) -> {
                    new ProcessCommitsThread(response, repo).start();
                },
                (VolleyError error) -> {
                    Log.e(TAG, "VOLLEY ERROR", error);
                }
        ));
    }

    public void unlinkAccount(Account account) {
        List<Repo> repos;

        try {
            repos = new GetReposByOwnerIdTask().execute(account.getId()).get();

            // loop through all the repos for the given account
            for (Repo repo : repos) {
                // delete all commits for the current repo
                new DeleteCommitByRepoIdTask().execute(repo.getId());
            }

            // delete all repos for the given account
            new DeleteReposByOwnerIdTask().execute(account.getId());

            // finally, delete the account
            new DeleteAccountByIdTask().execute(account.getId());
        } catch (Exception e) {

        }
    }

    public void refreshRepos(String login) {
        Account account = getAccountByLogin(login);

        // populate repos table for the given user
        Uri uri = Uri.parse(PockGitApp.API_BASE_URL)
                .buildUpon()
                .scheme("https")
                .path("user/repos")
                .build();

        String url = uri.toString();

        PockGitApp.mRequestQueue.add(new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                (JSONArray response) -> {
                    new ProcessReposThread(account.getAccessToken(), response, account).start();
                },
                (VolleyError error) -> {
                    e(TAG, error.toString(), error);
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "token " + account.getAccessToken());

                return headers;
            }
        });
    }

    private void getAccountInfo(String accessToken) {
        Uri uri = Uri.parse(PockGitApp.API_BASE_URL)
                .buildUpon()
                .scheme("https")
                .path("user")
                .build();

        String url = uri.toString();

        PockGitApp.mRequestQueue.add(new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (JSONObject response) -> {
                    new ProcessAccountThread(accessToken, response).start();
                },
                (VolleyError error) -> {
                    e(TAG, error.toString());
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "token " + accessToken);

                return headers;
            }
        });
    }

    // Threads
    private class ProcessAccountThread extends Thread {
        private String mAccessToken;
        private JSONObject mResponse;

        ProcessAccountThread(String accessToken, JSONObject response) {
            mAccessToken = accessToken;
            mResponse = response;
        }

        @Override
        public void run() {
            super.run();

            try {
                int id = mResponse.getInt("id");
                String login = mResponse.getString("login");
                String name = mResponse.getString("name");
                String avatarUrl = mResponse.getString("avatar_url");

                Account account = new Account(id, login, name, avatarUrl, mAccessToken);

                // check if the account has already been linked
                Account existingAccount = mDatabase.getAccountDao().getAccountById(account.getId());
                if (existingAccount == null) {
                    mDatabase.getAccountDao().insert(account);

                    Log.i(TAG, "Inserted new account to database");

                    // populate repos table for the given user
                    Uri uri = Uri.parse(PockGitApp.API_BASE_URL)
                            .buildUpon()
                            .scheme("https")
                            .path("user/repos")
                            .build();

                    String url = uri.toString();

                    PockGitApp.mRequestQueue.add(new JsonArrayRequest(
                            Request.Method.GET,
                            url,
                            null,
                            (JSONArray response) -> {
                                new ProcessReposThread(mAccessToken, response, account).start();
                            },
                            (VolleyError error) -> {
                                e(TAG, error.toString(), error);
                            }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/json");
                            headers.put("Authorization", "token " + mAccessToken);

                            return headers;
                        }
                    });
                } else {
                    // TODO: NOTIFY USERS
                }
            } catch (Exception e) {
                e(TAG, "Failed to link GitHub account.", e);
            }
        }
    }

    private class ProcessReposThread extends Thread {
        private String mAccessToken;
        private JSONArray mResponse;
        private Account mAccount;

        ProcessReposThread(String accessToken, JSONArray response, Account account) {
            mAccessToken = accessToken;
            mResponse = response;
            mAccount = account;
        }

        @Override
        public void run() {
            super.run();

            for (int i = 0; i < mResponse.length(); i++) {
                try {
                    JSONObject currentRepo = mResponse.getJSONObject(i);
                    int id = currentRepo.getInt("id");
                    String name = currentRepo.getString("name");
                    String fullName = currentRepo.getString("full_name");
                    String description = currentRepo.getString("description");
                    int ownerId = currentRepo.getJSONObject("owner").getInt("id");
                    String ownerType = currentRepo.getJSONObject("owner").getString("type");
                    String ownerLogin = currentRepo.getJSONObject("owner").getString("login");

                    Repo existingRepo = getRepoById(id);

                    if (ownerId == mAccount.getId()) {
                        if (existingRepo == null) {
                            Repo repo = new Repo(id, mAccount.getId(), name, fullName, description, ownerLogin, mAccount.getAvatarUrl());
                            mDatabase.getRepoDao().insert(repo);
                        }
                    } else {
                        if (ownerType.equals("User")) {
                            String url = "https://api.github.com/users/" + ownerLogin;

                            // make API call to get user avatar
                            PockGitApp.mRequestQueue.add(new JsonObjectRequest(
                                    Request.Method.GET,
                                    url,
                                    null,
                                    (JSONObject response) -> {
                                        String ownerAvatarUrl;
                                        try {
                                            ownerAvatarUrl = response.getString("avatar_url");
                                        } catch (JSONException e) {
                                            ownerAvatarUrl = "";
                                        }

                                        if (existingRepo == null) {
                                            new InsertRepoTask().execute(new Repo(id, mAccount.getId(), name, fullName, description, ownerLogin, ownerAvatarUrl));
                                        }
                                    },
                                    (VolleyError error) -> {
                                        Log.i(TAG, "VOLLEY ERROR!");
                                    }
                            ));
                        } else {
                            // make API call to get organization avatar
                            //TODO: make API call to get the organization's avatar
                            mDatabase.getRepoDao().insert(new Repo(id, mAccount.getId(), name, fullName, description, ownerLogin, ""));
                        }
                    }

                } catch (JSONException e) {
                    // TODO: NOTIFY USERS
                    e(TAG, "Failed to get repo at index: " + i, e);
                }
            }
        }
    }

    private class ProcessCommitsThread extends Thread {

        private JSONArray mResponse;
        private Repo mRepo;

        ProcessCommitsThread(JSONArray response, Repo repo) {
            mResponse = response;
            mRepo = repo;
        }

        @Override
        public void run() {
            super.run();

            for (int i = 0; i < mResponse.length(); i++) {
                try {
                    JSONObject currentCommit = mResponse.getJSONObject(i);

                    String id = currentCommit.getString("sha");
                    if (mDatabase.getCommitDao().getCommitById(id) == null) {
                        String committerName = currentCommit.getJSONObject("author").getString("login");
                        String commitDate = currentCommit.getJSONObject("commit").getJSONObject("author").getString("date");
                        String commitMessage = currentCommit.getJSONObject("commit").getString("message");
                        String authorAvatar = currentCommit.getJSONObject("author").getString("avatar_url");

                        Commit commit = new Commit(id, mRepo.getId(), committerName, commitDate, commitMessage, authorAvatar);
                        mDatabase.getCommitDao().insert(commit);

                        mApp.sendNotification(commit);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "ERROR", e);
                }
            }
        }
    }

    //Tasks
    private class GetAccountByLoginTask extends AsyncTask<String, Void, Account> {

        @Override
        protected Account doInBackground(String[] params) {
            return mDatabase.getAccountDao().getAccountByLogin(params[0]);
        }
    }

    private class InsertRepoTask extends AsyncTask<Repo, Void, Void> {

        @Override
        protected Void doInBackground(Repo[] params) {
            mDatabase.getRepoDao().insert(params[0]);
            return null;
        }
    }

    private class GetRepoByIdTask extends AsyncTask<Integer, Void, Repo> {

        @Override
        protected Repo doInBackground(Integer[] params) {
            return mDatabase.getRepoDao().getRepoById(params[0]);
        }
    }

    private class GetReposByOwnerIdTask extends AsyncTask<Integer, Void, List<Repo>> {

        @Override
        protected List<Repo> doInBackground(Integer[] params) {
            return mDatabase.getRepoDao().getRepoByOwnerId(params[0]);
        }
    }

    private class GetCommitByIdTask extends AsyncTask<String, Void, Commit> {

        @Override
        protected Commit doInBackground(String[] params) {
            return mDatabase.getCommitDao().getCommitById(params[0]);
        }
    }

    private class UpdateRepositoryTask extends AsyncTask<Repo, Void, Void> {

        @Override
        protected Void doInBackground(Repo[] params) {
            mDatabase.getRepoDao().updateRepo(params[0]);
            return null;
        }
    }

    private class DeleteAccountByIdTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer[] params) {
            mDatabase.getAccountDao().deleteAccountById(params[0]);
            return null;
        }
    }

    private class DeleteReposByOwnerIdTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer[] params) {
            mDatabase.getRepoDao().deleteReposByOwnerId(params[0]);
            return null;
        }
    }

    private class DeleteCommitByRepoIdTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer[] params) {
            mDatabase.getCommitDao().deleteCommitByRepoId(params[0]);
            return null;
        }
    }
}
