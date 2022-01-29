package com.HaiHa.ChefAssistant.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HaiHa.ChefAssistant.activities.DetailActivity;
import com.HaiHa.ChefAssistant.Helper;
import com.HaiHa.ChefAssistant.models.Food.Food;
import com.HaiHa.ChefAssistant.models.Food.FoodAdapter;
import com.HaiHa.ChefAssistant.models.Food.HomeFragmentViewModel;
import com.unity3d.player.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements FoodAdapter.OnItemListener{
    public HomeFragmentViewModel homeViewModel;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter ;
    private Spinner dropdown;

    private Helper.Type typeSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        typeSearch = Helper.Type.TYPE_NAME;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeFragmentViewModel.class);
        recyclerView = view.findViewById(R.id.rview);

        ///
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),
                                                                android.R.layout.simple_spinner_dropdown_item,
                                                                getResources().getStringArray(R.array.spinner_filter));
        dropdown = view.findViewById(R.id.spinner);
        dropdown.setAdapter(spinnerAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeSearch = Helper.Type.values()[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                typeSearch = Helper.Type.values()[0];
            }
        });
        ///

        ///
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                sendAndFilter(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        ///
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
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", foodAdapter.GetAt(position).id);
        startActivity(intent);
    }
    private void sendAndFilter(String newText) {
        // creating a new array list to send and filter our data.
        ArrayList<Food> searchedFood = new ArrayList<>();
        Helper.SendAndFilter(getContext(), newText, typeSearch, new Helper.GetFoodsCallBack() {
            @Override
            public void onSuccess(ArrayList<Food> foods) {
                foodAdapter.SetFoods(foods);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getContext(), "No data found...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}