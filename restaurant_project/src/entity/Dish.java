package entity;

public class Dish {
    private int id;
    private String name;
    private double price;
    private int totalSold;
    private int categoryId;
    private String desciption;
    private boolean deleted;
    private double averageCostPrice;
    private boolean soldOut;

    public Dish() {
    }

    public Dish(int id, String name, double price, int totalSold, int categoryId, String desciption, boolean deleted, double averageCostPrice, boolean soldOut) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.totalSold = totalSold;
        this.categoryId = categoryId;
        this.desciption = desciption;
        this.deleted = deleted;
        this.averageCostPrice = averageCostPrice;
        this.soldOut = soldOut;
    }

    public Dish(String name, double price, int categoryId, String desciption, double averageCostPrice) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.desciption = desciption;
        this.averageCostPrice = averageCostPrice;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(int totalSold) {
        this.totalSold = totalSold;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public double getAverageCostPrice() {
        return averageCostPrice;
    }

    public void setAverageCostPrice(double averageCostPrice) {
        this.averageCostPrice = averageCostPrice;
    }

    public boolean isSoldOut() {
        return soldOut;
    }

    public void setSoldOut(boolean soldOut) {
        this.soldOut = soldOut;
    }
}
