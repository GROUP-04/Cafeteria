package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Modality;

import java.sql.Connection;

public class manager {

    public static void display(Connection conn1, String workerid){
        Stage window = new Stage();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        window.setX(bounds.getMinX());
        window.setY(bounds.getMinY());
        window.setWidth(bounds.getWidth());
        window.setHeight(bounds.getHeight());
        Connection conn = conn1;

        //INSTANTIATE SQL MEMBER
        SQLClass sql = new SQLClass();

        window.setTitle("Cafeteria Management System");

        BorderPane mainLayout = new BorderPane();

        VBox leftLayout = new VBox(5);
        leftLayout.getStyleClass().add("leftLayout");
        //leftLayout.prefWidthProperty().bind(mainLayout.widthProperty());
        Label leftLabel = new Label("Workers Logged in");
        Label onlineUsers = new Label();
        sql.getOnlineUsers(conn, onlineUsers);
        leftLabel.setAlignment(Pos.CENTER);
        leftLabel.getStyleClass().add("manLeftLabel");
        Label newLineleft = new Label("\n");
        leftLayout.getChildren().addAll(leftLabel,newLineleft, onlineUsers);

        VBox middleSection = new VBox(5);
        middleSection.getStyleClass().add("middleLayout");
        //middleSection.prefWidthProperty().bind(mainLayout.widthProperty());
        Label manMiddle = new Label("Meals Available");
        Label foodLabel = new Label();
        sql.getFoodAvailable(conn, foodLabel);
        Label newLineright = new Label("\n");
        manMiddle.setAlignment(Pos.CENTER);
        manMiddle.getStyleClass().add("manMiddleLabel");
        middleSection.getChildren().addAll(manMiddle,newLineright, foodLabel);

        VBox rightLayout = new VBox(15);
        rightLayout.getStyleClass().add("rightLayout");
        rightLayout.setPrefWidth(mainLayout.getPrefWidth());
        Label rightLabel = new Label("Update Menu");
        rightLabel.setAlignment(Pos.CENTER);
        rightLabel.getStyleClass().add("manRightLabel");

        Button addFoodItem = new Button("New Food Item");
        addFoodItem.setOnAction(e -> {
            Stage windowadd = new Stage();
            windowadd.initModality(Modality.APPLICATION_MODAL);
            windowadd.setTitle("Add new Food Item");
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
        });

        Button removeFoodItem = new Button("Remove food Item");
        removeFoodItem.setOnAction(e -> {
            Stage windowremove = new Stage();
            windowremove.initModality(Modality.APPLICATION_MODAL);
            windowremove.setTitle("Remove Food Item from the database");
            Label foodItemLabel = new Label();
            Label foodName = new Label("Food Name");
            TextField foodField = new TextField();
            foodField.setPromptText("Food name");
            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction( e1 -> {
                windowremove.close();
            });
            Button removeButton = new Button("Remove");
            removeButton.setOnAction(e1 -> {
                String name = foodField.getText();
                if(name.length() != 0 ){
                    sql.removeFoodItem(conn, name, foodItemLabel);
                }else {
                    foodItemLabel.setText("You cannot submit an empty field");
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
        });

        Button unavailableFoodItem = new Button("Unavailable Food");
        unavailableFoodItem.setOnAction(e -> {
            Stage windowremove = new Stage();
            windowremove.initModality(Modality.APPLICATION_MODAL);
            windowremove.setTitle("Remove Food Item from the database");
            Label foodItemLabel = new Label();
            Label foodName = new Label("Food Name");
            TextField foodField = new TextField();
            foodField.setPromptText("Food name");
            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction( e1 -> {
                windowremove.close();
            });
            Button removeButton = new Button("Remove from menu");
            removeButton.setOnAction(e1 -> {
                String name = foodField.getText();
                if(name.length() != 0 ){
                    sql.setUnavailableFood(conn, name, foodItemLabel);
                }else {
                    foodItemLabel.setText("You cannot submit an empty field");
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
        });
        Button availableFood = new Button("Available food");
        availableFood.setOnAction(e -> {
            Stage windowremove = new Stage();
            windowremove.initModality(Modality.APPLICATION_MODAL);
            windowremove.setTitle("Remove Food Item from the database");
            Label foodItemLabel = new Label();
            Label foodName = new Label("Food Name");
            TextField foodField = new TextField();
            foodField.setPromptText("Food name");
            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction( e1 -> {
                windowremove.close();
            });
            Button removeButton = new Button("Add to menu");
            removeButton.setOnAction(e2 -> {
                String name = foodField.getText();
                if(name.length() != 0 ){
                    sql.setAvailableFood(conn, name, foodItemLabel);
                }else {
                    foodItemLabel.setText("You cannot submit an empty field");
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
        });
        Label newLine = new Label("\n");
        rightLayout.getChildren().addAll(rightLabel, newLine, addFoodItem, removeFoodItem, unavailableFoodItem, availableFood);

        MenuBar menuBar = new MenuBar();

        Menu ViewBookings = new Menu("_ViewBookings");
        MenuItem ViewAllBookings = new MenuItem("View All Bookings...");
        MenuItem ViewNewBookings = new MenuItem("View New Bookings...");
        ViewBookings.getItems().addAll(ViewNewBookings, ViewAllBookings);

        ViewAllBookings.setOnAction(e -> {
            Stage windowVall = new Stage();
            windowVall.setTitle("View All Bookings");
            windowVall.initModality(Modality.APPLICATION_MODAL);

            windowVall.showAndWait();
        });

        ViewNewBookings.setOnAction(e -> {
            Stage windowVnew = new Stage();
            windowVnew.setTitle("Deactivate a  user");
            windowVnew.initModality(Modality.APPLICATION_MODAL);

            windowVnew.showAndWait();
        });

        Menu ManageWorkers = new Menu("_ManageWorkers");
        MenuItem AddUser = new MenuItem("Add User...");
        MenuItem DeactivateUser = new MenuItem("Deactivate user...");
        MenuItem ActivateUser = new MenuItem("Activate user...");
        MenuItem ResetUserPass = new MenuItem("Reset User Password...");
        MenuItem AssignDuty = new MenuItem("Assign Duty...");
        ManageWorkers.getItems().addAll(AddUser, ActivateUser, DeactivateUser, ResetUserPass, AssignDuty);

       AddUser.setOnAction(e -> {
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

           Label passAlert = new Label();
           Label password = new Label("User password: ");
           TextField passField = new TextField();
           passField.setPromptText("Enter password");
           GridPane.setColumnSpan(passAlert, 3);
           GridPane.setConstraints(passAlert, 0, 5);
           GridPane.setConstraints(password, 0, 6);
           GridPane.setConstraints(passField, 1, 6);

           Label repassword = new Label("Re-enter the password: ");
           TextField repassField = new TextField();
           repassField.setPromptText("Re enter password");
           GridPane.setConstraints(repassword, 0, 7);
           GridPane.setConstraints(repassField,1, 7);

           minorLayout.getChildren().addAll(fname, fnameField, sname, snameField, lname, lnameField, idAlert, workerId, workerIdField, passAlert,
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

           bottomLayout.getChildren().addAll(addButton, cancelButton, doneButton);

           mainLayoutAdd.getChildren().addAll(topLayout, minorLayout, bottomLayout);


           Scene scene = new Scene(mainLayoutAdd, 500, 400);
           windowAdd.setScene(scene);
           windowAdd.showAndWait();
       });

       ActivateUser.setOnAction(e -> {
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
            TextField idField = new TextField();
            idField.setPromptText("Enter worker id ");
            GridPane.setConstraints(idField, 1, 1);
            midLayoutAct.getChildren().addAll(AlertLabel, workerIdLabel, idField);

            HBox bottomLayout = new HBox(10);
            Button actButton = new Button("Activate user");
            actButton.setOnAction(e1 -> {
                String wId = idField.getText();
                if(wId.length() == 0){
                    AlertLabel.setText("You cannot submit an empty field");
                }
                else {
                    sql.activateUser(conn, wId, AlertLabel);
                }

            });
            Button cancelButton = new Button("Cancel");
            Button doneButton = new Button("Done");
            bottomLayout.getChildren().addAll(actButton, cancelButton, doneButton);

            mainLayoutact.getChildren().addAll(midLayoutAct, bottomLayout);
            Scene scene = new Scene(mainLayoutact, 400, 350);
            windowAct.setScene(scene);
           windowAct.showAndWait();
       });

       DeactivateUser.setOnAction(e -> {
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
           TextField idField = new TextField();
           idField.setPromptText("Enter worker id ");
           GridPane.setConstraints(idField, 1, 1);
           midLayoutAct.getChildren().addAll(AlertLabel, workerIdLabel, idField);

           HBox bottomLayout = new HBox(10);
           Button actButton = new Button("Deactivate user");
           actButton.setOnAction(e1 -> {
               String wId = idField.getText();
               if(wId.length() == 0){
                   AlertLabel.setText("You cannot submit an empty field");
               }
               else {
                   sql.deactivateUser(conn, wId, AlertLabel);
               }

           });
           Button cancelButton = new Button("Cancel");
           Button doneButton = new Button("Done");
           bottomLayout.getChildren().addAll(actButton, cancelButton, doneButton);

           mainLayoutact.getChildren().addAll(midLayoutAct, bottomLayout);
           Scene scene = new Scene(mainLayoutact, 400, 350);
           windowDeAct.setScene(scene);

           windowDeAct.showAndWait();
       });

       ResetUserPass.setOnAction(e -> {
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
           TextField idField = new TextField();
           idField.setPromptText("Enter worker id ");
           GridPane.setConstraints(idField, 1, 1);
           Label passAlert = new Label();
           GridPane.setConstraints(passAlert, 0, 2);
           GridPane.setColumnSpan(passAlert, 2);
           Label passLabel = new Label("Password: ");
           GridPane.setConstraints(passLabel, 0, 3);
           TextField passField = new TextField();
           passField.setPromptText("Enter new password");
           GridPane.setConstraints(passField, 1, 3);
           Label rePassWord = new Label("Re-Type password: ");
           GridPane.setConstraints(rePassWord, 0, 4);
           TextField repassField = new TextField();
           repassField.setPromptText("Re-Enter the password");
           GridPane.setConstraints(repassField, 1, 4);

           midLayoutAct.getChildren().addAll(AlertLabel, workerIdLabel, idField, passAlert, passLabel,passField,rePassWord, repassField );

           HBox bottomLayout = new HBox(10);
           Button actButton = new Button("Reset Password");
           actButton.setOnAction(e1 -> {
               String wId = idField.getText();
               String pass = passField.getText();
               String repass = repassField.getText();
               if(wId.length() == 0 || pass.length() == 0 || repass.length() == 0){
                   AlertLabel.setText("You cannot submit an empty field(s)");
               }
               else {
                   if(pass.equals(repass)){
                       sql.updateUserPass(conn, wId,pass, AlertLabel);
                   }
                   else {
                       passAlert.setText("Password field should be the same ");
                       AlertLabel.setText("");
                   }

               }

           });
           Button cancelButton = new Button("Cancel");
           Button doneButton = new Button("Done");
           bottomLayout.getChildren().addAll(actButton, cancelButton, doneButton);

           mainLayoutact.getChildren().addAll(midLayoutAct, bottomLayout);
           Scene scene = new Scene(mainLayoutact, 400, 350);
           windowRe.setScene(scene);

           windowRe.showAndWait();
       });

       AssignDuty.setOnAction(e -> {
           Stage windowAss = new Stage();
           windowAss.setTitle("Assign a  user duty");
           windowAss.initModality(Modality.APPLICATION_MODAL);

           windowAss.showAndWait();
       });

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            sql.logout(conn, workerid);
            window.close();
        });

        menuBar.getMenus().addAll(ViewBookings, ManageWorkers);
        mainLayout.setTop(menuBar);
        mainLayout.setLeft(leftLayout);
        mainLayout.setCenter(middleSection);
        mainLayout.setRight(rightLayout);


        Scene scene = new Scene(mainLayout);
        scene.getStylesheets().add("Viper.css");
        window.setScene(scene);
        window.show();



    }
}
