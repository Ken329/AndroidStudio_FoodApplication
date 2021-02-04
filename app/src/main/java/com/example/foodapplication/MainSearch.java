package com.example.foodapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainSearch extends AppCompatActivity {
    ImageButton back;
    EditText search;
    ListView list;
    customAdapter adapter;
    ArrayList<detail> arrayList = new ArrayList<detail>();;
    String myUser = null;
    int[] image = {R.mipmap.banana_round, R.mipmap.beef_burger_round, R.mipmap.bento_round, R.mipmap.cupcake_round, R.mipmap.donut_round,
    R.mipmap.fish_round, R.mipmap.fries_round, R.mipmap.ham_round, R.mipmap.hot_dog_round, R.mipmap.icecream_round, R.mipmap.macarron_round,
    R.mipmap.onion_ring_round, R.mipmap.pizza_round, R.mipmap.spring_roll_round, R.mipmap.taco_round, R.mipmap.turkey_round};
    String[] name = {"Banana Split", "Beef Burger", "Bento", "Cupcake", "Donut", "Fish Set", "French Fries", "Ham", "Hot Dog", "Ice-cream",
    "Maccoron", "Onion Ring", "Pizza", "Spring Roll", "TacoBell", "Turkey"};
    String[] rate = {"4.3", "4.0", "4.8", "3.5", "4.2", "4.5", "4.9", "4.7", "4.2", "3.8", "4.4", "4.6", "4.4", "4.0", "4.7", "4.2"};
    String[] fees = {"2.99", "1.99", "4.99", "1.99", "0.99", "2.99", "Free", "3.99", "2.99", "1.99", "4.99", "2.99", "3.99", "2.99",
    "4.99", "3.99"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);
        getSupportActionBar().hide();

        back = findViewById(R.id.searchBack);
        search = findViewById(R.id.searchEditText);
        list = findViewById(R.id.searchList);

        myUser = getIntent().getStringExtra("username");
        search.addTextChangedListener(textWatcher);
        for(int i = 0; i < name.length; i++){
            detail detail = new detail(image[i], name[i], rate[i], fees[i]);
            arrayList.add(detail);
        }
        adapter = new customAdapter(this, arrayList);
        list.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainPage.class);
                intent.putExtra("username", myUser);
                startActivity(intent);
            }
        });
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String food = search.getText().toString().toLowerCase().trim();
            adapter.filter(food);

        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}