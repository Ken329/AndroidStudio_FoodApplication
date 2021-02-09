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

public class MainSetting extends AppCompatActivity {
    ImageButton back;
    EditText oldPass, newPass;
    TextView enter;

    DatabaseReference ref;
    String myUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_setting);
        getSupportActionBar().hide();

        back = findViewById(R.id.mainSettingBack);
        oldPass = findViewById(R.id.mainSettingOldPass);
        newPass = findViewById(R.id.mainSettingNewPass);
        enter = findViewById(R.id.mainSettingEnter);

        myUser = getIntent().getStringExtra("username");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainPage.class);
                intent.putExtra("username", myUser);
                startActivity(intent);
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPass.getText().toString();
                String newPassword = newPass.getText().toString();

                ref = FirebaseDatabase.getInstance().getReference("user").child(myUser);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String dataPass = snapshot.child("password").getValue().toString();
                        if(dataPass.equals(oldPassword)){
                            ref.child("password").setValue(newPassword);
                            oldPass.setText("");
                            newPass.setText("");
                            Toast.makeText(MainSetting.this, "Password has been changed successfully", Toast.LENGTH_LONG).show();
                        }else{
                            oldPass.setError("Password entered is not the same, please check");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}