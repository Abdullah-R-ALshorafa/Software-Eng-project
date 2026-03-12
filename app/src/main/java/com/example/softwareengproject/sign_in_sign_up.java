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

import com.example.softwareengproject.databinding.ActivitySignInSignUpBinding;

public class sign_in_sign_up extends AppCompatActivity {

    ActivitySignInSignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // تفعيل ميزة ملء الشاشة
        EdgeToEdge.enable(this);

        // ربط الواجهة باستخدام ViewBinding
        binding = ActivitySignInSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ضبط الحواف لتجنب تداخل شريط الحالة
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // رسالة الترحيب
        Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();

        // زر الانتقال لشاشة تسجيل الدخول
        binding.BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // استخدمنا login.class بناءً على التعديلات السابقة
                Intent i = new Intent(sign_in_sign_up.this, login.class);
                startActivity(i);
                finish();
            }
        });

        // زر الانتقال لشاشة إنشاء حساب جديد (لاحظت أنك سميت الزر BtnSignUn في كودك)
        binding.BtnSignUn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // افترضت أن اسم كلاس التسجيل الجديد هو sign_up بدلاً من Sign_up
                Intent intent = new Intent(sign_in_sign_up.this, sign_up.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
