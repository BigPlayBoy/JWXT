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
        String hql = "from GradeEntity where sid=? and xuefen=? ";
        List<GradeEntity> list = new ArrayList<GradeEntity>();
        list = session.createQuery(hql).setParameter(0,1330090010).setParameter(1,6).list();
        HibernateUtil.closeSession(session);
        System.out.println(list.size());
    }
}
