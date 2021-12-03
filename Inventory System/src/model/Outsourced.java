package model;

// @Author Jesse Knoblauch

/**
 * This class is a Part subclass with a unique companyName variable.
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets machineId variable for instance of InHouse Part.
     * @param companyName The companyName to set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Retrieves companyName variable.
     * @return The companyName variable.
     */
    public String getCompanyName() {
        return companyName;
    }
}