package persistence;

import enteties.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public User getUserByID(int userID) throws ClassNotFoundException {
        User user=null;

        try (Connection connection = DBconnector.connection()) {
            String sql = "SELECT fname,lname,pw,phone,address FROM usertable WHERE id = + '" + userID + "'";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String firstName=rs.getString("fname");
                    String lastName=rs.getString("lname");
                    String password=rs.getString("pw");
                    String phone=rs.getString("phone");
                    String address=rs.getString("address");

                    user=new User(firstName,lastName,password,phone,address);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }
}
