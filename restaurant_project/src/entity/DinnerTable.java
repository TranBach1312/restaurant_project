package entity;

public class DinnerTable {
    private int id;
    private String name;
    private int servingQuantity;
    private double surcharge;

    public DinnerTable() {
    }

    public DinnerTable(int id, String name, int servingQuantity, double surcharge) {
        this.id = id;
        this.name = name;
        this.servingQuantity = servingQuantity;
        this.surcharge = surcharge;
    }

    public DinnerTable(String name, int servingQuantity, double surcharge) {
        this.name = name;
        this.servingQuantity = servingQuantity;
        this.surcharge = surcharge;
    }

    public DinnerTable(String name, double surcharge) {
        this.name = name;
        this.surcharge = surcharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServingQuantity() {
        return servingQuantity;
    }

    public void setServingQuantity(int servingQuantity) {
        this.servingQuantity = servingQuantity;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(double surcharge) {
        this.surcharge = surcharge;
    }
}
