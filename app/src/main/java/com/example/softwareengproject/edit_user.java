package com.example.softwareengproject;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.softwareengproject.databinding.ActivityEditUserBinding;

public class edit_user extends AppCompatActivity {

    ActivityEditUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // تفعيل ميزة ملء الشاشة
        EdgeToEdge.enable(this);

        // ربط الواجهة باستخدام ViewBinding
        binding = ActivityEditUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ضبط الحواف لتجنب تداخل شريط الحالة وأزرار التنقل
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // إعداد رسالة التنبيه (AlertDialog)
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Error");
        builder1.setMessage("One or more fields are empty!!");
        builder1.setPositiveButton("Let's fill", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // زر الرجوع إلى شاشة البروفايل
        binding.IvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(edit_user.this, profile.class);
                startActivity(intent);
                finish();
            }
        });

        // زر حفظ التعديلات
        binding.BtnFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // التحقق من أن الحقول ليست فارغة
                if (binding.EtOldName.getText().toString().trim().equals("") ||
                        binding.EtNewName.getText().toString().trim().equals("") ||
                        binding.EtNewPassword.getText().toString().trim().equals("") ||
                        binding.EtNewType.getText().toString().trim().equals("")) {

                    builder1.create().show();

                } else {
                    // تحديث بيانات المستخدم في قاعدة البيانات
                    Database database = new Database(edit_user.this);
                    User user = new User(
                            binding.EtNewName.getText().toString(),
                            binding.EtNewPassword.getText().toString(),
                            binding.EtNewType.getText().toString()
                    );

                    database.editUser(user, binding.EtOldName.getText().toString());

                    // الانتقال إلى شاشة تسجيل الدخول بعد التعديل
                    Intent intent = new Intent(edit_user.this, login.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
