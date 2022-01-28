package com.HaiHa.ChefAssistant.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HaiHa.ChefAssistant.DetailActivity;
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

    private Helper.Type typeSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        typeSearch = Helper.Type.TYPE_NAME;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeFragmentViewModel.class);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuType_Name)
        {
            typeSearch = Helper.Type.TYPE_NAME;
            Toast.makeText(getContext(), "Name Type", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.menuType_Area)
        {
            typeSearch= Helper.Type.TYPE_AREA;
            Toast.makeText(getContext(), "Area Type", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.menuType_Ingredient)
        {
            typeSearch= Helper.Type.TYPE_INGREDIENT;
            Toast.makeText(getContext(), "Ingredient Type", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("id", foodAdapter.GetAt(position).id);
        startActivity(intent);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.action_bar);
        MenuItem filteIten =menu.findItem(R.id.menuType);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Food name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                sendAndFilter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                sendAndFilter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
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