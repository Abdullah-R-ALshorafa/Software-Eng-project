package com.example.softwareengproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.softwareengproject.Database.Database;
import com.example.softwareengproject.Model.Product;
import com.example.softwareengproject.databinding.ActivityEditProductBinding;

import java.util.ArrayList;

public class edit_product extends AppCompatActivity {
    ActivityEditProductBinding binding;
    ArrayList<String> sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityEditProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sections=new ArrayList<>();
        sections.add("drink");
        sections.add("food");
        sections.add("tool");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,sections);
        binding.SpinnerItem.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(edit_product.this );
        builder.setTitle("Error");
        builder.setMessage("one or more filed is Empty!!");
        builder.setPositiveButton("lits fill", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        binding.BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.EtName.getText().toString().equals("") || binding.EtQuantity.getText().toString().equals("") || binding.EtPrice.getText().toString().equals("") )
                {
                    dialog.show();
                }
                else{
                    String name = binding.EtName.getText().toString();
                    int quantity = Integer.parseInt(binding.EtQuantity.getText().toString());
                    double price = Double.parseDouble(binding.EtPrice.getText().toString());
                    String section = binding.SpinnerItem.getSelectedItem().toString();
                    String oldName = binding.EtEditName.getText().toString();

                    Database database = new Database(edit_product.this);
                    Product product = new Product(name,quantity,price,section);

                    database.editProduct(product , oldName);
                    finish();

                }

            }
        });
        binding.IvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(edit_product.this , profile.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
