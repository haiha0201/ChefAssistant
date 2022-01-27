package com.HaiHa.ChefAssistant;

import android.content.Context;
import android.util.Log;

import com.HaiHa.ChefAssistant.models.Food;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Helper {
    public interface VolleyCallBack {
        void onSuccess(Food food);
        void onFailure(Exception e);
    }
    static String randomURL = "https://www.themealdb.com/api/json/v1/1/random.php";
    static String searchURL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    public static void GetRandomFood(Context context, VolleyCallBack callback)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = randomURL;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            JSONObject meal = json.getJSONArray("meals").getJSONObject(0);
                            callback.onSuccess(new Food(meal));
                        }
                        catch (Exception e)
                        {
                            callback.onFailure(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
            }
        });
        queue.add(stringRequest);
    }
    public static void GetFood(Context context, String name, VolleyCallBack callback)
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = searchURL + name;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try
                    {
                        JSONObject json = new JSONObject(response);
                        JSONObject meal = json.getJSONArray("meals").getJSONObject(0);
                        callback.onSuccess(new Food(meal));
                    }
                    catch (Exception e)
                    {
                        callback.onFailure(e);
                    }
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.toString());
            }
        });
        queue.add(stringRequest);
    }
}
