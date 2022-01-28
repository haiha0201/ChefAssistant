package com.HaiHa.ChefAssistant.models.Ingredient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unity3d.player.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DirectionRecylerViewAdapter extends
        RecyclerView.Adapter<DirectionRecylerViewAdapter.DirectionViewHolder>
{
    Context ct;
    ArrayList<String> directions;

    public DirectionRecylerViewAdapter(Context ct)
    {
        this.ct = ct;
        this.directions = new ArrayList<String>();
    }
    public void SetDirections(String instruction)
    {
        this.directions = new ArrayList<String>(Arrays.asList(instruction.split("\\. ")));
        notifyDataSetChanged();
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
        holder.directionTextView.setText(this.directions.get(position));
    }

    @Override
    public int getItemCount() {
        return this.directions.size();
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
