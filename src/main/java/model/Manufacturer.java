package model;

public class Manufacturer extends BaseModel {
    protected String name;
    protected String address;
    protected int phoneNumber;

    public Manufacturer() {
        super();
    }

    public Manufacturer(
            int id,
            String name,
            String address,
            int phoneNumber) {
        super(id);
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
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
}
