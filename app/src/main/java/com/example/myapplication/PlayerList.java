package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.bson.Document;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;


public class PlayerList extends AppCompatActivity {

    App app;
    User user;
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;
    String appId = "chooseplayer-qwwcq";
    PlayerListAdapter playerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        Realm.init(getApplicationContext());

        ArrayList<PLayerDetails> list = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.player_list);

        app = new App(new AppConfiguration.Builder(appId).build());

        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){
                    user = app.currentUser();
                    client = user.getMongoClient("mongodb-atlas");
                    db = client.getDatabase("ChoosePlayer");
                    collection = db.getCollection("player_stats");
                    System.out.println("No Error");
                    Toast.makeText(PlayerList.this, "logged in", Toast.LENGTH_SHORT).show();
                    RealmResultTask<MongoCursor<Document>> playerList = collection.find().iterator();

                    playerList.getAsync(task->{
                        if(task.isSuccess()){
                            MongoCursor<Document> results = task.get();
                            while (results.hasNext()){
                                Document document = results.next();
                                PLayerDetails p = new PLayerDetails(document.getString("Name"),document.getString("Type"),document.getString("Team"));
                                list.add(p);
                            }
                            playerListAdapter = new PlayerListAdapter(PlayerList.this,list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
                            recyclerView.setAdapter(playerListAdapter);
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    System.out.println("Some Error");
                    Toast.makeText(PlayerList.this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });

        EditText searchBar = findViewById(R.id.search);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<PLayerDetails> result = new ArrayList<>();
                for (PLayerDetails player : list ){
                        if (player.getName().toLowerCase().contains(String.valueOf(s).toLowerCase())){
                                result.add(player);
                        }
                }
                playerListAdapter.setResult(result);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}