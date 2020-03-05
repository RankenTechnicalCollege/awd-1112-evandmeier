package edu.ranken.emeier.gittogo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.ranken.emeier.gittogo.adapters.RepoAdapter;
import edu.ranken.emeier.gittogo.models.Repo;

public class MainActivity extends AppCompatActivity {

    //constants
    private RequestQueue mQueue;


    //fields
    private ArrayList<Repo> mRepoList;
    private RepoAdapter mAdapter;
    private LoaderManager mLoaderMgr;

    // widgets
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind widgets
        mRecyclerView = findViewById(R.id.recyclerview);

        mQueue = Volley.newRequestQueue(this);
        mRepoList = new ArrayList<>();
    }

    public void makeRepoRequest(View view) {
        String url = "https://api.github.com/users/evandmeier/repos";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                (JSONArray response) -> {
                    try {
                        // Loop through the array elements
                        for(int i = 0; i < response.length(); i++){
                            // Get current json object
                            JSONObject currentRepo = response.getJSONObject(i);

                            // Get the current repo (json object) data
                            String name = currentRepo.getString("name");
                            String owner = currentRepo.getJSONObject("owner").getString("login");
                            String description = currentRepo.getString("description");

                            // Parse JSON data into Repo object
                            mRepoList.add(new Repo(name, owner, description));
                        }

                        mAdapter = new RepoAdapter(this, mRepoList);
                        mRecyclerView.setAdapter(mAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    } catch (JSONException e) {
                        Toast.makeText(this, "ERROR OCCURRED!", Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", e.getMessage());
                    }
                },
                (VolleyError error) -> {
                    // Do something when error occurred
                    Toast.makeText(this, "VOLLEY ERROR OCCURRED!", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", error.getMessage());
                });

        mQueue.add(request);
    }
}