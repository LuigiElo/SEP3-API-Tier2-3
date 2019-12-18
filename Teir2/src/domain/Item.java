package domain;

import java.io.Serializable;

/**
 *Java class defining and incapacitating the characteristics of an Item
 */
public class Item implements Serializable {
    /**
     *Properties of an Item:
     * -an unique id;
     * -a price of the item
     * -a name of the item
     */
    private int itemId;
    private double price;
    private String name;

    /**
     * Constructor of an Item object
     * @param itemId
     * @param price
     * @param name
     */
    public Item(int itemId, double price, String name) {
        this.itemId = itemId;
        this.price = price;
        this.name = name;
    }

    /**
     * Constructor of an Item object
     * @param price
     * @param name
     */
    public Item(double price, String name) {
        itemId = 0;
        this.price = price;
        this.name = name;
    }

    /**
     *Empty constructor of an Item object
     */
    public Item() { }

    /**
     *Getter for the itemId variable
     * @return itemId
     */
    public int getitemId() {
        return itemId;
    }

    /**
     *Setter for the itemId variable
     * @param itemId
     */
    public void setitemId(int itemId) {
        itemId = itemId;
    }

    /**
     *Getter for the price variable
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     *Setter for the price variable
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *Getter for the name variable
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *Setter for the name variable
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
/**
 * Returns a String representation of an Item object
 */
    @Override
    public String toString() {
        return "Item{" +
                "itemId=" + itemId +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
