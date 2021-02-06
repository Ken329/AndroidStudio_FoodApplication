package com.example.foodapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainCart extends AppCompatActivity {
    ImageButton back;
    ListView list;
    TextView amount, tax, total, enter, empty;
    LinearLayout priceLinear;

    String myUser;
    Double douAmount, douTax, douTotal;
    ArrayList<String> arrayName, arrayAmount, arrayPrice;
    DatabaseReference ref;
    private  static DecimalFormat d2f = new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cart);
        getSupportActionBar().hide();
        back = findViewById(R.id.mainCartBack);
        list = findViewById(R.id.mainCartList);
        amount = findViewById(R.id.mainCartAmount);
        tax = findViewById(R.id.mainCartTax);
        total = findViewById(R.id.mainCartTotal);
        enter = findViewById(R.id.mainCartEnter);
        empty = findViewById(R.id.mainCartEmpty);
        priceLinear = findViewById(R.id.linearLayout4);

        list.setVisibility(View.INVISIBLE);
        priceLinear.setVisibility(View.INVISIBLE);
        empty.setVisibility(View.INVISIBLE);
        myUser = getIntent().getStringExtra("username");

        arrayName = new ArrayList<>();
        arrayAmount = new ArrayList<>();
        arrayPrice = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("cart").child(myUser)
                .orderByChild("username")
                .equalTo(myUser);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayName.clear();
                arrayAmount.clear();
                arrayPrice.clear();
                douAmount = 0.0;
                douTax = 0.0;
                douTotal = 0.0;
                for(DataSnapshot s1 : snapshot.getChildren()){
                    String myAmount = s1.child("amount").getValue().toString();
                    String myFood = s1.child("food").getValue().toString();
                    String myPrice = s1.child("price").getValue().toString();
                    arrayAmount.add(myAmount);
                    arrayName.add(myFood);
                    arrayPrice.add(myPrice);
                    douAmount += Double.parseDouble(myPrice);
                }
                if(arrayAmount.size() == 0){
                    list.setVisibility(View.INVISIBLE);
                    priceLinear.setVisibility(View.INVISIBLE);
                    empty.setVisibility(View.VISIBLE);
                    enter.setText("Go To Cart");
                }else{
                    list.setVisibility(View.VISIBLE);
                    priceLinear.setVisibility(View.VISIBLE);
                    enter.setText("Place Order");
                    myAdapter adapter = new myAdapter();
                    list.setAdapter(adapter);
                    douTax = douAmount * 0.06;
                    douTotal = douTax + douAmount;
                    amount.setText(String.valueOf(douAmount));
                    tax.setText(String.valueOf(d2f.format(douTax)));
                    total.setText(String.valueOf(d2f.format(douTotal)));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enterText = enter.getText().toString();
                if(enterText.equals("Go To Cart")){
                    goBack(v);
                }else{
                    Toast.makeText(MainCart.this, "Thank you for ordering with us", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void goBack(View v){
        Intent intent = new Intent(v.getContext(), MainPage.class);
        intent.putExtra("username", myUser);
        startActivity(intent);
    }
    class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayName.size();
        }
        @Override
        public Object getItem(int position) {
            return position;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.main_cart_layout, null);
            TextView name = (TextView) convertView.findViewById(R.id.cartLayoutName);
            TextView amount = (TextView) convertView.findViewById(R.id.cartLayoutAmount);
            TextView price = (TextView) convertView.findViewById(R.id.cartLayoutPrice);
            TextView minus = (TextView) convertView.findViewById(R.id.cartLayoutMinus);
            TextView plus = (TextView) convertView.findViewById(R.id.cartLayoutPlus);
            calculatePrice c1 = new calculatePrice();
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newAmount = Integer.parseInt(arrayAmount.get(position)) - 1;
                    String price = c1.calculate(arrayName.get(position), String.valueOf(newAmount));
                    ref = FirebaseDatabase.getInstance().getReference("cart").child(myUser).child(arrayName.get(position));
                    if(newAmount < 1){
                        ref.removeValue();
                        Toast.makeText(MainCart.this, arrayName.get(position) + " has been removed", Toast.LENGTH_SHORT).show();
                    }else{
                        ref.child("amount").setValue(String.valueOf(newAmount));
                        ref.child("price").setValue(price);
                    }
                }
            });
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int newAmount = Integer.parseInt(arrayAmount.get(position)) + 1;
                    String price = c1.calculate(arrayName.get(position), String.valueOf(newAmount));
                    ref = FirebaseDatabase.getInstance().getReference("cart").child(myUser).child(arrayName.get(position));
                    ref.child("amount").setValue(String.valueOf(newAmount));
                    ref.child("price").setValue(price);
                }
            });

            name.setText(arrayName.get(position));
            amount.setText(arrayAmount.get(position));
            price.setText(arrayPrice.get(position));
            return convertView;
        }
    }
}