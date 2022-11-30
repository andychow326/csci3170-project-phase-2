package dao;

import java.sql.SQLException;
import java.util.List;

import model.PartRelational;

public interface PartRelationalDao {
    public List<PartRelational> getAllParts() throws SQLException;
}
