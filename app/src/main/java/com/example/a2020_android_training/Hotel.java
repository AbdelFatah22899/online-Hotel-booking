package com.example.a2020_android_training;


public class Hotel {
    public String name, address;
    public int single_capacity, double_capacity, singleRoom_Price, doubleRoom_price, quality, image_num;
    public float review;


    public Hotel(String name, int single_capacity, int double_capacity, int singleRoom_price, int doubleRoom_price, int quality, float review, String address, int image_num) {
        this.name = name;
        this.address = address;
        this.single_capacity = single_capacity;
        this.double_capacity = double_capacity;
        this.singleRoom_Price = singleRoom_price;
        this.doubleRoom_price = doubleRoom_price;
        this.quality = quality;
        this.image_num = image_num;
        this.review = review;
    }


}
