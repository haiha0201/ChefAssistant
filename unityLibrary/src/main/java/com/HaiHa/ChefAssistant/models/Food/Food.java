package com.HaiHa.ChefAssistant.models.Food;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;

import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Food {
    public String id;
    public String mealName;
    public String mealArea;
    public String mealThumb;
    public String mealInstruction;
    public Bitmap mealThumbBitmap;
    public ArrayList<Ingredient> ingredientList;
    public static int capacity = 20;
    public Food()
    {

    }
    public Food(JSONObject meal, boolean fullForm) throws JSONException
    {
        id = meal.getString("idMeal");
        mealName = meal.getString("strMeal");
        mealThumb = meal.getString("strMealThumb");
        mealThumbBitmap = null;
        ingredientList = new ArrayList<Ingredient>();
        if (fullForm)
        {
            mealArea = meal.getString("strArea");
            mealInstruction = meal.getString("strInstructions");
            InitialIngredients(meal);
        }
    }
    void InitialIngredients(JSONObject meal) throws JSONException
    {
        for (int i = 1; i <= capacity; i++)
        {
            if (Ingredient.isViable(meal, i))
            {
                ingredientList.add(new Ingredient(meal, i));
            }
        }

    }
}
