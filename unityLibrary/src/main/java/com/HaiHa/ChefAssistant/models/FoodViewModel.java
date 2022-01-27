package com.HaiHa.ChefAssistant.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodViewModel extends ViewModel {
    private final MutableLiveData<Food> selectedItem = new MutableLiveData<Food>();
    public void selectItem(Food item) {
        selectedItem.setValue(item);
    }
    public LiveData<Food> getSelectedItem() {
        return selectedItem;
    }
}
