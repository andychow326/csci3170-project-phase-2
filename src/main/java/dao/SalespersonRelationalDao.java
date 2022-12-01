package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.SalespersonRelational;

public class SalespersonRelationalDao extends DaoImpl<SalespersonRelationalDao> {

    public SalespersonRelationalDao(Connection conn) {
        super(conn);
    }

    public List<SalespersonRelational> getAllSalespersonsByExperienceRangeWithTransactionCount(
            int lowerBound,
            int upperBound) throws SQLException {
        String query = "SELECT *, COUNT(transaction.tID) transactionCount FROM " +
                "(salesperson INNER JOIN transaction ON transaction.sID = salesperson.sID) " +
                "WHERE sExperience >=? AND sExperience <=? " +
                "GROUP BY salesperson.sID " +
                getQuerySuffix();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, lowerBound);
        ps.setInt(2, upperBound);

        ResultSet rs = ps.executeQuery();
        List<SalespersonRelational> salespersons = new ArrayList<SalespersonRelational>();

        while (rs.next()) {
            SalespersonRelational salesperson = new SalespersonRelational(
                    rs.getInt(SalespersonRelational.ColumnKey.ID.toString()),
                    rs.getString(SalespersonRelational.ColumnKey.NAME.toString()),
                    rs.getString(SalespersonRelational.ColumnKey.ADDRESS.toString()),
                    rs.getInt(SalespersonRelational.ColumnKey.PHONE_NUMBER.toString()),
                    rs.getInt(SalespersonRelational.ColumnKey.EXPERIENCE.toString()),
                    rs.getInt("transactionCount"));
            salespersons.add(salesperson);
        }

        return salespersons;
    }
}
