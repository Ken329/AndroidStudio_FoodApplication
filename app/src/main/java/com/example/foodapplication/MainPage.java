package com.example.foodapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainPage extends AppCompatActivity {
    ActionBarDrawerToggle toggle = null;
    DrawerLayout sideMenu;
    NavigationView sideView;
    String myUser;
    LinearLayout search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        sideMenu = findViewById(R.id.drawLayout);
        sideView = findViewById(R.id.navView);
        search = findViewById(R.id.mainSearch);

        myUser = getIntent().getStringExtra("username");

        toggle = new ActionBarDrawerToggle(this, sideMenu, R.string.open, R.string.close);
        sideMenu.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View v = sideView.getHeaderView(0);
        TextView username = (TextView)v.findViewById(R.id.sideUsername);
        username.setText(myUser);
        sideView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        Toast.makeText(MainPage.this, "Item 1", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item2:
                        Toast.makeText(MainPage.this, "Item 2", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item3:
                        Toast.makeText(MainPage.this, "Item 3", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainSearch.class);
                intent.putExtra("username", myUser);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}