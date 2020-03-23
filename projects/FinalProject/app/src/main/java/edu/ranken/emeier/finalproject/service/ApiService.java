package edu.ranken.emeier.finalproject.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiService {
    private Context mContext;
    private IObjectCallback mObjectCallback;
    private IArrayCallback mArrayCallback;

    public ApiService(Context context, IObjectCallback objectCallback) {
        mContext = context;
        mObjectCallback = objectCallback;
    }

    public ApiService(Context context, IArrayCallback arrayCallback) {
        mContext = context;
        mArrayCallback = arrayCallback;
    }

    public void getJsonObject(String requestName, String url) {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                (JSONObject response) -> {
                    if (mObjectCallback != null) {
                        mObjectCallback.onSuccessResult(requestName, response);
                    }
                },
                (VolleyError error) -> {
                    if (mObjectCallback != null) {
                        mObjectCallback.onErrorResult(requestName, error);
                    }
                });

        queue.add(request);
    }

    public void getJsonArray(String requestName, String url) {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                (JSONArray response) -> {
                    if (mArrayCallback != null) {
                        mArrayCallback.onSuccessResult(requestName, response);
                    }
                },
                (VolleyError error) -> {
                    if (mArrayCallback != null) {
                        mArrayCallback.onErrorResult(requestName, error);
                    }
                });

        queue.add(request);
    }
}
