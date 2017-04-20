package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.sql.Connection;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.*;

public class Main extends Application {
    private Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception{

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        window = primaryStage;
        Scene scene;
        window.setTitle("Cafeteria Management System");

        //INSTANTIATING SQLClass member
        SQLClass SQL = new SQLClass();


        //Layouts
        BorderPane mainLayout = new BorderPane();
        VBox heading = new VBox(10);
        GridPane grid = new GridPane();


        grid.setPadding(new Insets(10,5,10,10));
        grid.setHgap(5);
        grid.setVgap(8);
        grid.setAlignment(Pos.CENTER);

        Label label = new Label("Cafeteria Management System");
        label.getStyleClass().add("label-Heading");
        label.setPadding(new Insets(30,10,10,10));
        Label deactivatedAccount = new Label();
        deactivatedAccount.getStyleClass().add("activationLabel");
        heading.getChildren().addAll(label, deactivatedAccount);
        heading.setAlignment(Pos.CENTER);


        Label useridAlert = new Label();
        GridPane.setConstraints(useridAlert, 1, 1);

        Label userIdLabel = new Label("UserId");
        GridPane.setConstraints(userIdLabel, 0,2);

        TextField userId = new TextField();
        userId.setPromptText("User id");
        GridPane.setConstraints(userId, 1, 2);

        Label passAlert = new Label();
        GridPane.setConstraints(passAlert, 1, 3);

        Label passLabel = new Label("Password");
        GridPane.setConstraints(passLabel, 0, 4);

        TextField passInput = new TextField();
        passInput.setPromptText("Password");
        GridPane.setConstraints(passInput, 1, 4);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 5);


        grid.getChildren().addAll(useridAlert, userIdLabel, userId, passAlert, passLabel, passInput, loginButton);

        mainLayout.setTop(heading);
        mainLayout.setCenter(grid);

        scene = new Scene(mainLayout);
        loginButton.setOnAction( e -> {
            String name = userId.getText();
            String pass = passInput.getText();
            //setUserAgentStylesheet(STYLESHEET_CASPIAN);
            if(name.length() < 5 | pass.length() < 8) {
                if (name.length() < 5) {
                    useridAlert.setText("Please input a valid user id");
                } else {
                    System.out.println("Your name is: " + name);
                    useridAlert.setText("");
                    userId.setText("");
                }

                if (pass.length() < 8) {
                    passAlert.setText("Please input password, Password must be atleast eight characters");
                } else {
                    System.out.println("Password is: " + pass);
                    passAlert.setText("");
                    passInput.setText("");
                }
            }else {
                SQLClass sql = new SQLClass();
                Connection conn =  sql.connect();
                ResultSet rs = sql.SelectFromDb(conn, userId.getText(), passInput.getText() );
                try {
                    int counter = 0;
                    String level = null;
                    String point = null;
                    String status = null;
                    String workerid = null;
                    while (rs.next()) {
                           level = rs.getString("level_of_operation");
                           point = rs.getString("operation_point");
                           status = rs.getString("status");
                           workerid = rs.getString("WorkerId");
                            ++counter;
                    }
                    if (counter == 0 | counter > 1){
                        System.out.println("There is no search user");
                    }
                    else {
                        if (status.equals("notactive")) {
                            deactivatedAccount.setText("Your account was deactivated please talk to the manager");
                        } else {
                            if (level.equals("major")) {
                                sql.login(conn, workerid );
                                manager.display(conn, workerid);
                                window.close();
                            } else {
                                if (point.equals("store")) {
                                    sql.login(conn, workerid );
                                    store.display(scene);
                                    window.close();
                                } else {
                                    sql.login(conn, workerid );
                                    salesPoint.display(conn);
                                    window.close();
                                }
                            }

                        }
                    }
                }catch (SQLException ex){
                    System.out.println("Unable to authenticate for the moment");
                }


            }


        });

        scene.getStylesheets().add("Viper.css");
        window.setScene(scene);
        window.show();
    }


    public static void main(String[] args) {
        SQLClass sql = new SQLClass();
       Connection conn =  sql.connect();
       /*ResultSet rs ; //sql.SelectFromDb(conn, "W13/21385/14", "@njokimuthoni");
        sql.updateUserPass(conn, "W13/21385/14", "@njokimuthoni");
        //sql.deactivateUser(conn, "W13/00002/14");
        //sql.adduser(conn, "John4","Maina4","Muchiri4", "W13/00006/14","minor","@johnmaina4");
        Double price = sql.getFoodPrice(conn, "Ugali");
        System.out.println(price);
        sql.addFoodItem(conn, "Pizza", 30.0);
        sql.updateFoodPrice(conn , "Tea", 10.0);
           /* try {
                while(rs.next()) {
                    String Fname = rs.getString("fname");
                    String Sname = rs.getString("sname");
                    String Lname = rs.getString("lname");
                    String Level = rs.getString("level_of_operation");
                    String pswd = rs.getString("pswd");

                    System.out.println("Fname: " + Fname + " Fname: " + Sname + " sname: " + Lname + " Level: " + Level +"Password: "+pswd);

                }
            }catch (SQLException E){

                System.out.println("There is no user with id ");

            }*/
        //}
      // System.out.println(conn);
        launch(args);
        sql.closeConnection(conn);
    }
}
