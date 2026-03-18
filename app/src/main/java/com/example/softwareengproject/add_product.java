package com.example.softwareengproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.softwareengproject.Database.Database;
import com.example.softwareengproject.databinding.ActivityAddProductBinding;

import java.util.ArrayList;

public class add_product extends AppCompatActivity {
   ActivityAddProductBinding binding;
    ArrayList<String> sections;

    // تم نقل تعريف الـ launcher إلى هنا ليكون متعرفاً عليه في كامل الكلاس
    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. تم إغلاق هذه الدالة بشكل صحيح هنا، وعدم وضع الأكواد الأخرى بداخلها
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }); // إغلاق القوس هنا

        // 2. تم تهيئة الـ launcher في بداية الـ onCreate
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) { // تأكد إضافي لمنع انهيار التطبيق إذا تراجع المستخدم
                    Uri uri = result.getData().getData();
                    binding.IvProduct.setImageURI(uri);
                    binding.TvImg.setText("");
                }
            }
        });

        // 3. باقي أكوادك تعمل الآن ضمن النطاق الصحيح لدالة onCreate
        sections = new ArrayList<>();
        sections.add("drink");
        sections.add("food");
        sections.add("tool");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sections);
        binding.SpinnerItem.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(add_product.this);
        builder.setTitle("Error");
        builder.setMessage("one or more filed is Empty!!");
        builder.setPositiveButton("lits fill", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();

        binding.IvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(i);
            }
        });

        binding.BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.EtName.getText().toString().equals("") || binding.EtQuantity.getText().toString().equals("") || binding.EtPrice.getText().toString().equals("") || binding.IvProduct.getDrawable() == null) {
                    dialog.show();
                } else {
                    String name = binding.EtName.getText().toString();
                    int quantity = Integer.parseInt(binding.EtQuantity.getText().toString());
                    double price = Double.parseDouble(binding.EtPrice.getText().toString());
                    Drawable image = binding.IvProduct.getDrawable();
                    String section = binding.SpinnerItem.getSelectedItem().toString();

                    Database database = new Database(add_product.this);
                    database.addProduct(name, quantity, price, section, image);
                    finish();
                }
            }
        });
    }
}
