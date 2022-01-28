package com.HaiHa.ChefAssistant.models.Ingredient;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Ingredient {
    public String name;
    public String measurement;
    public String mealThumb;
    public Bitmap mealThumbBMP;

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
    public static boolean isViable(JSONObject ingre, int index) throws  JSONException
    {
        String temp = ingre.getString(nameFormat + Integer.toString(index));
        Log.d("ERROR", ingre.toString());
        if (temp == null)
        {
            Log.d("FAULTY", "NULL");
            return false;
        }
        if (temp.equals(""))
        {
            Log.d("FAULTY", "Empty");
            return false;
        }
        return true;
    }
}
