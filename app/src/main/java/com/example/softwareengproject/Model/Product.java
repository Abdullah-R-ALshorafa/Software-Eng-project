package com.example.softwareengproject.Model;

import android.graphics.drawable.Drawable;

public class Product {
    private String name;
    private Drawable img;
    private double price;
    private int quantity;
    private String section;

    public Product(String name, int quantity, double price, String section) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.section = section;
    }

    public Product(String name, Drawable img, double price, int quantity, String section) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.quantity = quantity;
        this.section = section;
    }

    public String getSection() {
        return section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

