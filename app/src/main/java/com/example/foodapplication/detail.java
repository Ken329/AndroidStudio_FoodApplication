package com.example.foodapplication;

public class detail {
    int image;
    String name;
    String rate;
    String fees;

    public detail(int image, String name, String rate, String fees) {
        this.image = image;
        this.name = name;
        this.rate = rate;
        this.fees = fees;
    }

    public int getImage() {
        return this.image;
    }

    public String getName() {
        return this.name;
    }

    public String getRate() {
        return this.rate;
    }

    public String getFees() {
        return this.fees;
    }
}
