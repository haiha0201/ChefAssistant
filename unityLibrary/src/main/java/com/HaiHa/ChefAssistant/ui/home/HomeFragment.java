package com.HaiHa.ChefAssistant.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.HaiHa.ChefAssistant.models.Food.Food;
import com.HaiHa.ChefAssistant.models.Food.FoodAdapter;
import com.unity3d.player.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements FoodAdapter.OnItemListener{
    public HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter ;

    private final static int TYPE_NAME = 1;
    private final static int TYPE_AREA = 2;
    private final static int TYPE_INGREDIENT = 3;
    private int typeSearch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        typeSearch=1;
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
                filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
    private void filter(String newText) {
        // creating a new array list to filter our data.
        ArrayList<Food> searchedFood = new ArrayList<>();
//        for (Food item : foods) {
//            String itemAtri=item.mealName;
//            if(typeSearch==2)
//                itemAtri= item.mealArea;
//            // checking if the entered string matched with any item of our recycler view.
//            if (itemAtri.toLowerCase().contains(newText.toLowerCase())) {
//                // if the item is matched we are
//                // adding it to our filtered list.
//                searchedFood.add(item);
//            }
//        }
//        if (searchedFood.isEmpty()) {
//            // if no item is added in filtered list we are
//            // displaying a toast message as no data found.
//            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
//        } else {
//            // at last we are passing that filtered
//            // list to our adapter class.
//            foodAdapter.filterList(searchedFood);
//        }
    }
}