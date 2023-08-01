package com.example.b07group19;

public class Product {

    public String product;
    public double price;
    public String brand;
    public int quantity;
    public String length;
    public String width;
    public String height;
    public String turl;

    public Product(String product, double price, String brand, int quantity, String length, String width, String height, String turl) {
        this.product = product;
        this.price = price;
        this.brand = brand;
        this.quantity = quantity;
        this.length = length;
        this.width = width;
        this.height = height;
        this.turl = turl;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
