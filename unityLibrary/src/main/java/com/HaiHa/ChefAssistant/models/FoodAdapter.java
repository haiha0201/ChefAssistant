package com.HaiHa.ChefAssistant.models;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unity3d.player.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Food> foods;
    public FoodAdapter(Context _context, ArrayList<Food> listFood)
    {
        context = _context;
        foods = listFood;
    }
    public void SetFoods(ArrayList<Food> newFoods)
    {
        foods = newFoods;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View foodView;
        if (viewType == 0)
        {
            foodView = inflater.inflate(R.layout.food_thumbnail, parent, false);
        }
        else
        {
            foodView = inflater.inflate(R.layout.food_thumbnail2, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(foodView);
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foods.get(position);
        try {
            SetImage(holder.imageView, food);
        }
        catch (java.io.IOException e)
        {
            Log.e("ERROR", e.toString());
        }
        holder.name.setText(food.mealName);
        holder.country.setText(food.mealArea);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void SetImage(ImageView imageView, Food food) throws IOException {
        if (food.mealThumbBitmap == null)
        {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(food.mealThumb);
                        food.mealThumbBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(food.mealThumbBitmap);
                            }
                        });
                    }
                    catch (java.io.IOException e)
                    {
                        Log.e("ERROR", e.toString());
                    }
                }
            });
        }
        else{
            imageView.setImageBitmap(food.mealThumbBitmap);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView name;
        public TextView country;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            country = itemView.findViewById(R.id.country);
        }
    }

}
