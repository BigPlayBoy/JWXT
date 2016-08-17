package com.cui.dao;

import com.cui.Bean.LoginEntity;
import com.cui.tools.HibernateUtil;
import org.hibernate.Session;

/**
 * Created by CUI on 2016/8/17.
 */
public class LoginDao {
    static void addLogin(LoginEntity loginEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(loginEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        System.out.println("add login Success!");
    }

    static LoginEntity findLoginById(int id) {
        Session session = HibernateUtil.getSession();
        return (LoginEntity) session.get(LoginEntity.class, id);
    }

    static boolean updateLogin(LoginEntity loginEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(loginEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return true;
    }

    static boolean deleteLogin(int id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        LoginEntity loginEntity = (LoginEntity) session.get(LoginEntity.class, id);
        session.delete(loginEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return true;
    }

    public static void main(String[] args) {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setId(1330090002);
        loginEntity.setPassword("cuiminghui");
        addLogin(loginEntity);
    }
}
