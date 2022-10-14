package entity;

public class StaffPosition {
    private int id;
    private String name;
    private String desciption;

    public StaffPosition() {
    }

    public StaffPosition(int id, String name, String desciption) {
        this.id = id;
        this.name = name;
        this.desciption = desciption;
    }

    public StaffPosition(String name, String desciption) {
        this.name = name;
        this.desciption = desciption;
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

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
