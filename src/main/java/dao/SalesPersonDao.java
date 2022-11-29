package dao;

import java.sql.SQLException;
import java.util.List;

import model.SalesPerson;

public interface SalesPersonDao {
    public int add(SalesPerson salesPerson) throws SQLException;

    public void delete(int id) throws SQLException;

    public SalesPerson getSalesPerson(int id) throws SQLException;

    public List<SalesPerson> getAllSalesPersons() throws SQLException;

    public void update(SalesPerson salesPerson) throws SQLException;
}
