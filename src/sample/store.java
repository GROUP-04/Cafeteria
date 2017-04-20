package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
public class store {

    public static void display(Scene scene1){
        Stage window1 = new Stage();
        window1.setTitle("Cafeteria Management System Store");
        VBox layout = new VBox(10);
        Button logOut = new Button("Logout");
        logOut.setOnAction(e -> {
            window1.setScene(scene1);
            //window1.close();
        });
        Label label = new Label("Sales point Stage");

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, logOut);
        Scene scene = new Scene(layout, 600, 700 );
        scene.getStylesheets().add("Viper.css");
        window1.setScene(scene);
        window1.show();
    }
}
