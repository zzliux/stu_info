package cn.zzliux.entity;

public class User {
	private int id = 0;
	private int userType = 0;//用户类型( 0 - 普通用户 ， 1 - 管理员)
	private String name = null;
	private String password = null;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
