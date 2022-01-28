package com.HaiHa.ChefAssistant.models.Food;

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

import com.HaiHa.ChefAssistant.Helper;
import com.unity3d.player.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.widget.Toast;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Food> foods;
    private OnItemListener mOnItemListener;

    public FoodAdapter(Context _context, ArrayList<Food> listFood, OnItemListener onItemListener)
    {
        context = _context;
        foods = listFood;
        mOnItemListener=onItemListener;
    }
    public void SetFoods(ArrayList<Food> newFoods)
    {
        foods = newFoods;
        notifyDataSetChanged();
    }
    public Food GetAt(int i)
    {
        return foods.get(i);
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
        ViewHolder viewHolder = new ViewHolder(foodView, mOnItemListener);
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
            Helper.SetImage(holder.imageView, food);
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

    public interface OnItemListener{
        void onItemClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public ImageView imageView;
        public TextView name;
        public TextView country;
        OnItemListener onItemListener;
        public ViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            country = itemView.findViewById(R.id.country);
            onItemListener=onItemListener;
        }

        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
