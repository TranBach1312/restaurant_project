package entity;

import java.sql.Date;

public class Staff {
    private int id;
    private String name;
    private Date birthDate;
    private String phone;
    private String address;
    private String email;
    private int staffPositionId;
    private Date startWorkedAt;
    private Date createAt;

    public Staff() {
    }

    public Staff(int id, String name, Date birthDate, String phone, String address, String email, int staffPositionId, Date startWorkedAt, Date createAt) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.staffPositionId = staffPositionId;
        this.startWorkedAt = startWorkedAt;
        this.createAt = createAt;
    }

    public Staff(String name, Date birthDate, String phone, String address, String email, int staffPositionId, Date startWorkedAt) {
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.staffPositionId = staffPositionId;
        this.startWorkedAt = startWorkedAt;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStaffPositionId() {
        return staffPositionId;
    }

    public void setStaffPositionId(int staffPositionId) {
        this.staffPositionId = staffPositionId;
    }

    public Date getStartWorkedAt() {
        return startWorkedAt;
    }

    public void setStartWorkedAt(Date startWorkedAt) {
        this.startWorkedAt = startWorkedAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
