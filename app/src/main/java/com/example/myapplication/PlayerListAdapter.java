package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.ViewHolder> {

    Context context;
    ArrayList<PLayerDetails> pLayerDetails;

    PlayerListAdapter(Context context,ArrayList<PLayerDetails> pLayerDetails){
        this.context = context;
         this.pLayerDetails = pLayerDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.player_detail_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.number.setText(pLayerDetails.get(position).getNumber());
            holder.playerName.setText(pLayerDetails.get(position).getName());
            holder.position.setText(pLayerDetails.get(position).getPosition());
    }

    @Override
    public int getItemCount() {
        return pLayerDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        TextView number;
        TextView position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.player_number);
            playerName = itemView.findViewById(R.id.player_name);
            position = itemView.findViewById(R.id.position);
        }
    }
}
