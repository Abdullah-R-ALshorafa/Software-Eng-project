package com.example.softwareengproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwareengproject.Adapter.Adapter_rv_forCart;
import com.example.softwareengproject.Database.Database;
import com.example.softwareengproject.Model.Product;
import com.example.softwareengproject.databinding.ActivityCartBinding;

import java.util.ArrayList;

public class cart extends AppCompatActivity {

    ActivityCartBinding binding;
    private ArrayList<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database database = new Database(this);


        Adapter_rv_forCart adapter = new Adapter_rv_forCart(this, database.getAllCartProduct());
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);

        binding.RecyclerViewCart.setAdapter(adapter);
        binding.RecyclerViewCart.setLayoutManager(manager);

        /* add the bottom navigation after make all classes */
//
//        binding.IvProfileCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(cart.this, profile.class);
//                startActivity(i);
//                finish();
//            }
//        });
//        binding.IvHomeCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(cart.this, home.class);
//                startActivity(i);
//                finish();
//            }
//        });
//        binding.BtnCheckOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(cart.this, check_out.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}
