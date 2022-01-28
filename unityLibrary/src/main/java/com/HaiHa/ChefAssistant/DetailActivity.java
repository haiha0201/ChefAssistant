package com.HaiHa.ChefAssistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.HaiHa.ChefAssistant.models.Food.Food;
import com.HaiHa.ChefAssistant.models.Food.FoodViewModel;
import com.HaiHa.ChefAssistant.ui.detailedRecipe.IngredientsFragment;
import com.HaiHa.ChefAssistant.ui.detailedRecipe.InstructionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.unity3d.player.R;

public class DetailActivity extends AppCompatActivity {
    private FoodViewModel foodViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        BindData();
        BindNav();
    }
    void BindNav()
    {
        Fragment ingre = new IngredientsFragment();
        Fragment instruct = new InstructionFragment();
        Fragment none = new InstructionFragment();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment = null;
                if (id == R.id.navigation_ingredients)
                {
                    fragment = ingre;
                }
                else if (id == R.id.navigation_instructions)
                {
                    fragment = instruct;
                }
                else if (id == R.id.navigation_unity)
                {
                    fragment = ingre;
                }
                if (fragment != null) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.fragment_container_view, fragment).commit();
                    return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_ingredients);
    }
    void BindData()
    {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        Log.d("Detailed activity", "Looking for food");
        TestGetFood();
    }
    void TestGetFood()
    {
        foodViewModel.setFood((Food)getIntent().getSerializableExtra("food"));

//        Helper.GetRandomFood(getApplicationContext(), new Helper.VolleyCallBack()
//        {
//            @Override
//            public void onFailure(Exception e) {
//                Toast.makeText( getApplicationContext(),
//                        e.toString(),
//                        Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onSuccess(Food food) {
//                foodViewModel.setFood(food);
//                Log.d("Main Fragment", "i got called: " + foodViewModel.getSelectedItem().getValue().mealName.toString());
//            }
//        });
    }
}