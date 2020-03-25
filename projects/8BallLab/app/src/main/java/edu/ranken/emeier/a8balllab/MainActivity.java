package edu.ranken.emeier.a8balllab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "http://iwt.ranken.edu/ExampleWebServices/Magic8Ball";

    private TextView mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageView = findViewById(R.id.text_message);
    }

    public void getPhrase(View view) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL,
                (String response) -> {
                    mMessageView.setText(response);
                },
                (VolleyError error) -> {
                    mMessageView.setText(R.string.error_message);
                });

        queue.add(request);
    }
}
