package com.example.softwareengproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.softwareengproject.Model.Product;
import com.example.softwareengproject.Model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    Context context;
    private static final String name = "Store";
    private final String tableProduct = "product";
    private final String tableUsers = "users";
    private final String tableCartProduct = "cartProduct";
    private static int version = 18;
    private Product p;

    public Database(@Nullable Context context) {
        super(context, name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create table " + tableProduct + "" +
                "(id INTEGER primary key autoincrement," +
                "name text," +
                "quantity INTEGER not null," +
                "price double," +
                "section text," +
                "image BLOB);";

        String query2 = "Create table " + tableCartProduct + "" +
                "(id INTEGER primary key autoincrement," +
                "name text," +
                "quantity INTEGER not null," +
                "price double," +
                "section text," +
                "image BLOB);";

        String query3 = "create table " + tableUsers + "" +
                "(name text not null," +
                "password text not null," +
                "type text);";
        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + tableProduct;
        String queryUser = "DROP TABLE IF EXISTS " + tableUsers;
        String queryCart = "DROP TABLE IF EXISTS " + tableCartProduct;
        db.execSQL(query);
        db.execSQL(queryUser);
        db.execSQL(queryCart);
        onCreate(db);

    }

    public void addProduct(String name, int quantity, double price, String type, Drawable drawable) {
        Database database = new Database(context);

        SQLiteDatabase data = database.getWritableDatabase();

        //convert from drawable to bitmap to byte
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        byte[] byteArray = stream.toByteArray();


        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("price", price);
        values.put("section", type);
        values.put("image", byteArray);


        long rowId = data.insert(tableProduct, null, values);

        if (rowId == -1) {
            Toast.makeText(context, "Filed to add product", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Product> getAllProduct() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + tableProduct, null);
        ArrayList<Product> products = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            int quantity = cursor.getInt(2);
            double price = cursor.getDouble(3);
            String section = cursor.getString(4);
            byte[] image = cursor.getBlob(5);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);

            Product p = new Product(name, drawable, price, quantity, section);
            products.add(p);


        }
        return products;
    }

    public void deleteProduct(String name) {
        SQLiteDatabase database = getWritableDatabase();

        long rowId = database.delete(tableCartProduct, "name = ?", new String[]{name});

        if (rowId == -1) {
            Toast.makeText(context, "Filed to delete product", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
        }

    }
    public void editProduct (Product product , String rowName) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name",product.getName());
        values.put("quantity",product.getQuantity());
        values.put("price", product.getPrice());
        values.put("section" , product.getSection());

        long rowId = database.update(tableProduct , values,"name = ?",new String[]{rowName} );

        if(rowId == -1){
            Toast.makeText(context, "Filed to update product", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "update Success", Toast.LENGTH_SHORT).show();
        }

    }

    public Product getProductByName(String inputName){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableProduct + " WHERE  TRIM(name) = '"+inputName.trim()+"'" ,null);


        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            int quantity = cursor.getInt(2);
            double price = cursor.getDouble(3);
            String section =cursor.getString(4);
            byte[] image =cursor.getBlob(5);

            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            p = new Product(name , drawable,price,quantity,section);


        }

        return p;
    }

    public Product getProductByType(String inputType){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + tableProduct + " WHERE  TRIM(section) = '"+inputType.trim()+"'" ,null);


        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            int quantity = cursor.getInt(2);
            double price = cursor.getDouble(3);
            String section =cursor.getString(4);
            byte[] image =cursor.getBlob(5);

            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
            p = new Product(name , drawable,price,quantity,section);

        }

        return p;
    }

    public void addProductToCart (Product product){
        Database database = new Database(context);

        SQLiteDatabase data = database.getWritableDatabase();

        //convert from drawable to bitmap to byte
        BitmapDrawable bitmapDrawable = (BitmapDrawable) product.getImg();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        byte[] byteArray = stream.toByteArray();


        ContentValues values = new ContentValues();
        values.put("name",product.getName());
        values.put("quantity",product.getQuantity());
        values.put("price", product.getPrice());
        values.put("section" , product.getSection());
        values.put("image",byteArray);


        long rowId = data.insert(tableCartProduct, null, values);

        if(rowId == -1){
            Toast.makeText(context, "Filed to add product to cart", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Success to add product to cart", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Product> getAllCartProduct(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from "+ tableCartProduct,null);
        ArrayList<Product> products = new ArrayList<>();
        while (cursor.moveToNext()){
            String name = cursor.getString(1);
            int quantity = cursor.getInt(2);
            double price = cursor.getDouble(3);
            String section =cursor.getString(4);
            byte[] image =cursor.getBlob(5);

            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);

            Product p = new Product(name , drawable,price,quantity,section);
            products.add(p);


        }
        return products;
    }

    public void addUser(String name ,String password , String type ){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name" , name);
        values.put("password" , password);
        values.put("type" , type);

        long rowId =  database.insert(tableUsers , null , values);

        if(rowId == -1){
            Toast.makeText(context, "Filed to add user", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Success to add user", Toast.LENGTH_SHORT).show();
        }
    }
    String nameUser;
    String passwordUser;
    String typeUser;

    public User getUserInfo(String userName){

        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + tableUsers + " WHERE TRIM(name) = ?",
                new String[]{userName.trim()}
        );

        if (cursor.moveToFirst()) {

            String nameUser = cursor.getString(0);
            String passwordUser = cursor.getString(1);
            String typeUser = cursor.getString(2);

            cursor.close();

            return new User(nameUser, passwordUser, typeUser);
        }

        cursor.close();
        return null;
    }

    public void editUser (User user,String userName) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name",user.getName());
        values.put("password",user.getPassword());
        values.put("type" , user.getType());

        long rowId = database.update(tableUsers , values,"name = ?",new String[]{userName} );

        if(rowId == -1){
            Toast.makeText(context, "Filed to edit user", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Edit Success", Toast.LENGTH_SHORT).show();
        }

    }

}
