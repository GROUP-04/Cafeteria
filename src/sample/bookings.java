package sample;

public class bookings {

    private int attendance;
    private String OccurDate;
    private String foodCode;
    private String phone;

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getOccurDate() {
        return OccurDate;
    }

    public void setOccurDate(String occurDate) {
        OccurDate = occurDate;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public bookings(int att, String date, String code, String phn){
        this.attendance = att;
        this.foodCode = code;
        this.OccurDate = date;
        this.phone = phn;

    }

}
