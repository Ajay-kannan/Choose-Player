package com.example.myapplication;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComparePlayerListAdapter extends RecyclerView.Adapter<ComparePlayerListAdapter.ViewHolder> {
    Context context;
    ArrayList<PLayerDetails> playerDetails;
    PlayerForCompare playerForCompare;
    ComparePlayerListAdapter(Context context, ArrayList<PLayerDetails> playerDetails,PlayerForCompare playerForCompare){
        this.context = context;
         this.playerDetails = playerDetails;
         this.playerForCompare = playerForCompare;
    }
    public void setResult(ArrayList<PLayerDetails> pLayerDetails) {
        this.playerDetails = pLayerDetails;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.player_detail_card,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.number.setText(playerDetails.get(position).getNumber());
            holder.playerName.setText(playerDetails.get(position).getName());
            holder.position.setText(playerDetails.get(position).getPosition());
            holder.cardView.setOnClickListener(v->{
                playerForCompare.sendPlayerName(playerDetails.get(position).getName(),playerDetails.get(position).getNumber());
            });
    }
    @Override
    public int getItemCount() {
        return playerDetails.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        TextView number;
        TextView position;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.player_number);
            playerName = itemView.findViewById(R.id.player_name);
            position = itemView.findViewById(R.id.position);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}