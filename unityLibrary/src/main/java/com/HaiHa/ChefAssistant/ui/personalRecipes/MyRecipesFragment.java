package com.HaiHa.ChefAssistant.ui.personalRecipes;

import android.app.SearchManager;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HaiHa.ChefAssistant.Helper;
import com.HaiHa.ChefAssistant.activities.DetailActivity;
import com.HaiHa.ChefAssistant.models.Food.Food;
import com.HaiHa.ChefAssistant.models.Food.FoodAdapter;
import com.HaiHa.ChefAssistant.models.Food.PersonalRecipesViewModel;
import com.unity3d.player.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class MyRecipesFragment extends Fragment  implements FoodAdapter.OnItemListener{
    public PersonalRecipesViewModel favoriteFoodsViewModel;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter ;

    private SimpleCursorAdapter mAdapter;

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

        ///
        String[] from = new String[] {"cityName"};
        int[] to = new int[] {android.R.id.text1};

        mAdapter = new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        ///

        ///
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filter(searchView.getQuery().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        ///

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

    void filter(CharSequence query) {
        String loweredCase = query.toString().toLowerCase(Locale.ROOT);
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });

        Predicate<Food> pre = food -> (food != null && food.mealName.toLowerCase().contains(loweredCase));
        List<Food> results = foodAdapter.GetFoods()
                    .stream().filter(pre)
                    .collect(Collectors.toList());

        for (int i=0; i<results.size(); i++) {
            c.addRow(new Object[] {i, results.get(i).mealName});
        }

        mAdapter.changeCursor(c);
    }
}