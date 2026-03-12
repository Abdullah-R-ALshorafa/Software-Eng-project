package com.example.softwareengproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.softwareengproject.Database.Database;
import com.example.softwareengproject.Model.User;
import com.example.softwareengproject.databinding.ActivityLoginBinding;

public class login extends AppCompatActivity {

    String nameData;
    String passwordData;
    ActivityLoginBinding binding;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // تفعيل ميزة ملء الشاشة
        EdgeToEdge.enable(this);

        // ربط الواجهة باستخدام ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ضبط الحواف لتجنب تداخل شريط الحالة
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database database = new Database(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
        builder.setTitle("Error");
        builder.setMessage("There is no account with this info \nor you didn't check the policy!!");
        builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog.Builder builder1 = new AlertDialog.Builder(login.this);
        builder1.setTitle("Error");
        builder1.setMessage("One or more fields are empty!!");
        builder1.setPositiveButton("Let's fill", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // زر الرجوع
        binding.IvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, sign_in_sign_up.class);
                startActivity(intent);
                finish();
            }
        });

        // استقبال البيانات الممررة من شاشة التسجيل
        Intent i = getIntent();
        if (i != null) {
            String name = i.getStringExtra("name");
            String password = i.getStringExtra("password");
            if (name != null) binding.EtName.setText(name);
            if (password != null) binding.EtPassword.setText(password);
        }

        // زر تسجيل الدخول
        binding.BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // تم إصلاح الخطأ هنا: فحص حقل الباسورد بدلاً من فحص الاسم مرتين
                // وتم استخدام isEmpty() و trim() لتفادي المسافات الفارغة
                if (binding.EtName.getText().toString().trim().isEmpty() || binding.EtPassword.getText().toString().trim().isEmpty()) {
                    builder1.create().show();
                } else {
                    nameData = binding.EtName.getText().toString().trim();
                    passwordData = binding.EtPassword.getText().toString().trim();

                    user = database.getUserInfo(nameData);

                    if (user == null) {
                        builder.create().show();
                        return;
                    }

                    if (!user.getPassword().equals(passwordData)) {
                        builder.create().show();
                        return;
                    }
                    SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", nameData);
                    editor.apply();
                    /* move to home after making the file */
//                    Intent intent = new Intent(login.this, home.class);
//                    startActivity(intent);
//                    finish();

                }
            }
        });
    }
}