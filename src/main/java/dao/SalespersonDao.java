package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Salesperson;

public class SalespersonDao extends DaoImpl<SalespersonDao> implements BaseDao<Salesperson> {
    public SalespersonDao(Connection conn) {
        super(conn);
    }

    @Override
    public PreparedStatement getAddStatement() throws SQLException {
        String query = "INSERT INTO "
                + "salesperson (sID, sName, sAddress, sPhoneNumber, sExperience) "
                + " VALUES (?, ?, ?, ?, ?)";
        return conn.prepareStatement(query);
    }

    private PreparedStatement addSalesperson(PreparedStatement ps, Salesperson salesperson) throws SQLException {
        ps.setInt(1, salesperson.getID());
        ps.setString(2, salesperson.getName());
        ps.setString(3, salesperson.getAddress());
        ps.setInt(4, salesperson.getPhoneNumber());
        ps.setInt(5, salesperson.getExperience());
        return ps;
    }

    @Override
    public int add(Salesperson salesperson) throws SQLException {
        PreparedStatement ps = getAddStatement();
        return addSalesperson(ps, salesperson).executeUpdate();
    }

    @Override
    public int[] addAll(List<Salesperson> salespersons) throws SQLException {
        PreparedStatement ps = getAddStatement();

        for (Salesperson salesperson : salespersons) {
            ps = addSalesperson(ps, salesperson);
            ps.addBatch();
        }
        return ps.executeBatch();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM salesperson WHERE sID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public Salesperson get(int id) throws SQLException {
        String query = "SELECT * FROM salesperson WHERE sID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        Salesperson salesperson = new Salesperson();
        if (rs.next()) {
            salesperson.setID(rs.getInt("sID"));
            salesperson.setName(rs.getString("sName"));
            salesperson.setAddress(rs.getString("sAddress"));
            salesperson.setPhoneNumber(rs.getInt("sPhoneNumber"));
            salesperson.setExperience(rs.getInt("sExperience"));
            return salesperson;
        }

        return null;
    }

    @Override
    public List<Salesperson> getAll() throws SQLException {
        String query = "SELECT * FROM salesperson " + getQuerySuffix();
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<Salesperson> salespersons = new ArrayList<Salesperson>();
        while (rs.next()) {
            Salesperson salesperson = new Salesperson();
            salesperson.setID(rs.getInt("sID"));
            salesperson.setName(rs.getString("sName"));
            salesperson.setAddress(rs.getString("sAddress"));
            salesperson.setPhoneNumber(rs.getInt("sPhoneNumber"));
            salesperson.setExperience(rs.getInt("sExperience"));
            salespersons.add(salesperson);
        }

        return salespersons;
    }

    @Override
    public void update(Salesperson salesperson) throws SQLException {
        String query = "UPDATE salesperson SET sName=?, sAddress=?, sPhoneNumber=?, sExperience=? WHERE sID=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, salesperson.getName());
        ps.setString(2, salesperson.getAddress());
        ps.setInt(3, salesperson.getPhoneNumber());
        ps.setInt(4, salesperson.getExperience());
        ps.setInt(1, salesperson.getID());
        ps.executeUpdate();
    }
}
