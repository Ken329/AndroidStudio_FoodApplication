package com.example.foodapplication;

public class calculatePrice {
    public String calculate(String food, String amount){
        double total = 0;
        double totalAmount = Double.parseDouble(amount);
        switch (food){
            case "Pizza":
                total = 21.99 * totalAmount;
                break;
            case "Beef Burger":
                total = 9.99 * totalAmount;
                break;
            case "Donut":
                total = 2.99 * totalAmount;
                break;
            case "Onion Ring":
                total = 8.99 * totalAmount;
                break;
            case "Hot Dog":
                total = 10.99 * totalAmount;
                break;
            case "Bento":
                total = 11.99 * totalAmount;
                break;
            case "Fish Set":
                total = 12.99 * totalAmount;
                break;
            case "Ham":
                total = 6.99 * totalAmount;
                break;
            case "Spring Roll":
                total = 8.99 * totalAmount;
                break;
            case "Taco Bell":
                total = 16.99 * totalAmount;
                break;
            case "Turkey":
                total = 21.99 * totalAmount;
                break;
            case "French Fries":
                total = 5.99 * totalAmount;
                break;
            case "Banana Split":
                total = 10.99 * totalAmount;
                break;
            case "Ice-Cream":
                total = 3.99 * totalAmount;
                break;
            case "Cupcake":
                total = 3.99 * totalAmount;
                break;
            case "Maccoron":
                total = 5.99 * totalAmount;
                break;
        }
        return String.valueOf(total);
    }
}
