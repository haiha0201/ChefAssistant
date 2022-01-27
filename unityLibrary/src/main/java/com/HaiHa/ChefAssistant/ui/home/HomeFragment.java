package com.HaiHa.ChefAssistant.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HaiHa.ChefAssistant.Helper;
import com.HaiHa.ChefAssistant.models.Food;
import com.HaiHa.ChefAssistant.models.FoodAdapter;
import com.unity3d.player.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    public HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Home Fragment", "i got called");

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        recyclerView = view.findViewById(R.id.rview);

        foodAdapter = new FoodAdapter(getContext(), homeViewModel.getSelectedItem().getValue());
        if (homeViewModel.getSelectedItem().getValue() == null)
        {
            Log.d("ERROR", "rong");
        }
        homeViewModel.getSelectedItem().observe(requireActivity(), foods -> {
            Log.d("Home Fragment", "data changed");
            foodAdapter.SetFoods(foods);
        });
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}