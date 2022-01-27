package com.HaiHa.ChefAssistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.HaiHa.ChefAssistant.models.Food;
import com.HaiHa.ChefAssistant.models.FoodAdapter;
import com.unity3d.player.R;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    private ArrayList<Food> foods ;
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = findViewById(R.id.rview);
        foods = new ArrayList<>();
        for (int i =0; i <20; i++)
        {
            Helper.GetRandomFood(getApplicationContext(), new Helper.VolleyCallBack()
            {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText( getApplicationContext(),
                            e.toString(),
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onSuccess(Food food) {
                    foods.add(food);
                    foodAdapter.notifyDataSetChanged();
                }
            });
        }


        foodAdapter = new FoodAdapter(getApplicationContext(), foods);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}