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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainPage extends AppCompatActivity {
    ActionBarDrawerToggle toggle = null;
    DrawerLayout sideMenu;
    NavigationView sideView;
    String myUser;
    LinearLayout search;
    ImageView pizza, burger, donut, onion, hotdog, bento, fish, ham, roll, taco, turkey, fries, banana, icecream, cupcake, maccoron;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        sideMenu = findViewById(R.id.drawLayout);
        sideView = findViewById(R.id.navView);
        search = findViewById(R.id.mainSearch);
        pizza = findViewById(R.id.mainPizza);
        burger = findViewById(R.id.mainBurger);
        donut = findViewById(R.id.mainDonut);
        onion = findViewById(R.id.mainOnion);
        hotdog = findViewById(R.id.mainHotdog);
        bento = findViewById(R.id.mainBento);
        fish = findViewById(R.id.mainFish);
        ham = findViewById(R.id.mainHam);
        roll = findViewById(R.id.mainRoll);
        taco = findViewById(R.id.mainTaco);
        turkey = findViewById(R.id.mainTurkey);
        fries = findViewById(R.id.mainFries);
        banana = findViewById(R.id.mainBanana);
        icecream = findViewById(R.id.mainIcecream);
        cupcake = findViewById(R.id.mainCupcake);
        maccoron = findViewById(R.id.mainMaccaron);

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
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Pizza");
            }
        });
        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Beef Burger");
            }
        });
        donut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Donut");
            }
        });
        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Onion Ring");
            }
        });
        hotdog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Hot Dog");
            }
        });
        bento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Bento");
            }
        });
        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Fish Set");
            }
        });
        ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Ham");
            }
        });
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Spring Roll");
            }
        });
        taco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Taco Bell");
            }
        });
        turkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Turkey");
            }
        });
        fries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("French Fries");
            }
        });
        banana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Banana Split");
            }
        });
        icecream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Ice-Cream");
            }
        });
        cupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Cupcake");
            }
        });
        maccoron.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood("Maccoron");
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
    public void addFood(String food){
        ref = FirebaseDatabase.getInstance().getReference("cart").child(myUser).child(food);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int value = Integer.parseInt(snapshot.child("ammount").getValue().toString()) + 1;
                    ref = FirebaseDatabase.getInstance().getReference("cart").child(myUser).child(food);
                    ref.child("food").setValue(food);
                    ref.child("ammount").setValue(String.valueOf(value));
                    Toast.makeText(MainPage.this, food + " Has been added to cart", Toast.LENGTH_LONG).show();
                }else{
                    ref = FirebaseDatabase.getInstance().getReference("cart").child(myUser).child(food);
                    ref.child("food").setValue(food);
                    ref.child("ammount").setValue(String.valueOf(1));
                    Toast.makeText(MainPage.this, food + " has been added to cart", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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