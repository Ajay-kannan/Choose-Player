package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.bson.Document;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.result.InsertOneResult;

public class registerActivity extends AppCompatActivity {
    App app;
    User user;
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;
    String appId = "chooseplayer-qwwcq";

    EditText mail,username,password;

    Button reg_comp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mail=findViewById(R.id.Reg_mail);
        username=findViewById(R.id.Reg_username);
        password=findViewById(R.id.Reg_password);
        reg_comp=(Button) findViewById(R.id.reg_comp);
        Realm.init(getApplicationContext());
        app = new App(new AppConfiguration.Builder(appId).build());
        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()){
                    user = app.currentUser();
                    client = user.getMongoClient("mongodb-atlas");
                    db = client.getDatabase("ChoosePlayer");
                    collection = db.getCollection("users");
                }
                else{
                    System.out.println(result.getError());
                }

            }
        });
        reg_comp.setOnClickListener(V->{
            String mailStr=mail.getText().toString();
            String usernameStr=username.getText().toString();
            String passwordStr=password.getText().toString();
            Document document=new Document().append("email",mailStr).append("username",usernameStr).append("password",passwordStr);
            Document filter=new Document().append("email",mailStr);
            collection.findOne(filter).getAsync(result -> {
                if (result.isSuccess()){
                    if (result.get() == null){
                        collection.insertOne(document).getAsync(new App.Callback<InsertOneResult>() {
                            @Override
                            public void onResult(App.Result<InsertOneResult> result) {
                                if (result.isSuccess()){
                                    Intent intent=new Intent(registerActivity.this,ack_reg.class);
                                    startActivity(intent);
                                }
                                else{
                                    System.out.println(result.getError());
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(this, "use different email", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        });

    }
}
