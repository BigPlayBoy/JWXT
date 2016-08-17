package test;

import Bean.TestEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import tools.HibernateUtil;

/**
 * Created by CUI on 2016/8/16.
 */
public class test {

    public static void main(String[] args) {
        TestEntity testEntity = new TestEntity();
//        testEntity.setId(4);
        testEntity.setName("wang");

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(testEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
    }
}
