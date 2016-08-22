package test;

import com.cui.Bean.GradeEntity;
import com.cui.Bean.StudentEntity;
import com.cui.tools.HibernateUtil;
import org.hibernate.Session;

/**
 * Created by CUI on 2016/8/16.
 */
public class test {

    public static void main(String[] args) {
//        HibernateUtil.queryLogin();
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("张三");
        studentEntity.setSex("nan");
        studentEntity.setId(1330090004);
        GradeEntity gradeEntity=new GradeEntity();
        GradeEntity gradeEntity1=new GradeEntity();
//        gradeEntity.setId(1);
        gradeEntity.setKecheng("语文");
//        gradeEntity1.setId(2);
        gradeEntity1.setKecheng("数学");
        gradeEntity.setStudentEntity(studentEntity);
        gradeEntity1.setStudentEntity(studentEntity);
        Session session=HibernateUtil.getSession();
        session.beginTransaction();
//        session.save(studentEntity);
        session.save(gradeEntity);
        session.save(gradeEntity1);
        session.getTransaction().commit();
        HibernateUtil.closeSession(session);
//        addObject(studentEntity);
//        addGrade();
//        findStudentbyId();
//        GradeEntity gradeEntity = new GradeEntity();
//        gradeEntity.setId(3);
//        gradeEntity.setKecheng("语文");
//        updateStudent();
    }


}
