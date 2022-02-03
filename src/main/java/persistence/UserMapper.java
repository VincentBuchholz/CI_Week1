package persistence;

import enteties.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<User> getAllUsers() throws ClassNotFoundException {
        List<User> userList = null;

        try (Connection connection = DBconnector.connection()) {
            String sql = "SELECT fname, lname, pw, phone, address FROM usertable";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    if (userList == null) {
                        userList = new ArrayList<>();
                    }

                    String firstName=rs.getString("fname");
                    String lastName=rs.getString("lname");
                    String password=rs.getString("pw");
                    String phone=rs.getString("phone");
                    String address=rs.getString("address");

                    User user=new User(firstName,lastName,password,phone,address);
                    userList.add(user);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userList;
    }

    public void updateUser(int userID, String firstName, String lastName, String password, String phone, String address) throws ClassNotFoundException {

        try (Connection connection = DBconnector.connection()) {

            String sql = "UPDATE usertable SET fname =?, lname =?, pw=?, phone =?, address =? WHERE id =?";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {


                ps.setString(1,firstName);
                ps.setString(2,lastName);
                ps.setString(3,password);
                ps.setString(4,phone);
                ps.setString(5,address);
                ps.setInt(6,userID);


                int result = ps.executeUpdate();




            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
