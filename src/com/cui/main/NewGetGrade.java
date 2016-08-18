package com.cui.main;

import com.cui.Bean.GradeEntity;
import com.cui.Bean.LoginEntity;
import com.cui.Bean.Student;
import com.cui.Bean.StudentEntity;
import com.cui.tools.HibernateUtil;
import com.cui.tools.HtmlParse;
import com.cui.tools.Tools;
import org.hibernate.Session;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * Created by CUI on 2016/8/18.
 */
public class NewGetGrade {
    private static Logger log = LoggerFactory.getLogger(Getgrade.class);

    public static void main(String[] args) {
        //第一步从数据库中获取学好和密码，返回值类型Stack<LoginEntity>
        Stack<LoginEntity> loginEntityStack = HibernateUtil.queryLogin();//得到学号密码
        Stack<StudentEntity> studentEntityStack = new Stack<>();//存放更新了成绩的学生
        while (!loginEntityStack.isEmpty()) {
            LoginEntity loginEntity = loginEntityStack.pop();
            log.info("Success get ID:" + loginEntity.getId());
            String GradePage = Tools.getGradePage(loginEntity.getId().toString(), loginEntity.getPassword());
            log.info("Success get page ");
            Element table1 = HtmlParse.getTable(GradePage, 1);
            StudentEntity studentEntity  = HtmlParse.parseStudent(table1);
            Element table2 = HtmlParse.getTable(GradePage, 2);
            Stack<GradeEntity> gradeEntityStack = HtmlParse.parseGrade(table2);
            Session session=HibernateUtil.getSession();
            session.beginTransaction();
            session.save(studentEntity);
            while (!gradeEntityStack.isEmpty()){
                GradeEntity gradeEntity=gradeEntityStack.pop();
                gradeEntity.setSid(studentEntity.getId());
                System.out.println(gradeEntity);
                session.save(gradeEntity);
            }
            session.getTransaction().commit();
            HibernateUtil.closeSession(session);
        }
    }
}
