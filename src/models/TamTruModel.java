package models;

public class TamTruModel {
    private int id;
    private int chu_ho_id;
    private String from;
    public TamTruModel(int id, int chu_ho_id, String from){
        this.id = id;
        this.chu_ho_id = chu_ho_id;
        this.from=from;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getChu_ho_id() {
        return chu_ho_id;
    }
    public void setChu_ho_id(int chu_ho_id) {
        this.chu_ho_id = chu_ho_id;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    
}
