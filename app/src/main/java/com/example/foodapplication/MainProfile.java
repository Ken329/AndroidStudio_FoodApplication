package com.example.foodapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainProfile extends AppCompatActivity {
    ImageButton back;
    TextView name, pass, age, gender;

    String myUser = "";
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);
        getSupportActionBar().hide();

        back = findViewById(R.id.profileBack);
        name = findViewById(R.id.profileName);
        pass = findViewById(R.id.profilePassword);
        age = findViewById(R.id.profileAge);
        gender = findViewById(R.id.profileGender);

        myUser = getIntent().getStringExtra("username");

        ref = FirebaseDatabase.getInstance().getReference("user").child(myUser);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("username").getValue().toString());
                pass.setText(snapshot.child("password").getValue().toString());
                age.setText(snapshot.child("age").getValue().toString());
                gender.setText(snapshot.child("gender").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainPage.class);
                intent.putExtra("username", myUser);
                startActivity(intent);
            }
        });
    }
}