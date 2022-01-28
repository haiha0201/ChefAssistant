package com.HaiHa.ChefAssistant;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.HaiHa.ChefAssistant.models.Food.Food;
import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Helper {
    public static enum Type
    {
        TYPE_NAME,
        TYPE_AREA,
        TYPE_INGREDIENT
    }

    public interface GetFoodCallBack {
        void onSuccess(Food food);
        void onFailure(Exception e);
    }

    public interface GetFoodsCallBack {
        void onSuccess(ArrayList<Food> food);
        void onFailure(Exception e);
    }
    static String randomURL = "https://www.themealdb.com/api/json/v1/1/random.php";
    static String searchURL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    static String searchByID = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";

    static String searchByArea = "https://www.themealdb.com/api/json/v1/1/filter.php?a=";
    static String searchByIngrdients = "https://www.themealdb.com/api/json/v1/1/filter.php?i=";
    public static void GetRandomFood(Context context, GetFoodCallBack callback)
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
                            callback.onSuccess(new Food(meal, true));
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
    public static void GetFoodById(Context context, String id, GetFoodCallBack callback)
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = searchByID + id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject json = new JSONObject(response);
                            JSONObject meal = json.getJSONArray("meals").getJSONObject(0);
                            callback.onSuccess(new Food(meal, true));
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
    public static void GetFood(Context context, String name, GetFoodCallBack callback)
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
                        callback.onSuccess(new Food(meal, true));
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
    static public void SetImage(ImageView imageView, Food food) throws IOException {
        if (food.mealThumbBitmap == null)
        {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(food.mealThumb);
                        food.mealThumbBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(food.mealThumbBitmap);
                            }
                        });
                    }
                    catch (java.io.IOException e)
                    {
                        Log.e("ERROR", e.toString());
                    }
                }
            });
        }
        else{
            imageView.setImageBitmap(food.mealThumbBitmap);
        }
    }
    public static void SendAndFilter(Context context, String content, Type type, GetFoodsCallBack callback)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = null;
        switch (type)
        {
            case TYPE_AREA:
            {
                url = searchByArea + content;
                break;
            }
            case TYPE_INGREDIENT:
            {
                url = searchByIngrdients + content;
                break;
            }
            default:
            {
                url = searchURL + content;
                break;
            }

        }
        Log.d("Sending", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            ArrayList<Food> result = new ArrayList<Food>();
                            JSONObject json = new JSONObject(response);
                            JSONArray meals = json.getJSONArray("meals");
                            for (int i = 0 ;i <meals.length(); i++)
                            {
                                result.add(new Food(meals.getJSONObject(i), false));
                            }
                            callback.onSuccess(result);
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
