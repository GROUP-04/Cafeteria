package sample;

/**
 * Created by JQHN on 6/26/2017.
 */
public class ReceiptStructure {

    private String name;
    private int Quantity;
    private String Code;
    private Double unitPrice;

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ReceiptStructure(String name, int quantity, String Code, Double price){
        this.Code = Code;
        this.name = name;
        this.Quantity = quantity;
        this.unitPrice = price;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
