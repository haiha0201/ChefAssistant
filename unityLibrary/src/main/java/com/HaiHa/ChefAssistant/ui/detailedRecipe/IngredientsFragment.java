package com.HaiHa.ChefAssistant.ui.detailedRecipe;

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
import android.widget.TextView;
import android.widget.Toast;

import com.HaiHa.ChefAssistant.Helper;
import com.HaiHa.ChefAssistant.models.Food.FoodViewModel;
import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;
import com.HaiHa.ChefAssistant.models.Ingredient.IngredientAdapter;
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
        TextView name = (TextView) view.findViewById(R.id.ingredientMealNameTextView);
        TextView area = (TextView) view.findViewById(R.id.ingredientMealAreaTextView);

        ingredientAdapter = new IngredientAdapter(getContext(), new ArrayList<Ingredient>());
        foodViewModel.getSelectedItem().observe(requireActivity(), food -> {
            Log.d("Ingredients fragment", "got food");
            if (food != null) {
                ingredientAdapter.SetIngredients(food.ingredientList);
                try {
                    Helper.SetImage(imageView, food);

                    name.setText(food.mealName);
                    area.setText(food.mealArea);
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
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }
}