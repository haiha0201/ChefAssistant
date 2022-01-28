package com.HaiHa.ChefAssistant.ui.detailedRecipe;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.HaiHa.ChefAssistant.Helper;
import com.HaiHa.ChefAssistant.models.Food.FoodAdapter;
import com.HaiHa.ChefAssistant.models.Food.FoodViewModel;
import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;
import com.HaiHa.ChefAssistant.models.Ingredient.IngredientAdapter;
import com.HaiHa.ChefAssistant.ui.home.HomeViewModel;
import com.unity3d.player.R;

import java.util.ArrayList;


public class IngredientsFragment extends Fragment {
    public FoodViewModel foodViewModel;
    private RecyclerView recyclerView;
    private IngredientAdapter ingredientAdapter ;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        foodViewModel = new ViewModelProvider(requireActivity()).get(FoodViewModel.class);
        recyclerView = view.findViewById(R.id.rview);
        ImageView imageView = view.findViewById(R.id.imageView);

        ingredientAdapter = new IngredientAdapter(getContext(), new ArrayList<Ingredient>());
        foodViewModel.getSelectedItem().observe(requireActivity(), food -> {
            Log.d("Ingredients fragment", "got food");
            if (food != null) {
                ingredientAdapter.SetIngredients(food.ingredientList);
                try {
                    Helper.SetImage(imageView, food);
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(), "ERROR: Cannot catch image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(ingredientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_layout, container, false);
    }
}