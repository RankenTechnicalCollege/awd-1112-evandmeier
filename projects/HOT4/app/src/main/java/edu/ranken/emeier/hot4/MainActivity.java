package edu.ranken.emeier.hot4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.ranken.emeier.hot4.adapters.ArticleListAdapter;
import edu.ranken.emeier.hot4.models.Article;

public class MainActivity extends AppCompatActivity {

    // constants
    private final String TAG = MainActivity.class.getSimpleName();

    // widgets
    private RecyclerView mRecyclerView;

    // fields
    private ArrayList<Article> mArticleList;
    private ArticleListAdapter mArticleListAdapter;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize request queue
        mRequestQueue = Volley.newRequestQueue(this);

        // bind widgets
        mRecyclerView = findViewById(R.id.recyclerview);

        // initialize fields
        mArticleListAdapter = new ArticleListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mArticleListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getArticles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRequestQueue.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getArticles();
                showToast(getString(R.string.refreshed));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getArticles() {
        mArticleList = new ArrayList<>();
                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.GET,
                            TechNewsApp.GET_URL,
                            null,
                            (JSONObject response) -> {
                                try {
                                    JSONArray items = response.getJSONArray("items");
                                    for (int i = 0; i < items.length(); i++) {
                                        JSONObject item = items.getJSONObject(i);
                                        String imageUrl;
                                        try {
                                            imageUrl = item.getString("image");
                                        } catch (Exception e) {
                                            // if the there is not an image associated with a given article
                                            imageUrl = TechNewsApp.DEFAULT_IMAGE;
                                            Log.d(TAG, "Using default image at index " + i);
                                        }

                                        Article article = new Article(
                                                item.getString("title"),
                                                item.getJSONObject("author").getString("name"),
                                                item.getString("summary"),
                                                item.getString("date_published"),
                                                item.getString("url"),
                                                imageUrl
                                        );

                                        mArticleList.add(article);
                                    }
                                } catch (JSONException e) {
                                    showToast(getString(R.string.failed));
                                    Log.e(TAG, e.getMessage());
                                }

                    // update the UI with the new articles
                    mArticleListAdapter.setArticles(mArticleList);
                },
                (VolleyError error) -> {
                    showToast(getString(R.string.failed));
                    Log.e(TAG, error.getMessage());
                });

        mRequestQueue.add(request);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
