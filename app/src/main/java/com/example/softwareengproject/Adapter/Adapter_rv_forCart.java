package com.example.softwareengproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwareengproject.Database.Database;
import com.example.softwareengproject.Model.Product;
import com.example.softwareengproject.R;

import java.util.ArrayList;

public class Adapter_rv_forCart extends RecyclerView.Adapter <Adapter_rv_forCart.MyHolder> {

    Context context;
    ArrayList<Product> products ;

    public Adapter_rv_forCart(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custem_recycler_cart,null);
        MyHolder myHolder = new MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        int pos = position;


        Product p = products.get(position);
        holder.name.setText("name: "+p.getName());
        holder.price.setText("price: "+String.valueOf(p.getPrice()));
        holder.quantity.setText("Quantity" + p.getQuantity());
        holder.imageView.setImageDrawable(p.getImg());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database =new Database(v.getContext());
                database.deleteProduct(p.getName());
                if(products.size() != pos ){
                    products.remove(pos);
                    notifyItemRemoved(pos);
                }else{
                    products.equals(null) ;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView name , price,quantity;
        ImageView imageView , img;



        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Tv_NameCart);
            price = itemView.findViewById(R.id.Tv_priceCart);
            imageView = itemView.findViewById(R.id.Iv_custom_productCart);
            img = imageView.findViewById(R.id.Iv_delete);
            quantity =itemView.findViewById(R.id.Tv_quantityCard);

        }
    }
}
