package model;

public class ManufacturerRelational extends Manufacturer {
    protected int totalSalesValue;

    public ManufacturerRelational() {
        super();
    }

    public ManufacturerRelational(
            int id,
            String name,
            String address,
            int phoneNumber,
            int totalSalesValue) {
        super(
                id,
                name,
                address,
                phoneNumber);
        this.totalSalesValue = totalSalesValue;
    }

    public int getTotalSalesValue() {
        return totalSalesValue;
    }

    public void setTotalSalesValue(int totalSalesValue) {
        this.totalSalesValue = totalSalesValue;
    }
}
