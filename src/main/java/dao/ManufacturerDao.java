package dao;

import java.sql.SQLException;
import java.util.List;

import model.Manufacturer;

public interface ManufacturerDao {
    public int add(Manufacturer manufacturer) throws SQLException;

    public void delete(int id) throws SQLException;

    public Manufacturer getManufacturer(int id) throws SQLException;

    public List<Manufacturer> getAllManufacturers() throws SQLException;

    public void update(Manufacturer manufacturer) throws SQLException;
}
