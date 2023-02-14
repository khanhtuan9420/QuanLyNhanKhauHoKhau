package models;

import java.time.Year;

public class NhanKhauModel {
	int id;
	String cmnd;
	String ten;
	String ngaysinh;
	String sex;
	String from;
	String to;
	
	public NhanKhauModel() {}
	
	public NhanKhauModel(String cmnd, String ten, String ngaysinh , String sex) {
		this.cmnd = cmnd;
		this.ten=ten;
		this.ngaysinh=ngaysinh;
		this.sex = sex;
	}
	
	public NhanKhauModel(int id,String cmnd, String ten, String ngaysinh , String sex) {
		this.id=id;
		this.cmnd = cmnd;
		this.ten=ten;
		this.ngaysinh=ngaysinh;
		this.sex = sex;
	}

	public NhanKhauModel(int id,String cmnd, String ten, String ngaysinh , String sex, String from, String to) {
		this.id=id;
		this.cmnd = cmnd;
		this.ten=ten;
		this.ngaysinh=ngaysinh;
		this.sex = sex;
		this.from=from;
		this.to=to;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public int getTuoi() {
		int year = Year.now().getValue();
		int birthday = Integer.parseInt(ngaysinh.substring(0,4));
		return year - birthday;
	}

	

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getNgaysinh() {
		return ngaysinh;
	}

	public void setNgaysinh(String ngaysinh) {
		this.ngaysinh = ngaysinh;
	}
	
	
}
