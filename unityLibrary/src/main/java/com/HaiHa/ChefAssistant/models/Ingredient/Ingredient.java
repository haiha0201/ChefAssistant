package com.HaiHa.ChefAssistant.models.Ingredient;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient {
    String name;
    String measurement;
    public String mealThumb;
    Bitmap mealThumbBMP;

    static String thumbFormat = "https://www.themealdb.com//images//ingredients//";
    static String nameFormat = "strIngredient";
    static String measurementFormat = "strMeasure";
    public Ingredient(JSONObject ingre, int index) throws JSONException
    {
        name = ingre.getString(nameFormat + Integer.toString(index));
        measurement = ingre.getString(measurementFormat + Integer.toString(index));

        mealThumb = thumbFormat + name + ".png";
        mealThumbBMP = null;
    }
}
