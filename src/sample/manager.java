package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javafx.scene.control.MenuItem;
import javafx.util.Duration;

public class ManagerController implements Initializable{
    public MenuItem viewNew;
    public MenuItem viewAll;
    public MenuItem addUser;
    public MenuItem activateUser;
    public MenuItem deactivateUser;
    public MenuItem resetUserPass;
    public MenuItem assignDuty;
    public Label availableFood;
    public Label loggedInUsers;

    public JFXButton addNewFoodItem;
    public JFXButton removeFoodItem;
    public JFXButton addAvailableFood;
    public JFXButton removeFromMenu;
    public JFXButton updatefoodPrice;

    SQLClass sql = new SQLClass();



    private Connection conn = MainController.conn;



    public void newFoodItem(){
        Stage windowadd = new Stage();
        windowadd.initModality(Modality.APPLICATION_MODAL);
        windowadd.setTitle("Add new Food Item to the database");
        Label foodItemLabel = new Label();
        Label foodName = new Label("Food Name");
        TextField foodField = new TextField();
        foodField.setPromptText("Food name");
        Label foodPrice = new Label("Food Price");
        TextField priceField = new TextField();
        priceField.setPromptText("Food price");

        Button addButton = new Button("Add");
        addButton.setOnAction(e1 ->{
            String name = foodField.getText();
            String price = priceField.getText();
            if(name.length() != 0 || price.length() != 0){
                sql.addFoodItem(conn, name, price, foodItemLabel);
            }else{
                foodItemLabel.setText("Cannot submit empty fields");
            }

        });
        Button doneButton = new Button("Done");
        doneButton.setOnAction(e1 -> {
            windowadd.close();
        });

        GridPane addFoodLayout = new GridPane();
        addFoodLayout.setVgap(20);
        addFoodLayout.setHgap(10);
        /// GridPane.setConstraints(foodItemLabel, 0 , 0);
        GridPane.setConstraints(foodName, 0, 1);
        GridPane.setConstraints(foodField, 1, 1);
        GridPane.setConstraints(foodPrice, 0, 2);
        GridPane.setConstraints(priceField, 1, 2);
        GridPane.setConstraints(addButton, 0, 4);
        GridPane.setConstraints(doneButton, 1, 4);

        HBox topLabel = new HBox(10);
        topLabel.getChildren().add(foodItemLabel);
        VBox mainLayoutadd = new VBox(10);
        mainLayoutadd.getChildren().addAll(topLabel, addFoodLayout);

        addFoodLayout.getChildren().addAll(foodName, foodField, foodPrice, priceField, addButton, doneButton);
        Scene sceneadd = new Scene(mainLayoutadd, 400, 300);
        windowadd.setScene(sceneadd);
        windowadd.showAndWait();
    }

    public void deleteFoodItem(){
        Stage windowremove = new Stage();
        windowremove.initModality(Modality.APPLICATION_MODAL);
        windowremove.setTitle("Delete Food Item from the database");
        Label foodItemLabel = new Label();
        Label foodName = new Label("Food Name");
        ComboBox<String> foodField = new ComboBox<>();
        foodField.setEditable(true);
        sql.selectFoodName(conn, foodField, foodItemLabel);
        foodField.setPromptText("Food name");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction( e1 -> {
            windowremove.close();
        });
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e1 -> {
            String name = foodField.getValue();
            if(foodField.getValue() != null) {
                if (name.length() != 0) {
                    sql.removeFoodItem(conn, name, foodItemLabel);
                } else {
                    foodItemLabel.setText("You cannot submit an empty field");
                }
            }else{
                foodItemLabel.setText("Please select an item");
            }
        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e1 -> {
            windowremove.close();
        });

        GridPane addFoodLayout = new GridPane();
        addFoodLayout.setVgap(20);
        addFoodLayout.setHgap(10);
        GridPane.setConstraints(foodName, 0, 1);
        GridPane.setConstraints(foodField, 1, 1);
        GridPane.setConstraints(removeButton, 0, 3);
        GridPane.setConstraints(doneButton, 1, 3);


        HBox topLabel = new HBox(10);
        topLabel.getChildren().add(foodItemLabel);
        foodItemLabel.setWrapText(true);
        VBox mainLayoutadd = new VBox(10);


        addFoodLayout.getChildren().addAll(foodName, foodField, removeButton, doneButton);
        mainLayoutadd.getChildren().addAll(topLabel, addFoodLayout);
        Scene sceneadd = new Scene(mainLayoutadd, 400, 300);
        windowremove.setScene(sceneadd);
        windowremove.showAndWait();
    }
    public void addToMenu(){
        Stage windowAvail = new Stage();
        windowAvail.initModality(Modality.APPLICATION_MODAL);
        windowAvail.setTitle("Add Food Item To Menu");
        Label foodItemLabel = new Label();
        Label foodName = new Label("Food Name");
        ComboBox<String> foodField = new ComboBox<>();
        foodField.setEditable(true);
        sql.selectFoodName(conn, foodField, foodItemLabel);
        foodField.setPromptText("Food name");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction( e1 -> {
            windowAvail.close();
        });
        Button removeButton = new Button("Add to menu");
        removeButton.setOnAction(e2 -> {
            if(foodField.getValue() != null) {
                String name = foodField.getValue();
                if (name.length() != 0) {
                    sql.setAvailableFood(conn, name, foodItemLabel);
                } else {
                    foodItemLabel.setText("You cannot submit an empty field");
                }
            }else{
                foodItemLabel.setText("Please select an item");
            }
        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e1 -> {
            windowAvail.close();
        });

        GridPane addFoodLayout = new GridPane();
        addFoodLayout.setVgap(20);
        addFoodLayout.setHgap(10);
        GridPane.setConstraints(foodName, 0, 1);
        GridPane.setConstraints(foodField, 1, 1);
        GridPane.setConstraints(removeButton, 0, 3);
        GridPane.setConstraints(doneButton, 1, 3);


        HBox topLabel = new HBox(10);
        topLabel.getChildren().add(foodItemLabel);
        foodItemLabel.setWrapText(true);
        VBox mainLayoutadd = new VBox(10);


        addFoodLayout.getChildren().addAll(foodName, foodField, removeButton, doneButton);
        mainLayoutadd.getChildren().addAll(topLabel, addFoodLayout);
        Scene sceneadd = new Scene(mainLayoutadd, 400, 300);
        windowAvail.setScene(sceneadd);
        windowAvail.showAndWait();

    }
    public void removeFromMenu(){
        Stage windowUnAvail = new Stage();
        windowUnAvail.initModality(Modality.APPLICATION_MODAL);
        windowUnAvail.setTitle("Remove Food Item from the menu");
        Label foodItemLabel = new Label();
        Label foodName = new Label("Food Name");
        ComboBox<String> foodField = new ComboBox<>();
        foodField.setEditable(true);
        sql.selectFoodName(conn, foodField, foodItemLabel);
        foodField.setPromptText("Food name");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction( e1 -> {
            windowUnAvail.close();
        });
        Button removeButton = new Button("Remove from menu");
        removeButton.setOnAction(e1 -> {
            if(foodField.getValue() != null) {
                String name = foodField.getValue();
                if (name.length() != 0) {
                    sql.setUnavailableFood(conn, name, foodItemLabel);
                } else {
                    foodItemLabel.setText("You cannot submit an empty field");
                }
            }else{
                foodItemLabel.setText("Please select an item");
            }
        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e1 -> {
            windowUnAvail.close();
        });

        GridPane addFoodLayout = new GridPane();
        addFoodLayout.setVgap(20);
        addFoodLayout.setHgap(10);
        GridPane.setConstraints(foodName, 0, 1);
        GridPane.setConstraints(foodField, 1, 1);
        GridPane.setConstraints(removeButton, 0, 3);
        GridPane.setConstraints(doneButton, 1, 3);


        HBox topLabel = new HBox(10);
        topLabel.getChildren().add(foodItemLabel);
        foodItemLabel.setWrapText(true);
        VBox mainLayoutadd = new VBox(10);


        addFoodLayout.getChildren().addAll(foodName, foodField, removeButton, doneButton);
        mainLayoutadd.getChildren().addAll(topLabel, addFoodLayout);
        Scene sceneadd = new Scene(mainLayoutadd, 400, 300);
        windowUnAvail.setScene(sceneadd);
        windowUnAvail.showAndWait();
    }

    public void updateFoodPrice(){
        Stage windowUpdatePrice = new Stage();
        windowUpdatePrice.initModality(Modality.APPLICATION_MODAL);
        windowUpdatePrice.setTitle("Update Food Item price");
        Label foodItemLabel = new Label();
        Label foodName = new Label("Food Name");
        ComboBox<String> foodField = new ComboBox<>();
        foodField.setEditable(true);
        sql.selectFoodName(conn, foodField, foodItemLabel);
        foodField.setPromptText("Food name");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction( e1 -> {
            windowUpdatePrice.close();
        });

        JFXTextField price = new JFXTextField();
        price.setPromptText("New food price");
        Label priceName = new Label("New Food Price");

        Button removeButton = new Button("Add to menu");
        removeButton.setOnAction(e2 -> {
            if(foodField.getValue() != null) {
                String name = foodField.getValue();
                Double priceto = Double.parseDouble(price.getText());
                if (name.length() != 0) {
                    sql.updateFoodPrice(conn, name, priceto, foodItemLabel);
                } else {
                    foodItemLabel.setText("You cannot submit an empty field");
                }
            }else{
                foodItemLabel.setText("Please select an item");
            }
        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e1 -> {
            windowUpdatePrice.close();
        });

        GridPane addFoodLayout = new GridPane();
        addFoodLayout.setVgap(20);
        addFoodLayout.setHgap(10);
        GridPane.setConstraints(foodName, 0, 1);
        GridPane.setConstraints(foodField, 1, 1);
        GridPane.setConstraints(priceName, 0, 2);
        GridPane.setConstraints(price, 1, 2);

        GridPane.setConstraints(removeButton, 0, 4);
        GridPane.setConstraints(doneButton, 1, 4);


        HBox topLabel = new HBox(10);
        topLabel.getChildren().add(foodItemLabel);
        foodItemLabel.setWrapText(true);
        VBox mainLayoutadd = new VBox(10);


        addFoodLayout.getChildren().addAll(foodName, foodField, priceName, price, removeButton, doneButton);
        mainLayoutadd.getChildren().addAll(topLabel, addFoodLayout);
        Scene sceneadd = new Scene(mainLayoutadd, 400, 300);
        windowUpdatePrice.setScene(sceneadd);
        windowUpdatePrice.showAndWait();

    }

    public void activateUserFunc() {
        Stage windowAct = new Stage();
        windowAct.setTitle("Activate a user");
        windowAct.initModality(Modality.APPLICATION_MODAL);

        VBox mainLayoutact = new VBox(10);

        GridPane midLayoutAct = new GridPane();
        Label AlertLabel = new Label();
        GridPane.setColumnSpan(AlertLabel, 2);
        GridPane.setConstraints(AlertLabel, 0, 0);
        Label workerIdLabel = new Label("Worker Id: ");
        GridPane.setConstraints(workerIdLabel, 0, 1);
        ComboBox<String> idField = new ComboBox<>();
        idField.setEditable(true);
        sql.selectForCombo(conn, idField, AlertLabel);
        idField.setPromptText("Enter worker id ");
        GridPane.setConstraints(idField, 1, 1);
        midLayoutAct.getChildren().addAll(AlertLabel, workerIdLabel, idField);

        HBox bottomLayout = new HBox(10);
        Button actButton = new Button("Activate user");
        actButton.setOnAction(e1 -> {
            if(idField.getValue() != null) {
                String wId = idField.getValue();
                if (wId.length() == 0) {
                    AlertLabel.setText("You cannot submit an empty field");
                } else {
                    sql.activateUser(conn, wId, AlertLabel);
                }
            }else{
                AlertLabel.setText("Please select an id");
            }

        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> {
            windowAct.close();
        });
        bottomLayout.getChildren().addAll(actButton, doneButton);

        mainLayoutact.getChildren().addAll(midLayoutAct, bottomLayout);
        Scene scene = new Scene(mainLayoutact, 400, 350);
        windowAct.setScene(scene);
        windowAct.showAndWait();
    }

    public void addUserFunc() {
        Stage windowAdd = new Stage();
        windowAdd.setTitle("Add New user");
        windowAdd.initModality(Modality.APPLICATION_MODAL);
        VBox mainLayoutAdd = new VBox(10);
        HBox topLayout = new HBox(10);
        Label mainLabel = new Label();
        topLayout.getChildren().add(mainLabel);

        GridPane minorLayout = new GridPane();
        minorLayout.setVgap(10);
        Label fname  = new Label("First Name: ");
        TextField fnameField = new TextField();
        fnameField.setPromptText("Enter First name");
        GridPane.setConstraints(fname, 0 ,0);
        GridPane.setConstraints(fnameField, 1, 0);

        Label sname = new Label("Second Name: ");
        TextField snameField = new TextField();
        snameField.setPromptText("Enter Second name");
        GridPane.setConstraints(sname, 0, 1);
        GridPane.setConstraints(snameField, 1, 1);

        Label lname = new Label("Last Name: ");
        TextField lnameField = new TextField();
        lnameField.setPromptText("Enter last name");
        GridPane.setConstraints(lname, 0, 2);
        GridPane.setConstraints(lnameField, 1, 2);

        Label idAlert = new Label();
        Label workerId = new Label("New Worker Id: ");
        TextField workerIdField = new TextField();
        workerIdField.setPromptText("Enter a new Worker id");
        GridPane.setColumnSpan(idAlert, 3);
        GridPane.setConstraints(idAlert, 0, 3);
        GridPane.setConstraints(workerId, 0, 4);
        GridPane.setConstraints(workerIdField, 1, 4);

        Label opPoint = new Label("Operation point: ");
        ComboBox<String> opPointField = new ComboBox<>();
        opPointField.setEditable(true);
        opPointField.getItems().addAll("sales", "store");
        opPointField.setPromptText("Enter operation point");
        GridPane.setConstraints(opPoint, 0, 5);
        GridPane.setConstraints(opPointField, 1, 5);

        Label passAlert = new Label();
        Label password = new Label("User password: ");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter password");
        GridPane.setColumnSpan(passAlert, 3);
        GridPane.setConstraints(passAlert, 0, 6);
        GridPane.setConstraints(password, 0, 7);
        GridPane.setConstraints(passField, 1, 7);

        Label repassword = new Label("Re-enter the password: ");
        PasswordField repassField = new PasswordField();
        repassField.setPromptText("Re enter password");
        GridPane.setConstraints(repassword, 0, 8);
        GridPane.setConstraints(repassField,1, 8);

        minorLayout.getChildren().addAll(fname, fnameField, sname, snameField, lname, lnameField, idAlert, workerId, workerIdField,opPoint, opPointField, passAlert,
                password, passField, repassword, repassField);

        HBox bottomLayout = new HBox(20);
        Button addButton = new Button("Add");
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e1 -> {
            windowAdd.close();
        });
        Button doneButton = new Button("Done");
        doneButton.setOnAction(e1 -> {
            windowAdd.close();
        });

        addButton.setOnAction(e1 -> {
            if(fnameField.getText() != null && snameField.getText() != null && lnameField.getText() != null && opPointField.getValue() != null &&  passField.getText() != null
                    && repassField.getText() != null && workerIdField.getText() != null){
                String fName = fnameField.getText();
                String sName = snameField.getText();
                String lName = lnameField.getText();
                String opPnt = opPointField.getValue();
                String pass = passField.getText();
                String rePass = repassField.getText();
                String id = workerIdField.getText();

                if(id.length() < 10){
                    idAlert.setText("Worker id must be at least 10 characters long");
                }
                else{
                    if (pass.equals(rePass)) {
                        sql.adduser(conn, fName, sName, lName, id, opPnt, pass, mainLabel);

                    }else{
                        passAlert.setText("Password and confirmed password must be the same");
                    }
                }
            }else{
                mainLabel.setText("You connot submit an empty field");
            }


        });

        bottomLayout.getChildren().addAll(addButton, cancelButton, doneButton);

        mainLayoutAdd.getChildren().addAll(topLayout, minorLayout, bottomLayout);


        Scene scene = new Scene(mainLayoutAdd, 500, 400);
        windowAdd.setScene(scene);
        windowAdd.showAndWait();
    }

    public void assignDutyFunc() {
        Stage windowAss = new Stage();
        windowAss.setTitle("Assign a  user duty");
        windowAss.initModality(Modality.APPLICATION_MODAL);

        VBox assLayout = new VBox(10);
        GridPane mainLayoutAss = new GridPane();
        mainLayoutAss.setVgap(20);
        Label assLabel = new Label();
        GridPane.setColumnSpan(assLabel, 3);
        GridPane.setConstraints(assLabel, 0, 0);
        Label idLab = new Label("Select worker id: ");
        GridPane.setConstraints(idLab, 0 , 1);
        ComboBox<String> workeridCom = new ComboBox<>();
        workeridCom.setPromptText("Select worker id");
        workeridCom.setEditable(true);
        sql.selectForCombo(conn, workeridCom, assLabel);
        GridPane.setConstraints(workeridCom, 1, 1);

        Label opLabel = new Label("Select operation point: ");
        GridPane.setConstraints(opLabel, 0, 2);
        ComboBox<String> operPoint = new ComboBox<>();
        operPoint.setEditable(true);
        operPoint.setPromptText("Select operation point");
        GridPane.setConstraints(operPoint, 1, 2);
        operPoint.getItems().addAll("sales", "store");

        Button assignButton = new Button("Assign");
        GridPane.setConstraints(assignButton, 0, 3);

        Button doneButton = new Button("Done");
        GridPane.setConstraints(doneButton, 1, 3);

        mainLayoutAss.getChildren().addAll(assLabel, idLab, workeridCom, opLabel, operPoint, assignButton, doneButton);

        assignButton.setOnAction(e1 -> {
            String id = workeridCom.getValue();
            String opp = operPoint.getValue();
            if(id != null && opp != null){
                sql.assignDuty(conn, id, opp, assLabel);
            }
            else {
                assLabel.setText("You cannot submit empty fields");
            }
        });

        doneButton.setOnAction(e1 -> {
            windowAss.close();
        });

        assLayout.getChildren().addAll(mainLayoutAss);
        Scene scene = new Scene(assLayout, 300, 300);
        windowAss.setScene(scene);
        windowAss.showAndWait();
    }

    public void deactivateUserFunc(ActionEvent event) {
        Stage windowDeAct = new Stage();
        windowDeAct.setTitle("Deactivate a  user");
        windowDeAct.initModality(Modality.APPLICATION_MODAL);

        VBox mainLayoutact = new VBox(10);

        GridPane midLayoutAct = new GridPane();
        Label AlertLabel = new Label();
        GridPane.setColumnSpan(AlertLabel, 2);
        GridPane.setConstraints(AlertLabel, 0, 0);
        Label workerIdLabel = new Label("Worker Id: ");
        GridPane.setConstraints(workerIdLabel, 0, 1);
        ComboBox<String> idField = new ComboBox<>();
        idField.setEditable(true);
        sql.selectForCombo(conn, idField, AlertLabel);
        idField.setPromptText("Enter worker id ");
        GridPane.setConstraints(idField, 1, 1);
        midLayoutAct.getChildren().addAll(AlertLabel, workerIdLabel, idField);

        HBox bottomLayout = new HBox(10);
        Button actButton = new Button("Deactivate user");
        actButton.setOnAction(e1 -> {
            if(idField.getValue() != null) {
                String wId = idField.getValue();
                if (wId.length() == 0) {
                    AlertLabel.setText("You cannot submit an empty field");
                } else {
                    sql.deactivateUser(conn, wId, AlertLabel);
                }
            }else{
                AlertLabel.setText("Please select an id");
            }

        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> {
            windowDeAct.close();
        });
        bottomLayout.getChildren().addAll(actButton, doneButton);

        mainLayoutact.getChildren().addAll(midLayoutAct, bottomLayout);
        Scene scene = new Scene(mainLayoutact, 400, 350);
        windowDeAct.setScene(scene);

        windowDeAct.showAndWait();
    }

    public void resetPassFunc(ActionEvent event) {
        Stage windowRe = new Stage();
        windowRe.setTitle("Reset a  user password");
        windowRe.initModality(Modality.APPLICATION_MODAL);

        VBox mainLayoutact = new VBox(10);

        GridPane midLayoutAct = new GridPane();
        midLayoutAct.setHgap(10);
        midLayoutAct.setVgap(10);
        Label AlertLabel = new Label();
        GridPane.setColumnSpan(AlertLabel, 2);
        GridPane.setConstraints(AlertLabel, 0, 0);
        Label workerIdLabel = new Label("Worker Id: ");
        GridPane.setConstraints(workerIdLabel, 0, 1);

        ComboBox<String> idField = new ComboBox<>();
        idField.setEditable(true);
        sql.selectForCombo(conn, idField, AlertLabel);
        idField.setPromptText("Enter worker id ");
        GridPane.setConstraints(idField, 1, 1);

        Label passAlert = new Label();
        GridPane.setConstraints(passAlert, 0, 2);
        GridPane.setColumnSpan(passAlert, 2);
        Label passLabel = new Label("Password: ");
        GridPane.setConstraints(passLabel, 0, 3);
        JFXPasswordField passField = new JFXPasswordField();
        passField.setPromptText("Enter new password");
        GridPane.setConstraints(passField, 1, 3);
        Label rePassWord = new Label("Re-Type password: ");
        GridPane.setConstraints(rePassWord, 0, 4);
        JFXPasswordField repassField = new JFXPasswordField();
        repassField.setPromptText("Re-Enter the password");
        GridPane.setConstraints(repassField, 1, 4);

        midLayoutAct.getChildren().addAll(AlertLabel, workerIdLabel, idField, passAlert, passLabel,passField,rePassWord, repassField );

        HBox bottomLayout = new HBox(10);
        Button actButton = new Button("Reset Password");
        actButton.setOnAction(e1 -> {
            String wId = idField.getValue();
            String pass = passField.getText();
            String repass = repassField.getText();
            if(idField.getValue() != null) {
                if (wId.length() == 0 || pass.length() == 0 || repass.length() == 0) {
                    AlertLabel.setText("You cannot submit an empty field(s)");
                } else {
                    if (pass.equals(repass)) {
                        sql.updateUserPass(conn, wId, pass, AlertLabel);
                    } else {
                        passAlert.setText("Password field should be the same ");
                        AlertLabel.setText("");
                    }

                }
            }else{
                AlertLabel.setText("You cannot submit an empty field(s)");
            }

        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e1 -> {
            windowRe.close();
        });
        bottomLayout.getChildren().addAll(actButton, doneButton);

        mainLayoutact.getChildren().addAll(midLayoutAct, bottomLayout);
        Scene scene = new Scene(mainLayoutact, 400, 350);
        windowRe.setScene(scene);

        windowRe.showAndWait();
    }

    public void viewAllFunc() {
        Stage windowVall = new Stage();
        windowVall.setTitle("View All Bookings");
        windowVall.initModality(Modality.APPLICATION_MODAL);

        TableView<bookings> table = new TableView<>();
        ObservableList<bookings> list = FXCollections.observableArrayList();

        //Date of event column
        TableColumn<bookings, String> dateOfBooked = new TableColumn("Date of Event");
        dateOfBooked.setMinWidth(100);
        dateOfBooked.setCellValueFactory(new PropertyValueFactory<>("OccurDate"));

        //Attendance column
        TableColumn<bookings, String> attendance = new TableColumn("Attendance");
        attendance.setMinWidth(100);
        attendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));

        //Date of event column
        TableColumn<bookings, String> phoneNumber = new TableColumn("Phone number");
        phoneNumber.setMinWidth(100);
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //Food code column
        TableColumn<bookings, String> foodCode = new TableColumn("Food Code");
        foodCode.setMinWidth(100);
        foodCode.setCellValueFactory(new PropertyValueFactory<>("foodCode"));

        Button loadButton = new Button("Load/ReloadTable");
        table.getColumns().addAll(attendance, dateOfBooked,phoneNumber, foodCode);
        loadButton.setOnAction(e1 -> {
            list.clear();
            sql.selectAllBookings(conn, list);
            table.setItems(list);
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(table, loadButton);
        Scene scene = new Scene(layout);
        windowVall.setScene(scene);
        windowVall.showAndWait();
    }

    public void viewNewFunc() {
        Stage windowVnew = new Stage();
        windowVnew.setTitle("View New Bookings");
        windowVnew.initModality(Modality.APPLICATION_MODAL);

        TableView<bookings> table = new TableView<>();
        ObservableList<bookings> list = FXCollections.observableArrayList();

        //Date of event column
        TableColumn<bookings, String> dateOfevent = new TableColumn("Date of Event");
        dateOfevent.setMinWidth(100);
        dateOfevent.setCellValueFactory(new PropertyValueFactory<>("OccurDate"));

        //Attendance column
        TableColumn<bookings, String> attendance = new TableColumn("Attendance");
        attendance.setMinWidth(100);
        attendance.setCellValueFactory(new PropertyValueFactory<>("attendance"));

        //Date of event column
        TableColumn<bookings, String> phoneNumber = new TableColumn("Phone number");
        phoneNumber.setMinWidth(100);
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //Food code column
        TableColumn<bookings, String> foodCode = new TableColumn("Food Code");
        foodCode.setMinWidth(100);
        foodCode.setCellValueFactory(new PropertyValueFactory<>("foodCode"));

        table.getColumns().addAll(attendance, dateOfevent,phoneNumber, foodCode);

        Button loadButton = new Button("Load/Reload Table");
        loadButton.setOnAction(e1 -> {
            list.clear();
            sql.selectNewBookings(conn, list);
            table.setItems(list);
        });

        VBox layout = new VBox();
        layout.getChildren().addAll(table, loadButton);
        Scene scene = new Scene(layout);
        windowVnew.setScene(scene);
        windowVnew.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql.getOnlineUsers(conn, loggedInUsers);
        sql.getFoodAvailable(conn, availableFood);

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                sql.getOnlineUsers(conn, loggedInUsers);
                sql.getFoodAvailable(conn, availableFood);
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();


    }

    public void display(){
        try {
            Parent layout = FXMLLoader.load(getClass().getResource("ManagerLayout.fxml"));
            System.out.println("Initializing data");
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
            System.out.println(E);
        }
    }


}
