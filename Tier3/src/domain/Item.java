package domain;

import java.io.Serializable;

public class Item implements Serializable {

    private int ItemID;
    private double price;
    private String name;

    public Item(int itemID, double price, String name) {
        this.ItemID = itemID;
        this.price = price;
        this.name = name;
    }

    public Item(double price, String name) {
        ItemID = 0;
        this.price = price;
        this.name = name;
    }

    public Item()
    {

    }


    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
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
