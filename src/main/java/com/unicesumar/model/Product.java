package com.unicesumar.model;

import java.util.UUID;

// Classe que representa a entidade "products"
public class Product extends Entity {
    private String name;
    private double price;

    public Product(String name, double price) {
        super();
        this.name = name;
        this.price = price;
    }

    public Product(UUID uuid, String name, double price) {
        super(uuid);
        this.name = name;
        this.price = price;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return String.format("%s | %s | %s", getUuid(), name, price);
    }
}
