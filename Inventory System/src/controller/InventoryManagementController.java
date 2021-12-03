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
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handles logic for the InventoryManagement User Interface.
 */
public class InventoryManagementController implements Initializable {
    private Stage stage;
    private Parent scene;
    private Parent root;

    @FXML
    private TextField productsSearch;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productsID;

    @FXML
    private TableColumn<Product, String> productsNameCol;

    @FXML
    private TableColumn<Product, Integer> productsInventoryCol;

    @FXML
    private TableColumn<Product, Double> productsPriceCol;

    @FXML
    private Button productsAdd;

    @FXML
    private Button productsModify;

    @FXML
    private Button productsDelete;

    @FXML
    private TextField partsSearch;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partsIDCol;

    @FXML
    private TableColumn<Part, String> partsNameCol;

    @FXML
    private TableColumn<Part, Integer> partsInventoryCol;

    @FXML
    private TableColumn<Part, Double> partsPriceCol;

    @FXML
    private Button partsAdd;

    @FXML
    private Button partsModify;

    @FXML
    private Button partsDelete;

    @FXML
    private Button exitButton;

    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        Button b = (Button)event.getSource();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));

        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDelete(ActionEvent event) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("You are attempting to delete a part");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            filteredParts.remove(selectedPart);
            Inventory.deletePart(selectedPart);
        }
    }

    @FXML
    void onActionModify(ActionEvent event) throws IOException {
        if(!partsTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPartForm.fxml"));
            root = loader.load();

            ModifyPartFormController modifyForm = loader.getController();
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            modifyForm.setTextFields(selectedPart);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = root;

            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select a part to modify");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionProductsAdd(ActionEvent event) throws IOException {
        Button b = (Button)event.getSource();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));

        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionProductsDelete(ActionEvent event) throws IOException {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("You are attempting to delete a product");
            alert.setContentText("Are you sure?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                filteredProducts.remove(selectedProduct);
                Inventory.deleteProduct(selectedProduct);
            }
        } else {
            inputError("Cannot delete product with associated parts");
        }
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionProductsModify(ActionEvent event) throws IOException {
        if(!productsTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProductForm.fxml"));
            root = loader.load();

            ModifyProductFormController modifyProductForm = loader.getController();
            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
            modifyProductForm.setTextFields(selectedProduct);
            modifyProductForm.setTransientAssociatedPartsList(selectedProduct.getAllAssociatedParts());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = root;

            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            inputError("Please select a product to modify");
        }
    }

    @FXML
    void onActionPartSearch(ActionEvent event) {
        String searchItem = partsSearch.getText();

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
                partsTable.getSelectionModel().select( (Part)Inventory.lookupPart(integerSearchItem) );
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

    @FXML
    void onActionProductsSearch(ActionEvent event) throws IOException {
        String searchItem = productsSearch.getText();

        if(isInteger(searchItem)) {
            int integerSearchItem = Integer.parseInt(searchItem);
            if(Inventory.lookupProduct(integerSearchItem) == null) {
                InventoryManagementController.inputError("No products found with ID: " + searchItem);
            }
            else {
                if(!productsTable.getItems().contains( (Product)Inventory.lookupProduct(integerSearchItem)) ) {
                    filteredProducts.clear();
                    filteredProducts.addAll(Inventory.getAllProducts());
                }
                productsTable.getSelectionModel().select( (Product)Inventory.lookupProduct(integerSearchItem) );
            }
        } else if (searchItem.isEmpty()) {
            filteredProducts.clear();
            filteredProducts.addAll(Inventory.getAllProducts());
        } else {
            if(Inventory.lookupProduct(searchItem) == null) {
                InventoryManagementController.inputError("No products found with name: " + searchItem);
            } else {
                filteredProducts.clear();
                filteredProducts.addAll(Inventory.lookupProduct(searchItem));
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
    private ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * Calls an error alert with a custom message.
     * @param message Message to be displayed in error alert.
     */
    public static void inputError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filteredParts.addAll(Inventory.getAllParts());
        filteredProducts.addAll(Inventory.getAllProducts());

        partsTable.setItems(filteredParts);

        partsIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(filteredProducts);

        productsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}