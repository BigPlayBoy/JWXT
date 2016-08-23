package test;

import com.cui.Bean.GradeEntity;
import com.cui.tools.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CUI on 2016/8/22.
 */
public class QueryTest {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSession();
        String hql = "select sid,xuefen from Grade where sid=1330090010 and kecheng='编译原理' ";
        List<GradeEntity> list  = session.createSQLQuery(hql).list();
        HibernateUtil.closeSession(session);
        System.out.println(list.size());
    }
}
