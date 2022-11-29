package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Part;

public class PartDaoImpl extends DaoImpl implements PartDao {
    public PartDaoImpl(Connection conn) {
        super(conn);
    }

    @Override
    public PreparedStatement getAddStatement() throws SQLException {
        String query = "INSERT INTO "
                + "part (pID, pName, pPrice, mID, cID, pWarrantyPeriod, pAvailableQuantity) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return conn.prepareStatement(query);
    }

    private PreparedStatement addPart(PreparedStatement ps, Part part) throws SQLException {
        ps.setInt(1, part.getID());
        ps.setString(2, part.getName());
        ps.setInt(3, part.getPrice());
        ps.setInt(4, part.getManufacturerID());
        ps.setInt(5, part.getCategoryID());
        ps.setInt(6, part.getWarrantyPeriod());
        ps.setInt(7, part.getAvailableQuantity());
        return ps;
    }

    @Override
    public int add(Part part) throws SQLException {
        PreparedStatement ps = getAddStatement();
        return addPart(ps, part).executeUpdate();
    }

    @Override
    public int[] addAll(List<Part> parts) throws SQLException {
        PreparedStatement ps = getAddStatement();
        for (Part part : parts) {
            ps = addPart(ps, part);
            ps.addBatch();
        }
        return ps.executeBatch();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM part WHERE pID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public Part getPart(int id) throws SQLException {
        String query = "SELECT * FROM part WHERE pID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Part part = new Part();
        if (rs.next()) {
            part.setID(rs.getInt("pID"));
            part.setName(rs.getString("pName"));
            part.setPrice(rs.getInt("pPrice"));
            part.setManufacturerID(rs.getInt("mID"));
            part.setCategoryID(rs.getInt("cID"));
            part.setWarrantyPeriod(rs.getInt("pWarrantyPeriod"));
            part.setAvailableQuantity(rs.getInt("pAvailableQuantity"));
            return part;
        }

        return null;
    }

    @Override
    public List<Part> getAllParts() throws SQLException {
        String query = "SELECT * FROM part " + getQuerySuffix();
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<Part> parts = new java.util.ArrayList<Part>();

        while (rs.next()) {
            Part part = new Part();
            part.setID(rs.getInt("pID"));
            part.setName(rs.getString("pName"));
            part.setPrice(rs.getInt("pPrice"));
            part.setManufacturerID(rs.getInt("mID"));
            part.setCategoryID(rs.getInt("cID"));
            part.setWarrantyPeriod(rs.getInt("pWarrantyPeriod"));
            part.setAvailableQuantity(rs.getInt("pAvailableQuantity"));
            parts.add(part);
        }

        return parts;
    }

    @Override
    public void update(Part part) throws SQLException {
        String query = "UPDATE part SET pName =?, pPrice =?, mID =?, cID =?, pWarrantyPeriod =?, pAvailableQuantity =? WHERE pID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, part.getName());
        ps.setInt(2, part.getPrice());
        ps.setInt(3, part.getManufacturerID());
        ps.setInt(4, part.getCategoryID());
        ps.setInt(5, part.getWarrantyPeriod());
        ps.setInt(6, part.getAvailableQuantity());
        ps.setInt(7, part.getID());
        ps.executeUpdate();
    }
}
