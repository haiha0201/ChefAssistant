package com.HaiHa.ChefAssistant.models.Ingredient;

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

import com.HaiHa.ChefAssistant.models.Ingredient.Ingredient;
import com.unity3d.player.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Ingredient> ingredients;
    public IngredientAdapter(Context _context, ArrayList<Ingredient> initial)
    {
        context = _context;
        ingredients = initial;
    }
    public void SetIngredients(ArrayList<Ingredient> newIngres)
    {
        ingredients = newIngres;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View foodView = inflater.inflate(R.layout.ingredient_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(foodView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingre = ingredients.get(position);
        try {
            SetImage(holder.imageView, ingre);
        }
        catch (java.io.IOException e)
        {
            Log.e("ERROR", e.toString());
        }
        holder.name.setText(ingre.name);
        holder.measurement.setText(ingre.measurement);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void SetImage(ImageView imageView, Ingredient ingredient) throws IOException {
        if (ingredient.mealThumb == null || ingredient.mealThumb == "") return;
        if (ingredient.mealThumbBMP == null)
        {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(ingredient.mealThumb);
                        ingredient.mealThumbBMP = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(ingredient.mealThumbBMP);
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
            imageView.setImageBitmap(ingredient.mealThumbBMP);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView name;
        public TextView measurement;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            measurement = itemView.findViewById(R.id.measurement);
        }
    }

}
