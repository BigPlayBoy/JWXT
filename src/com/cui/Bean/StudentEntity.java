package com.cui.Bean;

import java.util.Set;

/**
 * Created by CUI on 2016/8/17.
 * Student
 */
public class StudentEntity {
    private Integer id;
    private String name;
    private String sex;
    private String xuezhi;
    private String yuanxi;
    private String zhuanye;
    private String banji;
    private String ruxueriqi;
    private Integer gradeNumber;
    private String eMail;

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEntity that = (StudentEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        if (xuezhi != null ? !xuezhi.equals(that.xuezhi) : that.xuezhi != null) return false;
        if (yuanxi != null ? !yuanxi.equals(that.yuanxi) : that.yuanxi != null) return false;
        if (zhuanye != null ? !zhuanye.equals(that.zhuanye) : that.zhuanye != null) return false;
        if (banji != null ? !banji.equals(that.banji) : that.banji != null) return false;
        if (ruxueriqi != null ? !ruxueriqi.equals(that.ruxueriqi) : that.ruxueriqi != null) return false;
        if (gradeNumber != null ? !gradeNumber.equals(that.gradeNumber) : that.gradeNumber != null) return false;
        if (eMail != null ? !eMail.equals(that.eMail) : that.eMail != null) return false;
        return gradeEntitySet != null ? gradeEntitySet.equals(that.gradeEntitySet) : that.gradeEntitySet == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (xuezhi != null ? xuezhi.hashCode() : 0);
        result = 31 * result + (yuanxi != null ? yuanxi.hashCode() : 0);
        result = 31 * result + (zhuanye != null ? zhuanye.hashCode() : 0);
        result = 31 * result + (banji != null ? banji.hashCode() : 0);
        result = 31 * result + (ruxueriqi != null ? ruxueriqi.hashCode() : 0);
        result = 31 * result + (gradeNumber != null ? gradeNumber.hashCode() : 0);
        result = 31 * result + (eMail != null ? eMail.hashCode() : 0);
        result = 31 * result + (gradeEntitySet != null ? gradeEntitySet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", xuezhi='" + xuezhi + '\'' +
                ", yuanxi='" + yuanxi + '\'' +
                ", zhuanye='" + zhuanye + '\'' +
                ", banji='" + banji + '\'' +
                ", ruxueriqi='" + ruxueriqi + '\'' +
                ", gradeNumber=" + gradeNumber +
                ", eMail='" + eMail + '\'' +
                ", gradeEntitySet=" + gradeEntitySet +
                '}';
    }

    private Set<GradeEntity> gradeEntitySet;

    public Set<GradeEntity> getGradeEntitySet() {
        return gradeEntitySet;
    }

    public void setGradeEntitySet(Set<GradeEntity> gradeEntitySet) {
        this.gradeEntitySet = gradeEntitySet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getGradeNumber() {
        return gradeNumber;
    }

    public void setGradeNumber(Integer gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

}
