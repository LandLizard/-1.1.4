package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

//    private final Connection connection = Util.connectToDB();

    public UserDaoJDBCImpl() {

    }
    @Override
    public void createUsersTable() {
        try (Connection connection = Util.connectToDB(); Statement statement =
                connection.createStatement()) {
            statement.execute("""
                    create table if not exists user (
                    id int auto_increment primary key,
                    name varchar(30),
                    lastName varchar(30),
                    age tinyint
                    );
                    """);
            System.out.println("Successfully created the table");
        } catch (SQLException e) {
            System.out.println("Failed creation the table");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.connectToDB(); Statement statement =
                connection.createStatement()) {
            statement.executeUpdate("drop table if exists user;");
            System.out.println("Successfully removed the table");
        } catch (SQLException e) {
            System.out.println("Failed to remove the table");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.connectToDB(); PreparedStatement pStatement = connection
                .prepareStatement("insert into user (name, lastName, age) values (?,?,?);")) {
            pStatement.setString(1, name);
            pStatement.setString(2, lastName);
            pStatement.setByte(3, age);
            pStatement.execute();
            System.out.println("User " + name + " successfully added");
        } catch (SQLException e) {
            System.out.println("Failed to add the user");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = Util.connectToDB(); PreparedStatement pStatement = connection
                .prepareStatement("delete from user where id = ?")) {
            pStatement.setLong(1, id);
            pStatement.execute();
            System.out.println("Successfully removed user " + id);
        } catch (SQLException e) {
            System.out.println("Failed to remove the user " + id);
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = Util.connectToDB(); Statement statement =
                connection.createStatement(); ResultSet resultSet =
                statement.executeQuery("select * from user;")) {

            /*There are multiple SQl statements in this method
            which should execute in one transaction.
            Then setting auto commit to false
             */
            connection.setAutoCommit(false);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            connection.commit();
            connection.setAutoCommit(true); //Setup auto commit to default for perhaps reuse
        } catch (SQLException e) {
            System.out.println("Failed to obtain users");
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = Util.connectToDB(); Statement statement =
                connection.createStatement()) {
            statement.execute("truncate table user;");
            System.out.println("Successfully clean the table");
        } catch (SQLException e) {
            System.out.println("Failed to clean the table");
            e.printStackTrace();
        }
    }
}
