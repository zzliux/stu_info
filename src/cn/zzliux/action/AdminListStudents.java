package cn.zzliux.action;

import java.util.HashMap;
import java.util.Map;

import cn.zzliux.util.Db;

public class AdminListStudents {
	private Map<String, Object> out = new HashMap<String, Object>();
	public String execute(){
		this.out.put("err", 0);
		this.out.put("msg", "success");
		this.out.put("students", Db.getStudents());
		return "success";
	}
	public Map<String, Object> getOut() {
		return out;
	}
	public void setOut(Map<String, Object> out) {
		this.out = out;
	}
}
