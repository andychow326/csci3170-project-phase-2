package dao;

import java.sql.SQLException;
import java.util.List;

import model.SalespersonRelational;

public interface SalespersonRelationalDao {
    public List<SalespersonRelational> getAllSalespersonsByExperienceRangeWithTransactionCount(
            int lowerBound,
            int upperBound)
            throws SQLException;
}
