package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.bson.Document;


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

import android.os.Bundle;

public class activity_manager_profile extends AppCompatActivity {
    App app;
    User user;
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;
    String appId = "chooseplayer-qwwcq";
    String userName,email,phoneNumber,password,teamName;
    TextView userNameTxt,emailTxt,phoneNumberTxt,passwordTxt,teamNameTxt;
    ImageView edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profile);

        Realm.init(getApplicationContext());


        app = new App(new AppConfiguration.Builder(appId).build());

        edit = findViewById(R.id.edit);

        userNameTxt = findViewById(R.id.textView6);
        emailTxt = findViewById(R.id.textView7);
        phoneNumberTxt = findViewById(R.id.textView8);
        passwordTxt = findViewById(R.id.textView9);
        teamNameTxt = findViewById(R.id.textView10);

        edit.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),activity_magager_edit_profile.class);
            startActivity(intent);
        });

        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()){
                    user = app.currentUser();
                    client = user.getMongoClient("mongodb-atlas");
                    db = client.getDatabase("ChoosePlayer");
                    collection = db.getCollection("users");

                    Document profFilter = new Document().append("email", LoginCreds.userName);

                    collection.findOne(profFilter).getAsync(results -> {
                        if (results.isSuccess()) {
                           Document doc = results.get();
                            try {
                                userName = doc.getString("username");
                                System.out.println(userName);
                            } catch (NullPointerException ex) {
                                userName = "Nill";
                            }
                            try {
                                email = doc.getString("email");
                            } catch (NullPointerException ex) {
                                email = "Nill";
                            }
                            System.out.println("Email" + email);
                            try {
                                phoneNumber =  String.valueOf(doc.getString("phone"));
                            } catch (NullPointerException ex) {
                                phoneNumber = "Nill";
                            }
                            try {
                                password = doc.getString("password");
                            } catch (NullPointerException ex) {
                                password = "Nill";
                            }
                            try {
                                teamName = doc.getString("Team Name");
                            } catch (NullPointerException ex) {
                                teamName = "Nill";
                            }
                            userNameTxt.setText(userName);
                            emailTxt.setText(email);
                            phoneNumberTxt.setText(phoneNumber);
                            passwordTxt.setText(password);
                            teamNameTxt.setText(teamName);
                        }
                    });

                }
                else{
                    System.out.println(result.getError());
                }
            }
        });


    }
}