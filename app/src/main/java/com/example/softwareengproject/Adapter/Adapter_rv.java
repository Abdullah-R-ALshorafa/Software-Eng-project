package com.example.softwareengproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwareengproject.Model.Product;
import com.example.softwareengproject.R;

import java.util.ArrayList;

public class Adapter_rv extends RecyclerView.Adapter <Adapter_rv.MyHolder> {
    EditText quantityalert;

    Context context;
    ArrayList<Product> products;


    public Adapter_rv(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custem_recycler, null);
        MyHolder mh = new MyHolder(v);
        return mh;
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Product p = products.get(position);
        String name = p.getName();
        Drawable image = p.getImg();
        double price = p.getPrice();
        int quantity = p.getQuantity();
        String section = "";




        holder.price.setText(String.valueOf(price));
        holder.name.setText(String.valueOf(name));
        holder.quantity.setText(String.valueOf(quantity));
        holder.image.setImageDrawable(image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* connect the activity with class after making the class */
//                Intent intent = new Intent(v.getContext(), product_detals.class);
//                intent.putExtra("name", holder.name.getText().toString().trim());
//                v.getContext().startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price, name, quantity;
        CardView cardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.Tv_price);
            image = itemView.findViewById(R.id.Iv_custom_product);
            name = itemView.findViewById(R.id.Tv_Name);
            quantity = itemView.findViewById(R.id.Tv_quantity);
            cardView = itemView.findViewById(R.id.CardView_home);

        }
    }

}
