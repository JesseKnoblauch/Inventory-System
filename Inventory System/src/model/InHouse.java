package model;

 // @Author Jesse Knoblauch
/**
 * This class is a Part subclass with a unique MachineId variable.
 */
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /**
     * Sets machineId variable for instance of InHouse Part.
     * @param machineId The machineID to set
     */
    public void setMachineId(int machineId) { this.machineId = machineId; }
    /**
     * Retrieves machineId variable.
     * @return The machineId variable.
     */
    public int getMachineId() { return machineId; }
}