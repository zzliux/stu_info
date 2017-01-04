package cn.zzliux.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import cn.zzliux.entity.Student;
import cn.zzliux.util.Db;

public class GetStudentInfo implements SessionAware {
	Map<String, Object> ss;
	Map<String, Object> out = new HashMap<String, Object>();

	public String execute() {
		try{
			Student stu = Db.getStudentById(Integer.parseInt((String)ss.get("userName")));
			if(stu == null){
				out.put("err", 1);
				out.put("msg", "未知错误");
			}else{
				out.put("err", 0);
				out.put("stu", stu);
				out.put("msg", "查询成功");
			}
		}catch(Exception e){
			out.put("err", 1);
			out.put("msg", "无权限");
		}
		
		return "success";
	}

	public void setSession(Map<String, Object> session) {
		this.ss = session;
	}

	public Map<String, Object> getOut() {
		return out;
	}

	public void setOut(Map<String, Object> out) {
		this.out = out;
	}
}
