package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.SalesPerson;

public class SalesPersonDaoImpl extends DaoImpl implements SalesPersonDao {
    public SalesPersonDaoImpl(Connection conn) {
        super(conn);
    }

    @Override
    public int add(SalesPerson salesPerson) throws SQLException {
        String query = "INSERT INTO "
                + "salesperson (sID, sName, sAddress, sPhoneNumber, sExperience) "
                + " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, salesPerson.getID());
        ps.setString(2, salesPerson.getName());
        ps.setString(3, salesPerson.getAddress());
        ps.setInt(4, salesPerson.getPhoneNumber());
        ps.setInt(5, salesPerson.getExperience());
        return ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM salesperson WHERE sID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public SalesPerson getSalesPerson(int id) throws SQLException {
        String query = "SELECT * FROM salesperson WHERE sID =?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        SalesPerson salesPerson = new SalesPerson();
        if (rs.next()) {
            salesPerson.setID(rs.getInt("sID"));
            salesPerson.setName(rs.getString("sName"));
            salesPerson.setAddress(rs.getString("sAddress"));
            salesPerson.setPhoneNumber(rs.getInt("sPhoneNumber"));
            salesPerson.setExperience(rs.getInt("sExperience"));
            return salesPerson;
        }

        return null;
    }

    @Override
    public List<SalesPerson> getAllSalesPersons() throws SQLException {
        String query = "SELECT * FROM salesperson";
        PreparedStatement ps = conn.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        List<SalesPerson> salesPersons = new ArrayList<SalesPerson>();
        while (rs.next()) {
            SalesPerson salesPerson = new SalesPerson();
            salesPerson.setID(rs.getInt("sID"));
            salesPerson.setName(rs.getString("sName"));
            salesPerson.setAddress(rs.getString("sAddress"));
            salesPerson.setPhoneNumber(rs.getInt("sPhoneNumber"));
            salesPerson.setExperience(rs.getInt("sExperience"));
            salesPersons.add(salesPerson);
        }

        return salesPersons;
    }

    @Override
    public void update(SalesPerson salesPerson) throws SQLException {
        String query = "UPDATE salesperson SET sName=?, sAddress=?, sPhoneNumber=?, sExperience=? WHERE sID=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, salesPerson.getName());
        ps.setString(2, salesPerson.getAddress());
        ps.setInt(3, salesPerson.getPhoneNumber());
        ps.setInt(4, salesPerson.getExperience());
        ps.setInt(1, salesPerson.getID());
        ps.executeUpdate();
    }
}
