package com.example.foodapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class customAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<detail> detailList;
    ArrayList<detail> arrayList;
    String myUser;
    DatabaseReference ref;
    public customAdapter(Context mContext, List<detail> detailList, String username) {
        this.context = mContext;
        this.detailList = detailList;
        this.myUser = username;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<detail>();
        this.arrayList.addAll(detailList);
    }
    public class ViewHolder{
        TextView nameView, rateView, feeView;
        ImageView imgView;
    }
    @Override
    public int getCount() {
        return detailList.size();
    }

    @Override
    public Object getItem(int position) {
        return detailList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.main_search_layout, null);

            holder.imgView = convertView.findViewById(R.id.searchLayoutImage);
            holder.nameView = convertView.findViewById(R.id.searchLayoutName);
            holder.rateView = convertView.findViewById(R.id.searchLayoutRate);
            holder.feeView = convertView.findViewById(R.id.searchLayoutFee);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imgView.setImageResource(detailList.get(position).getImage());
        holder.nameView.setText(detailList.get(position).getName());
        holder.rateView.setText(detailList.get(position).getRate());
        holder.feeView.setText(detailList.get(position).getFees());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = null;
                switch (detailList.get(position).getName()){
                    case "Banana Split":
                        food = "Banana Split";
                        break;
                    case "Beef Burger":
                        food = "Beef Burger";
                        break;
                    case "Bento":
                        food = "Bento";
                        break;
                    case "Cupcake":
                        food = "Cupcake";
                        break;
                    case "Donut":
                        food = "Donut";
                        break;
                    case "Fish Set":
                        food = "Fish Set";
                        break;
                    case "French Fries":
                        food = "French Fries";
                        break;
                    case "Ham":
                        food = "Ham";
                        break;
                    case "Hot Dog":
                        food = "Hot Dog";
                        break;
                    case "Ice-Cream":
                        food = "Ice-Cream";
                        break;
                    case "Maccoron":
                        food = "Maccoron";
                        break;
                    case "Onion Ring":
                        food = "Onion Ring";
                        break;
                    case "Pizza":
                        food = "Pizza";
                        break;
                    case "Spring Roll":
                        food = "Spring Roll";
                        break;
                    case "Taco Bell":
                        food = "Taco Bell";
                        break;
                    case "Turkey":
                        food = "Turkey";
                        break;
                    default:
                        return;
                }
                addFood(food);
            }
        });
        return convertView;
    }
    public void addFood(String food){
        ref = FirebaseDatabase.getInstance().getReference("cart").child(myUser).child(food);
        calculatePrice c1 = new calculatePrice();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int value = Integer.parseInt(snapshot.child("amount").getValue().toString()) + 1;
                    String price = c1.calculate(food, String.valueOf(value));
                    ref = FirebaseDatabase.getInstance().getReference("cart").child(myUser).child(food);
                    ref.child("amount").setValue(String.valueOf(value));
                    ref.child("price").setValue(price);
                    Toast.makeText(context.getApplicationContext(), food + " Has been added to cart", Toast.LENGTH_LONG).show();
                }else{
                    String price = c1.calculate(food, "1");
                    ref = FirebaseDatabase.getInstance().getReference("cart").child(myUser).child(food);
                    ref.child("food").setValue(food);
                    ref.child("amount").setValue("1");
                    ref.child("username").setValue(myUser);
                    ref.child("price").setValue(price);
                    Toast.makeText(context.getApplicationContext(), food + " has been added to cart", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        detailList.clear();
        if(charText.length() == 0){
            detailList.addAll(arrayList);
        }else{
            for(detail d1 : arrayList){
                if(d1.getName().trim().toLowerCase(Locale.getDefault()).contains(charText)){
                    detailList.add(d1);
                }
            }
        }
        notifyDataSetChanged();
    }
}
