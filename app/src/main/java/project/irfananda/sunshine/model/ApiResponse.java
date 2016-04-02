package project.irfananda.sunshine.model;

import java.util.List;

/**
 * Created by irfananda on 02/04/16.
 */
public class ApiResponse {
    private City city;
    private int cnt;
    private String cod;
    private String message;
    private List<Day> list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<Day> getList() {
        return list;
    }

    public void setList(List<Day> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
