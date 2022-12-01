package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.Manufacturer;
import model.PartRelational;

public class PartRelationalDao extends DaoImpl<PartRelationalDao> {
    public PartRelationalDao(Connection conn) {
        super(conn);
    }

    public List<PartRelational> getAll() throws SQLException {
        String query = "SELECT * FROM (part " +
                "INNER JOIN manufacturer ON part.mID = manufacturer.mID " +
                "INNER JOIN category ON part.cID = category.cID) " +
                getQuerySuffix();
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<PartRelational> parts = new ArrayList<PartRelational>();

        while (rs.next()) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setID(rs.getInt("mID"));
            manufacturer.setName(rs.getString("mName"));
            manufacturer.setAddress(rs.getString("mAddress"));
            manufacturer.setPhoneNumber(rs.getInt("mPhoneNumber"));

            Category category = new Category();
            category.setID(rs.getInt("cID"));
            category.setName(rs.getString("cName"));

            PartRelational part = new PartRelational(
                    rs.getInt("pID"),
                    rs.getString("pName"),
                    rs.getInt("pPrice"),
                    manufacturer,
                    category,
                    rs.getInt("pWarrantyPeriod"),
                    rs.getInt("pAvailableQuantity"));
            parts.add(part);
        }

        return parts;
    }

    public List<PartRelational> getAllPartsWithTransactionCount() throws SQLException {
        String query = "SELECT *, COUNT(transaction.tID) transactionCount FROM (part " +
                "INNER JOIN transaction ON transaction.pID = part.pID) " +
                "GROUP BY part.pID " +
                "ORDER BY transactionCount DESC " +
                getQuerySuffix();
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<PartRelational> parts = new ArrayList<PartRelational>();

        while (rs.next()) {
            PartRelational part = new PartRelational();
            part.setID(rs.getInt("pID"));
            part.setName(rs.getString("pName"));
            part.setPrice(rs.getInt("pPrice"));
            part.setAvailableQuantity(rs.getInt("pAvailableQuantity"));
            part.setWarrantyPeriod(rs.getInt("pWarrantyPeriod"));
            part.setTransactionCount(rs.getInt("transactionCount"));
            parts.add(part);
        }

        return parts;
    }
}
