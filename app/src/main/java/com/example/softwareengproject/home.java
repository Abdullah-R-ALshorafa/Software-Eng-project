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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softwareengproject.Adapter.Adapter_rv;
import com.example.softwareengproject.Database.Database;
import com.example.softwareengproject.Model.Product;
import com.example.softwareengproject.databinding.ActivityHomeBinding;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // تفعيل ميزة ملء الشاشة
        EdgeToEdge.enable(this);

        // ربط الواجهة باستخدام ViewBinding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database database = new Database(home.this);
        ArrayList<Product> products = database.getAllProduct();
        Adapter_rv adapterRv = new Adapter_rv(home.this, products);
        RecyclerView.LayoutManager manager = new GridLayoutManager(home.this, 2);

        binding.RecyclerView.setAdapter(adapterRv);
        binding.RecyclerView.setLayoutManager(manager);


        binding.IvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, profile.class);
                startActivity(intent);
                finish();
            }
        });

        binding.IvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, cart.class);
                startActivity(intent);
                finish();
            }
        });

        binding.IvCartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, cart.class);
                startActivity(intent);
                finish();
            }
        });

        binding.IcSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.EtSearch.getText().toString().trim().equals("")) {
                    Toast.makeText(home.this, "The field should not be empty", Toast.LENGTH_SHORT).show();
                } else {
                    Product p = database.getProductByName(binding.EtSearch.getText().toString());
                    ArrayList<Product> searched = new ArrayList<>();
                    if (p != null) {
                        searched.add(p);
                    }
                    Adapter_rv adapterRv1 = new Adapter_rv(home.this, searched);
                    binding.RecyclerView.setAdapter(adapterRv1);
                    binding.RecyclerView.setLayoutManager(manager);
                }
            }
        });

        View all = binding.TvAll;
        View drink = binding.TvDrinks;
        View food = binding.TvFood;
        View tool = binding.TvTools;

        binding.TvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackground(getDrawable(R.drawable.sectoins_after_click));
                drink.setBackground(getDrawable(R.drawable.selector));
                food.setBackground(getDrawable(R.drawable.selector));
                tool.setBackground(getDrawable(R.drawable.selector));

                binding.RecyclerView.setAdapter(adapterRv);
                binding.RecyclerView.setLayoutManager(manager);
            }
        });

        binding.TvDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackground(getDrawable(R.drawable.selector));
                drink.setBackground(getDrawable(R.drawable.sectoins_after_click));
                food.setBackground(getDrawable(R.drawable.selector));
                tool.setBackground(getDrawable(R.drawable.selector));

                Product p = database.getProductByType("drink");
                ArrayList<Product> searched = new ArrayList<>();
                if (p != null) searched.add(p);
                Adapter_rv adapterRv1 = new Adapter_rv(home.this, searched);
                binding.RecyclerView.setAdapter(adapterRv1);
                binding.RecyclerView.setLayoutManager(manager);
            }
        });

        binding.TvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackground(getDrawable(R.drawable.selector));
                drink.setBackground(getDrawable(R.drawable.selector));
                food.setBackground(getDrawable(R.drawable.sectoins_after_click));
                tool.setBackground(getDrawable(R.drawable.selector));

                Product p = database.getProductByType("food");
                ArrayList<Product> searched = new ArrayList<>();
                if (p != null) searched.add(p);
                Adapter_rv adapterRv1 = new Adapter_rv(home.this, searched);
                binding.RecyclerView.setAdapter(adapterRv1);
                binding.RecyclerView.setLayoutManager(manager);
            }
        });

        binding.TvTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all.setBackground(getDrawable(R.drawable.selector));
                drink.setBackground(getDrawable(R.drawable.selector));
                food.setBackground(getDrawable(R.drawable.selector));
                tool.setBackground(getDrawable(R.drawable.sectoins_after_click));

                Product p = database.getProductByType("tool");
                ArrayList<Product> searched = new ArrayList<>();
                if (p != null) searched.add(p);
                Adapter_rv adapterRv1 = new Adapter_rv(home.this, searched);
                binding.RecyclerView.setAdapter(adapterRv1);
                binding.RecyclerView.setLayoutManager(manager);
            }
        });
    }
}
