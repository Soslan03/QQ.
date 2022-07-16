package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
 //       Util.getConnection();
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        UserServiceImpl userDao = new UserServiceImpl();

        userDao.createUsersTable();

        List<User> users = new ArrayList<>();
       userDao.saveUser("Name1", "LastName1", (byte) 20);
       userDao.saveUser("Name2", "LastName2", (byte) 25);
       userDao.saveUser("Name3", "LastName3", (byte) 31);
       userDao.saveUser("Name4", "LastName4", (byte) 38);
        userDao.removeUserById(3);
       users = userDao.getAllUsers();
        System.out.println(users);
        userDao.cleanUsersTable();
       userDao.dropUsersTable();

        session.close();


        Util.shutdown();

    }
}
