package com.HaiHa.ChefAssistant;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.HaiHa.ChefAssistant.models.Food.Food;
import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

//    public static void GrabIngredientImages(Context context, Food food, VolleyCallBack callback)
//    {
//        for (int i = 0 ; i < Food.capacity; i++)
//        {
//            RequestQueue queue = Volley.newRequestQueue(context);
//            Ingredient ingredient = food.ingredientList.get(i);
//            String url = ingredient.mealThumb;
//
//            // Request a string response from the provided URL.
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try
//                            {
//                                JSONObject json = new JSONObject(response);
//                                JSONObject meal = json.getJSONArray("meals").getJSONObject(0);
//                                callback.onSuccess(new Food(meal));
//                            }
//                            catch (Exception e)
//                            {
//                                callback.onFailure(e);
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("ERROR", error.toString());
//                }
//            });
//            queue.add(stringRequest);
//        }
//    }
}
