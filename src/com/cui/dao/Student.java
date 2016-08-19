package com.cui.dao;

import com.cui.Bean.StudentEntity;
import org.hibernate.Session;
import com.cui.tools.HibernateUtil;

/**
 * Created by CUI on 2016/8/17.
 */
public class Student {
    static void addStudent(StudentEntity studentEntity) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(studentEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        System.out.println("add Student Success!");

    }

    static StudentEntity findStudentbyId(int id) {
        Session session = HibernateUtil.getSession();
        return (StudentEntity) session.get(StudentEntity.class, id);
    }

    static boolean updateStudentGradeNum(int id, int num) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        StudentEntity studentEntity = (StudentEntity) session.get(StudentEntity.class, id);
        studentEntity.setGradeNumber(num);
        session.update(studentEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return true;
    }

    static boolean deleteStudentById(int id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        StudentEntity studentEntity = (StudentEntity) session.get(StudentEntity.class, id);
        session.delete(studentEntity);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
        return true;
    }
}
