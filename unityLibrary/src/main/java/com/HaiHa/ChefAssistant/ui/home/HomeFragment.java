package com.HaiHa.ChefAssistant.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HaiHa.ChefAssistant.DetailActivity;
import com.HaiHa.ChefAssistant.models.Food.FoodAdapter;
import com.unity3d.player.R;


public class HomeFragment extends Fragment implements FoodAdapter.OnItemListener{
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

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        recyclerView = view.findViewById(R.id.rview);

        foodAdapter = new FoodAdapter(getContext(), homeViewModel.getSelectedItem().getValue(), this);
        homeViewModel.getSelectedItem().observe(requireActivity(), foods -> {
            foodAdapter.SetFoods(foods);
        });
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return view;
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getContext(), "Get food at", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("food", foodAdapter.GetAt(position));
        startActivity(intent);
    }
}