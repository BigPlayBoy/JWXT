package com.cui.tools;

/**
 * Created by CUI on 2016/8/17.
 *
 */

import com.cui.Bean.LoginEntity;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.List;
import java.util.Stack;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static Session session=null;
    private static Configuration configuration = null;
    //     private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry = null;

    static {
        // 创建Configuration对象，读取hibernate.cfg.xml文件，完成初始化
        try {
            configuration = new Configuration().configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    //获取SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //获取Session
    public static Session getSession() {
        session = sessionFactory.openSession();
        return session;
    }

    //关闭Session
    public static void closeSession(Session session) {
        if (session != null) {
            session.close();
        }
    }

    public static void main(String[] args) {
        System.out.println(sessionFactory);
    }

    public static Stack<LoginEntity> queryLogin() {
        Stack<LoginEntity> loginEntities = new Stack<>();
        String hql = "from LoginEntity";
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query query = session.createQuery(hql);
        List<LoginEntity> loginEntityList = query.list();
        for (LoginEntity loginEntity : loginEntityList) {
            loginEntities.push(loginEntity);
        }
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return loginEntities;
    }
}
