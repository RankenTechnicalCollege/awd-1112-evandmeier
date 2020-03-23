package edu.ranken.emeier.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.ranken.emeier.finalproject.service.ApiService;
import edu.ranken.emeier.finalproject.service.IArrayCallback;
import edu.ranken.emeier.finalproject.service.IObjectCallback;

public class MainActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getSimpleName();

    // api info
    private ApiService mApiService;
    private IObjectCallback mUserListCallback;

    // widgets
    private TextView mUserLogin;
    private CircleImageView mUserAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get widgets
        mUserLogin = findViewById(R.id.text_user_login);
        mUserAvatar = findViewById(R.id.image_user_image);

        // initialize callbacks
        initializeUserListCallback();

        // initialize ApiService
        mApiService = new ApiService(this, mUserListCallback);

        mApiService.getJsonObject("TEST REQUEST", getUserSearchUrl("evandmeier"));
    }

    public void initializeUserListCallback() {
        mUserListCallback = new IObjectCallback() {
            @Override
            public void onSuccessResult(String requestName, JSONObject response) {
                Log.d(TAG, "Making Volley Request: " + requestName);

                try {
                    JSONArray items = response.getJSONArray("items");

                    JSONObject user = items.getJSONObject(0);
                    String login = user.getString("login");
                    String avatarUrl = user.getString("avatar_url");

                    mUserLogin.setText(login);
                    Picasso.get().load(avatarUrl).resize(100, 100).into(mUserAvatar);

                } catch (JSONException e) {
                    Log.d(TAG, "JSON EXCEPTION: " + requestName);
                }

                Log.d(TAG, "Successfully parsed JSON!");
            }

            @Override
            public void onErrorResult(String requestName, VolleyError error) {
                Log.d(TAG, "Failed to make JSON request for " + requestName);
            }
        };
    }

    private String getUserSearchUrl(String username) {
        return "https://api.github.com/search/users?q=" + username;
    }
}
