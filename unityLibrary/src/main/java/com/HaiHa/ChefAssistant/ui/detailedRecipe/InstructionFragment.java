package com.HaiHa.ChefAssistant.ui.detailedRecipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HaiHa.ChefAssistant.Helper;
import com.HaiHa.ChefAssistant.models.Food.FoodViewModel;
import com.HaiHa.ChefAssistant.models.Ingredient.DirectionRecylerViewAdapter;
import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;
import com.HaiHa.ChefAssistant.models.Ingredient.IngredientAdapter;
import com.unity3d.player.R;

public class InstructionFragment extends Fragment {
    public FoodViewModel foodViewModel;
    DirectionRecylerViewAdapter directionRecylerViewAdapter;
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scrolling, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rview);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView name = (TextView) view.findViewById(R.id.ingredientMealNameTextView);
        TextView area = (TextView) view.findViewById(R.id.ingredientMealAreaTextView);


        directionRecylerViewAdapter = new DirectionRecylerViewAdapter(getContext());
        recyclerView.setAdapter(directionRecylerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        foodViewModel = new ViewModelProvider(requireActivity()).get(FoodViewModel.class);
        foodViewModel.getSelectedItem().observe(requireActivity(), food -> {
            if (food != null)
            {
                directionRecylerViewAdapter.SetDirections(food.mealInstruction);

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
    }
}