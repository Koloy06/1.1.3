package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService test = new UserServiceImpl(new UserDaoJDBCImpl());
        test.createUsersTable();
        test.saveUser("Владимир", "Владимирович", (byte) 32);
        test.saveUser("Александра", "Петрова", (byte) 22);
        test.saveUser("Сергей", "Простой", (byte) 44);
        test.saveUser("Дима", "Масленников", (byte) 32);
        System.out.println(test.getAllUsers());
        test.cleanUsersTable();
        test.dropUsersTable();
    }
}
