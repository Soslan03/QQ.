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


    SessionFactory sessionFactory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
//            String sql = "CREATE TABLE IF NOT EXISTS USERS" +
//                    "  id       BIGINT       PRIMARY KEY AUTOINCREMENT NOT NULL," +
//                    "  name     VARCHAR(250) DEFAULT NULL," +
//                    "  lastname VARCHAR(250) DEFAULT NULL," +
//                    "  age      TINYINT      DEFAULT NULL)";
            String sqlCommand = "CREATE TABLE PERSON (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, name VARCHAR(20), lastName VARCHAR(20), age INT)";
            session.beginTransaction();
            //session.createNativeQuery(sql);
            session.createSQLQuery(sqlCommand).executeUpdate();
            //session.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            String sql = "DROP TABLE PERSON";
           session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
           // session.createNativeQuery("DROP TABLE IF EXISTS PERSON");
            session.getTransaction().commit();
        }catch (Exception e) {
            System.out.println("Database  not found...");
            System.out.println(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try  (Session session1 = Util.getSessionFactory().openSession()){

            String sql = "INSERT INTO Person (name, lastName, age) VALUES (:name, :lastName, :age )";
            session1.beginTransaction();
            Query query = session1.createSQLQuery(sql);
            query.setParameter("name", name);
            query.setParameter("lastName", lastName);
            query.setParameter("age", age);

            query.executeUpdate();
 //         transaction.commit();
            session1.getTransaction().commit();
            System.out.println("User с именем –" + name + " добавлен в базу данных");
        }catch (Throwable throwables) {
            System.out.println(throwables);
        }
//        User user = new User(name, lastName, age);
//
//
//        Transaction transaction = null;
//        try (Session session1 = Util.getSessionFactory().openSession()) {
//            // start a transaction
//            transaction = session1.beginTransaction();
//            // save the student object
//            session1.save(user);
//            // commit transaction
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            //session.beginTransaction();
      //     session.delete(session.get(User.class, id));//1й вариант

        // session.createQuery("DELETE FROM USERS WHERE id=?")
          //          .setParameter("id", id).executeUpdate();
            User user = (User) session.get(User.class, id);
            session.delete(user);
               //session.executeUpdate();
            session.getTransaction().commit();
        }catch (Exception throwables) {
            System.out.println(throwables);
        }
    }

    @Override
    public List<User> getAllUsers() {
        //List<User> userList = new ArrayList<>();
        List<User> people = new ArrayList<>();
//        String sql = "From " + User.class.getSimpleName();
//        System.out.println("sql = " + sql);
//
//        List<User> users = session.createQuery(sql).list();
//
//        System.out.println("users.size = " + users.size());
//        for (Iterator<User> it = users.iterator(); it.hasNext();) {
//            User user = (User) it.next();
//            System.out.println(user.toString());
//        }
        String SQL = "SELECT id, name, lastName, age FROM Person";
        try (Session session1 = Util.getSessionFactory().openSession()) {
            session1.beginTransaction();
           //peaple = session1.createSQLQuery(SQL).stream().toList();
          //people =  session1.createNativeQuery(SQL).list();
            Query query = session1.createSQLQuery(SQL);
            //List<User> users = query.list();

           // users.forEach(System.out::println);
            //users = session1.createCriteria(User.class).list();
           // users = session1.createQuery(SQL).getResultList();

            session1.getTransaction().commit();
            List<Object[]> rows = query.list();

            for(Object[] row : rows) {
                int i=0;
                User user = new User();
                user.setId     (Long.valueOf(row[0].toString()));
                user.setName                  (row[1].toString());
                user.setLastName              (row[2].toString());
                i= (Integer.valueOf( row[3].toString()));
                user.setAge((byte)i);

                people.add(user);
                System.out.println(user.toString());
            }

        }catch (Exception e) {
            System.out.println("Database  not found...");
            System.out.println(e);
        }




        return people;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            String SQL = "DELETE FROM PERSON";
            session.beginTransaction();
            //session.delete(SQL);
            session.createSQLQuery(SQL).executeUpdate();
            session.getTransaction().commit();
        }catch (Exception throwables) {
            System.out.println(throwables);
        }
    }
}