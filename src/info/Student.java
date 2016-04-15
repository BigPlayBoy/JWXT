package info;
import java.util.Stack;

public class Student {
	String name;
	public int number;
	String sex;
	String xuezhi;
	String yuanxi;
	String zhuanye;
	String banji;
	String ruxueriqi;
	public Stack<Grade> grade = new Stack<>();
	public int gradeNUmber;

	public int getGradeNUmber() {
		return gradeNUmber;
	}

	public void setGradeNUmber(int gradeNUmber) {
		this.gradeNUmber = gradeNUmber;
	}

	public Stack<Grade> getGrade() {
		return grade;
	}

	public void setGrade(Stack<Grade> grade) {
		this.grade = grade;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(String name, int number, String sex, String xuezhi, String yuanxi, String zhuanye, String banji,
			String ruxueriqi) {
		super();
		this.name = name;
		this.number = number;
		this.sex = sex;
		this.xuezhi = xuezhi;
		this.yuanxi = yuanxi;
		this.zhuanye = zhuanye;
		this.banji = banji;
		this.ruxueriqi = ruxueriqi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getXuezhi() {
		return xuezhi;
	}

	public void setXuezhi(String xuezhi) {
		this.xuezhi = xuezhi;
	}

	public String getYuanxi() {
		return yuanxi;
	}

	public void setYuanxi(String yuanxi) {
		this.yuanxi = yuanxi;
	}

	public String getZhuanye() {
		return zhuanye;
	}

	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}

	public String getBanji() {
		return banji;
	}

	public void setBanji(String banji) {
		this.banji = banji;
	}

	public String getRuxueriqi() {
		return ruxueriqi;
	}

	public void setRuxueriqi(String ruxueriqi) {
		this.ruxueriqi = ruxueriqi;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", number=" + number + ", sex=" + sex + ", xuezhi=" + xuezhi + ", yuanxi="
				+ yuanxi + ", zhuanye=" + zhuanye + ", banji=" + banji + ", ruxueriqi=" + ruxueriqi + ", grade=" + grade
				+ ", gradeNUmber=" + gradeNUmber + "]";
	}

	public String getSql() {// 信息更新
		return "update Student set Name='" + this.name + "',Sex='" + this.sex + "',Xuezhi='" + this.xuezhi
				+ "',Yuanxi='" + this.yuanxi + "',Zhuanye='" + this.zhuanye + "',Banji='" + this.banji + "',Ruxueriqi='"
				+ this.ruxueriqi + "',KechengNo='" + this.gradeNUmber + "' where StuID='" + this.number + "'";

	}

}
