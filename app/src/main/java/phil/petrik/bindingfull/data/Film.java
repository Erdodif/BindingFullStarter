package phil.petrik.bindingfull.data;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Film{
    private Integer id;
    private String cim;
    private String kategoria;
    private Integer hossz;
    private Integer ertekels;//Az api félre van gépelve, szóval...

    public static Film emptyFilm(){
        return new Film(null,null,null,null,null);
    }

    public Film(Integer id, String cim, String kategoria, Integer hossz, Integer ertekels) {
        this.id = id;
        this.cim = cim;
        this.kategoria = kategoria;
        this.hossz = hossz;
        this.ertekels = ertekels;
    }

    public Integer getId() {
        return id;
    }

    public String getCim() {
        return cim;
    }

    public String getKategoria() {
        return kategoria;
    }

    public String getHosszString(){
        return hossz==null?"":hossz.toString();
    }

    public Integer getHossz() {
        return hossz;
    }

    public String getErtekelsString(){
        return ertekels==null?"":ertekels.toString();
    }

    public Integer getErtekels() {
        return ertekels;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public void setHosszString(String hossz) {
        try {
            this.hossz = Integer.parseInt(hossz);
        }
            catch (Exception e){
            this.hossz = 0;
        }
    }

    public void setHossz(int hossz) {
        this.hossz = hossz;
    }

    public void setErtekelsString(String ertekels) {
        try {
            this.ertekels = Integer.parseInt(ertekels);
        }
        catch (Exception e){
            this.ertekels = 0;
        }
    }

    public void setErtekels(int ertekels) {
        this.ertekels = ertekels;
    }

    @NonNull
    @Override
    public String toString() {
        return "id:" + id + ", cim:" + cim + ", kategoria:" + kategoria
                + ", hossz:" + hossz + ", ertekeles:" + ertekels;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this);
    }
}
