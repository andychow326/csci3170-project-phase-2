package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.SalespersonColumnKey;
import model.SalespersonRelational;

public class SalespersonRelationalDaoImpl extends DaoImpl<SalespersonRelationalDaoImpl>
        implements SalespersonRelationalDao {

    public SalespersonRelationalDaoImpl(Connection conn) {
        super(conn);
    }

    @Override
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
                    rs.getInt(SalespersonColumnKey.ID.toString()),
                    rs.getString(SalespersonColumnKey.NAME.toString()),
                    rs.getString(SalespersonColumnKey.ADDRESS.toString()),
                    rs.getInt(SalespersonColumnKey.PHONE_NUMBER.toString()),
                    rs.getInt(SalespersonColumnKey.EXPERIENCE.toString()),
                    rs.getInt("transactionCount"));
            salespersons.add(salesperson);
        }

        return salespersons;
    }
}
