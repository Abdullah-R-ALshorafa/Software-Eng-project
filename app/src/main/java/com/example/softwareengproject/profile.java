package com.example.softwareengproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.softwareengproject.Database.Database;
import com.example.softwareengproject.Model.User;
import com.example.softwareengproject.databinding.ActivityProfileBinding;

public class profile extends AppCompatActivity {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database database = new Database(this);

        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userName = prefs.getString("username", null);

        if (userName == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
        }


        if (userName != null) {
            User user = database.getUserInfo(userName);

            if (user != null) {
                binding.TvName.setText("Name: " + user.getName());
                binding.TvPassword.setText("Password: " + user.getPassword());
                binding.TvAccountType.setText("Account type: " + user.getType());

                if (user.getType().equals("admin")) {
                    binding.LinearAddProduct.setVisibility(View.VISIBLE);
                    binding.LinearEditproduct.setVisibility(View.VISIBLE);
                } else {
                    binding.LinearAddProduct.setVisibility(View.INVISIBLE);
                    binding.LinearEditproduct.setVisibility(View.INVISIBLE);
                }
            } else {
                Toast.makeText(this, "User not found in database!", Toast.LENGTH_SHORT).show();
            }
        }


        /* add the bottom navigation after make all classes */
//        binding.LinearAddProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(profile.this, add_product.class);
//                startActivity(intent);
//            }
//        });
//
//        binding.LinearEditproduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(profile.this, edit_product.class);
//                startActivity(intent);
//            }
//        });
//
//        binding.LinearEditUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(profile.this, edit_user.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        binding.IvCartProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(profile.this, cart.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        binding.IvHomeProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(profile.this, home.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}


    /*
    @Override
    public void onCreateContextMenu(android.view.ContextMenu menu, View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        android.view.MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.custem_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull android.view.MenuItem item) {
        if (item.getItemId() == R.id.Iv_logout) {
            Intent intent = new Intent(this, Sign_in_Sign_up.class); // تأكد من الاسم
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.Edit_profile){
            return true;
        }
        return super.onOptionsItemSelected(item);

     */
