package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handles logic for the ModifyProductForm User Interface.
 */
public class ModifyProductFormController implements Initializable {
    private Stage stage;
    private Parent scene;
    private ObservableList<Part> transientAssociatedPartsList = FXCollections.observableArrayList();

    private void addNewTransientPart(Part newAssociatedPart) {
        transientAssociatedPartsList.add(newAssociatedPart);
    }

    private boolean isNewAssocPart(Part newAssociatedPart) {
        for (Part existingAssociatedPart : transientAssociatedPartsList) {
            if (existingAssociatedPart.getId() == newAssociatedPart.getId()) {
                return false;
            }
        }
        return true;
    }

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField invTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partsIDCol;

    @FXML
    private TableColumn<Part, String> partsNameCol;

    @FXML
    private TableColumn<Part, Integer> partsInventoryCol;

    @FXML
    private TableColumn<Part, Double> partsCostCol;

    @FXML
    private TableView<Part> assocPartsTable;

    @FXML
    private TableColumn<Part, Integer> assocIDCol;

    @FXML
    private TableColumn<Part, String> assocNameCol;

    @FXML
    private TableColumn<Part, Integer> assocInvCol;

    @FXML
    private TableColumn<Part, Double> assocCostCol;

    @FXML
    private TextField modifyProductSearch;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addProduct;

    @FXML
    private Button removePartButton;

    @FXML
    void onActionRemovePart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are attempting to remove an associated part");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            transientAssociatedPartsList.remove(assocPartsTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onActionAdd(ActionEvent event) {
        Part partToAdd = partsTable.getSelectionModel().getSelectedItem();
        if(isNewAssocPart(partToAdd)) {
            addNewTransientPart(partToAdd);
        }
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Button b = (Button)event.getSource();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/InventoryManagement.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        // Initializes part variables.
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        Double price = 0.0;
        int min = 0;
        int max = 0;
        int stock = 0;

        // Exception boolean keeps track of whether or not an exception has been met. This way the save logic doesn't inadvertently continue.
        boolean exception = false;

        // Try Catch statements to avoid runtime errors. Also calls error message if exception is caught.
        try {price = Double.parseDouble(priceTextField.getText());} catch (NumberFormatException e) {
            InventoryManagementController.inputError("Invalid price input. Please input a number.");
            exception = true;
        }
        try {stock = Integer.parseInt(invTextField.getText());} catch (NumberFormatException e) {
            InventoryManagementController.inputError("Invalid inventory input. Please input a whole number.");
            exception = true;
        }
        try {min = Integer.parseInt(minTextField.getText());} catch (NumberFormatException e) {
            InventoryManagementController.inputError("Invalid minimum input. Please input a whole number.");
            exception = true;
        }
        try {max = Integer.parseInt(maxTextField.getText());} catch (NumberFormatException e) {
            InventoryManagementController.inputError("Invalid maximum input. Please input a whole number.");
            exception = true;
        }

        // Exception handling for price, min, max, and stock fields, and calls error message if exception is caught.
        // Ensures that min and price are greater than zero, and that stock value is between minimum and maximum.
        if (price < 0) {
            InventoryManagementController.inputError("Please input a price greater than zero.");
            exception = true;
        }
        if (min < 0) {
            InventoryManagementController.inputError("Please input a minimum stock greater than zero.");
            exception = true;
        }
        if (min > max) {
            InventoryManagementController.inputError("Invalid input. Minimum stock must be greater than maximum stock.");
            exception = true;
        }
        if (stock < min) {
            InventoryManagementController.inputError("Invalid input. Current stock must be greater than minimum.");
            exception = true;
        }
        if (stock > max) {
            InventoryManagementController.inputError("Invalid input. Current stock cannot be greater than maximum.");
            exception = true;
        }

        // Continues with save logic if there is no exception.
        if(!exception) {
            Product newProduct = new Product(id, name, price, stock, min, max);
            for (Part partToAdd : transientAssociatedPartsList) {
                newProduct.addAssociatedPart(partToAdd);
            }
            Inventory.updateProduct(id, newProduct);
            // Goes back to Inventory Management.
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/InventoryManagement.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Receives Product data from the onActionProductsModify event handler in InventoryManagementController.
     * @param product Product received by onActionModify event handler.
     */
    public void setTextFields(Product product) {
        idTextField.setText(String.valueOf(product.getId()));
        nameTextField.setText(product.getName());
        priceTextField.setText(String.valueOf(product.getPrice()));
        invTextField.setText(String.valueOf(product.getStock()));
        minTextField.setText(String.valueOf(product.getMin()));
        maxTextField.setText(String.valueOf(product.getMax()));
    }

    /**
     * Receives Associated Parts list from the onActionProductsModify event handler in InventoryManagement Controller.
     * Sets up the associated parts table list with data.
     * @param existingAssociatedParts List of associated parts already attached to the Product being modified.
     */
    public void setTransientAssociatedPartsList(ObservableList<Part> existingAssociatedParts) {
        for (Part part : existingAssociatedParts) {
            addNewTransientPart(part);
        }
    }

    @FXML
    void onActionSearch(ActionEvent event) {
        String searchItem = modifyProductSearch.getText();

        if(isInteger(searchItem)) {
            int integerSearchItem = Integer.parseInt(searchItem);
            if(Inventory.lookupPart(integerSearchItem) == null) {
                InventoryManagementController.inputError("No parts found with ID: " + searchItem);
            }
            else {
                if(!partsTable.getItems().contains( (Part)Inventory.lookupPart(integerSearchItem))) {
                    filteredParts.clear();
                    filteredParts.addAll(Inventory.getAllParts());
                }
                partsTable.getSelectionModel().select( (Part) Inventory.lookupPart(integerSearchItem) );
            }
        } else if (searchItem.isEmpty()) {
            filteredParts.clear();
            filteredParts.addAll(Inventory.getAllParts());
        } else {
            if(Inventory.lookupPart(searchItem) == null) {
                InventoryManagementController.inputError("No parts found with name: " + searchItem);
            } else {
                filteredParts.clear();
                filteredParts.addAll(Inventory.lookupPart(searchItem));
            }
        }
    }

    private boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(filteredParts);
        filteredParts.addAll(Inventory.getAllParts());

        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assocPartsTable.setItems(transientAssociatedPartsList);

        assocIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
