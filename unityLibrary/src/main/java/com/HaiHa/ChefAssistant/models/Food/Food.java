package com.HaiHa.ChefAssistant.models.Food;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Food {
    public String id;
    public String mealName;
    public String mealArea;
    public String mealThumb;
    public Bitmap mealThumbBitmap;
    public ArrayList<Ingredient> ingredientList;
    public static int capacity = 20;
    public Food(JSONObject meal) throws JSONException
    {
        id = meal.getString("idMeal");
        mealName = meal.getString("strMeal");
        mealArea = meal.getString("strArea");
        mealThumb = meal.getString("strMealThumb");
        ingredientList = new ArrayList<Ingredient>(20);
        mealThumbBitmap = null;
    }
    void InitialIngredients(JSONObject meal) throws JSONException
    {
        for (int i = 0; i <capacity; i++)
        {
            ingredientList.add(new Ingredient(meal, i));
        }

    }
}
