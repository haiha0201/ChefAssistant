package com.HaiHa.ChefAssistant.models.Ingredient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unity3d.player.R;

public class DirectionRecylerViewAdapter extends
        RecyclerView.Adapter<DirectionRecylerViewAdapter.DirectionViewHolder>
{
    Context ct;
    String[] directions;

    public DirectionRecylerViewAdapter(Context ct, String directions)
    {
        this.directions = directions.split("\\. ");
        this.ct = ct;
    }

    @NonNull
    @Override
    public DirectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.ct);
        View view = inflater.inflate(R.layout.direction_row, parent,
                false);
        return new DirectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectionViewHolder holder, int position) {
        holder.directionTextView.setText(this.directions[position]);
    }

    @Override
    public int getItemCount() {
        return this.directions.length;
    }

    public class DirectionViewHolder extends RecyclerView.ViewHolder
    {
        TextView directionTextView;
        public DirectionViewHolder(@NonNull View itemView) {
            super(itemView);

            directionTextView =
                    (TextView) itemView.findViewById(R.id.directionTextView);
        }
    }
}
