package model;

public class PartRelational extends Part {
    protected Manufacturer manufacturer;
    protected Category category;

    public PartRelational() {
        super();
    }

    public PartRelational(
            int id,
            String name,
            int price,
            Manufacturer manufacturer,
            Category category,
            int warrantyPeriod,
            int availableQuantity) {
        super(
                id,
                name,
                price,
                manufacturer.getID(),
                category.getID(),
                warrantyPeriod,
                availableQuantity);
        this.manufacturer = manufacturer;
        this.category = category;
    }

    public Manufacturer getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
