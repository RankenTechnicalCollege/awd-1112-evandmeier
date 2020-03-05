package edu.ranken.emeier.gittogo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.ranken.emeier.gittogo.models.Repo;

public class NetworkUtils {

    public static ArrayList<Repo> getRepoList(Context context, RequestQueue queue) {
        ArrayList<Repo> repoList = new ArrayList<>();

        String url ="https://api.github.com/users/evandmeier/repos";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                (JSONArray response) -> {
                    try {
                        // Loop through the array elements
                        for(int i=0;i<response.length();i++){
                            // Get current json object
                            JSONObject currentRepo = response.getJSONObject(i);

                            // Get the current repo (json object) data
                            String name = currentRepo.getString("name");
                            String owner = currentRepo.getJSONObject("owner").getString("login");
                            String description = currentRepo.getString("description");

                            // Parse JSON data into Repo object
                            Repo repo = new Repo(name, owner, description);
                            repoList.add(repo);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context, "ERROR OCCURRED!", Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", e.getMessage());
                    }
                },
                (VolleyError error) -> {
                    // Do something when error occurred
                    Toast.makeText(context, "VOLLEY ERROR OCCURRED!", Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", error.getMessage());
                });

        queue.add(request);

        return repoList;
    }
}
