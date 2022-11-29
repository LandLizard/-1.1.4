package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSFConnect();

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

        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(tableProp).executeUpdate();
            transaction.commit();
            System.out.println("Successfully created the table");
        } catch (RuntimeException e) {
            System.out.println("Failed to create the table");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("drop table if exists user;").executeUpdate();
            transaction.commit();
            System.out.println("Successfully removed the table");
        } catch (RuntimeException e) {
            System.out.println("Failed to remove the table");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
            System.out.println("User " + name + " successfully added or updated");
        } catch (RuntimeException e) {
            System.out.println("Failed to add the user");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("Successfully removed user " + id);
        } catch (RuntimeException e) {
            System.out.println("Failed to remove the user " + id);
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List <User> allUsers = null;

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            allUsers = session.createQuery("select N from User N", User.class).list();
            System.out.println(allUsers);
        } catch (RuntimeException e) {
            System.out.println("Failed to obtain users");
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("delete from my_db.user;").addEntity(User.class).executeUpdate();
            transaction.commit();
            System.out.println("Successfully cleaned the table");
        } catch (RuntimeException e) {
            System.out.println("Failed to clean the table");
            e.printStackTrace();
        }
    }
}
