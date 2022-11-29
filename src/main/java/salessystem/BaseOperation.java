package salessystem;

import model.Category;
import model.Manufacturer;
import model.Part;
import model.SalesPerson;
import model.Transaction;

public class BaseOperation {
    public static final String[] TABLES = {
            Category.TABLE_NAME,
            Manufacturer.TABLE_NAME,
            Part.TABLE_NAME,
            SalesPerson.TABLE_NAME,
            Transaction.TABLE_NAME
    };
}
