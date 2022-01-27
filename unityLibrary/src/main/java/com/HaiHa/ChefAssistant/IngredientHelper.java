package com.HaiHa.ChefAssistant;

import com.HaiHa.ChefAssistant.models.Food.Food;
import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;

public class IngredientHelper {
    public interface VolleyCallBack {
        void onSuccess(Ingredient ingre);
        void onFailure(Exception e);
    }
}
