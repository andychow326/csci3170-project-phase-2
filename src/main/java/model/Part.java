package model;

public class Part extends BaseModel {
    protected String name;
    protected int price;
    protected int manufacturerID;
    protected int categoryID;
    protected int warrantyPeriod;
    protected int availableQuantity;

    public Part() {
        super();
    }

    public Part(
            int id,
            String name,
            int price,
            int manufacturerID,
            int categoryID,
            int warrantyPeriod,
            int availableQuantity) {
        super(id);
        this.name = name;
        this.price = price;
        this.manufacturerID = manufacturerID;
        this.categoryID = categoryID;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getManufacturerID() {
        return this.manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public int getCategoryID() {
        return this.categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getWarrantyPeriod() {
        return this.warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public int getAvailableQuantity() {
        return this.availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
