package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Initiates application.
 * <p>
 * Javadoc located in the main zip directory along-side the project folder.
 * <p>
 * FUTURE IMPROVEMENT: Implement a list of disabled/deleted Parts and/or Products which can be accessed from the InventoryManagement Stage.
 * Parts and/or Products could be restored to their respective Inventory lists with newly generated IDs to maintain data integrity.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/InventoryManagement.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Provided Data
        Part part1 = new InHouse(Inventory.getUniquePartID(), "3/4mm bolt", 0.50, 500, 250, 500, 1);
        Part part2 = new InHouse(Inventory.getUniquePartID(), "1/2mm bolt", 0.65, 350, 150, 400, 1);
        Part part3 = new InHouse(Inventory.getUniquePartID(), "Steering Ribbon", 125, 40, 20, 50, 2);
        Part part4 = new Outsourced(Inventory.getUniquePartID(), "6-pin Cable", 15, 25, 20, 30, "Made Up Company");
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Product product1 = new Product(Inventory.getUniqueProductID(), "Mercedes", 25000.00, 10, 5, 20);
        Product product2 = new Product(Inventory.getUniqueProductID(), "Audio", 30000.00, 8, 5, 10);
        product1.addAssociatedPart(part2);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch(args);
    }
}
