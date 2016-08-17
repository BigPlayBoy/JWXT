package Bean;

/**
 * Created by CUI on 2016/8/17.
 */
public class GradeEntity {
    private Integer id;
    private String kecheng;
    private Integer xuefen;
    private Integer cehngji;
    private Integer jidian;
    private String shuxing;
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKecheng() {
        return kecheng;
    }

    public void setKecheng(String kecheng) {
        this.kecheng = kecheng;
    }

    public Integer getXuefen() {
        return xuefen;
    }

    public void setXuefen(Integer xuefen) {
        this.xuefen = xuefen;
    }

    public Integer getCehngji() {
        return cehngji;
    }

    public void setCehngji(Integer cehngji) {
        this.cehngji = cehngji;
    }

    public Integer getJidian() {
        return jidian;
    }

    public void setJidian(Integer jidian) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradeEntity that = (GradeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (kecheng != null ? !kecheng.equals(that.kecheng) : that.kecheng != null) return false;
        if (xuefen != null ? !xuefen.equals(that.xuefen) : that.xuefen != null) return false;
        if (cehngji != null ? !cehngji.equals(that.cehngji) : that.cehngji != null) return false;
        if (jidian != null ? !jidian.equals(that.jidian) : that.jidian != null) return false;
        if (shuxing != null ? !shuxing.equals(that.shuxing) : that.shuxing != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (kecheng != null ? kecheng.hashCode() : 0);
        result = 31 * result + (xuefen != null ? xuefen.hashCode() : 0);
        result = 31 * result + (cehngji != null ? cehngji.hashCode() : 0);
        result = 31 * result + (jidian != null ? jidian.hashCode() : 0);
        result = 31 * result + (shuxing != null ? shuxing.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
