package com.example.myapplication;

import static io.realm.Sort.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.BsonDocument;
import org.bson.Document;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.Sort;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;


public class compare_player extends AppCompatActivity {

    App app;
    User user;
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;

    int fiftyOne2016,fiftyOne2017,fiftyOne2018,fiftyOne2019,fiftyOne2020,fiftyOne2021,fiftyOne2022;
    int fiftyTwo2016,fiftyTwo2017,fiftyTwo2018,fiftyTwo2019,fiftyTwo2020,fiftyTwo2021,fiftyTwo2022;

    int hundredOne2016,hundredOne2017,hundredOne2018,hundredOne2019,hundredOne2020,hundredOne2021,hundredOne2022;
    int hundredTwo2016,hundredTwo2017,hundredTwo2018,hundredTwo2019,hundredTwo2020,hundredTwo2021,hundredTwo2022;

    String appId = "chooseplayer-qwwcq";

    String team, type, fullName,age;
    double value, match, runs,battingAvg, sr;

    String teamTwo, typeTwo, fullNameTwo,ageTwo;
    double valueTwo, matchTwo, runsTwo,battingAvgTwo, srTwo;

    String playerOneStr,playerTwoStr,teamOneStr,teamTwoStr;

    TextView playerOneTxt,playerTwoTxt,teamOneTxt,teamTwoTxt;

    RelativeLayout playerOneProfile,playerTwoProfile;

    int playerOneScore,playerTwoScore;

    CardView compare;
    Button playerOneCard,playerTwoCard;
    ActivityResultLauncher<Intent> playerOne = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    playerOneScore = 0;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        playerOneStr = data.getStringExtra("name");
                        teamOneStr = data.getStringExtra("team");
                        if (playerTwoStr != null){
                            if (playerTwoStr.equals(playerOneStr)){
                                Toast.makeText(compare_player.this, "select different player", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        playerOneCard.setText(playerOneStr);
                        playerOneTxt.setText(playerOneStr);
                        teamOneTxt.setText(teamOneStr);

                        Document filterOne = new Document().append("Name", playerOneStr);

                        collection.findOne(filterOne).getAsync(results -> {
                            if (results.isSuccess()) {
                                Toast.makeText(compare_player.this, "Success", Toast.LENGTH_SHORT).show();
                                Document doc = results.get();
                                try {
                                    fullName = doc.getString("Full Name");
                                } catch (NullPointerException ex) {
                                    fullName = "Nill";
                                }
                                try {
                                    team = doc.getString("Team");
                                } catch (NullPointerException ex) {
                                    team = "Nill";
                                }
                                try {
                                    type = doc.getString("Type");
                                } catch (NullPointerException ex) {

                                    type = "Nill";
                                }
                                try {
                                    value = doc.getDouble("ValueinCR");
                                } catch (NullPointerException ex) {
                                    value = 0;
                                }
                                try {
                                    age = doc.getString("Age");
                                } catch (NullPointerException ex) {
                                    age = "Nill";
                                }
                                try {
                                    match = doc.getInteger("MatchPlayed");
                                } catch (NullPointerException ex) {
                                    match = 0;
                                }
                                try {
                                    runs = doc.getInteger("RunsScored");
                                } catch (NullPointerException ex) {
                                    runs = 0;
                                }
                                try {
                                    battingAvg = doc.getDouble("BattingAVG");
                                } catch (NullPointerException ex) {
                                    battingAvg = 0;
                                }
                                try {
                                    sr = doc.getDouble("BattingS/R");
                                } catch (NullPointerException ex) {
                                    sr = 0;
                                }
                                MongoCollection<Document> batting2022 = db.getCollection("batsman_seven");
                                MongoCollection<Document> batting2021 = db.getCollection("batsman_six");
                                MongoCollection<Document> batting2020 = db.getCollection("batsman_five");
                                MongoCollection<Document> batting2019 = db.getCollection("batsman_four");
                                MongoCollection<Document> batting2018 = db.getCollection("batsman_three");
                                MongoCollection<Document> batting2017 = db.getCollection("batsman_two");
                                MongoCollection<Document> batting2016 = db.getCollection("batsman_one");
                                Document filter = new Document().append("Player", playerOneStr);
                                                    batting2016.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            Document docu = result1.get();
                            try {
                                fiftyOne2016 = doc.getInteger(50);
                                System.out.println(fiftyOne2016);
                            }catch (NullPointerException ex) {
                                fiftyOne2016 = 0;
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });

                    batting2017.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            try {
                                fiftyOne2017 = doc.getInteger(50);
                                System.out.println(fiftyOne2017);
                            }catch (NullPointerException ex) {
                                fiftyOne2017 = 0;
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });

                    batting2018.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            try {
                                fiftyOne2018 = doc.getInteger(50);
                                System.out.println(fiftyOne2018);
                            }catch (NullPointerException ex) {
                                fiftyOne2018 = 0;
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });

                    batting2019.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            try {
                                fiftyOne2019 = doc.getInteger(50);
                            }catch (NullPointerException ex) {
                                fiftyOne2019 = 0;
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });
//
                    batting2020.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            try {
                                fiftyOne2020 = doc.getInteger(50);
                            }catch (NullPointerException ex) {
                                fiftyOne2020 = 0;
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });
                    batting2021.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            try {
                                fiftyOne2021 = doc.getInteger("50");
                            }catch (NullPointerException ex) {
                                fiftyOne2021 = 0;
                            }
                        }
                    });
                    batting2022.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            try {
                                fiftyOne2022 = doc.getInteger("50");
                            }catch (NullPointerException ex) {
                                fiftyOne2022 = 0;
                            }
                        }
                    });
                            } else {
                                Toast.makeText(compare_player.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });
    ActivityResultLauncher<Intent> playerTwo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    playerTwoScore = 0;
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        playerTwoStr = data.getStringExtra("name");
                        teamTwoStr = data.getStringExtra("team");
                        if (playerOneStr != null){
                            if (playerTwoStr.equals(playerOneStr)){
                                Toast.makeText(compare_player.this, "select different player", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        playerTwoCard.setText(playerTwoStr);
                        playerTwoTxt.setText(playerTwoStr);
                        teamTwoTxt.setText(teamTwoStr);

                        Document filterTwo = new Document().append("Name", playerTwoStr);
                        collection.findOne(filterTwo).getAsync(results -> {
                            if (results.isSuccess()) {
                                Toast.makeText(compare_player.this, "Success", Toast.LENGTH_SHORT).show();
                                Document doc = results.get();
                                try {
                                    fullNameTwo = doc.getString("Full Name");
                                } catch (NullPointerException ex) {
                                    fullNameTwo = "Nill";
                                }
                                try {
                                    teamTwo = doc.getString("Team");
                                } catch (NullPointerException ex) {
                                    teamTwo = "Nill";
                                }
                                try {
                                    typeTwo = doc.getString("Type");
                                } catch (NullPointerException ex) {
                                    typeTwo = "Nill";
                                }
                                try {
                                    valueTwo = doc.getDouble("ValueinCR");
                                } catch (NullPointerException ex) {
                                    valueTwo = 0;
                                }
                                try {
                                    ageTwo = doc.getString("Age");
                                } catch (NullPointerException ex) {
                                    ageTwo = "Nill";
                                }
                                try {
                                    matchTwo = doc.getInteger("MatchPlayed");
                                } catch (NullPointerException ex) {
                                    matchTwo = 0;
                                }
                                try {
                                    runsTwo = doc.getInteger("RunsScored");
                                } catch (NullPointerException ex) {
                                    runsTwo = 0;
                                }
                                try {
                                    battingAvgTwo = doc.getDouble("BattingAVG");
                                } catch (NullPointerException ex) {
                                    battingAvgTwo = 0;
                                }
                                try {
                                    srTwo = doc.getDouble("BattingS/R");
                                } catch (NullPointerException ex) {
                                    srTwo = 0;
                                }
                                MongoCollection<Document> batting2022 = db.getCollection("batsman_seven");
                                MongoCollection<Document> batting2021 = db.getCollection("batsman_six");
                                MongoCollection<Document> batting2020 = db.getCollection("batsman_five");
                                MongoCollection<Document> batting2019 = db.getCollection("batsman_four");
                                MongoCollection<Document> batting2018 = db.getCollection("batsman_three");
                                MongoCollection<Document> batting2017 = db.getCollection("batsman_two");
                                MongoCollection<Document> batting2016 = db.getCollection("batsman_one");
                                Document filter = new Document().append("Player", playerTwoStr);
                                batting2016.findOne(filter).getAsync(result1 -> {
                                    if (result1.isSuccess()){
                                        Document docu = result1.get();
                                        try {
                                            fiftyTwo2016 = doc.getInteger("50");
                                        }catch (NullPointerException ex) {
                                            fiftyTwo2016 = 0;
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                                        System.out.println(result1.getError());
                                    }
                                });

                                batting2017.findOne(filter).getAsync(result1 -> {
                                    if (result1.isSuccess()){
                                        try {
                                            fiftyTwo2017 = doc.getInteger("50");
                                        }catch (NullPointerException ex) {
                                            fiftyTwo2017 = 0;
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                                        System.out.println(result1.getError());
                                    }
                                });

                                batting2018.findOne(filter).getAsync(result1 -> {
                                    if (result1.isSuccess()){
                                        try {
                                            fiftyTwo2018 = doc.getInteger("50");
                                        }catch (NullPointerException ex) {
                                            fiftyTwo2018 = 0;
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                                        System.out.println(result1.getError());
                                    }
                                });

                                batting2019.findOne(filter).getAsync(result1 -> {
                                    if (result1.isSuccess()){
                                        try {
                                            fiftyTwo2019 = doc.getInteger("50");
                                        }catch (NullPointerException ex) {
                                            fiftyTwo2019 = 0;
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                                        System.out.println(result1.getError());
                                    }
                                });
//
                                batting2020.findOne(filter).getAsync(result1 -> {
                                    if (result1.isSuccess()){
                                        try {
                                            fiftyTwo2020 = doc.getInteger("50");
                                        }catch (NullPointerException ex) {
                                            fiftyTwo2020 = 0;
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                                        System.out.println(result1.getError());
                                    }
                                });
                                batting2021.findOne(filter).getAsync(result1 -> {
                                    if (result1.isSuccess()){
                                        try {
                                            fiftyTwo2021 = doc.getInteger("50");
                                        }catch (NullPointerException ex) {
                                            fiftyTwo2021 = 0;
                                        }
                                    }
                                });
                                batting2022.findOne(filter).getAsync(result1 -> {
                                    if (result1.isSuccess()){
                                        try {
                                            fiftyTwo2022 = doc.getInteger("50");
                                        }catch (NullPointerException ex) {
                                            fiftyTwo2022 = 0;
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(compare_player.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_player);

        Realm.init(getApplicationContext());

        app = new App(new AppConfiguration.Builder(appId).build());

        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()) {
                    user = app.currentUser();
                    client = user.getMongoClient("mongodb-atlas");
                    db = client.getDatabase("ChoosePlayer");
                    collection = db.getCollection("player_stats");
                } else {
                    System.out.println("Some Error");
                }
            }
        });

        playerOneCard = findViewById(R.id.player_one_card);
        playerTwoCard = findViewById(R.id.player_two_card);
        playerOneTxt = findViewById(R.id.player_name_card);
        playerTwoTxt = findViewById(R.id.player_two_name_card);
        teamOneTxt = findViewById(R.id.team_one);
        teamTwoTxt = findViewById(R.id.team_two);
        playerOneProfile = findViewById(R.id.playerOne);
        playerTwoProfile = findViewById(R.id.playerTwo);

        compare = findViewById(R.id.compare);

        playerOneCard.setOnClickListener(v->{
            Intent intent = new Intent(this,ComparePlayerList.class);
            playerOne.launch(intent);
        });

        playerTwoCard.setOnClickListener(v->{
            Intent intent = new Intent(this,ComparePlayerList.class);
            playerTwo.launch(intent);
        });

        compare.setOnClickListener(v->{
            int smallRun = 0,hugeRun = 0;
            if (playerTwoStr == null || playerOneStr == null){
                Toast.makeText(this, "Please Select both players", Toast.LENGTH_SHORT).show();
            }
            else{
                if (match > matchTwo){
                    playerOneScore++;
                }
                if (runs > runsTwo){
                    playerOneScore++;
                    smallRun = (int) runsTwo;
                    hugeRun = (int) runs;
                }
                if (sr > srTwo){
                    playerOneScore++;
                }
                if (battingAvg > battingAvgTwo){
                    playerOneScore++;
                }

                if (match < matchTwo){
                    playerTwoScore++;
                }
                if (runs < runsTwo){
                    playerTwoScore++;
                    smallRun = (int) runs;
                    hugeRun = (int) runsTwo;
                }
                if (sr < srTwo){
                    playerTwoScore++;
                }
                if (battingAvg <  battingAvgTwo){
                    playerTwoScore++;
                }
                Intent intent = new Intent(this,recommended_player.class);
                System.out.println(playerOneScore);
                System.out.println(playerTwoScore);
                if (playerOneScore > playerTwoScore){
                    intent.putExtra("winner",playerOneCard.getText().toString());
                    intent.putExtra("winnerTeam",teamOneStr);
                }
                if (playerOneScore < playerTwoScore){
                    intent.putExtra("winner",playerTwoCard.getText().toString());
                    intent.putExtra("winnerTeam",teamTwoStr);
                }
                intent.putExtra("smallRun",smallRun);
                intent.putExtra("hugeRun",hugeRun);
                intent.putExtra("fiftyOne2016",fiftyOne2016);
                intent.putExtra("fiftyOne2017",fiftyOne2017);
                intent.putExtra("fiftyOne2018",fiftyOne2018);
                intent.putExtra("fiftyOne2019",fiftyOne2019);
                intent.putExtra("fiftyOne2020",fiftyOne2020);
                intent.putExtra("fiftyOne2021",fiftyOne2021);
                intent.putExtra("fiftyOne2022",fiftyOne2022);
                intent.putExtra("fiftyTwo2016",fiftyTwo2016);
                intent.putExtra("fiftyTwo2017",fiftyTwo2017);
                intent.putExtra("fiftyTwo2018",fiftyTwo2018);
                intent.putExtra("fiftyTwo2019",fiftyTwo2019);
                intent.putExtra("fiftyTwo2020",fiftyTwo2020);
                intent.putExtra("fiftyTwo2021",fiftyTwo2021);
                intent.putExtra("fiftyTwo2022",fiftyTwo2022);
                startActivity(intent);
            }
        });

        playerOneProfile.setOnClickListener(v->{
            Intent intent = new Intent(this,PlayerStatistic.class);
            intent.putExtra("name",playerOneCard.getText().toString());
            startActivity(intent);
        });
        playerTwoProfile.setOnClickListener(v->{
            Intent intent = new Intent(this,PlayerStatistic.class);
            intent.putExtra("name",playerTwoCard.getText().toString());
            startActivity(intent);
        });
    }
}