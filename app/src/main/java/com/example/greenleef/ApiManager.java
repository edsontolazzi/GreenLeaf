package com.example.greenleef;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ApiManager {

    public String endpoint = "";
    public String response = "";
    private Context context;
    RequestQueue queue;

    /**
     * Constructor method
     *
     * @param endpoint
     */
    public ApiManager(String endpoint, Context context) {
        this.context = context;
        this.endpoint = endpoint;
        this.queue = Volley.newRequestQueue(context);
    }

    public static JSONObject jsonStringToObject(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        return obj;
    }

    public void getPointsByCpf(HomeActivity activity, String cpf) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.endpoint + "rewards/" + cpf, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = jsonStringToObject(response);
                    activity.setPoints(obj.get("points").toString());
                } catch (Throwable t) {
                    Log.e("GreenLeaf", "Could not parse malformed JSON: \"" + response + "\"");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error-api", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept","application/json");
                params.put("Content-Type","application/json");

                return params;
            }
        };

        this.queue.add(stringRequest);
    }

    public void getReportsByCpf(ListActivity activity, String cpf) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.endpoint + "reports/" + cpf, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray json = new JSONArray(response);
                    activity.setListView(json);
                } catch (Throwable t) {
                    Log.e("GreenLeaf", "Could not parse malformed JSON: \"" + response + "\"");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error-api", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept","application/json");
                params.put("Content-Type","application/json");

                return params;
            }
        };

        this.queue.add(stringRequest);
    }

    public void createReport(String cpf, String items, String nameReport, double lat, double lng, String imageBase64) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, this.endpoint + "report", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // aqui
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error-api", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept","application/json");
                params.put("Content-Type","application/json");

                return params;
            }

            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            public byte[] getBody() throws AuthFailureError {
                JSONObject json = new JSONObject();
                try {
                    json.put("items", items);
                    json.put("name", nameReport);
                    json.put("latitude", lat);
                    json.put("longitude", lng);
                    json.put("image", imageBase64);
                    json.put("cpf", cpf);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return json.toString().getBytes(StandardCharsets.UTF_8);
            }
        };

        this.queue.add(stringRequest);
    }
}
