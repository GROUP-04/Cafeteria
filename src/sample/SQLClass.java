package sample;



import javafx.collections.ObservableList;
import javafx.scene.control.*;
import java.sql.*;
public class SQLClass {

    private Statement st;
    private ResultSet rs ;
    private Connection conn = null;
//Sql methods

    public Connection createConnection (Label label){
        final String USER = "root";
        final String PASSWORD = "gemini";
        final String DB_URL = "jdbc:mysql://localhost/cafeteria";
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

        try{
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

        }catch (Exception e){

            label.setText(e.getMessage());
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

    public  ResultSet SelectFromDb(Connection conn, String workerId, String password, Label label){
        try {

            st = conn.createStatement();
            String sql = "SELECT * FROM users WHERE WorkerId = " +"'"+workerId+"'"+" AND " + "pswd = "+"'"+password+"'";
            rs = st.executeQuery(sql);

        }catch (SQLException e){
            label.setText("Unable to fetch records");
        }
        return rs;
    }

    public void login(Connection conn, String workerid){

        String sql = "UPDATE users SET onlineStatus = 1 WHERE WorkerId = '"+workerid+"'";
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
        String sql = "UPDATE users SET onlineStatus = 0 WHERE WorkerId = '"+workerid+"'";
        try{
            st = conn.createStatement();
            try{
                st.executeUpdate(sql);
                System.out.println("User LoggedOut");
            }catch (SQLException ex){
                System.out.println(ex);
            }
        }catch (SQLException ex){
            System.out.println("Unable to establish a connection");
        }
    }

    //Used to create a drop down list for the manager displaying workerids
    public void selectForCombo(Connection conn, ComboBox combo, Label label){
        try {

            st = conn.createStatement();
            String sql = "SELECT WorkerId FROM users ";
            rs = st.executeQuery(sql);

            try{
                while(rs.next()){
                    String id = rs.getString("WorkerId");
                    combo.getItems().add(id);
                }
            }catch (SQLException e){
                label.setText("Unable to fetch a list of users ids");
            }


        }catch (SQLException e){
            label.setText(e+"");
        }
    }

    //Function used by the manager to create a new user
    public void adduser(Connection conn, String fname, String sname,String lname, String workerId, String level_of_operarion, String pswd, Label label){
        try{
            st = conn.createStatement();
            String sql = "SELECT * FROM users WHERE WorkerId = '"+ workerId +"'";
            ResultSet rs = st.executeQuery(sql);
            int count = 0;
            try{
                while(rs.next()){
                    int i = ++count;
                }
                if(count > 0){
                    label.setText("A user with search id already exists");
                }else {
                    try{
                        int level = 0;
                        if(level_of_operarion.equals("store")) level = 2;

                        if(level_of_operarion.equals("sales")) level = 1;
                        String sql2 = "INSERT INTO users(WorkerId, fname, sname, lname, operation_point, pswd)VALUES"+
                                "('"+workerId+"', '"+fname+"', '"+sname+"','"+lname+"', '"+level+"', '"+pswd+"')";
                        st.executeUpdate(sql2);

                        label.setText("User successfully added");
                    }catch (SQLException ex){
                        label.setText("Failed to add user1");
                    }
                }
            }catch (SQLException ex){
                label.setText("Unable to add user at the moment2");
            }

        }catch (SQLException ex){
            label.setText("Failed to add user3");
        }
    }

    //Used by the manager to re assign duty i.e change the point of operation of a worker
    //i.e to store or salespoint
    public void assignDuty(Connection conn, String id, String opPoint, Label label){
        try {
            int operation = 0;
            if (opPoint.equals("sales")) operation = 1;

            if(opPoint.equals("store")) operation = 2;
            st = conn.createStatement();
            String sql = "UPDATE users SET operation_point = '" + operation + "' WHERE WorkerId = '" + id + "'";
            String sql1 = "SELECT * FROM users WHERE WorkerId = '" + id + "'";

            try {
                rs = st.executeQuery(sql1);
                int count = 0;
                while (rs.next()) {
                    ++count;
                }
                if (count != 0) {
                    try {
                        st.executeUpdate(sql);
                        label.setText("Operation point successfully updated");
                    } catch (SQLException e) {
                        label.setText("Unable to update operation point");
                    }
                }else{
                    label.setText("Incorrect user id");
                }
            } catch(SQLException e){
                label.setText("");
            }

        }catch (SQLException e){

        }
    }

    //Method used by the manager to reactivated a user incase he/she was deactivated for any reason
    public  void activateUser(Connection conn, String workerid, Label label){
        try {
            st = conn.createStatement();
            String sql2 = "UPDATE users SET status=1 WHERE WorkerId='"+workerid+"'";
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

    //Function used to deactivate a user by the manager
    public  void deactivateUser(Connection conn, String workerid, Label label ){
        try {
            st = conn.createStatement();
            String sql2 = "UPDATE users SET status=0 WHERE WorkerId='"+workerid+"'";
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

    //Method used by the manager to update a user password incase of forgetting
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

    public  void selectAllBookings(Connection conn, ObservableList<bookings> list){
        String sql = "SELECT * FROM bookings ";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                String date = rs.getString("dateToOccur");
                String foodCode = rs.getString("foodCode");
                String phone = rs.getString("PHONE");
                int attendance = Integer.parseInt(rs.getString("attendance"));
                list.add(new bookings(attendance, date, foodCode, phone));
            }
        }catch (SQLException E) {

        }
    }

    public  void selectNewBookings(Connection conn, ObservableList<bookings> list){
        String sql = "SELECT * FROM bookings WHERE ReadStatus = 0 ";
        String sql1 = "UPDATE bookings SET ReadStatus = 1 WHERE ReadStatus = 0";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                String date = rs.getString("dateToOccur");
                String foodCode = rs.getString("foodCode");
                String phone = rs.getString("PHONE");
                int attendance = Integer.parseInt(rs.getString("attendance"));
                list.add(new bookings(attendance, date, foodCode, phone));
            }

            st.executeUpdate(sql1);
        }catch (SQLException E) {

        }
    }

    //Method used to get the worker who are online, used at the manager's class
    public  void getOnlineUsers(Connection conn, Label label){
        String sql = "SELECT * FROM users WHERE onlineStatus = 1";
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
                    label.setText("");
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

    //Method used to get the food that is ready to be sold to customers
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

    //Method used to add a new food item, used by the manager
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

    //To make a drop down list for the manager with food names
    // incase he/she doesn't know the actual name of the food item
    public void selectFoodName(Connection conn, ComboBox combo, Label label){
        try {

            st = conn.createStatement();
            String sql = "SELECT FoodName FROM food ";
            rs = st.executeQuery(sql);
            try{
                while(rs.next()){
                    String id = rs.getString("FoodName");
                    combo.getItems().add(id);
                }
            }catch (SQLException e){
                label.setText("Unable to fetch a list of food names");
            }
        }catch (SQLException e){
            label.setText(e+"");
        }
    }

    //Used to delete food item although it not supposed to be used
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

    //Set food available to appear on the menu
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

    //Used to set food as unavailable, used by the manager
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
                            label.setText("Unable to update food item at the moment");
                        }
                    }else {
                        label.setText("No food item with the name "+name);
                    }
                }catch (SQLException ex){
                    label.setText("Unable to fetch results from database");
                }
            }catch (SQLException ex){
                label.setText("Unable to query the database");
            }

        }catch (SQLException ex){
            label.setText("Unable to establish a connection");
        }
    }

    //Method used to update food price used by the manager
    public  void updateFoodPrice(Connection conn, String name, Double price, Label label){
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
                            label.setText("Food item price successfully updated");
                        }catch (SQLException ex){
                            label.setText("Unable to update food item price");
                        }
                    }else {
                        label.setText("Incorrect food item name");
                    }

                }catch (SQLException ex){
                    label.setText("Unable to fetch results");
                }
            }catch (SQLException ex){
                label.setText("Unable to verify food item");
            }
        }catch (SQLException ex){
            label.setText("Invalid connection handle");
        }

    }

    //Method to new stock to the database
    public  void addToStore(Connection conn, String foodName, Double cost, int quantity, Double measurement, Label label){
        //SQL query to check if the food already exists in database if its not
        String sql = "SELECT * FROM current_stock WHERE FoodName = '"+foodName+"'";

        //SQL query to insert new stock to the database
        String sql2 = "INSERT INTO store(FoodName, quantity, measurement, cost) VALUES('"+foodName+"', "+quantity+",'"+measurement+"', '"+cost+"')";
        String sql3 = "INSERT INTO current_stock(FoodName, Quantity) VALUES('"+foodName+"', "+quantity+")";
        int qnt = 0;
        try{
            st = conn.createStatement();
            try{
                int count = 0;
                rs =  st.executeQuery(sql);
                while(rs.next()){
                    qnt = Integer.parseInt(rs.getString("Quantity"));
                    ++count;
                }
                String sql4 = "UPDATE current_stock SET Quantity = "+(quantity + qnt)+" WHERE FoodName = '"+foodName+"'";
                if(count == 0){
                    st.executeUpdate(sql2);
                    st.executeUpdate(sql3);
                    label.setText("Food item successfully added to the database");
                }else if(count == 1){
                    st.executeUpdate(sql2);
                    st.executeUpdate(sql4);
                    label.setText("Food item successfully added to the database");
                }else{
                    label.setText("Failed to add food item to the database");
                }


            }catch (SQLException e){
                label.setText("Unable to fetch results");
            }
        }catch (SQLException e) {
            label.setText("Database connection failed");
        }
    }

    public ResultSet selectCurrentStock(Connection conn, Label label){

        String sql = "SELECT * FROM current_stock";
        try{
            st = conn.createStatement();
            rs = st.executeQuery(sql);
        }catch (SQLException e) {
            label.setText("Failed to connect to the database");
        }
        return rs;
    }

    //Method used at the salesPoint to get the foodPrice
    public ResultSet getFoodPrice(Connection conn, String Code) {

        try {
            st = conn.createStatement();
            String sql1 = "SELECT * FROM food WHERE Code = '" + Code + "'";
            try {
                rs = st.executeQuery(sql1);

            } catch (SQLException ex) {
                System.out.println("Failed to execute database query");
            }

        } catch (SQLException ex) {
            System.out.println("Failed to fetch data from the database");

        }
        return rs;
    }

}
