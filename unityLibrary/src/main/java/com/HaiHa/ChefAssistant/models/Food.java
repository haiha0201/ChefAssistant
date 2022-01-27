package com.HaiHa.ChefAssistant.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Food {
    public String id;
    public String mealName;
    public String mealArea;
    public String mealThumb;
    public Bitmap mealThumbBitmap;

    public Food(String _id, String _mealName, String _mealArea, String _mealThumb)
    {
        id = _id;
        mealName = _mealName;
        mealArea = _mealArea;
        mealThumb = _mealThumb;
        mealThumbBitmap = null;
    }
    public Food(JSONObject meal) throws JSONException
    {
        id = meal.getString("idMeal");
        mealName = meal.getString("strMeal");
        mealArea = meal.getString("strArea");
        mealThumb = meal.getString("strMealThumb");
        mealThumbBitmap = null;
    }
}
