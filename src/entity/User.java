package entity;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private int staffId;
    private boolean isManager;
    private boolean isActive;
    private Date createdAt;

    public User() {
    }

    public User(int id, String username, String password, int staffId, boolean isManager, boolean isActive, Date createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.staffId = staffId;
        this.isManager = isManager;
        this.isActive = isActive;
        this.createdAt = createdAt;

    }

    public User(String username, String password, int staffId, boolean isManager, boolean isActive) {
        this.username = username;
        this.password = password;
        this.staffId = staffId;
        this.isManager = isManager;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
