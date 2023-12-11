package com.example.assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TreasureAdapter extends RecyclerView.Adapter<TreasureAdapter.TreasureViewHolder> {

    private final List<Treasure> treasures;

    public TreasureAdapter(TreasureList treasureList, List<Treasure> treasures) {
        this.treasures = treasures;
    }

    public static class TreasureViewHolder extends RecyclerView.ViewHolder {
        public final TextView treasureName;

        public TreasureViewHolder(View itemView) {
            super(itemView);
            treasureName = itemView.findViewById(R.id.textTreasureName);
        }
    }


    @Override
    public TreasureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_treasure, parent, false);
        return new TreasureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TreasureViewHolder holder, int position) {
        if (holder != null) {
            Treasure currentTreasure = treasures.get(position);
            holder.treasureName.setText(currentTreasure.getName());
        }
    }

    @Override
    public int getItemCount() {
        return treasures.size();
    }
}
