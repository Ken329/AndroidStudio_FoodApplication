package com.example.foodapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainSignUp extends AppCompatActivity {
    ImageButton back;
    EditText username, password, conPass, age, gender;
    TextView enter;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);
        getSupportActionBar().hide();

        back = findViewById(R.id.signUpBack);
        username = findViewById(R.id.signUpUsername);
        password = findViewById(R.id.signUpPassword);
        conPass = findViewById(R.id.signUpConpass);
        age = findViewById(R.id.signUpAge);
        gender = findViewById(R.id.signUpGender);
        enter = findViewById(R.id.signUpEnter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String conP = conPass.getText().toString();
                String myAge = age.getText().toString();
                String gen = gender.getText().toString();
                if(pass.equals(conP)){
                    Query query = FirebaseDatabase.getInstance().getReference("user")
                            .orderByChild("username")
                            .equalTo(user);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(MainSignUp.this, "Username appear, please check", Toast.LENGTH_LONG).show();
                            }else{
                                user_detail newUser = new user_detail(user, pass, myAge, gen);
                                ref = FirebaseDatabase.getInstance().getReference().child("user");
                                ref.child(user).setValue(newUser);
                                Toast.makeText(MainSignUp.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                                goBack(v);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    conPass.setError("Not equal to password");
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
    }
    public void goBack(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }
}