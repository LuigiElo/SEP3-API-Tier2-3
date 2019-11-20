package model;

import java.io.Serializable;

public class Item implements Serializable {

    private String ItemID;
    private double price;
    private String name;

    public Item(String itemID, double price, String name) {
        ItemID = itemID;
        this.price = price;
        this.name = name;
    }


    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
