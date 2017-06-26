package sample;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Main extends Application {

    private Stage Main;
    private String workerId = null;

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        double Height = bounds.getHeight();
        double Width = bounds.getWidth();

        primaryStage.setTitle("CAFETERIA MANAGEMENT SYSTEM");
        Main = primaryStage;

        Image icon = new Image("file:src/images/cafe.jpg");
        Scene scene = new Scene(root, Width, Height);
        scene.getStylesheets().add("CafeteriaStyle.css");
        Main.setScene(scene);
        Main.getIcons().add(icon);
        Main.show();
    }

    @Override
    public void stop(){
        SQLClass sql = new SQLClass();
        if (workerId != null){
            sql.logout(MainController.conn, this.getWorkerId());
        }
        sql.closeConnection(MainController.conn );
    }

    private void loadSplash (){
        Scene Splash;
        try{
            StackPane pane = FXMLLoader.load(getClass().getResource("WelcomeLayout.fxml"));


            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(0);
            fadeOut.setToValue(1);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished(e -> {
                fadeOut.play();


            });
            // Splash  = new Scene(pane, 700, 600);
           // Main.setScene(Splash);
           // Main.show();
        }catch (Exception e){
            //Done something
        }
    }

    @Override
    public void init(){
        loadSplash();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
