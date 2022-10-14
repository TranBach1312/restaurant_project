package entity;

import java.sql.Date;

public class Bill {
    private int id;
    private String billNumber;
    private int orderId;
    private int customerId;
    private boolean haveVat;
    private double vat;
    private double totalDiscount;
    private double totalMoney;
    private String payMethod;
    private String note;
    private Date createdAt;

    public Bill() {
    }

    public Bill(int id, String billNumber, int orderId, int customerId, boolean haveVat, double vat, double totalDiscount, double totalMoney, String payMethod, String note, Date createdAt) {
        this.id = id;
        this.billNumber = billNumber;
        this.orderId = orderId;
        this.customerId = customerId;
        this.haveVat = haveVat;
        this.vat = vat;
        this.totalDiscount = totalDiscount;
        this.totalMoney = totalMoney;
        this.payMethod = payMethod;
        this.note = note;
        this.createdAt = createdAt;
    }

    public Bill(String billNumber, int orderId, int customerId, boolean haveVat, double vat, double totalDiscount, double totalMoney, String payMethod, String note) {
        this.billNumber = billNumber;
        this.orderId = orderId;
        this.customerId = customerId;
        this.haveVat = haveVat;
        this.vat = vat;
        this.totalDiscount = totalDiscount;
        this.totalMoney = totalMoney;
        this.payMethod = payMethod;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isHaveVat() {
        return haveVat;
    }

    public void setHaveVat(boolean haveVat) {
        this.haveVat = haveVat;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
