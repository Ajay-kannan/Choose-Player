package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import io.realm.mongodb.mongo.result.InsertOneResult;

public class activity_magager_edit_profile extends AppCompatActivity {
    App app;
    User user;
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;
    String appId = "chooseplayer-qwwcq";
    TextView saveTxt;
    String userName,email,phoneNumber,password,teamName;
    EditText userNameTxt,emailTxt,phoneNumberTxt,passwordTxt,teamNameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magager_edit_profile);

        Realm.init(getApplicationContext());


        app = new App(new AppConfiguration.Builder(appId).build());

        saveTxt = findViewById(R.id.textView2);

        userNameTxt = findViewById(R.id.editText2);
        emailTxt = findViewById(R.id.editText3);
        phoneNumberTxt = findViewById(R.id.editText4);
        passwordTxt = findViewById(R.id.editText5);
        teamNameTxt = findViewById(R.id.editText6);

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
                            } catch (NullPointerException ex) {
                                userName = "Nill";
                            }
                            try {
                                email = doc.getString("email");
                            } catch (NullPointerException ex) {
                                email = "Nill";
                            }
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
        saveTxt.setOnClickListener(v->{
            userName = userNameTxt.getText().toString();
            email= emailTxt.getText().toString();
            phoneNumber = phoneNumberTxt.getText().toString();
            password = passwordTxt.getText().toString();
            teamName = teamNameTxt.getText().toString();
            Document document = new Document().append("username",userName).append("phone",phoneNumber).append("password",password).append("teamname",teamName).append("email",email);
            Document queryFilter = new Document().append("email",email);

            collection.updateOne(queryFilter,document).getAsync(result1->{
                if (result1.isSuccess()){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    System.out.println(result1.getError());
                }
            });
        });
    }
}