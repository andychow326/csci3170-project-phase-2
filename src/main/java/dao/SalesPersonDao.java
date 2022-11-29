package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.SalesPerson;

public interface SalesPersonDao {
    public PreparedStatement getAddStatement() throws SQLException;

    public int add(SalesPerson salesPerson) throws SQLException;

    public int[] addAll(List<SalesPerson> salesPersons) throws SQLException;

    public void delete(int id) throws SQLException;

    public SalesPerson getSalesPerson(int id) throws SQLException;

    public List<SalesPerson> getAllSalesPersons() throws SQLException;

    public void update(SalesPerson salesPerson) throws SQLException;
}
