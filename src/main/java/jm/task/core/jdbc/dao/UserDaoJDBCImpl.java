package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);

        try (Statement statement = connection.createStatement()) {

            // команда создания таблицы
            String sqlCommand = "CREATE TABLE Person (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age INT)";


            // создание таблицы
            statement.executeUpdate(sqlCommand);
            connection.commit();

            System.out.println("Database has been created!");

        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }

            e.printStackTrace();

        } finally {
            connection.setAutoCommit(true);
            if (connection != null) {
                connection.close();
            }
        }


    }

    public void dropUsersTable() throws SQLException {

        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);


        try (Statement statement = connection.createStatement()) {

            String sql = "DROP TABLE person";
            statement.executeUpdate(sql);
            connection.commit();

            System.out.println("Database dropped successfully...");
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }

            e.printStackTrace();

        } finally {
            connection.setAutoCommit(true);
            if (connection != null) {
                connection.close();
            }
        }


    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        User user = new User(name, lastName, age);
        String sql = "INSERT INTO Person (name, lastname, age) VALUES (?, ?, ? )";
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());


            preparedStatement.executeUpdate();
            connection.commit();


            System.out.println("User с именем –" + user.getName() + " добавлен в базу данных");
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }

            e.printStackTrace();

        } finally {
            connection.setAutoCommit(true);
            if (connection != null) {
                connection.close();
            }
        }


    }

    public void removeUserById(long id) throws SQLException {

        String sql = "DELETE FROM Person WHERE id=?";
        Connection connection = Util.getConnection();
        connection.setAutoCommit(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            connection.commit();


        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }

            e.printStackTrace();

        } finally {
            connection.setAutoCommit(true);
            if (connection != null) {
                connection.close();
            }
        }


    }

    public List<User> getAllUsers() {

        List<User> people = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            String SQL = "SELECT * FROM Person";

            ResultSet resultSet = statement.executeQuery(SQL);


            while (resultSet.next()) {
                User person = new User();

                person.setId(resultSet.getLong("id"));
                person.setName(resultSet.getString("name"));
                person.setLastName(resultSet.getString("lastName"));
                person.setAge((byte) resultSet.getInt("age"));

                people.add(person);

                // System.out.println(person);

            }

        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();

        }
        System.out.println(people);
        return people;
    }

    public void cleanUsersTable() throws SQLException {
        String sql = "DELETE FROM Person ";


        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(sql);
            connection.commit();
            System.out.println("the table is cleared");


        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }

            System.out.println("Table is empty");

        } finally {
            connection.setAutoCommit(true);
            if (connection != null) {
                connection.close();
            }
        }


    }
}
