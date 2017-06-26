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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
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

    private static int count = 0;

    public String workerId;

    public Alert alert = new Alert(Alert.AlertType.ERROR);

    public  ArrayList<ReceiptStructure> receipt = new ArrayList<>();
    private Double total = 0.0;

    private Connection conn = MainController.conn;
    private SQLClass sql = new SQLClass();

    public void ResetFunction() {
        cumulativeReceiptTextArea.setText(null);
        total = 0.0;
        receipt.clear();
        quantityField.setText(null);
        foodCodeField.setValue(null);
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
                int availability = 1;

                while (rs.next()) {
                    name = rs.getString("FoodName");
                    price = rs.getDouble("Price_Per_Unit");
                    availability = rs.getInt("Availability");
                    ++count;

                }

                if(count == 0 || count > 1){
                    alert.setHeaderText("Invalid foodCode");
                    alert.setContentText("Food Code was invalid");
                    alert.show();
                }else {
                    if(availability == 0){
                        alert.setHeaderText("Food Unavailable");
                        alert.setContentText("Food requested is not available at the moment");
                        alert.showAndWait();
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


                }
            }catch (SQLException e){
                alert.setHeaderText("Database error occurred");
                alert.show();
            }

        }

    }

    public void editReceiptFunc() {


        Stage window = new Stage();
        window.setTitle("Deactivate a  user");
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label("Update Receipt");

        JFXTextField foodName = new JFXTextField();
        foodName.setPromptText("Food Name");
        foodName.setLabelFloat(true);
        foodName.setFocusColor(Paint.valueOf("#1bbce0"));
        foodName.setStyle("-fx-font-size: 20px");

        JFXTextField quantity = new JFXTextField();
        quantity.setPromptText("Quantity");
        quantity.setLabelFloat(true);
        quantity.setFocusColor(Paint.valueOf("#1bbce0"));
        quantity.setStyle("-fx-font-size: 20px");


        JFXButton updateReceipt = new JFXButton("Update Receipt");
        updateReceipt.setStyle("-fx-background-color: #1bbce0");
        updateReceipt.setOnAction(e -> {

        });

        JFXButton nextBtn = new JFXButton("Next Item");
        nextBtn.setStyle("-fx-background-color: #1bbce0");
        nextBtn.setOnAction( new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                foodName.setText(receipt.get(count).getName());
                int qunt = receipt.get(count).getQuantity();
                quantity.setText(String.valueOf(qunt));
                ++count;
            }

        });

        JFXButton doneBtn = new JFXButton("Done");
        doneBtn.setStyle("-fx-background-color: #1bbce0");
        doneBtn.setOnAction(e -> {
            window.close();
        });

        HBox lowerLayout  = new HBox(10);
        lowerLayout.getChildren().addAll(updateReceipt, nextBtn, doneBtn);

        VBox mainLayout = new VBox(30);
        mainLayout.setStyle("-fx-padding: 10");
        mainLayout.getChildren().addAll(label, foodName, quantity, lowerLayout);

        window.setScene(new Scene(mainLayout, 300, 300));
        window.setTitle("Deduct From student Account");
        window.showAndWait();

    }

    public void studentAccountFunc() {
        Stage window = new Stage();
        window.setTitle("Deactivate a  user");
        window.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label("Amount to be deducted is: " + total);

        JFXTextField studentReg = new JFXTextField();
        studentReg.setPromptText("Enter student Reg No");
        studentReg.setLabelFloat(true);
        studentReg.setFocusColor(Paint.valueOf("#1bbce0"));
        studentReg.setStyle("-fx-font-size: 20px");

        JFXButton updateReceipt = new JFXButton("Deduct Amount");
        updateReceipt.setStyle("-fx-background-color: #1bbce0");
        updateReceipt.setOnAction(e -> {
            if(studentReg.getText().equals("")){
                //do nothing
            }else {
                sql.chargeStudent(conn, studentReg.getText(), label, total);
            }
        });


        JFXButton doneBtn = new JFXButton("Done");
        doneBtn.setStyle("-fx-background-color: #1bbce0");
        doneBtn.setOnAction(e -> {
            window.close();
        });

        HBox lowerLayout  = new HBox(10);
        lowerLayout.getChildren().addAll(updateReceipt, doneBtn);

        VBox mainLayout = new VBox(30);
        mainLayout.setStyle("-fx-padding: 10");
        mainLayout.getChildren().addAll(label, studentReg, lowerLayout);

        window.setScene(new Scene(mainLayout, 300, 300));
        window.setTitle("Deduct From student Account");
        window.showAndWait();
    }

    public void viewReceiptFunc() {
       String newRe = "";
       for(ReceiptStructure rec : receipt){
           newRe += " "+ rec.getQuantity() + "\t\t" + rec.getName() + "\t\t" + rec.getUnitPrice() + "\n";
       }

       receiptTextArea.setText(newRe);
        totalField.setText(total.toString());
    }

    public void viewTotalFunc() {
        totalField.setText(total.toString());
    }

    public void display(String workerid){

        this.workerId = workerid;
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
