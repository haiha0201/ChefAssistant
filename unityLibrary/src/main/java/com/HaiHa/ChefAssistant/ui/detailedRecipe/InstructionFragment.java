package com.HaiHa.ChefAssistant.ui.detailedRecipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.HaiHa.ChefAssistant.models.Food.FoodViewModel;
import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;
import com.HaiHa.ChefAssistant.models.Ingredient.IngredientAdapter;
import com.unity3d.player.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class InstructionFragment extends Fragment {
    public FoodViewModel foodViewModel;
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
        TextView content = view.findViewById(R.id.content);

        foodViewModel = new ViewModelProvider(requireActivity()).get(FoodViewModel.class);
        foodViewModel.getSelectedItem().observe(requireActivity(), food -> {
            if (food != null) content.setText(food.mealInstruction);
        });
    }
}