package entity;

import java.sql.Timestamp;

public class OrderItem {
    private int id;
    private int dishId;
    private int quantity;
    private double originTotalPrice;
    private double discount;
    private double totalMoney;
    private Timestamp createAt;

    public OrderItem() {
    }

    public OrderItem(int id, int dishId, int quantity, double originTotalPrice, double discount, double totalMoney, Timestamp createAt) {
        this.id = id;
        this.dishId = dishId;
        this.quantity = quantity;
        this.originTotalPrice = originTotalPrice;
        this.discount = discount;
        this.totalMoney = totalMoney;
        this.createAt = createAt;
    }

    public OrderItem(int dishId, int quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOriginTotalPrice() {
        return originTotalPrice;
    }

    public void setOriginTotalPrice(double originTotalPrice) {
        this.originTotalPrice = originTotalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
}
