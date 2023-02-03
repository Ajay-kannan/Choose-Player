package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ImageView addPlayer = findViewById(R.id.add_player);
        ImageView playerList = findViewById(R.id.player_list);
        ImageView compare = findViewById(R.id.compare);
        addPlayer.setOnClickListener(v->{
            Intent intent = new Intent(this,activity_manager_profile.class);
            startActivity(intent);
        });
        playerList.setOnClickListener(v->{
            Intent intent = new Intent(this,PlayerList.class);
            startActivity(intent);
        });
        compare.setOnClickListener(v->{
            Intent intent = new Intent(this,compare_player.class);
            startActivity(intent);
        });
    }
}
