package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreController {

    private Connection conn = MainController.conn;
    private SQLClass sql = new SQLClass();
    public JFXButton releaseToKitchenBtn;
    public JFXButton takeInItemsBtn;
    ObservableList<currentStock> list = FXCollections.observableArrayList();
    public TableView<currentStock> table;


    public JFXButton loadReloadBtn;


    public void loadReload() {

        list.clear();
        /*ResultSet rs = sql.selectCurrentStock(conn, errorLable);
        try{
            while(rs.next()){
                list.add(new currentStock(Integer.parseInt(rs.getString("id")),
                        rs.getString("FoodName" ),
                        Integer.parseInt(rs.getString("Quantity"))));
            }
        }catch (SQLException e2){
            //errorLable.setText("Unable to get elements from the database");
        }
        table.setItems(list);*/

    }

    public void releaseToKitchen() {

    }

    public void takeInItems() {
        Stage windowTa = new Stage();
        windowTa.setTitle("Release items to kitchen");
        windowTa.initModality(Modality.APPLICATION_MODAL);
        VBox mainLay = new VBox(25);
        Label alertLabel = new Label();
        HBox fnameLay = new HBox(15);
        Label fLabel = new Label("Food Name: ");
        ComboBox<String> fName = new ComboBox<>();
        sql.selectFoodName(conn, fName, alertLabel);
        fName.setPromptText("Food name");
        fName.setEditable(true);
        fnameLay.getChildren().addAll(fLabel, fName);

        HBox quantityLay = new HBox(15);
        Label qLabel = new Label("Quantity: ");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        quantityLay.getChildren().addAll(qLabel, quantityField);

        HBox measLay = new HBox(15);
        Label measLabel = new Label("Measurement: ");
        TextField measField = new TextField();
        measField.setPromptText("Measurement Unit: ");
        measLay.getChildren().addAll(measLabel, measField);

        HBox costLay = new HBox(15);
        Label costLabel = new Label("Cost: ");
        TextField costField = new TextField();
        costField.setPromptText("Cost Per Unit: ");
        costLay.getChildren().addAll(costLabel, costField);

        HBox buttons = new HBox(10);
        Button addButton = new Button("Add item");
        Button doneButton = new Button("Done");
        buttons.getChildren().addAll(addButton, doneButton);

        addButton.setOnAction(e -> {

            String fname = fName.getValue();
            Double cost = Double.parseDouble(costField.getText());
            int qnt = Integer.parseInt(quantityField.getText());
            Double measureUnit = Double.parseDouble(measField.getText());
            if(fName.getValue() != null && costField.getText() != null && quantityField.getText() != null && measField.getText() != null){
                if(qnt != 0 && cost != 0){
                    sql.addToStore(conn, fname, cost, qnt, measureUnit, alertLabel);
                    alertLabel.setText("Product successfully added to the database");
                }else {
                    alertLabel.setText("You cannot submit food of cost 0 or quantity 0 ");
                }
            }
        });


        doneButton.setOnAction(e -> {
            windowTa.close();
        });


        mainLay.getChildren().addAll(alertLabel, fnameLay,quantityLay,measLay, costLay, buttons);
        Scene scene = new Scene(mainLay);
        windowTa.setScene(scene);
        windowTa.showAndWait();
    }


    public void display(){

        try {
            Parent layout = FXMLLoader.load(getClass().getResource("StoreLayout.fxml"));

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            double Height = bounds.getHeight();
            double Width = bounds.getWidth();

            Image icon = new Image("file:src/images/cafe.png");

            Scene scene = new Scene(layout, Width, Height);
            scene.getStylesheets().add("CafeteriaStyle.css");
            Stage window = new Stage();

            window.getIcons().add(icon);
            window.setTitle("Manager");
            window.setScene(scene);
            window.show();

        }catch (Exception E){
            System.out.println();
        }
    }


}
