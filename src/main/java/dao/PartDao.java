package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Part;

public interface PartDao {
    public PreparedStatement getAddStatement() throws SQLException;

    public int add(Part part) throws SQLException;

    public int[] addAll(List<Part> parts) throws SQLException;

    public void delete(int id) throws SQLException;

    public Part getPart(int id) throws SQLException;

    public List<Part> getAllParts() throws SQLException;

    public void update(Part part) throws SQLException;
}
