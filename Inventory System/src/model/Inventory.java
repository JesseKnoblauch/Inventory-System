package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Data Provider for Parts list.
 */

// @Author Jesse Knoblauch

public class Inventory {

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Adds a new Part to the allParts list.
     * @param newPart Part to be added to the allParts list.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /**
     * Adds a new Product to the allProducts list.
     * @param newProduct Product to be added to the allProducts list.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches allParts list and returns a single Part whose ID matches the parameter partID. Returns null if there is no match.
     * @param partID Part ID to search for.
     * @return Part to return.
     */
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if(part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches allProducts list and returns a single Product whose ID matches the parameter productID. Returns null if there is no match.
     * @param productID Product ID to search for.
     * @return Product to return.
     */
    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if(product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Searches allParts list and returns a list of Parts whose name contains the parameter partName. Returns null if there is no match.
     * @param partName Part name to search for.
     * @return ObservableList of Parts.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partsReturn = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if(part.getName().contains(partName)) {
                partsReturn.add(part);
            }
        }
        if(partsReturn.isEmpty()) return null;
        return partsReturn;
    }

    /**
     * Searches allProducts list and returns a list of Products whose name contains the parameter productName. Returns null if there is no match.
     * @param productName Product name to search for.
     * @return ObservableList of Products.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productsReturn = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if(product.getName().contains(productName)) {
                productsReturn.add(product);
            }
        }
        if(productsReturn.isEmpty()) return null;
        return productsReturn;
    }

    /**
     * Replaces existing Part from allParts with new Part.
     * @param id ID of Part to be replaced.
     * @param partToUpdate New Part to be inserted into allParts list.
     */
    public static void updatePart(int id, Part partToUpdate) {
        int index = -1;
        for(Part part : allParts) {
            index++;

            if(part.getId() == id) {
                allParts.set(index, partToUpdate);
            }
        }
    }

    /**
     * Replaces existing Product from allProducts with new Product.
     * @param id ID of Product to be replaced.
     * @param productToUpdate New Product to be inserted into allProducts list.
     */
    public static void updateProduct(int id, Product productToUpdate) {
        int index = -1;
        for(Product product : allProducts) {
            index++;

            if(product.getId() == id) {
                allProducts.set(index, productToUpdate);
            }
        }
    }

    /**
     * Removes a specific Part from the allParts list.
     * @param selectedPart Specific Part to be removed.
     * @return Returns true if a Part has been deleted, and false if it has not.
     */
    public static boolean deletePart(Part selectedPart) {
        if(allParts.remove(selectedPart)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a specific Product in the allProducts list.
     * @param selectedProduct Specific Product to be removed.
     * @return Returns true if a Product has been deleted, and false if it has not.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * Retrieves all Parts on the allParts list.
     * @return allParts - allParts ObservableList.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Retrieves all Products on the allProducts list.
     * @return allProducts - allProducts ObservableList.
     */
    public static ObservableList<Product> getAllProducts() {return allProducts;};

    private static int partID = 0;
    private static int productID = 0;

    /**
     * Generates a unique Part ID
     * @return partID - The newly generated Part ID
     */
    public static int getUniquePartID() {
        partID = partID + 1;
        return partID;
    }

    /**
     * Generates a unique Product ID
     * @return productID - The newly generated Product ID
     */
    public static int getUniqueProductID() {
        productID = productID + 1;
        return productID;
    }
}