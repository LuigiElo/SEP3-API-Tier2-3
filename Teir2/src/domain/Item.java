package domain;

import java.io.Serializable;

public class Item implements Serializable {

    private int itemId;
    private double price;
    private String name;

    public Item(int itemId, double price, String name) {
        this.itemId = itemId;
        this.price = price;
        this.name = name;
    }

    public Item(double price, String name) {
        itemId = 0;
        this.price = price;
        this.name = name;
    }

    public Item()
    {
        
    }


    public int getitemId() {
        return itemId;
    }

    public void setitemId(int itemId) {
        itemId = itemId;
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

    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
