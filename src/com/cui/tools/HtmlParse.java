package com.cui.tools;

import com.cui.Bean.GradeEntity;
import com.cui.Bean.StudentEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

/**
 * Created by CUI on 2016/8/17.
 * 解析html网页
 */
public class HtmlParse {
    public static void main(String[] args) {
//        Element table = getTable(1);
//        parseStudent(table);
        parseGrade(getTable(2),parseStudent(getTable(1)));

    }

    public static Element getTable(int index) {
        File input = new File(System.getProperty("user.dir") + "\\resource\\1.html");
        Document doc;
        Element table = null;
        try {
            doc = Jsoup.parse(input, "gb2312", "");
            table = doc.getElementById("datapost").getElementsByTag("table").get(1).getElementsByTag("table").get(index);//1 获得学生信息 2获得学生成绩
//            System.out.println("显示table里的内容" + table);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }
    public static Element getTable(String htmlpage,int index) {
        Document doc;
        Element table = null;
        doc = Jsoup.parse(htmlpage, "gb2312");
        table = doc.getElementById("datapost").getElementsByTag("table").get(1).getElementsByTag("table").get(index);//1 获得学生信息 2获得学生成绩
        return table;
    }

    public static StudentEntity parseStudent(Element table) {
        StudentEntity studentEntity = new StudentEntity();
        Element tr1 = table.getElementsByTag("tbody").get(0).getElementsByTag("tr").get(0);
        Element tr2 = table.getElementsByTag("tbody").get(0).getElementsByTag("tr").get(1);
        Element tr3 = table.getElementsByTag("tbody").get(0).getElementsByTag("tr").get(2);
        studentEntity.setName(tr1.getElementsByTag("td").get(1).html().replace("&nbsp;", ""));
        studentEntity.setId(Integer.parseInt(tr1.getElementsByTag("td").get(3).html().replace("&nbsp;", "")));
        studentEntity.setSex(tr1.getElementsByTag("td").get(5).getElementsByTag("span").html().replace("&nbsp;", ""));
        studentEntity.setXuezhi(tr1.getElementsByTag("td").get(7).html().replace("&nbsp;", ""));
        studentEntity.setYuanxi(tr2.getElementsByTag("td").get(1).html().replace("&nbsp;", ""));
        studentEntity.setZhuanye(tr2.getElementsByTag("td").get(1).html().replace("&nbsp;", ""));
        studentEntity.setBanji(tr3.getElementsByTag("td").get(1).html().replace("&nbsp;", ""));
        studentEntity.setRuxueriqi(tr3.getElementsByTag("td").get(1).html().replace("&nbsp;", ""));
//        System.out.println(studentEntity);
        return studentEntity;
    }

    public static Stack<GradeEntity> parseGrade(Element table,StudentEntity studentEntity) {
        Stack<GradeEntity> gradeEntityStack = new Stack<>();
        Elements trs = table.getElementsByTag("tr");
        int trLength = table.getElementsByTag("tr").size();
        int i = 0;
        for (Element tr : trs) {
            i++;
            if (i == 1) continue;
            if (i == trLength) continue;
            GradeEntity gradeEntity = new GradeEntity();
            gradeEntity.setKecheng(tr.getElementsByTag("td").get(0).html().replace("&nbsp;", ""));
            gradeEntity.setXuefen(parseDouble(tr.getElementsByTag("td").get(1).html().replace("&nbsp;", "")));
            gradeEntity.setCehngji(parseDouble(tr.getElementsByTag("td").get(2).html().replace("&nbsp;", "")));
            gradeEntity.setShuxing(tr.getElementsByTag("td").get(3).html().replace("&nbsp;", ""));
            gradeEntity.setTime(tr.getElementsByTag("td").get(4).html().replace("&nbsp;", ""));
            gradeEntity.setStudentEntity(studentEntity);
//            System.out.println(gradeEntity);
            gradeEntityStack.push(gradeEntity);
        }
        return gradeEntityStack;
    }

    static double parseDouble(String num) {
        double a;
        if (num.length() == 0) a = 0.0d;
        else a = Double.parseDouble(num);
        return a;
    }
}
