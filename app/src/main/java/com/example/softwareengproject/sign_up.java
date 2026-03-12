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
import com.example.softwareengproject.databinding.ActivitySignUpBinding;

import java.util.ArrayList;

public class sign_up extends AppCompatActivity {

    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // تفعيل ميزة ملء الشاشة
        EdgeToEdge.enable(this);

        // ربط الواجهة باستخدام ViewBinding
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ضبط الحواف لتجنب تداخل شريط الحالة
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // إعداد نافذة التنبيه (AlertDialog)
        AlertDialog.Builder builder = new AlertDialog.Builder(sign_up.this);
        builder.setTitle("Error");
        builder.setMessage("One or more fields are empty!!");
        builder.setPositiveButton("Let's fill", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // إعداد القائمة المنسدلة (Spinner)
        ArrayList<String> users = new ArrayList<>();
        users.add("admin");
        users.add("customer");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(sign_up.this, android.R.layout.simple_spinner_item, users);
        binding.SpinnerSignUp.setAdapter(adapter);

        // زر الانتقال لشاشة تسجيل الدخول (إذا كان لديه حساب بالفعل)
        binding.TvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up.this, login.class); // تأكد من اسم الكلاس login
                intent.putExtra("name", binding.EtName.getText().toString().trim());
                intent.putExtra("password", binding.EtPassword.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });

        // زر إنشاء الحساب
        binding.BtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // التحقق من الحقول الفارغة
                if (binding.EtName.getText().toString().trim().isEmpty() || binding.EtPassword.getText().toString().trim().isEmpty()) {
                    builder.create().show();
                } else {
                    Intent intent = new Intent(sign_up.this, login.class); // تأكد من اسم الكلاس login
                    String name = binding.EtName.getText().toString().trim();
                    String password = binding.EtPassword.getText().toString().trim();
                    String type = binding.SpinnerSignUp.getSelectedItem().toString();

                    Database database = new Database(sign_up.this);

                    // إضافة المستخدم لقاعدة البيانات
                    database.addUser(name, password, type);

                    // تمرير البيانات لشاشة تسجيل الدخول
                    intent.putExtra("name", name);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    finish();
                }
            }
        });

        // زر سهم الرجوع
        binding.IvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up.this, sign_in_sign_up.class); // تأكد من اسم الكلاس
                startActivity(intent);
                finish();
            }
        });
    }
}
