package cn.zzliux.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.zzliux.util.Db;

import com.opensymphony.xwork2.ActionContext;

public class RegisterStudent {
	private Map<String, Object> out = new HashMap<String, Object>();
	
	public String execute(){
		ActionContext context = ActionContext.getContext();  
		HttpServletRequest req = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
		try{
			int    id          = Integer.parseInt(req.getParameter("stu_id"));
			int    age         = Integer.parseInt(req.getParameter("stu_age"));
			int    sex         = Integer.parseInt(req.getParameter("stu_sex"));
			String name        = req.getParameter("stu_name");
			String password    = req.getParameter("stu_password");
			String location    = req.getParameter("stu_location");
			String className   = req.getParameter("stu_className");
			String phoneNumber = req.getParameter("stu_phoneNumber");
			if(Db.getStudentById(id)==null){
				if(Db.insertStudent(id, name, sex, age, className, phoneNumber, location, password)){
					this.out.put("err", 0);
					this.out.put("msg", "添加成功！");
				}else{
					this.out.put("err", 1);
					this.out.put("msg", "未知错误");
				}
			}else{
				this.out.put("err", 1);
				this.out.put("msg", "该学号已存在！");
			}
			
		}catch(Exception e){
//			e.printStackTrace();
			this.out.put("err", 1);
			this.out.put("msg", "未知错误");
		}
		return "success";
	}

	public Map<String, Object> getOut() {
		return out;
	}

	public void setOut(Map<String, Object> out) {
		this.out = out;
	}
}
