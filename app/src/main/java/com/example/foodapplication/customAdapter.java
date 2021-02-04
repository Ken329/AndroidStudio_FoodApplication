package com.example.foodapplication;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class customAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<detail> detailList;
    ArrayList<detail> arrayList;

    public customAdapter(Context mContext, List<detail> detailList) {
        this.context = mContext;
        this.detailList = detailList;
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
        return convertView;
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
