package com.example.foodapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainHistory extends AppCompatActivity {
    ImageButton back;
    ListView list;
    TextView empty, clearText;
    LinearLayout clear;

    String myUser;
    ArrayList<String> name, time, price;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_history);
        getSupportActionBar().hide();
        back = findViewById(R.id.mainHistoryBack);
        list = findViewById(R.id.mainHistoryList);
        empty = findViewById(R.id.mainHistoryEmpty);
        clear = findViewById(R.id.mainHistoryClear);
        clearText = findViewById(R.id.mainHistoryClearText);

        myUser = getIntent().getStringExtra("username");
        empty.setVisibility(View.INVISIBLE);
        clear.setVisibility(View.INVISIBLE);

        name = new ArrayList<>();
        time = new ArrayList<>();
        price = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("history").child(myUser)
                .orderByChild("username")
                .equalTo(myUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.clear();
                time.clear();
                price.clear();
                for(DataSnapshot s1 : snapshot.getChildren()){
                    name.add(s1.child("food").getValue().toString());
                    time.add(s1.child("date").getValue().toString());
                    price.add(s1.child("total").getValue().toString());
                }
                if(name.size() > 0){
                    clear.setVisibility(View.VISIBLE);
                    myAdapter adapter = new myAdapter();
                    list.setAdapter(adapter);
                }else{
                    empty.setVisibility(View.VISIBLE);
                    list.setVisibility(View.INVISIBLE);
                    clear.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = FirebaseDatabase.getInstance().getReference("history").child(myUser);
                ref.removeValue();
                Toast.makeText(MainHistory.this, "History has been clear", Toast.LENGTH_LONG).show();
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
    class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return name.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.main_history_layout, null);
            TextView cvFood = (TextView) convertView.findViewById(R.id.historyLayoutFood);
            TextView cvTime = (TextView) convertView.findViewById(R.id.historyLayoutTime);
            TextView cvPrice = (TextView) convertView.findViewById(R.id.historyLayoutTotal);

            cvFood.setText(name.get(position));
            cvTime.setText(time.get(position));
            cvPrice.setText(price.get(position));
            return convertView;
        }
    }
}