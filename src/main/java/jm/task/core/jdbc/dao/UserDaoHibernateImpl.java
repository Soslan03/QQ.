package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    // private static Session session = Util.getSessionFactory().openSession();
    Transaction transaction = null;


    SessionFactory sessionFactory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {


            String sqlCommand = "CREATE TABLE User (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, name VARCHAR(20), lastName VARCHAR(20), age INT)";
            transaction = session.beginTransaction();

            session.createSQLQuery(sqlCommand).executeUpdate();

            transaction.commit();

        } catch (Exception ex) {
            try {
                transaction.rollback();
            } catch (Exception e) {
                System.out.println("The table has already been created ");
            }
            System.out.println("The table has already been created");

            System.out.println(ex);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            String sql = "DROP TABLE User";
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            try {
                transaction.rollback();
            } catch (Exception exception) {
                System.out.println("Database not found");
            }
            System.out.println("Database  not found...");
            System.out.println(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {


        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User с именем –" + name + " добавлен в базу данных");
        } catch (Throwable throwables) {
            try {
                transaction.rollback();
            } catch (Exception e) {
                System.out.println("Упс");
            }
            System.out.println(throwables);
        }

    }

    @Override
    public void removeUserById(long id) {

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);

            transaction.commit();
        } catch (Exception throwables) {
            try {
                transaction.rollback();
            } catch (Exception e) {
                System.out.println("Упс");
            }
            System.out.println(throwables);
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> people = new ArrayList<>();

        String SQL = "FROM User";//""SELECT id, name, lastName, age FROM USERS";
        try (Session session1 = Util.getSessionFactory().openSession()) {
            session1.beginTransaction();

            people = session1.createQuery(SQL).list();


        } catch (Exception e) {
            System.out.println("Database  not found...");
            System.out.println(e);
        }


        return people;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = Util.getSessionFactory().openSession()) {
            String SQL = "DELETE FROM USER";
            transaction = session.beginTransaction();
            //session.delete(SQL);
            session.createSQLQuery(SQL).executeUpdate();
            transaction.commit();
        } catch (Exception throwables) {
            try {
                transaction.rollback();
            } catch (Exception e) {
                System.out.println("Упс");
            }
            System.out.println(throwables);
        }
    }
}