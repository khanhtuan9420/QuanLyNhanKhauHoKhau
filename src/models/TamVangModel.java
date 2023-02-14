package models;

public class TamVangModel {
    int id;
    int nhan_khau_id;
    String diadiem;
    String from;
    String to;
    String lydo;

    
    public TamVangModel(int id, int nhan_khau_id, String diadiem, String from, String to, String lydo) {
        this.id = id;
        this.nhan_khau_id = nhan_khau_id;
        this.diadiem=diadiem;
        this.from = from;
        this.to = to;
        this.lydo = lydo;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getNhan_khau_id() {
        return nhan_khau_id;
    }
    public void setNhan_khau_id(int nhan_khau_id) {
        this.nhan_khau_id = nhan_khau_id;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getLydo() {
        return lydo;
    }
    public void setLydo(String lydo) {
        this.lydo = lydo;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    
}
