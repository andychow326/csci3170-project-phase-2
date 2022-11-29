package model;

public class SalesPerson extends BaseModel {
    protected String name;
    protected String address;
    protected int phoneNumber;
    protected int experience;

    public SalesPerson() {
        super();
    }

    public SalesPerson(
            int id,
            String name,
            String address,
            int phoneNumber,
            int experience) {
        super(id);
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.experience = experience;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getExperience() {
        return this.experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
