package sample;

/**
 * Created by JQHN on 6/25/2017.
 */
public class currentStock {
    private int id;
    private String Foodname;
    private int Quantity;

    public currentStock(int Id, String name, int quantity){
        this.id = Id;
        this.Foodname = name;
        this.Quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
