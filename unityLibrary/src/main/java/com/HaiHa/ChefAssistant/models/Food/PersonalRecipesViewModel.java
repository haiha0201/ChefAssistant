package com.HaiHa.ChefAssistant.models.Food;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class PersonalRecipesViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Food>> foods = new MutableLiveData<ArrayList<Food>>();
    public void setFoods(ArrayList<Food> items) {
        foods.setValue(items);
    }
    public void addFood(Food food)
    {
        ArrayList<Food> oldList = foods.getValue();
        oldList.add(food);

        foods.setValue(oldList);
    }
    public boolean Contains(Food food)
    {
        return foods.getValue().contains(food);
    }
    public LiveData<ArrayList<Food>> getSelectedItem() {
        return foods;
    }
}