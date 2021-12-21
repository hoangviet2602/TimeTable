package Day;

import java.io.Serializable;

public class Day implements Serializable {
    int id;
    String hinhanh;
    String tittle;

    public Day(int id, String hinhanh, String tittle) {
        this.id = id;
        this.hinhanh = hinhanh;
        this.tittle = tittle;
    }

    public Day() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }
}
