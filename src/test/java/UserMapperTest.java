import enteties.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.DBconnector;
import persistence.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {
    int amountOfUsersAdded = 0;

    @BeforeEach
    void setUp() {
        System.out.println("TESTINNNNGGGG");
        Connection con = null;
        try {
            con = DBconnector.connection();
            String createTable = "CREATE TABLE IF NOT EXISTS `startcode_test`.`usertable` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `fname` VARCHAR(45) NULL,\n" +
                    "  `lname` VARCHAR(45) NULL,\n" +
                    "  `pw` VARCHAR(45) NULL,\n" +
                    "  `phone` VARCHAR(45) NULL,\n" +
                    "  `address` VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (`id`));";
            con.prepareStatement(createTable).executeUpdate();
            String SQL = "INSERT INTO startcode_test.usertable (fname, lname, pw, phone, address) VALUES (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "Hans");
            ps.setString(2, "Hansen");
            ps.setString(3, "Hemmelig123");
            ps.setString(4, "40404040");
            ps.setString(5,"Rolighedsvej 3");
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        amountOfUsersAdded++;

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getUserByID() throws ClassNotFoundException {
        UserMapper userMapper = new UserMapper();

        User user;

        user = userMapper.getUserByID(3);

        assertEquals("Hans",user.getFirstName());
        assertEquals("Hansen",user.getLastName());
        assertEquals("Hemmelig123",user.getPassword());
        assertEquals("40404040",user.getPhone());
        assertEquals("Rolighedsvej 3",user.getAddress());

    }

    @Test
    public void getAllUsersTest() throws ClassNotFoundException {
        UserMapper userMapper = new UserMapper();
        List<User> actual = userMapper.getAllUsers();

        assertEquals(amountOfUsersAdded,actual.size());
//        for (int i = 0; i < actual.size()-1; i++) {
//            assertEquals(expected.get(i).getFirstName(),actual.get(i).getFirstName());
//            assertEquals(expected.get(i).getLastName(),actual.get(i).getLastName());
//        }
    }

    @Test
    public void updateUserTest() throws ClassNotFoundException {
        UserMapper userMapper = new UserMapper();

        userMapper.updateUser(1,"Erik","Larsen","1234","40404040","Ulrikkenborg Alle 33");

        User user = userMapper.getUserByID(1);

        assertEquals("Erik",user.getFirstName());
        assertEquals("Larsen",user.getLastName());
        assertEquals("1234",user.getPassword());
        assertEquals("40404040",user.getPhone());
        assertEquals("Ulrikkenborg Alle 33",user.getAddress());
    }
}
