package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ManufacturerRelational;

public class ManufacturerRelationalDao extends DaoImpl<ManufacturerRelationalDao> {

    public ManufacturerRelationalDao(Connection conn) {
        super(conn);
    }

    public List<ManufacturerRelational> getAllManufacturersWithTotalSalesValue() throws SQLException {
        String query = "SELECT *, SUM(part.pPrice) totalSales FROM (" +
                "transaction " +
                "INNER JOIN part ON part.pID = transaction.pID " +
                "INNER JOIN manufacturer ON manufacturer.mID = part.mID) " +
                "GROUP BY manufacturer.mID " +
                "ORDER BY totalSales DESC " +
                getQuerySuffix();
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<ManufacturerRelational> manufacturers = new ArrayList<ManufacturerRelational>();

        while (rs.next()) {
            ManufacturerRelational manufacturer = new ManufacturerRelational(
                    rs.getInt(ManufacturerRelational.ColumnKey.ID.toString()),
                    rs.getString(ManufacturerRelational.ColumnKey.NAME.toString()),
                    rs.getString(ManufacturerRelational.ColumnKey.ADDRESS.toString()),
                    rs.getInt(ManufacturerRelational.ColumnKey.PHONE_NUMBER.toString()),
                    rs.getInt("totalSales"));
            manufacturers.add(manufacturer);
        }

        return manufacturers;
    }
}
