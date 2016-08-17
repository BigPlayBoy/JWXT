package com.cui.Bean;

public class IdAndPasswd {
	String StuId;
	String Passswd;

	public String getStuId() {
		return StuId;
	}

	public void setStuId(String stuId) {
		StuId = stuId;
	}

	public String getPassswd() {
		return Passswd;
	}

	public void setPassswd(String passswd) {
		Passswd = passswd;
	}

	@Override
	public String toString() {
		return "IdAndPasswd [StuId=" + StuId + ", Passswd=" + Passswd + "]";
	}

}
