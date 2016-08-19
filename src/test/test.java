package test;

import com.cui.Bean.GradeEntity;
import com.cui.Bean.StudentEntity;
import com.cui.tools.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by CUI on 2016/8/16.
 */
public class test {

    public static void main(String[] args) {
//        HibernateUtil.queryLogin();
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("张三");
        studentEntity.setSex("nan");
        studentEntity.setId(1330090002);
        studentEntity.setXuezhi("4");
        studentEntity.setYuanxi("计算计");
        studentEntity.setZhuanye("计算机");
        studentEntity.setBanji("1301");
        studentEntity.setRuxueriqi("123456");
        studentEntity.setGradeNumber(45);
        GradeEntity gradeEntity = new GradeEntity();
        gradeEntity.setId(1);
        gradeEntity.setKecheng("shuxue");
        GradeEntity gradeEntity1 = new GradeEntity();
        gradeEntity1.setId(2);
        gradeEntity1.setKecheng("语文");
        gradeEntity.setStudentEntity(studentEntity);
        gradeEntity1.setStudentEntity(studentEntity);
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.save(studentEntity);
        session.save(gradeEntity);
        session.save(gradeEntity1);
        tx.commit();
        HibernateUtil.closeSession(session);
//        studentEntity.
//        addObject(studentEntity);
//        addGrade();
//        findStudentbyId();
//        GradeEntity gradeEntity = new GradeEntity();
//        gradeEntity.setId(3);
//        gradeEntity.setKecheng("语文");
//        updateStudent();
    }
}
