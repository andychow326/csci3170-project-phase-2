package model;

public class SalespersonRelational extends Salesperson {
    protected int transactionCount;

    public SalespersonRelational() {
        super();
    }

    public SalespersonRelational(
            int id,
            String name,
            String address,
            int phoneNumber,
            int experience,
            int transactionCount) {
        super(
                id,
                name,
                address,
                phoneNumber,
                experience);
        this.transactionCount = transactionCount;
    }

    public int getTransactionCount() {
        return this.transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }
}
