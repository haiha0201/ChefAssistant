package com.HaiHa.ChefAssistant.ui.personalRecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HaiHa.ChefAssistant.DetailActivity;
import com.HaiHa.ChefAssistant.models.Food.FoodAdapter;
import com.HaiHa.ChefAssistant.models.Food.HomeFragmentViewModel;
import com.HaiHa.ChefAssistant.models.Food.PersonalRecipesViewModel;
import com.unity3d.player.R;


public class MyRecipesFragment extends Fragment  implements FoodAdapter.OnItemListener{
    public PersonalRecipesViewModel favoriteFoodsViewModel;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteFoodsViewModel = new ViewModelProvider(requireActivity()).get(PersonalRecipesViewModel.class);
        recyclerView = view.findViewById(R.id.rview);

        foodAdapter = new FoodAdapter(getContext(), favoriteFoodsViewModel.getSelectedItem().getValue(), this);
        favoriteFoodsViewModel.getSelectedItem().observe(requireActivity(), foods -> {
            foodAdapter.SetFoods(foods);
        });
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return view;
        return inflater.inflate(R.layout.fragment_my_recipes, container, false);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", foodAdapter.GetAt(position).id);
        startActivity(intent);
    }
}