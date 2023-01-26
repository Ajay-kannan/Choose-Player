package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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

public class PlayerStatistic extends AppCompatActivity {

    App app;
    User user;
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;

    String appId = "chooseplayer-qwwcq";

    String team, type, fullName,value, age, nation, batting, bowling, match, innings, no, runs, high, hundred, fifty, fours, six, battingAvg, sr, catches, ducks, ro, bowled, overs, maidens, runsgive, wickets, best, three, five, economy, bowlingAvg, srBowl;

    TextView nameTxt,typeTxt,teamTxt,fullNameTxt,valueTxt,ageTxt,nationTxt,matchPlayed;

    TextView battingTxt,inningsTxt,noTxt,runsTxt,highTxt, hundredTxt, fiftyTxt, foursTxt, sixTxt, battingAvgTxt, srTxt;

    TextView bowlingTxt,bowledTxt,overTxt,maidenTxt,runsgiveTxt,wicketTxt,fiveTxt,bowlingAvgTxt,economyTxt,bestTxt;

    LineChart lineChart;

    LineDataSet dataSet;

    ArrayList<Entry> fiftyEntry,hundredEntry,srEntry,runsEntry,avgEntry;

    ArrayList<Entry> wicketEntry,ecoEntry,srBowlEntry,runsGivenEntry,avgBowlEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_stats);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Realm.init(getApplicationContext());

        ArrayList<PLayerDetails> list = new ArrayList<>();

        setUpUi();

        fiftyEntry = new ArrayList<>();
        hundredEntry = new ArrayList<>();
        srEntry = new ArrayList<>();
        runsEntry = new ArrayList<>();
        avgEntry = new ArrayList<>();

        app = new App(new AppConfiguration.Builder(appId).build());

        final ArrayList<String> xLabel = new ArrayList<>();

        for(int i=2016; i<=2022; i++) {
            xLabel.add(""+i);
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int)value);
            }
        });

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawAxisLine(true);
        yAxisRight.setDrawGridLines(true);

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setDrawGridLines(true);

        dataSet = new LineDataSet(fiftyEntry,"2022");
        dataSet.setColor(Color.GREEN);
        dataSet.setValueTextColor(Color.RED);

        LineData lineData = new LineData(dataSet);

        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()) {
                    user = app.currentUser();
                    client = user.getMongoClient("mongodb-atlas");
                    db = client.getDatabase("ChoosePlayer");
                    collection = db.getCollection("player_stats");

                    MongoCollection<Document> batting2022 = db.getCollection("batsman_seven");
                    MongoCollection<Document> batting2021 = db.getCollection("batsman_six");
                    MongoCollection<Document> batting2020 = db.getCollection("batsman_five");
                    MongoCollection<Document> batting2019 = db.getCollection("batsman_four");
                    MongoCollection<Document> batting2018 = db.getCollection("batsman_three");
                    MongoCollection<Document> batting2017 = db.getCollection("batsman_two");
                    MongoCollection<Document> batting2016 = db.getCollection("batsman_one");

                    Document playerFilter = new Document().append("Name", name);
                    Document filter = new Document().append("Player", name);

                    collection.findOne(playerFilter).getAsync(results -> {
                        if (results.isSuccess()) {
                            Toast.makeText(PlayerStatistic.this, "Success", Toast.LENGTH_SHORT).show();
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
                                value = String.valueOf(doc.getDouble("ValueinCR"));
                            } catch (NullPointerException ex) {
                                value = "Nill";
                            }
                            try {
                                age = doc.getString("Age");
                            } catch (NullPointerException ex) {
                                age = "Nill";
                            }
                            try {
                                nation = doc.getString("National Side");
                            } catch (NullPointerException ex) {
                                nation = "Nill";
                            }
                            try {
                                batting = doc.getString("Batting Style");
                            } catch (NullPointerException ex) {
                                batting = "Nill";
                            }
                            try {
                                bowling = doc.getString("Bowling");
                            } catch (NullPointerException ex) {
                                bowling = "Nill";
                            }
                            try {
                                match = String.valueOf(doc.getInteger("MatchPlayed"));
                            } catch (NullPointerException ex) {
                                match = "Nill";
                            }
                            try {
                                innings = String.valueOf(doc.getInteger("InningsBatted"));
                            } catch (NullPointerException ex) {
                                innings = "Nill";
                            }
                            try {
                                no = String.valueOf(doc.getInteger("NotOuts"));
                            } catch (NullPointerException ex) {
                                no = "Nill";
                            }
                            try {
                                runs = String.valueOf(doc.getInteger("RunsScored"));
                            } catch (NullPointerException ex) {
                                runs = "Nill";
                            }
                            try {
                                high = doc.getString("HighestInnScore");
                            } catch (NullPointerException ex) {
                                high = "Nill";
                            }
                            try {
                                hundred = String.valueOf(doc.getInteger("100s"));
                            } catch (NullPointerException ex) {
                                hundred = "Nill";
                            }
                            try {
                                fifty = String.valueOf(doc.getInteger("50s"));
                            } catch (NullPointerException ex) {
                                fifty = "Nill";
                            }
                            try {
                                fours = String.valueOf(doc.getInteger("4s"));
                            } catch (NullPointerException ex) {
                                fours = "Nill";
                            }
                            try {
                                six = String.valueOf(doc.getInteger("6s"));
                            } catch (NullPointerException ex) {
                                six = "Nill";
                            }
                            try {
                                battingAvg = String.valueOf(doc.getDouble("BattingAVG"));
                            } catch (NullPointerException ex) {
                                battingAvg = "Nill";
                            }
                            try {
                                sr = String.valueOf(doc.getInteger("InningsBatted"));
                            } catch (NullPointerException ex) {
                                sr = "Nill";
                            }
                            try {
                                catches = String.valueOf(doc.getInteger("CatchesTaken"));
                            } catch (NullPointerException ex) {
                                catches = "Nill";
                            }
                            try {
                                ducks = String.valueOf(doc.getInteger("6s"));
                            } catch (NullPointerException ex) {
                                ducks = "Nill";
                            }
                            try {
                                ro = String.valueOf(doc.getInteger("R/O"));
                            } catch (NullPointerException ex) {
                                ro = "Nill";
                            }
                            try {
                                bowled = String.valueOf(doc.getInteger("InningsBowled"));
                            } catch (NullPointerException ex) {
                                bowled = "Nill";
                            }
                            try {
                                overs = String.valueOf(doc.getDouble("Overs"));
                            } catch (NullPointerException ex) {
                                overs = "Nill";
                            }
                            try {
                                maidens = String.valueOf(doc.getInteger("Madiens"));
                            } catch (NullPointerException ex) {
                                maidens = "Nill";
                            }
                            try {
                                runsgive = String.valueOf(doc.getInteger("RunsConceded"));
                            } catch (NullPointerException ex) {
                                runsgive = "Nill";
                            }
                            try {
                                wickets = String.valueOf(doc.getInteger("Wickets"));
                            } catch (NullPointerException ex) {
                                wickets = "Nill";
                            }
                            try {
                                best = doc.getString("Best");
                            } catch (NullPointerException ex) {
                                best = "Nill";
                            }
                            try {
                                three = String.valueOf(doc.getInteger("3s"));
                            } catch (NullPointerException ex) {
                                three = "Nill";
                            }
                            try {
                                five = String.valueOf(doc.getInteger("5s"));
                            } catch (NullPointerException ex) {
                                five = "Nill";
                            }
                            try {
                                economy = String.valueOf(doc.getDouble("EconomyRate"));
                            } catch (NullPointerException ex) {
                                economy = "Nill";
                            }
                            try {
                                bowlingAvg = String.valueOf(doc.getDouble("BowlingAVG"));
                            } catch (NullPointerException ex) {
                                bowlingAvg = "Nill";
                            }
                            try {
                                srBowl = String.valueOf(doc.getDouble("S/R"));
                            } catch (NullPointerException ex) {
                                srBowl = "Nill";
                            }
                            nameTxt.setText(name);
                            typeTxt.setText(type);
                            teamTxt.setText(team);
                            fullNameTxt.setText(fullName);
                            ageTxt.setText(age);
                            nationTxt.setText(nation);
                            valueTxt.setText(value);
                            matchPlayed.setText(match);

                            battingTxt.setText(batting);
                            inningsTxt.setText(innings);
                            noTxt.setText(no);
                            runsTxt.setText(runs);
                            highTxt.setText(high);
                            hundredTxt.setText(hundred);
                            fiftyTxt.setText(fifty);
//                            foursTxt.setText(fours);
//                            sixTxt.setText(six);
                            battingAvgTxt.setText(battingAvg);
                            srTxt.setText(sr);

                            bowlingTxt.setText(bowling);
                            bowledTxt.setText(bowled);
                            overTxt.setText(overs);
                            maidenTxt.setText(maidens);
                            runsgiveTxt.setText(runsgive);
                            wicketTxt.setText(wickets);
                            fiveTxt.setText(five);
                            bowlingAvgTxt.setText(bowlingAvg);
                            economyTxt.setText(economy);
                            bestTxt.setText(best);

                        } else {
                            Toast.makeText(PlayerStatistic.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
//                            playerName.setText(document.getString("Player"));
                    });



                    batting2016.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            Document doc = result1.get();
                            fiftyEntry.add(new Entry(1,doc.getInteger("50")));
//                            hundredEntry.add(new Entry(1,result1.get().getInteger("100")));
//                            srEntry.add(new Entry(1,result1.get().getInteger("SR")));
//                            runsEntry.add(new Entry(1,result1.get().getInteger("Runs")));
//                            avgEntry.add(new Entry(1,result1.get().getInteger("Avg")));
//                            System.out.println(result1.get().getInteger("50"));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });

                    batting2017.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            try {
                                fiftyEntry.add(new Entry(2,result1.get().getInteger("50")));
                            }catch (NullPointerException ex) {
                                fiftyEntry.add(new Entry(2,0));
                            }
//                            hundredEntry.add(new Entry(1,result1.get().getInteger("100")));
//                            srEntry.add(new Entry(1,result1.get().getDouble("SR")));
//                            runsEntry.add(new Entry(1,result1.get().getInteger("Runs")));
//                            avgEntry.add(new Entry(1,result1.get().getInteger("Avg")));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });

                    batting2018.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            fiftyEntry.add(new Entry(3,result1.get().getInteger("50")));
//                            hundredEntry.add(new Entry(1,result1.get().getInteger("100")));
//                            srEntry.add(new Entry(1,result1.get().getInteger("SR")));
//                            runsEntry.add(new Entry(1,result1.get().getInteger("Runs")));
//                            avgEntry.add(new Entry(1,result1.get().getInteger("Avg")));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });

                    batting2019.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            fiftyEntry.add(new Entry(4,result1.get().getInteger("50")));
//                            hundredEntry.add(new Entry(1,result1.get().getInteger("100")));
//                            srEntry.add(new Entry(1,result1.get().getInteger("SR")));
//                            runsEntry.add(new Entry(1,result1.get().getInteger("Runs")));
//                            avgEntry.add(new Entry(1,result1.get().getInteger("Avg")));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });
//
                    batting2020.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            fiftyEntry.add(new Entry(5,result1.get().getInteger("50")));
//                            hundredEntry.add(new Entry(1,result1.get().getInteger("100")));
//                            srEntry.add(new Entry(1,result1.get().getInteger("SR")));
//                            runsEntry.add(new Entry(1,result1.get().getInteger("Runs")));
//                            avgEntry.add(new Entry(1,result1.get().getInteger("Avg")));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Not",Toast.LENGTH_LONG).show();
                            System.out.println(result1.getError());
                        }
                    });
//
                    batting2021.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            fiftyEntry.add(new Entry(6,result1.get().getInteger("50")));
//                            hundredEntry.add(new Entry(1,result1.get().getInteger("100")));
//                            srEntry.add(new Entry(1,result1.get().getInteger("SR")));
//                            runsEntry.add(new Entry(1,result1.get().getInteger("Runs")));
//                            avgEntry.add(new Entry(1,result1.get().getInteger("Avg")));
                        }
                    });
//
                    batting2022.findOne(filter).getAsync(result1 -> {
                        if (result1.isSuccess()){
                            fiftyEntry.add(new Entry(7,result1.get().getInteger("50")));
//                            hundredEntry.add(new Entry(1,result1.get().getInteger("100")));
//                            srEntry.add(new Entry(1,result1.get().getInteger("SR")));
//                            runsEntry.add(new Entry(1,result1.get().getInteger("Runs")));
//                            avgEntry.add(new Entry(1,result1.get().getInteger("Avg")));
                        }
                    });
                } else {
                    System.out.println("Some Error");
                    Toast.makeText(PlayerStatistic.this, "Not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });
        matchPlayed.setOnClickListener(v->{
            for (Entry entry: fiftyEntry) {
                System.out.println(entry.getY());
            }
            lineChart.setData(lineData);
            dataSet.notifyDataSetChanged();
            lineData.notifyDataChanged();
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        });
    }

    private void setUpUi() {
        fullNameTxt = findViewById(R.id.fullname);

        ageTxt = findViewById(R.id.age);

        typeTxt = findViewById(R.id.position);

        teamTxt = findViewById(R.id.team);

        nationTxt = findViewById(R.id.nation);

        valueTxt = findViewById(R.id.value);

        nameTxt = findViewById(R.id.player_name);

        matchPlayed = findViewById(R.id.match);

        battingTxt = findViewById(R.id.batting);

        inningsTxt = findViewById(R.id.insbatted);

        noTxt = findViewById(R.id.no);

        runsTxt = findViewById(R.id.runs);

        highTxt = findViewById(R.id.high);

        hundredTxt = findViewById(R.id.hundred);

        fiftyTxt = findViewById(R.id.fifty);

        battingAvgTxt = findViewById(R.id.battingAvg);

        srTxt = findViewById(R.id.sr);

        bowlingTxt = findViewById(R.id.bowling);

        bowledTxt = findViewById(R.id.insbowled);

        overTxt = findViewById(R.id.overs);

        maidenTxt = findViewById(R.id.maiden);

        runsgiveTxt = findViewById(R.id.runsgive);

        wicketTxt = findViewById(R.id.wickets);

        fiveTxt = findViewById(R.id.five);

        bowlingAvgTxt = findViewById(R.id.bowlingAvg);

        economyTxt = findViewById(R.id.economyrate);

        bestTxt = findViewById(R.id.best);

        lineChart = findViewById(R.id.lineChart);
    }
}
