package com.HaiHa.ChefAssistant.models.Food;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.HaiHa.ChefAssistant.Helper;
import com.unity3d.player.R;

import java.util.ArrayList;

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
    public ArrayList<Food> GetFoods()
    {
        return foods;
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
        holder.id.setText("#" + food.id);
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
        public TextView id;
        OnItemListener onItemListener;
        public ViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mealImageView);
            name = itemView.findViewById(R.id.mealNameTextView);
            id = itemView.findViewById(R.id.mealIdTextView);

            this.onItemListener=onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "I got clicked", Toast.LENGTH_SHORT);
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

}
