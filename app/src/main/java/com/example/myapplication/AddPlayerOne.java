package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddPlayerOne extends AppCompatActivity {
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player_one);
        next = findViewById(R.id.next);
        next.setOnClickListener(v->{
            Intent intent = new Intent(this,AddPlayerTwo.class);
            startActivity(intent);
        });
    }
}