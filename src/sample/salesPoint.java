package sample;

import com.sun.corba.se.impl.protocol.BootstrapServerRequestDispatcher;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.DoubleSummaryStatistics;
import java.util.StringJoiner;

public class salesPoint {



    public static void display(Connection conn){
        Stage window1 = new Stage();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Connection connection;

        window1.setX(bounds.getMinX());
        window1.setY(bounds.getMinY());
        window1.setWidth(bounds.getWidth());
        window1.setHeight(bounds.getHeight());

        BorderPane mainLayout = new BorderPane();

        HBox topLayout = new HBox(10);
        Button logoutButton = new Button("Logout");
        topLayout.setAlignment(Pos.BASELINE_RIGHT);
        topLayout.getChildren().addAll(logoutButton);

        //Start of left layout
        VBox leftLayout = new VBox();
        leftLayout.setAlignment(Pos.CENTER);
        leftLayout.getStyleClass().add("salLeftLayout");
        FlowPane foodLayout = new FlowPane(Orientation.VERTICAL);
        GridPane checkAndTextField = new GridPane();
        checkAndTextField.setHgap(15);
        checkAndTextField.setVgap(10);

        Label foodHeading = new Label("FOOD");
        foodHeading.getStyleClass().addAll("foodHeading");
        CheckBox food1 = new CheckBox("Chapati");
        TextField food1T = new TextField();
        food1T.setText("0");
        GridPane.setConstraints(food1 , 0,0);
        GridPane.setConstraints(food1T, 1,0);


        CheckBox food2 = new CheckBox("Ugali");
        TextField food2T = new TextField();
        food2T.setText("0");
        GridPane.setConstraints(food2, 0,1);
        GridPane.setConstraints(food2T, 1,1);

        CheckBox food3 = new CheckBox("Rice");
        TextField food3T = new TextField();
        food3T.setText("0");
        GridPane.setConstraints(food3, 0,2);
        GridPane.setConstraints(food3T, 1,2);

        CheckBox food4 = new CheckBox("Beaf");
        TextField food4T = new TextField();
        food4T.setText("0");
        GridPane.setConstraints(food4, 0,3);
        GridPane.setConstraints(food4T, 1,3);

        CheckBox food5 = new CheckBox("Beans");
        TextField food5T = new TextField();
        food5T.setText("0");
        GridPane.setConstraints(food5, 0,4);
        GridPane.setConstraints(food5T, 1,4);

        CheckBox food6 = new CheckBox("Ndengu");
        TextField food6T = new TextField();
        food6T.setText("0");
        GridPane.setConstraints(food6, 0,5);
        GridPane.setConstraints(food6T, 1,5);

        checkAndTextField.getChildren().addAll(food1,food1T,food2,food2T,food3,food3T,food4, food4T,food5, food5T,food6, food6T);

        foodLayout.getChildren().addAll(checkAndTextField);
        //Subtotal section
        VBox subTotal = new VBox(10);
        subTotal.getStyleClass().add("subTotal");
        GridPane subGrid = new GridPane();
        subGrid.setVgap(10);
        subGrid.setHgap(10);
        Label costOfDrinks = new Label("Cost of Drinks");
        TextField drinksField = new TextField("0");
        GridPane.setConstraints(costOfDrinks, 0,0);
        GridPane.setConstraints(drinksField, 1,0);

        Label costOfFood = new Label("Cost of Food");
        TextField foodField = new TextField("0");
        GridPane.setConstraints(costOfFood, 0,1);
        GridPane.setConstraints(foodField, 1,1);

        Label costOfSnacks = new Label("Cost of Snacks");
        TextField snacksField = new TextField("0");
        GridPane.setConstraints(costOfSnacks, 0,2);
        GridPane.setConstraints(snacksField, 1,2);
        subGrid.getChildren().addAll(costOfDrinks, drinksField,costOfFood,foodField,costOfSnacks,snacksField);
        subTotal.getChildren().addAll(subGrid);

        leftLayout.getChildren().addAll(foodHeading, foodLayout, subTotal);


        //END OF LEFT LAYOUT

        //START OF MIDDLE SECTION
        VBox middleLayout = new VBox();
        middleLayout.getStyleClass().add("salmiddleLayout");
        GridPane drinksLayout = new GridPane();
        drinksLayout.setHgap(10);
        drinksLayout.setVgap(10);

        Label drinksLabel = new Label("DRINKS");
        CheckBox Tea = new CheckBox("Tea");
        TextField teaField = new TextField("0");
        GridPane.setConstraints(Tea, 0,1);
        GridPane.setConstraints(teaField, 1,1);

        CheckBox Soda = new CheckBox("Soda");
        TextField sodaField = new TextField("0");
        GridPane.setConstraints(Soda, 0,2);
        GridPane.setConstraints(sodaField, 1,2);

        CheckBox FruitJuice = new CheckBox("Fruit Juice");
        TextField fruitField = new TextField("0");
        GridPane.setConstraints(FruitJuice, 0,3);
        GridPane.setConstraints(fruitField, 1,3);

        drinksLayout.getChildren().addAll(Tea,teaField, Soda, sodaField, FruitJuice, fruitField);

       FlowPane snacksLayout = new FlowPane(Orientation.VERTICAL);
       //snacksLayout.setHgap(10);
       snacksLayout.setVgap(20);
       GridPane snacksGrid = new GridPane();
       snacksGrid.setHgap(10);
       snacksGrid.setVgap(10);

        Label snacksLabel = new Label("SNACKS");
        CheckBox Buns = new CheckBox("Buns");
        TextField bunsField= new TextField("0");
        GridPane.setConstraints(Buns, 0,1);
        GridPane.setConstraints(bunsField, 1,1);

        CheckBox Mandazi = new CheckBox("Mandazi");
        TextField mandaziField = new TextField("0");
        GridPane.setConstraints(Mandazi, 0,2);
        GridPane.setConstraints(mandaziField, 1,2);

        CheckBox Softcake = new CheckBox("Soft Cakes");
        TextField softField= new TextField("0");
        GridPane.setConstraints(Softcake, 0,3);
        GridPane.setConstraints(softField, 1,3);

        snacksGrid.getChildren().addAll(Buns, bunsField, Mandazi, mandaziField, Softcake, softField);
        snacksLayout.getChildren().addAll(snacksGrid);

        //TOTAL LAYOUT
        Label totalLabel = new Label("TOTAL");
        TextField totalField = new TextField("0");
        HBox totalLayout = new HBox(10);
        totalLayout.getChildren().addAll(totalLabel, totalField);


        middleLayout.getChildren().addAll(drinksLabel, drinksLayout, snacksLabel, snacksLayout, totalLayout);
        //END OF MIDDLE SECTION

        //START OF RIGHT SECTION
        VBox rightLayout = new VBox(10);
        rightLayout.setSpacing(20);
        Label receiptLabel = new Label("RECEIPT");
        TextArea receipt = new TextArea();
        receipt.setEditable(false);

        HBox buttonsLayout = new HBox(10);
        Button Total = new Button("Total");
        Button recieptButton = new Button("Receipt");
        Button resetButton = new Button("Reset");
        Button exitButton = new Button("EXIT");

        buttonsLayout.getChildren().addAll(Total, recieptButton, resetButton, exitButton);


        rightLayout.getChildren().addAll(receiptLabel, receipt, buttonsLayout);
        rightLayout.prefHeight(mainLayout.getMaxHeight());
        //END OF THE RIGHT SECTION
        //SETTING BUTTONS ACTIONS
        exitButton.setOnAction(e -> window1.close());

        resetButton.setOnAction(e -> {
            food1.setSelected(false);
            food1T.setText("0");
            food2.setSelected(false);
            food2T.setText("0");
            food3.setSelected(false);
            food3T.setText("0");
            food4.setSelected(false);
            food4T.setText("0");
            food5.setSelected(false);
            food5T.setText("0");
            food6.setSelected(false);
            food6T.setText("0");
            drinksField.setText("0");
            foodField.setText("0");
            snacksField.setText("0");
            Tea.setSelected(false);
            teaField.setText("0");
            Soda.setSelected(false);
            sodaField.setText("0");
            FruitJuice.setSelected(false);
            fruitField.setText("0");
            Buns.setSelected(false);
            bunsField.setText("0");
            Mandazi.setSelected(false);
            mandaziField.setText("0");
            Softcake.setSelected(false);
            softField.setText("0");
            receipt.setText(null);

        });

        //SETTING ACTION FOR THE RECEIPT BUTTON

        recieptButton.setOnAction(new EventHandler<ActionEvent>() {
            Double TOTAL = 0.0;
            Double foodCost = 0.0;
            Double drinksCost = 0.0;
            Double snacksCost = 0.0;
            SQLClass sql = new SQLClass();
            @Override
            public void handle(ActionEvent event) {
                String receiptText = "";
                int food1txt = Integer.parseInt(food1T.getText());
                if((food1txt != 0)){
                   Double price = sql.getFoodPrice(conn, "Chapati" );
                   TOTAL +=food1txt * price;
                    foodCost += food1txt * price;
                    receiptText = receiptText + "Chapati\t\t"+food1txt+"  @ SHs."+price+"\n";
                }

                int food2txt = Integer.parseInt(food2T.getText());
                if(food2txt != 0 ){
                    Double price = sql.getFoodPrice(conn, "Ugali" );
                    TOTAL +=food2txt * price;
                    foodCost += food2txt * price;
                    receiptText = receiptText + "Ugali\t\t"+food2txt+"  @ SHs."+price+"\n";
                }

                int food3txt = Integer.parseInt(food3T.getText());
                if(food3txt != 0 ){
                    Double price = sql.getFoodPrice(conn, "Rice" );
                    TOTAL +=food3txt * price;
                    foodCost += food3txt * price;
                    receiptText = receiptText + "Rice \t\t"+food3txt+"  @ SHs."+price+"\n";
                }

                int food4txt = Integer.parseInt(food4T.getText());
                if(food4txt != 0 ){
                    Double price = sql.getFoodPrice(conn, "Beaf" );
                    TOTAL +=food4txt * price;
                    foodCost += food4txt * price;
                    receiptText = receiptText + "Beaf \t\t"+food4txt+"  @ SHs."+price+"\n";
                }

                int food5txt = Integer.parseInt(food5T.getText());
                if(food5txt != 0 ){
                    Double price = sql.getFoodPrice(conn, "Beans" );
                    TOTAL +=food5txt * price;
                    foodCost += food5txt * price;
                    receiptText = receiptText + "Beans \t\t"+food5txt+"  @ SHs."+price+"\n";
                }
                int food6txt = Integer.parseInt(food6T.getText());
                if(food2txt != 0 ){
                    Double price = sql.getFoodPrice(conn, "Ndengu" );
                    TOTAL +=food6txt * price;
                    foodCost += food6txt * price;
                    receiptText = receiptText + "Ndengu \t\t"+food6txt+"  @ SHs."+price+"\n";
                }

                //handling Drinks
                //receiptText = receiptText + "\nDRINKS \t\t";
                int drink1txt = Integer.parseInt(teaField.getText());
                if(drink1txt != 0 ){
                    Double price = sql.getFoodPrice(conn, "Tea" );
                    TOTAL +=drink1txt * price;
                    drinksCost += drink1txt * price;
                    receiptText = receiptText + "Tea \t\t"+drink1txt+"  @ SHs."+price+"\n";
                }

                int drink2txt = Integer.parseInt(sodaField.getText());
                if(drink1txt != 0 ){
                    Double price = sql.getFoodPrice(conn, "Soda" );
                    TOTAL +=drink2txt * price;
                    drinksCost += drink2txt * price;
                    receiptText = receiptText + "Soda \t\t"+drink2txt+"  @ SHs."+price+"\n";
                }

                int drink3txt = Integer.parseInt(fruitField.getText());
                if(drink3txt != 0 ){
                    Double price = sql.getFoodPrice(conn, "Fruit juice" );
                    TOTAL +=drink3txt * price;
                    drinksCost += drink3txt * price;
                    receiptText = receiptText + "Fruit juice \t\t"+drink3txt+"  @ SHs."+price+"\n";
                }

                //Handling Snacks
                int snacks1txt = Integer.parseInt(bunsField.getText());
                int snack1 = bunsField.getText().length();
                if(snacks1txt != 0 && snack1 != 0){
                    Double price = sql.getFoodPrice(conn, "Buns" );
                    TOTAL +=snacks1txt * price;
                    snacksCost += snacks1txt * price;
                    receiptText = receiptText + "Buns \t\t"+snacks1txt+"  @ SHs."+price+"\n";
                }

                int snacks2txt = Integer.parseInt(mandaziField.getText());
                if(snacks2txt != 0 && !mandaziField.getText().trim().isEmpty()){
                    Double price = sql.getFoodPrice(conn, "Mandazi" );
                    TOTAL +=snacks2txt * price;
                    snacksCost += snacks2txt * price;
                    receiptText = receiptText + "Mandazi \t\t"+snacks2txt+"   @ SHs."+price+"\n";
                }

                int snacks3txt = Integer.parseInt(softField.getText());
                if(snacks3txt != 0 && !softField.getText().trim().isEmpty()){
                    Double price = sql.getFoodPrice(conn, "Soft cakes" );
                    TOTAL += snacks3txt * price;
                    snacksCost += snacks3txt * price;
                    receiptText = receiptText + "Soft cakes \t\t"+snacks3txt+"  @ SHs."+price+"\n";
                }

                receiptText = receiptText + "\n\n==================================================\n\n";
                receiptText = receiptText + "COST Of FOOD: " + foodCost+"\n";
                receiptText = receiptText + "COST OF SNACKS: " + snacksCost+"\n";
                receiptText = receiptText + "COST OF DRINKS: " + drinksCost+"\n\n";
                receiptText = receiptText + "TOTAL: " + TOTAL;
                drinksField.setText(drinksCost+"");
                foodField.setText(foodCost+"");
                snacksField.setText(snacksCost+"");
                totalField.setText(""+TOTAL +"");
                receipt.setText(receiptText);
            }
        }
       );

        //SETTING ACTION FOR THE TOTAL BUTTON
        Total.setOnAction(new EventHandler<ActionEvent>() {
                              Double TOTAL = 0.0;
                              Double foodCost = 0.0;
                              Double drinksCost = 0.0;
                              Double snacksCost = 0.0;
                              SQLClass sql = new SQLClass();
                              @Override
                              public void handle(ActionEvent event) {
                                  String receiptText = "";
                                  int food1txt = Integer.parseInt(food1T.getText());
                                  if(food1txt != 0){
                                      Double price = sql.getFoodPrice(conn, "Chapati" );
                                      TOTAL +=food1txt * price;
                                      foodCost += food1txt * price;
                                  }

                                  int food2txt = Integer.parseInt(food2T.getText());
                                  if(food2txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Ugali" );
                                      TOTAL +=food2txt * price;
                                      foodCost += food2txt * price;
                                  }

                                  int food3txt = Integer.parseInt(food3T.getText());
                                  if(food3txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Rice" );
                                      TOTAL +=food3txt * price;
                                      foodCost += food3txt * price;
                                  }

                                  int food4txt = Integer.parseInt(food4T.getText());
                                  if(food4txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Beaf" );
                                      TOTAL +=food4txt * price;
                                      foodCost += food4txt * price;
                                  }

                                  int food5txt = Integer.parseInt(food5T.getText());
                                  if(food5txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Beans" );
                                      TOTAL +=food5txt * price;
                                      foodCost += food5txt * price;
                                  }
                                  int food6txt = Integer.parseInt(food6T.getText());
                                  if(food2txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Ndengu" );
                                      TOTAL +=food6txt * price;
                                      foodCost += food6txt * price;
                                  }

                                  //handling Drinks
                                  //receiptText = receiptText + "\nDRINKS \t\t";
                                  int drink1txt = Integer.parseInt(teaField.getText());
                                  if(drink1txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Tea" );
                                      TOTAL +=drink1txt * price;
                                      drinksCost += drink1txt * price;
                                  }

                                  int drink2txt = Integer.parseInt(sodaField.getText());
                                  if(drink1txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Soda" );
                                      TOTAL +=drink2txt * price;
                                      drinksCost += drink2txt * price;
                                  }

                                  int drink3txt = Integer.parseInt(fruitField.getText());
                                  if(drink3txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Fruit juice" );
                                      TOTAL +=drink3txt * price;
                                      drinksCost += drink3txt * price;
                                  }

                                  //Handling Snacks
                                  int snacks1txt = Integer.parseInt(bunsField.getText());
                                  if(snacks1txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Buns" );
                                      TOTAL +=snacks1txt * price;
                                      snacksCost += snacks1txt * price;
                                  }

                                  int snacks2txt = Integer.parseInt(mandaziField.getText());
                                  if(snacks2txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Mandazi" );
                                      TOTAL +=snacks2txt * price;
                                      snacksCost += snacks2txt * price;

                                  }

                                  int snacks3txt = Integer.parseInt(softField.getText());
                                  if(snacks3txt != 0 ){
                                      Double price = sql.getFoodPrice(conn, "Soft cakes" );
                                      TOTAL += snacks3txt * price;
                                      snacksCost += snacks3txt * price;

                                  }

                                  receiptText = receiptText + "\n\n==================================================\n\n";
                                  receiptText = receiptText + "COST Of FOOD: " + foodCost+"\n";
                                  receiptText = receiptText + "COST OF SNACKS: " + snacksCost+"\n";
                                  receiptText = receiptText + "COST OF DRINKS: " + drinksCost+"\n\n";
                                  receiptText = receiptText + "TOTAL: " + TOTAL;
                                  drinksField.setText(drinksCost+"");
                                  foodField.setText(foodCost+"");
                                  snacksField.setText(snacksCost+"");
                                  totalField.setText(""+TOTAL +"");
                              }
                          }
        );
        //END TO BUTTONS ACTIONS

        mainLayout.setTop(topLayout);
        mainLayout.setRight(rightLayout);
        mainLayout.setCenter(middleLayout);
        mainLayout.setLeft(leftLayout);

        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add("Viper.css");
        window1.setScene(scene);
        window1.show();


    }

}
