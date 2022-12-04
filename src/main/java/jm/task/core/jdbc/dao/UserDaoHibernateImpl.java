package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getHibernateSF();
    private Session session;
    private Transaction transaction;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String tableProp = """
                    create table if not exists user (
                    id int auto_increment primary key,
                    name varchar(30),
                    lastName varchar(30),
                    age tinyint
                    );
                    """;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(tableProp).executeUpdate();
            transaction.commit();
            System.out.println("Successfully created the table");
        } catch (RuntimeException e) {
            System.out.println("Failed to create the table");
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists user;").executeUpdate();
            transaction.commit();
            System.out.println("Successfully removed the table");
        } catch (RuntimeException e) {
            System.out.println("Failed to remove the table");
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            System.out.println("User " + name + " successfully added or updated");
        } catch (RuntimeException e) {
            System.out.println("Failed to add the user");
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("Successfully removed user " + id);
        } catch (RuntimeException e) {
            System.out.println("Failed to remove the user " + id);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> allUsers = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            allUsers = session.createQuery("select N from User N", User.class).list();
            transaction.commit();
            System.out.println(allUsers);
        } catch (RuntimeException e) {
            System.out.println("Failed to obtain users");
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        List <User> users = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            users = session.createQuery("select N from User N", User.class).list();
            for (User user: users) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("Successfully cleaned the table");
        } catch (RuntimeException e) {
            System.out.println("Failed to clean the table");
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
