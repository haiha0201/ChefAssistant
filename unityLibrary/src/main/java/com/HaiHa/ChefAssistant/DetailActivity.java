package com.HaiHa.ChefAssistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.HaiHa.ChefAssistant.models.Food.Food;
import com.HaiHa.ChefAssistant.models.Food.FoodViewModel;
import com.HaiHa.ChefAssistant.models.Reference.Reference;
import com.HaiHa.ChefAssistant.ui.detailedRecipe.IngredientsFragment;
import com.HaiHa.ChefAssistant.ui.detailedRecipe.InstructionFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.unity3d.player.R;

public class DetailActivity extends AppCompatActivity {
    private FoodViewModel foodViewModel;

    private FirebaseFirestore mbase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        InitDB();
        BindData();
        BindNav();
    }
    void InitDB()
    {
        mbase = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
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

        FloatingActionButton button = (FloatingActionButton)  findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check whether it already existed
                String mealID = foodViewModel.getSelectedItem().getValue().id;
                mbase.collection("Favorites")
                        .whereEqualTo("userID", mAuth.getUid().toString())
                        .whereEqualTo("mealID", mealID)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if (!queryDocumentSnapshots.isEmpty())
                                {
                                    Toast.makeText(getApplicationContext(), "Already in your favorite meals", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Add();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                            }
                        });
    }
    public void Add()
    {
        String mealID = foodViewModel.getSelectedItem().getValue().id;
        mbase.collection("Favorites").add(new Reference(mAuth.getUid(), mealID))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                    }
                });
    }
        });
    }
    void BindData()
    {
        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        Log.d("Detailed activity", "Looking for food");
        String id = (String) getIntent().getSerializableExtra("id");
        Helper.GetFoodById(getApplicationContext(), id, true, new Helper.GetFoodCallBack()
        {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText( getApplicationContext(),
                        e.toString(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(Food food) {
                foodViewModel.setFood(food);
                Log.d("Main Fragment", "i got called: " + foodViewModel.getSelectedItem().getValue().mealName.toString());
            }
        });
    }
}