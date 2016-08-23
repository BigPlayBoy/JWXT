package com.cui.main;

import com.cui.Bean.*;
import com.cui.tools.EmailGrade;
import com.cui.tools.HibernateUtil;
import com.cui.tools.HtmlParse;
import com.cui.tools.Tools;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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
        Stack<GradeEntity> gradeEntityStack1 = new Stack<>();
        while (!loginEntityStack.isEmpty()) {
            LoginEntity loginEntity = loginEntityStack.pop();
//            log.info("Success get ID:" + loginEntity.getId());
//            String GradePage = Tools.getGradePage(loginEntity.getId().toString(), loginEntity.getPassword());
            log.info("Success get page ");
//            Element table1 = HtmlParse.getTable(GradePage, 1);//从网页获得数据
            Element table1 = HtmlParse.getTable(1);//从本地获得数据
            StudentEntity studentEntity = HtmlParse.parseStudent(table1);
//            Element table2 = HtmlParse.getTable(GradePage, 2);//从网站获得数据
            Element table2 = HtmlParse.getTable(2);//从本地获得数据
            Stack<GradeEntity> gradeEntityStack = HtmlParse.parseGrade(table2, studentEntity);
            //获得学生信息 保存数据库
            gradeEntityStack1 = saveStudentStack(gradeEntityStack);//保留的是新增的成绩队列
//下面发送邮件

        }
        if (!gradeEntityStack1.isEmpty()) {
            //数据不为空  发送邮件
            EmailGrade.sendEmailWithGrade(gradeEntityStack1);
        }
        log.info("end");
    }

    public static Stack<GradeEntity> saveStudentStack(Stack<GradeEntity> gradeEntityStack) {
        Session session = HibernateUtil.getSession();
        //获取数据库中是否存在这个成绩
        Stack<GradeEntity> gradeEntityStack1 = new Stack<>();//存放新增加的成绩
        while (!gradeEntityStack.isEmpty()) {
            log.info("堆栈当前长度" + gradeEntityStack.size());
            GradeEntity gradeEntity = gradeEntityStack.pop();//从堆栈中获取成绩
            String sql = "select id from Grade where sid=" + gradeEntity.getStudentEntity().getId() + " and kecheng='" + gradeEntity.getKecheng() + "'";
            log.info("生成的SQL语句：" + sql);
            List<GradeEntity> o = session.createSQLQuery(sql).list();
            log.info("查询出来的长度" + o.size());
            if (o.size() == 0) {//如果查询结果为空，则说明需要添加
                session.beginTransaction();
                session.save(gradeEntity);//保存成绩
                session.getTransaction().commit();
                gradeEntityStack1.push(gradeEntity);
            }
        }
        HibernateUtil.closeSession(session);
        log.info("新成绩数量" + gradeEntityStack1.size());
        return gradeEntityStack1;
    }
}

