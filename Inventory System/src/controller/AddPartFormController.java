package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles logic for the AddPartForm User Interface.
 */
public class AddPartFormController implements Initializable {

    private Stage stage;
    private Parent scene;

    @FXML
    private RadioButton inhouseRadio;

    @FXML
    private ToggleGroup addPartToggleGroup;

    @FXML
    private RadioButton outsourcedRadio;

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
    private TextField variableTextField;

    @FXML
    private Label labelChange;

    @FXML
    private TextField minTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

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
        int id = Inventory.getUniquePartID();
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
            InventoryManagementController.inputError("Invalid minimum stock input. Please input a whole number.");
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
        // This statement holds one last try catch to handle exceptions for the Machine ID input.
        if(!exception) {
            if (inhouseRadio.isSelected()) {
                try {
                    int special = Integer.parseInt(variableTextField.getText());
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, special));
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/InventoryManagement.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                } catch (NumberFormatException e) {
                    InventoryManagementController.inputError("Invalid Machine ID input. Please input a whole number.");
                }
            } else {
                String special = variableTextField.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, special));
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/InventoryManagement.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    @FXML
    void onInhouseRadio(ActionEvent event) {
        labelChange.setText("Machine ID");
    }

    @FXML
    void onOutsourcedRadio(ActionEvent event) {
        labelChange.setText("Company Name");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
