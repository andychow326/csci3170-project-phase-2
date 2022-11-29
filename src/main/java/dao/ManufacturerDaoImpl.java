package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Manufacturer;

public class ManufacturerDaoImpl extends DaoImpl<ManufacturerDaoImpl> implements ManufacturerDao {
    public ManufacturerDaoImpl(Connection conn) {
        super(conn);
    }

    @Override
    public PreparedStatement getAddStatement() throws SQLException {
        String query = "INSERT INTO "
                + "manufacturer (mID, mName, mAddress, mPhoneNumber) "
                + "VALUES (?, ?, ?, ?)";
        return conn.prepareStatement(query);
    }

    private PreparedStatement addManufacturer(PreparedStatement ps, Manufacturer manufacturer) throws SQLException {
        ps.setInt(1, manufacturer.getID());
        ps.setString(2, manufacturer.getName());
        ps.setString(3, manufacturer.getAddress());
        ps.setInt(4, manufacturer.getPhoneNumber());
        return ps;
    }

    @Override
    public int add(Manufacturer manufacturer) throws SQLException {
        PreparedStatement ps = getAddStatement();
        return addManufacturer(ps, manufacturer).executeUpdate();
    }

    @Override
    public int[] addAll(List<Manufacturer> manufacturers) throws SQLException {
        PreparedStatement ps = getAddStatement();

        for (Manufacturer manufacturer : manufacturers) {
            ps = addManufacturer(ps, manufacturer);
            ps.addBatch();
        }
        return ps.executeBatch();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM manufacturer WHERE mID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public Manufacturer getManufacturer(int id) throws SQLException {
        String query = "SELECT * FROM manufacturer WHERE mID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Manufacturer manufacturer = new Manufacturer();
        if (rs.next()) {
            manufacturer.setID(rs.getInt("mID"));
            manufacturer.setName(rs.getString("mName"));
            manufacturer.setAddress(rs.getString("mAddress"));
            manufacturer.setPhoneNumber(rs.getInt("mPhoneNumber"));
            return manufacturer;
        }

        return null;
    }

    @Override
    public List<Manufacturer> getAllManufacturers() throws SQLException {
        String query = "SELECT * FROM manufacturer " + getQuerySuffix();
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();

        while (rs.next()) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setID(rs.getInt("mID"));
            manufacturer.setName(rs.getString("mName"));
            manufacturer.setAddress(rs.getString("mAddress"));
            manufacturer.setPhoneNumber(rs.getInt("mPhoneNumber"));
            manufacturers.add(manufacturer);
        }

        return manufacturers;
    }

    @Override
    public void update(Manufacturer manufacturer) throws SQLException {
        String query = "UPDATE manufacturer SET mName =?, mAddress =?, mPhoneNumber =? WHERE mID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, manufacturer.getName());
        ps.setString(2, manufacturer.getAddress());
        ps.setInt(3, manufacturer.getPhoneNumber());
        ps.setInt(4, manufacturer.getID());
        ps.executeUpdate();
    }
}
