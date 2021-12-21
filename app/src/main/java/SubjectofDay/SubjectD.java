package SubjectofDay;

public class SubjectD {

    int idUser;
    int idThu;
    int idSubject;
    String tittle;
    String hinhanh;
    String startTime;
    String endTime;
    int sort;

    public SubjectD(int idUser, int idThu, int idSubject, String tittle, String hinhanh, String startTime, String endTime, int sort) {
        this.idUser = idUser;
        this.idThu = idThu;
        this.idSubject = idSubject;
        this.tittle = tittle;
        this.hinhanh = hinhanh;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sort = sort;
    }

    public SubjectD() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdThu() {
        return idThu;
    }

    public void setIdThu(int idThu) {
        this.idThu = idThu;
    }

    public int getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(int idSubject) {
        this.idSubject = idSubject;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
