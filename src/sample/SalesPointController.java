package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import java.util.ArrayList;

public class SalesPointController implements Initializable{


    public JFXButton addToReceipt;
    public JFXTextArea cumulativeReceiptTextArea;
    public JFXButton editReceipt;
    public JFXTextField totalField;
    public JFXTextArea receiptTextArea;
    public JFXButton viewTotalButton;
    public JFXButton receiptButton;
    public JFXButton studentAccountButton;
    public JFXButton resetButton;
    public Label availableFoodLabel;
    public JFXComboBox<String> foodCodeField;
    public JFXTextField quantityField;

    public Alert alert = new Alert(Alert.AlertType.ERROR);

    public  ArrayList<ReceiptStructure> receipt = new ArrayList<>();
    private Double total = 0.0;

    private Connection conn = MainController.conn;
    private SQLClass sql = new SQLClass();

    public void ResetFunction() {
        cumulativeReceiptTextArea.setText(null);
        total = 0.0;
        receipt.clear();
        receiptTextArea.setText(null);
    }

    public void addReceipt() {

        if(foodCodeField.getValue() == null|| quantityField.getText().equals("")){

        }else{
            String foodCode = foodCodeField.getValue();
            int quantity = Integer.parseInt(quantityField.getText());
            ResultSet rs = sql.getFoodPrice(conn, foodCode);
            try {
                int count = 0;
                String name = null;
                Double price = 0.0;

                while (rs.next()) {
                    name = rs.getString("FoodName");
                    price = rs.getDouble("Price_Per_Unit");
                    ++count;
                }

                if(count == 0 || count > 1){
                    alert.setHeaderText("Invalid foodCode");
                    alert.show();
                }else {
                    receipt.add(new ReceiptStructure(name, quantity, foodCode, price));
                    total += quantity * price;
                    totalField.setText(total.toString());
                    String newReceipt = "";
                    for(ReceiptStructure obj: receipt){
                        newReceipt += " "+ obj.getQuantity() + "\t\t" + obj.getName() + "\t\t" + obj.getUnitPrice() + "\n";
                    }

                    cumulativeReceiptTextArea.setText(newReceipt);

                }
            }catch (SQLException e){
                alert.setHeaderText("Database error occurred");
                alert.show();
            }

        }

    }

    public void editReceiptFunc() {

    }

    public void studentAccountFunc() {

    }

    public void viewReceiptFunc() {
        //receiptTextArea.setText(receipt);
        totalField.setText(total.toString());
    }

    public void viewTotalFunc() {
        totalField.setText(total.toString());
    }

    public void display(){

        try {
            Parent layout = FXMLLoader.load(getClass().getResource("SalesPointLayout.fxml"));
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            double Height = bounds.getHeight();
            double Width = bounds.getWidth();

            Image icon = new Image("file:src/images/cafe.png");

            Scene scene = new Scene(layout, Width, Height);
            scene.getStylesheets().add("CafeteriaStyle.css");
            Stage window = new Stage();
            window.getIcons().add(icon);
            window.setTitle("Cafeteria SalesPoint");
            window.setScene(scene);
            window.show();

        }catch (Exception E){
            System.out.println(E);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sql.getFoodAvailable(conn, availableFoodLabel);

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                sql.getFoodAvailable(conn, availableFoodLabel);
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
    }
}
