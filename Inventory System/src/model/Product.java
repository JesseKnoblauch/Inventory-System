package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product data class.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price The price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock The stock to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min The min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max The max to set.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return id - Product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * @return name - Product name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return price - Product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return stock - Product stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return min - Product min.
     */
    public int getMin() {
        return min;
    }

    /**
     * @return max - Product max.
     */
    public int getMax() {
        return max;
    }

    /**
     * @return this.associatedParts - associatedParts list for instance of Product.
     */
    public ObservableList<Part> getAllAssociatedParts() { return this.associatedParts; }

    /**
     * @param newAssociatedPart New Part to be added to associatedParts list.
     */
    public void addAssociatedPart(Part newAssociatedPart) {
        this.associatedParts.add(newAssociatedPart);
    }

    /**
     * Removes a Part from the associatedParts list.
     * @param selectedAssociatedPart Part to be removed from associatedParts list.
     * @return Returns true if associated Parts have been deleted, and false if they have not.
     */
    public boolean deleteAssociatedParts(Part selectedAssociatedPart) {
        if(associatedParts.remove(selectedAssociatedPart)) { return true; } else { return false; }
    }
}