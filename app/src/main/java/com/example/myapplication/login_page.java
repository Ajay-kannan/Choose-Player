package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class login_page extends AppCompatActivity {
    TextView backtosignup;
    App app;
    User user;
    MongoClient client;
    MongoDatabase db;
    MongoCollection<Document> collection;
    String appId = "chooseplayer-qwwcq";
    EditText loginMail,loginPassword;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        loginMail=findViewById(R.id.login_page_mail);
        loginPassword=findViewById(R.id.login_page_password);
        Realm.init(getApplicationContext());
        login=findViewById(R.id.login_page_btn);
        backtosignup=(TextView) findViewById(R.id.sign_up);
        app = new App(new AppConfiguration.Builder(appId).build());
        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){user = app.currentUser();
                    client = user.getMongoClient("mongodb-atlas");
                    db = client.getDatabase("ChoosePlayer");
                    collection = db.getCollection("users");
                }
                else{
                    System.out.println(result.getError());
                }
            }
        });

        login.setOnClickListener(v->{
            String loginMailStr=loginMail.getText().toString();
            String loginPasswordStr=loginPassword.getText().toString();
            Document filter=new Document().append("email",loginMailStr).append("password",loginPasswordStr);
            collection.findOne(filter).getAsync(result -> {
                if (result.isSuccess()){
                    if (result.get() == null){
                        Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        LoginCreds.userName = loginMailStr;
                        Intent intent_four=new Intent(login_page.this,Dashboard.class);
                        startActivity(intent_four);
                    }
                }
            });
        });

        backtosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_four;
                intent_four=new Intent(login_page.this,registerActivity.class);
                startActivity(intent_four);
            }
        });
    }
    private int checkPassword(String name,String password){

        return 0;
    }
}