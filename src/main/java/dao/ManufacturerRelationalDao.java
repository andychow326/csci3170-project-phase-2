package dao;

import java.sql.SQLException;
import java.util.List;

import model.ManufacturerRelational;

public interface ManufacturerRelationalDao {
    public List<ManufacturerRelational> getAllManufacturersWithTotalSalesValue() throws SQLException;
}
