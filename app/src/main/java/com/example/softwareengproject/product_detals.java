package com.example.softwareengproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.softwareengproject.Database.Database;
import com.example.softwareengproject.Model.Product;
import com.example.softwareengproject.databinding.ActivityProductDetalsBinding;

public class product_detals extends AppCompatActivity {

    private int aquantity = 0;
    private double price;

    ActivityProductDetalsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // تفعيل ميزة ملء الشاشة
        EdgeToEdge.enable(this);

        // ربط الواجهة باستخدام ViewBinding
        binding = ActivityProductDetalsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ضبط الحواف لتجنب شريط الحالة
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // استقبال اسم المنتج الممرر من الشاشة السابقة
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        Database database = new Database(this);
        Product product = database.getProductByName(name);

        // حماية التطبيق من الانهيار في حال عدم العثور على المنتج
        if (product != null) {
            String productName = String.valueOf(product.getName());
            int intProductQuantity = product.getQuantity(); // الحصول على الرقم مباشرة لتسهيل المقارنة
            String productPrice = String.valueOf(product.getPrice());
            Drawable productImage = product.getImg();

            // عرض البيانات على الشاشة
            binding.TvNameDetals.setText(productName);
            binding.TvQuantityDetals.setText(String.valueOf(aquantity)); // تبدأ من 0
            binding.TvPriceDetals.setText("$" + productPrice);
            binding.IvProducrimage.setBackground(productImage);

            // زر زيادة الكمية
            binding.IvAddDetals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aquantity = aquantity + 1;
                    binding.TvQuantityDetals.setText(String.valueOf(aquantity));
                }
            });

            // زر إنقاص الكمية
            binding.IvMinusDetals.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (aquantity > 0) {
                        aquantity = aquantity - 1;
                        binding.TvQuantityDetals.setText(String.valueOf(aquantity));
                    }
                }
            });

            // زر الإضافة إلى السلة
            binding.BtnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // التحقق من توافر الكمية المطلوبة في المخزون
                    if (intProductQuantity < aquantity || aquantity == 0) {
                        Toast.makeText(product_detals.this, "Invalid number. The requested quantity is not in stock or is 0", Toast.LENGTH_SHORT).show();
                    } else {
                        // حساب السعر الإجمالي وإنشاء منتج جديد للسلة
                        price = aquantity * product.getPrice();
                        Product p = new Product(product.getName(), product.getImg(), price, aquantity, product.getSection());
                        database.addProductToCart(p);

                        // إغلاق الشاشة والعودة
                        finish();
                    }
                }
            });

        } else {
            // في حال تم تمرير اسم خاطئ ولم يتم إيجاد المنتج
            Toast.makeText(this, "Product not found!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}