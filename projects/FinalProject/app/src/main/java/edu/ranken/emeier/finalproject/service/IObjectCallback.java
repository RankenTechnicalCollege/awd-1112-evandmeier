package edu.ranken.emeier.finalproject.service;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface IObjectCallback {
    void onSuccessResult(String requestName, JSONObject response);
    void onErrorResult(String requestName, VolleyError error);
}
