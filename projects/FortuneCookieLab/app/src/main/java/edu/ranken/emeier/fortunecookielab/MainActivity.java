package edu.ranken.emeier.fortunecookielab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // widgets
    private ImageView mImage;
    private TextView mMessage;
    private TextView mUpdated;

    // fields
    private RequestQueue mRequestQueue;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (FortuneApp.NEW_FORTUNE_ACTION.equals(action)) {
                String message = intent.getStringExtra(FortuneApp.EXTRA_MESSAGE);
                String lastUpdated = intent.getStringExtra(FortuneApp.EXTRA_LAST_UPDATED);

                try {
                    showFortune(message, lastUpdated);
                } catch (Exception e) {
                    mMessage.setText(e.getMessage());
                    mUpdated.setText(R.string.updated_just_now);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind widgets
        mImage = findViewById(R.id.fortune_image);
        mMessage = findViewById(R.id.fortune_message);
        mUpdated = findViewById(R.id.fortune_updated);

        mRequestQueue = Volley.newRequestQueue(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(FortuneApp.NEW_FORTUNE_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();

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
                refreshFortune();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickImage(View view) {
        refreshFortune();
    }

    public void refreshFortune() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, FortuneApp.API_URL, null,
                (JSONObject response) -> {
                    try {
                        String message = response.getString("Fortune");
                        String lastUpdated = response.getString("LastUpdated");

                        showFortune(message, lastUpdated);
                    } catch (Exception e) {
                        mMessage.setText(e.getMessage());
                        mUpdated.setText(R.string.updated_just_now);
                        Toast.makeText(MainActivity.this, R.string.toast_error, Toast.LENGTH_SHORT).show();
                    }
                },
                (VolleyError error) -> {
                    mMessage.setText(error.getMessage());
                    mUpdated.setText(R.string.updated_just_now);
                    Toast.makeText(MainActivity.this, R.string.toast_error, Toast.LENGTH_SHORT).show();
                });

        mRequestQueue.add(request);
        Toast.makeText(this, "Refreshed!", Toast.LENGTH_SHORT).show();
    }

    public void showFortune(String message, String lastUpdated) throws ParseException {
        // 2020-03-26 18:34:41 UTC
        // yyyy-MM-dd HH:mm:ss z
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.US);
        Date lastUpdatedDate = format.parse(lastUpdated);
        long lastUpdatedMillis = lastUpdatedDate.getTime();
        long currentMillis = System.currentTimeMillis();
        long elapsedSeconds = (currentMillis - lastUpdatedMillis) / 1000L;

        mMessage.setText(message);
        mUpdated.setText(getString(R.string.updated_seconds_ago, elapsedSeconds));
        Toast.makeText(MainActivity.this, R.string.toast_refreshed, Toast.LENGTH_SHORT).show();
    }
}