package info;

public class Grade {
	int number;
	String kecheng;// 课程名
	float xuefen;// 学分
	float chengji;// 成绩
	float jidian;// 绩点
	String shuxing;// 属性 院选 校选 必修
	String time;// 考试时间

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getKecheng() {
		return kecheng;
	}

	public void setKecheng(String kecheng) {
		this.kecheng = kecheng;
	}

	public float getXuefen() {
		return xuefen;
	}

	public void setXuefen(float xuefen) {
		this.xuefen = xuefen;
	}

	public float getChengji() {
		return chengji;
	}

	public void setChengji(float chengji) {
		this.chengji = chengji;
	}

	public float getJidian() {
		return jidian;
	}

	public void setJidian(float jidian) {
		this.jidian = jidian;
	}

	public String getShuxing() {
		return shuxing;
	}

	public void setShuxing(String shuxing) {
		this.shuxing = shuxing;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "\nGrade [学号=" + number + ", 课程名=" + kecheng + ", 学分=" + xuefen + ", 成绩=" + chengji + ", 绩点=" + jidian
				+ ", 属性=" + shuxing + ", 考试时间=" + time + "]";
	}

	public String getsql() {
		return "insert into Grade(StuID,Kecheng,Xuefen,Grade,Jidian,Shuxing,Time) values('" + this.number + "','"
				+ this.kecheng + "','" + this.xuefen + "','" + this.chengji + "','" + this.jidian + "','" + this.shuxing
				+ "','" + this.time + "')";
	}
	public String getsqlmini() {
		return "insert into Grade(STuid,kecheng) values('" + this.number + "','"
				+ this.kecheng + "')";
	}
}
