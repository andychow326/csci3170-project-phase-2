package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Salesperson;

public interface SalespersonDao {
    public PreparedStatement getAddStatement() throws SQLException;

    public int add(Salesperson salesperson) throws SQLException;

    public int[] addAll(List<Salesperson> salespersons) throws SQLException;

    public void delete(int id) throws SQLException;

    public Salesperson getSalesperson(int id) throws SQLException;

    public List<Salesperson> getAllSalespersons() throws SQLException;

    public void update(Salesperson salesperson) throws SQLException;
}
