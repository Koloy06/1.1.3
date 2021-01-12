package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        dropUsersTable();
        Connection connection = Util.getMySQLConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE users (id INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(30) NOT NULL, last_name VARCHAR(50) NOT NULL, age SMALLINT NOT NULL)")) {
            preparedStatement.execute();
        } catch (SQLException cause) {
            RuntimeException createTableError = new RuntimeException("Create table error", cause);
            throw createTableError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getMySQLConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS users")) {
            preparedStatement.execute();
        } catch (SQLException cause) {
            RuntimeException dropTableError = new RuntimeException("Drop table error", cause);
            throw dropTableError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getMySQLConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException cause) {
            RuntimeException saveUserError = new RuntimeException("Save user error", cause);
            throw saveUserError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getMySQLConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException cause) {
            RuntimeException removeUserByIdError = new RuntimeException("Remove user by id error", cause);
            throw removeUserByIdError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getMySQLConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, last_name, age FROM users")) {
            connection.setReadOnly(true);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException cause) {
            throw new RuntimeException("Get all users error", cause);
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getMySQLConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users")) {
            preparedStatement.execute();
        } catch (SQLException cause) {
            RuntimeException cleanTableError = new RuntimeException("Clean table error", cause);
            throw cleanTableError;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {}
            }
        }
    }
}
