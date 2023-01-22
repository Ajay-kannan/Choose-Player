package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        RecyclerView recyclerView = findViewById(R.id.player_list);
        PLayerDetails p = new PLayerDetails("Azarudeen","batsman","7");
        PLayerDetails p2 = new PLayerDetails("Nandhini","bowler","2");
        PLayerDetails p3 = new PLayerDetails("Ajay","batsman","3");
        PLayerDetails p4 = new PLayerDetails("Brittney","bowler","1");
        ArrayList<PLayerDetails> list = new ArrayList<>();
        list.add(p);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        PlayerListAdapter playerListAdapter = new PlayerListAdapter(getApplicationContext(),list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(playerListAdapter);
    }
}