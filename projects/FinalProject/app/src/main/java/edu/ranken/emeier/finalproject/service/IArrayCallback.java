package edu.ranken.emeier.finalproject.service;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface IArrayCallback {
    void onSuccessResult(String requestName, JSONArray response);
    void onErrorResult(String requestName, VolleyError error);
}
