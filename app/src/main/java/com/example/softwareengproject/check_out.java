package com.example.softwareengproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwareengproject.Adapter.Adapter_checkOut;
import com.example.softwareengproject.Database.Database;
import com.example.softwareengproject.Model.Product;
import com.example.softwareengproject.databinding.ActivityCheckOutBinding;

import java.util.ArrayList;

public class check_out extends AppCompatActivity {

    ActivityCheckOutBinding binding;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);


        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Database database = new Database(this);
        products = database.getAllCartProduct();


        Adapter_checkOut adapter_checkOut = new Adapter_checkOut(this, products);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        binding.RecyclerViewCheckOut.setAdapter(adapter_checkOut);
        binding.RecyclerViewCheckOut.setLayoutManager(manager);


        binding.CbPayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.TvTotalPrice.setText("Total price: " + Adapter_checkOut.totalPrice);
            }
        });


        binding.BtnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.CbPayAll.isChecked()) {
                    Toast.makeText(check_out.this, "Payment successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(check_out.this, home.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(check_out.this, "You should check the button", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.IvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(check_out.this,cart.class);
                startActivity(intent);
            }
        });
    }
}