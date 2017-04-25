package sample;

import javafx.scene.control.Label;

import java.sql.*;
import java.util.concurrent.ConcurrentNavigableMap;

public class SQLClass {
    private String DB_URL = "jdbc:mysql://localhost/cafeteria";
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String USER = "JQHN";
    private String PASSWORD = "gemini";
    public Statement st;
    public ResultSet rs ;
    private Connection conn = null;

    public Connection connect(){
        try {
            Class.forName(JDBC_DRIVER);
            //Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        }catch (Exception e){
            System.out.println("Failed to connect to the database");

        }


        return conn;
    }
    public void closeConnection(Connection conn){
        try{
            if(conn!=null)
                conn.close();
            System.out.println("The connection was close");
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    public  ResultSet SelectFromDb(Connection conn, String workerId, String password){
        try {

            st = conn.createStatement();
            String sql = "SELECT * FROM users WHERE WorkerId = " +"'"+workerId+"'"+" AND " + "pswd = "+"'"+password+"'";
            rs = st.executeQuery(sql);

        }catch (SQLException e){
            System.out.println(e);
        }
        return rs;
    }
     public void adduser(Connection conn, String fname, String sname,String lname, String workerId, String level_of_operarion, String pswd){
        try{
            st = conn.createStatement();
            String sql = "SELECT * FROM users WHERE WorkerId = '"+ workerId +"'";
            ResultSet rs = st.executeQuery(sql);
            int count = 0;
            try{
                //fetch rows from the result set
                while(rs.next()){
                    ++count;
                }
                if(count > 0){
                    System.out.println("A user with search id already exists");
                }else {
                    try{
                        String sql2 = "INSERT INTO users(WorkerId, fname, sname, lname, level_of_operation, pswd)VALUES"+
                                "('"+workerId+"', '"+fname+"', '"+sname+"','"+lname+"', '"+level_of_operarion+"', '"+pswd+"')";
                        st.executeUpdate(sql2);

                        System.out.println("User successfully added");
                    }catch (SQLException ex){
                        System.out.println("Failed to add user1");
                    }
                }
            }catch (SQLException ex){
                System.out.println("Unable to add user at the moment2");
            }

        }catch (SQLException ex){
            System.out.println("Failed to add user3");
        }
     }

     public void updateUserPass(Connection conn, String workerid, String newpass, Label label){
         try {
             st = conn.createStatement();
             String sql = "UPDATE users SET pswd ='"+newpass+"' WHERE WorkerId = '"+workerid+"'";
             String sql1 = "SELECT * FROM users WHERE WorkerId = '"+workerid+"'";
             rs = st.executeQuery(sql1);
             try{
                 int count = 0;
                while(rs.next()){
                     ++count;
                 }
                 if(count == 0 ){
                     label.setText("Incorrect worker id ");

                 }else {
                     try{
                         st.executeUpdate(sql);
                         label.setText("User password successfully updated");
                     }catch (SQLException ex){
                         label.setText("Failed to update user password1");
                     }
                 }
             }catch(SQLException ex){
                 label.setText("Unable verify results 2");
             }
         }catch (SQLException ex){
             label.setText("Unable to get user 3");
         }
     }

     public  void activateUser(Connection conn, String workerid, Label label){
         try {
             st = conn.createStatement();
             String sql2 = "UPDATE users SET status='active' WHERE WorkerId='"+workerid+"'";
             String sql1 = "SELECT * FROM users WHERE WorkerId = '"+workerid+"'";
             rs = st.executeQuery(sql1);
             try{
                 int count = 0;
                 while(rs.next()){
                     ++count;
                 }
                 if(count == 0 ){
                     label.setText("Incorrect worker id ");

                 }else {
                     try{
                         st.executeUpdate(sql2);
                        label.setText("Worker successfully activated");
                     }catch (SQLException ex){
                         label.setText("Failed to update user password1");
                     }
                 }
             }catch(SQLException ex){
                 label.setText("Unable verify results 2");
             }
         }catch (SQLException ex){
             label.setText("Unable to get user 3");
         }
     }

     public  void deactivateUser(Connection conn, String workerid, Label label ){
         try {
             st = conn.createStatement();
             String sql2 = "UPDATE users SET status='notactive' WHERE WorkerId='"+workerid+"'";
             String sql1 = "SELECT * FROM users WHERE WorkerId = '"+workerid+"'";
             rs = st.executeQuery(sql1);
             try{
                 int count = 0;
                 while(rs.next()){
                     ++count;
                 }
                 if(count == 0 ){
                     label.setText("Incorrect worker id ");

                 }else {
                     try{
                         st.executeUpdate(sql2);
                         label.setText("Worker successfully deactivated");
                     }catch (SQLException ex){
                         label.setText("Failed to deactivate user");
                     }
                 }
             }catch(SQLException ex){
                 label.setText("Unable verify results 2");
             }
         }catch (SQLException ex){
             label.setText("Unable to get user 3");
         }
     }

     public Double getFoodPrice(Connection conn, String foodName){
         Double price = 0.0;
         try{
             st = conn.createStatement();
             String sql1 = "SELECT * FROM food WHERE FoodName = '"+foodName+"'";
             try{
                 rs = st.executeQuery(sql1);
                 try{
                     while(rs.next()){
                         price = Double.parseDouble(rs.getString("Price_Per_Unit"));
                     }
                 }catch (SQLException ex){
                     System.out.println("Failed to fetch query results");
                 }
             }catch (SQLException ex){
                 System.out.println("Failed to execute database query");
             }

         }catch (SQLException ex){
            System.out.println("Failed to fetch data from the database");

         }

         return price;
     }

     public void addFoodItem(Connection conn, String name, String price, Label label){
        try{
            st = conn.createStatement();
            String sql = "INSERT INTO FOOD (FoodName, Unit_Of_Measurement, Price_Per_Unit, Availability)VALUES ('"+name+"', 1 ,'"+price+"', 1)";
            try{
                rs = st.executeQuery("SELECT * FROM food WHERE FoodName = '"+name+"'");
                try{
                    int count = 0;
                    while(rs.next()){
                        ++count;
                    }
                    if(count == 0){
                        try{
                            st.executeUpdate(sql);
                            label.setText("Food item was successfully added");
                        }catch (SQLException ex){
                            label.setText("Unable to insert into the database");
                        }
                    }
                    else {
                        label.setText("Food item already exists");
                    }
                }catch (SQLException ex){
                    label.setText("Unable to validate food name");
                }


        }catch (SQLException ex){
            System.out.println("Unable to reach the database");
        }
        }catch(SQLException ex){
            System.out.println("Unable to check if food item already exists");
        }
     }

     public  void updateFoodPrice(Connection conn, String name, Double price){
         String sql = "SELECT * FROM food WHERE FoodName= '"+name+"'";
         String sql1 = "UPDATE food SET Price_Per_Unit = "+price+"WHERE FoodName = '"+name+"'";
         try{
             st = conn.createStatement();
             try{
                 rs = st.executeQuery(sql);
                 try{
                     int count  = 0;
                     while(rs.next()){
                         ++count;
                     }
                     if(count == 1){
                         try {
                             st.executeUpdate(sql1);
                             System.out.println("Food item price successfully updated");
                         }catch (SQLException ex){
                             System.out.println("Unable to update food item price");
                         }
                     }else {
                         System.out.println("Incorrect food item name");
                     }

                 }catch (SQLException ex){
                     System.out.println("Unable to fetch results");
                 }
             }catch (SQLException ex){
                 System.out.println("Unable to verify food item");
             }
         }catch (SQLException ex){
             System.out.println("Invalid connection handle");
         }

     }

    public void login(Connection conn, String workerid){

         String sql = "UPDATE users SET onlineStatus = 'online' WHERE WorkerId = '"+workerid+"'";
         try{
              st = conn.createStatement();
              try{
                  st.executeUpdate(sql);
              }catch (SQLException ex){
                  System.out.println("Unable to login");
              }

         }catch (SQLException ex){
             System.out.println("Unable to establish a connection");
         }

    }

    public  void logout(Connection conn, String workerid){
        String sql = "UPDATE users SET onlineStatus = 'offline' WHERE WorkerId = '"+workerid+"'";
        try{
            st = conn.createStatement();
            try{
                st.executeUpdate(sql);
            }catch (SQLException ex){
                System.out.println(ex);
            }
        }catch (SQLException ex){
            System.out.println("Unable to establish a connection");
        }
    }

        public  void getOnlineUsers(Connection conn, Label label){
        String sql = "SELECT * FROM users WHERE onlineStatus = 'online'";
        String workerid = null;
        String fname = null;
        String sname = null;
        String labelString = "";

        try {
            st = conn.createStatement();
            try{
                rs = st.executeQuery(sql);
                try{
                    while(rs.next()){
                        workerid = rs.getString("WorkerId");
                        fname = rs.getString("fname");
                        sname = rs.getString("sname");

                        labelString += workerid+ "\t  "+fname +"\t  "+sname+"\n\n";
                    }
                    label.setText(labelString);
                }catch (SQLException ex){
                    System.out.println("Unable to acquire result");
                }
            }catch (SQLException ex){
                System.out.println("Unable to fetch results");
            }
        }catch (SQLException ex){
            System.out.println("Unable to establish a connection");
        }
        }

        public  void getFoodAvailable(Connection conn, Label label){
            String labelString = "";
            String foodName = null;
            Double foodPrice = null;
            String sql = "SELECT * FROM food WHERE Availability = 1";
            try{
                 st = conn.createStatement();

                 try{
                     rs = st.executeQuery(sql);
                     try{
                         while(rs.next()){
                             foodName = rs.getString("FoodName");
                             foodPrice =Double.parseDouble(rs.getString("Price_Per_Unit"));

                             labelString += " "+foodName +"\t  @"+ foodPrice +"\n\n";
                             label.setText(labelString);
                         }
                     }catch (SQLException ex){}
                 }catch (SQLException ex){
                     System.out.println(ex);
                 }

            }catch (SQLException ex){
                System.out.println("Unable to establish a connection");
            }

        }

        public  void removeFoodItem(Connection conn, String foodName, Label label){
            String sql = "DELETE FROM food WHERE FoodName = '"+foodName+"'";
            String sql1 = "SELECT * FROM food WHERE FoodName = '"+foodName+"'";
            try{
                st = conn.createStatement();
                try{
                    rs  = st.executeQuery(sql1);
                    try{
                        int count = 0;
                        while(rs.next()){
                            ++count;
                        }
                        if(count == 1){
                            try{
                                st.executeUpdate(sql);
                                label.setText("Food Item successfully removed");
                            }catch (SQLException ex){
                                label.setText("Unable to remove food item " + ex);
                            }
                        }else {
                            label.setText("No food item with the name: "+ foodName);
                        }
                    }catch (SQLException ex){
                        label.setText("Unable to fetch results");
                    }
                }catch (SQLException ex){
                    label.setText("Unable to check if food item exists");
                }

            }catch (SQLException ex){
                label.setText("Unable to establish a database connection");
            }

        }

        public  void setUnavailableFood(Connection conn, String name, Label label){
            String sql = "SELECT * FROM food WHERE FoodName = '"+name+"'";
            String sql1 = "UPDATE food SET Availability = 0 WHERE FoodName = '"+name+"'";

            try{
                st = conn.createStatement();
                try{
                    rs = st.executeQuery(sql);
                    try{
                        int count = 0;
                        while(rs.next()){
                            ++count;
                        }
                        if(count == 1){
                            try{
                                st.executeUpdate(sql1);
                                label.setText("Food item successfully updated");
                            }catch (SQLException ex){
                                label.setText("Unable to update food item");
                            }
                        }else {
                            label.setText("No food item with the name "+name);
                        }
                    }catch (SQLException ex){
                        label.setText("Unable to fetch results");
                    }
                }catch (SQLException ex){
                    label.setText("Unable to query the database");
                }

            }catch (SQLException ex){
                label.setText("Unable to establish a connection");
            }
        }

        public void setAvailableFood(Connection conn, String name, Label label){
            String sql = "SELECT * FROM food WHERE FoodName = '"+name+"'";
            String sql2 = "UPDATE food SET Availability = 1 WHERE FoodName = '"+name+"'";

            try{
                st = conn.createStatement();
                try{
                    rs = st.executeQuery(sql);
                    try{
                        int count = 0;
                        while(rs.next()){
                            ++count;
                        }
                        if(count == 1){
                            try{
                                st.executeUpdate(sql2);
                                label.setText("Food item successfully updated");
                            }catch (SQLException e){
                                label.setText("Unable to update food item");
                            }
                        }else {
                            label.setText("No food item with the name "+name);
                        }

                    }catch (SQLException ex){
                        label.setText("Unable to fetch results");
                    }
                }catch (SQLException ex){
                    label.setText("Unable to query the database");
                }

            }catch (SQLException ex){
                label.setText("Unable to establish a connection");
            }
        }

}



