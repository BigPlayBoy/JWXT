package com.cui.dao;

import com.cui.Bean.GradeEntity;
import org.hibernate.Session;
import com.cui.tools.HibernateUtil;

/**
 * Created by CUI on 2016/8/17.
 */
public class Grade {
    static void addGrade(GradeEntity gradeEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(gradeEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        System.out.println("add Grade Success!");
    }

    static GradeEntity findGraddebyId(int id) {
        Session session = HibernateUtil.getSession();
        return (GradeEntity) session.get(GradeEntity.class, id);
    }

    static boolean updateGrade(GradeEntity gradeEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.update(gradeEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return true;
    }

    static boolean deleteGrade(int id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        GradeEntity gradeEntity = (GradeEntity) session.get(GradeEntity.class, id);
        session.delete(gradeEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return true;
    }

    public static void main(String[] args) {
        GradeEntity gradeEntity = new GradeEntity();
        gradeEntity.setId(3);
        gradeEntity.setKecheng("数学");
        gradeEntity.setCehngji(99.0);
        updateGrade(gradeEntity);
    }
}
