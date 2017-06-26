package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainController {
    public Button login;
    public Button reset;
    public JFXTextField userId;
    public JFXPasswordField password;

    public static Connection conn = null;


    private SQLClass sql = new SQLClass();

    public Label alertLabel;

    public void loginUser(){

        if(userId.getText().isEmpty() || password.getText().isEmpty()){
            alertLabel.setText("Please fill all fields");
        }else{
            String userid = userId.getText();
            String pass = password.getText();

            if(userid.length() < 6){
                alertLabel.setText("Invalid UserId");
            }else if (pass.length() < 6){
                alertLabel.setText("Password must be at least six characters");
            }else {
                if(conn == null) {
                    conn = sql.createConnection(alertLabel);
                }

                ResultSet rs =  sql.SelectFromDb(conn, userid, pass, alertLabel);

                try {
                    int count = 0;
                    int level = 7;
                    int point = 8;
                    Boolean status = false;
                    String workerid = null;
                    while(rs.next()){
                        level = rs.getInt("level_of_operation");
                        point = rs.getInt("operation_point");
                        status = rs.getBoolean("status");
                        workerid = rs.getString("WorkerId");
                        ++count;
                    }

                    if(count == 0 || count > 1){
                        alertLabel.setText("Incorrect Password or WorkerId");
                    }else {
                        if(!status){
                            alertLabel.setText("Your account was deactivated Please talk to the manager");
                        }else {
                            Main mn = new Main();
                            mn.setWorkerId(workerid);
                            if(level == 0){
                                if(point == 1){
                                    Scene toClose = alertLabel.getScene();
                                    Window toCloseWindow = toClose.getWindow();
                                    toCloseWindow.hide();
                                    sql.login(conn, workerid);
                                    SalesPointController sales = new SalesPointController();
                                    sales.display();
                                }else {
                                    Scene toClose = alertLabel.getScene();
                                    Window toCloseWindow = toClose.getWindow();
                                    toCloseWindow.hide();
                                    sql.login(conn, workerid);
                                    StoreController store = new StoreController();
                                    store.display();
                                }
                            }else {
                                Scene toClose = alertLabel.getScene();
                                Window toCloseWindow = toClose.getWindow();
                                toCloseWindow.hide();
                                sql.login(conn, workerid);
                                ManagerController manager = new ManagerController();
                                manager.display();
                            }
                        }
                    }

                }catch (SQLException E){
                    alertLabel.setText("Unable fetch results from ");
                }
            }



        }
    }

    public void resetFields(){
        userId.setText("");
        password.setText("");
        alertLabel.setText(null);
    }



}
