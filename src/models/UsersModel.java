package models;


public class UsersModel {
	int id;
	String username;
	String passwd;
	String name;
	int role;
	
	public UsersModel() {}
	public UsersModel(int id, String name, String username, String passwd, int role) {
		this.username = username;
		this.passwd = passwd;
		this.id=id;
		this.role=role;
		this.name=name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setRole(int role) {
		this.role=role;
	}
	public int getRole() {
		return role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	
}
