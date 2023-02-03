package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

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

public class recommended_player extends AppCompatActivity {

    App app;
    User user;
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;

    ImageView one,two,three;

    int playerOneScore,playerTwoScore;

    String appId = "chooseplayer-qwwcq";

    String team, type, fullName,age;
    double value, match, battingAvg, sr;

    String teamTwo, typeTwo, fullNameTwo,ageTwo;
    double valueTwo, matchTwo,battingAvgTwo, srTwo;

    int runs,runsTwo;

    TextView winner_name,teamName,recOne,recTwo,recThree,recTeamOne,recTeamTwo,recTeamThree;

    ImageView playerImage;

    BarChart barChart;

    int fiftyOne2016,fiftyOne2017,fiftyOne2018,fiftyOne2019,fiftyOne2020,fiftyOne2021,fiftyOne2022;
    int fiftyTwo2016,fiftyTwo2017,fiftyTwo2018,fiftyTwo2019,fiftyTwo2020,fiftyTwo2021,fiftyTwo2022;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_player);
        Intent intent = getIntent();
        String playerOne = intent.getStringExtra("winner");
        String teamOne = intent.getStringExtra("winnerTeam");
        String nameOne = intent.getStringExtra("bestOne");
        String nameTwo = intent.getStringExtra("bestTwo");
        String nameThree = intent.getStringExtra("bestTwo");

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);

        runs = intent.getIntExtra("smallRun",0);
        runsTwo = intent.getIntExtra("hugeRun",0);

        fiftyOne2016 = intent.getIntExtra("fiftyOne2016",0);
        fiftyOne2017 = intent.getIntExtra("fiftyOne2017",0);
        fiftyOne2018 = intent.getIntExtra("fiftyOne2018",0);
        fiftyOne2019 = intent.getIntExtra("fiftyOne2019",0);
        fiftyOne2020 = intent.getIntExtra("fiftyOne2020",0);
        fiftyOne2021 = intent.getIntExtra("fiftyOne2021",0);
        fiftyOne2022 = intent.getIntExtra("fiftyOne2022",0);

        recOne = findViewById(R.id.rec_player_one);
        recTwo = findViewById(R.id.rec_player_two);
        recThree = findViewById(R.id.rec_player_three);

        recTeamOne = findViewById(R.id.rec_team_one);
        recTeamTwo = findViewById(R.id.rec_team_two);
        recTeamThree = findViewById(R.id.rec_team_three);

        winner_name = findViewById(R.id.winner_name);
        teamName = findViewById(R.id.team_name);
        playerImage = findViewById(R.id.person_image);

//        barChart = findViewById(R.id.barChart_view);

        ArrayList<String> playerNameList = new ArrayList<>();
        ArrayList<String> teamList = new ArrayList<>();

        winner_name.setText(playerOne);
        teamName.setText(teamOne);

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
                            System.out.println("No Error");
                            Document filter = new Document().append("RunsScored",new Document().append("$gte",runs).append("$lt",runsTwo));
                            RealmResultTask<MongoCursor<Document>> playerList = collection.find(filter).sort(new Document().append("RunsScored",-1)).limit(3).iterator();
                            playerList.getAsync(task-> {
                                if (task.isSuccess()) {
                                    MongoCursor<Document> results = task.get();
                                    while (results.hasNext()) {
                                        Document document = results.next();
                                        playerNameList.add(document.getString("Name"));
                                        teamList.add(document.getString("Team"));
                                    }
                                    recTeamOne.setText(teamList.get(0));
                                    recTeamTwo.setText(teamList.get(1));
                                    recTeamThree.setText(teamList.get(2));
                                    recOne.setText(playerNameList.get(0));
                                    recTwo.setText(playerNameList.get(1));
                                    recThree.setText(playerNameList.get(2));
                                }
                            });
                        }
                    }
                });

        one.setOnClickListener(v->{
            Intent player = new Intent(this,PlayerStatistic.class);
            player.putExtra("name",recOne.getText().toString());
            startActivity(player);
        });

        two.setOnClickListener(v->{
            Intent player = new Intent(this,PlayerStatistic.class);
            player.putExtra("name",recTwo.getText().toString());
            startActivity(player);
        });

        three.setOnClickListener(v->{
            Intent player = new Intent(this,PlayerStatistic.class);
            player.putExtra("name",recThree.getText().toString());
            startActivity(player);
        });


        playerImage.setOnClickListener(v->{
            Intent winner = new Intent(this,PlayerStatistic.class);
            winner.putExtra("name",playerOne);
            startActivity(winner);
        });
//       initBarChart();
//       showBarChart();
        }



    private void showBarChart()
    {
        ArrayList<Integer> valueList = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Title";

        valueList.add(fiftyOne2016);
        valueList.add(fiftyOne2017);
        valueList.add(fiftyOne2018);
        valueList.add(fiftyOne2019);
        valueList.add(fiftyOne2020);
        valueList.add(fiftyOne2021);
        valueList.add(fiftyOne2022);

        //fit the data into a bar
        for (int i = 0; i < valueList.size(); i++) {
            System.out.println(valueList.get(i).floatValue());
            BarEntry barEntry = new BarEntry(i, valueList.get(i).floatValue());
            entries.add(barEntry);
        }

        BarDataSet barDataSet = new BarDataSet(entries, title);
        initBarDataSet(barDataSet);
        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        barChart.invalidate();
    }


    private void initBarDataSet(BarDataSet barDataSet){
        //Changing the color of the bar
        barDataSet.setColor(Color.parseColor("#304567"));
        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(12f);
    }

    private void initBarChart(){
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        barChart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        barChart.setDrawBorders(false);

        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        barChart.animateY(1000);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        barChart.animateX(1000);

        XAxis xAxis = barChart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = barChart.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);

        Legend legend = barChart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);

    }

}