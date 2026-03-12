package com.example.softwareengproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwareengproject.Model.Product;
import com.example.softwareengproject.R;

import java.util.ArrayList;

public class Adapter_checkOut extends RecyclerView.Adapter<Adapter_checkOut.MyHolder> {

    Context context;
    ArrayList<Product> products;

    public static double totalPrice;

    public Adapter_checkOut(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public Adapter_checkOut.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custem_checkout,null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_checkOut.MyHolder holder, int position) {
        Product p = products.get(position);
        totalPrice = totalPrice +p.getPrice();
        holder.name.setText(String.valueOf(position+1+ ":  "+p.getName()));
        holder.price.setText(String.valueOf("\t$"+p.getPrice()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        TextView name,price;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Tv_nameCheckOut);
            price = itemView.findViewById(R.id.Tv_priceCheckOut);
        }
    }
}