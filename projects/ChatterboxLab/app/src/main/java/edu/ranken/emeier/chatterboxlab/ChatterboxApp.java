package edu.ranken.emeier.chatterboxlab;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ranken.emeier.chatterboxlab.models.Message;

public class ChatterboxApp extends Application {

    // constants
    public static final String TAG = ChatterboxApp.class.getSimpleName();
    public static final String GET_URL = "http://iwt.ranken.edu/ExampleWebServices/Chaterbox/GetMessages";
    public static final String POST_URL = "http://iwt.ranken.edu/ExampleWebServices/Chaterbox/PostMessage";

    // fields
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);
    }

    public ArrayList<Message> getMessages() {
        ArrayList<Message> messages = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                GET_URL,
                null,
                (JSONArray response) -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            Message message = new Message(
                                    response.getJSONObject(i).getString("user"),
                                    response.getJSONObject(i).getString("msg"),
                                    response.getJSONObject(i).getString("time")
                            );

                            Log.d(TAG, message.getUser());
                            messages.add(message);
                        } catch (JSONException e) {
                            Log.e(TAG, String.format("Failed to parse Message at index '%d'.", i));
                        }
                    }
                },
                (VolleyError error) -> {
                    Toast.makeText(this, "Failed to get messages!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Failed to parse JSONArray.");
                }
        );

        mRequestQueue.add(request);

        return messages;
    }

    public void postMessage(String username, String message) {
        try {
            JSONObject params =
                    new JSONObject()
                            .put("user", username)
                            .put("message", message);

            StringRequest request = new StringRequest(
                    Request.Method.POST,
                    POST_URL,
                    (String response) -> {
                        Toast.makeText(this, "Successfully sent message!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, String.format("%s posted '%s'", username, message));
                    },
                    (VolleyError error) -> {
                        Toast.makeText(this, "Failed to send message!", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Failed to send message! VOLLEY");
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("user", username);
                    params.put("message", message);
                    return params;
                }
            };

            mRequestQueue.add(request);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Failed to send message! JSON");
        }
    }
}
