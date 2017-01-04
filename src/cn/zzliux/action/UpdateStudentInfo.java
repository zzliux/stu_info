package cn.zzliux.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;

import cn.zzliux.util.Db;

public class UpdateStudentInfo implements SessionAware{
	private Map<String, Object> ss;
	private Map<String, Object> out = new HashMap<String, Object>();
	
	public String execute(){
		ActionContext context = ActionContext.getContext();  
		HttpServletRequest req = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
		try{
			int    id          = Integer.parseInt(req.getParameter("stu_id"));
			int    age         = Integer.parseInt(req.getParameter("stu_age"));
			int    sex         = Integer.parseInt(req.getParameter("stu_sex"));
			int    idFromSs    = Integer.parseInt((String)ss.get("userName"));
			String name        = req.getParameter("stu_name");
			String location    = req.getParameter("stu_location");
			String className   = req.getParameter("stu_className");
			String phoneNumber = req.getParameter("stu_phoneNumber");
			if(id == idFromSs){
				if(Db.updateStudent(id, name, sex, age, className, phoneNumber, location)){
					this.out.put("err", 0);
					this.out.put("msg", "修改成功！");
				}else{
					this.out.put("err", 1);
					this.out.put("msg", "未知错误");
				}
			}else{
				this.out.put("err", 1);
				this.out.put("msg", "没有权限");
			}
			
		}catch(Exception e){
//			e.printStackTrace();
			this.out.put("err", 1);
			this.out.put("msg", "未知错误");
		}
		return "success";
	}
	
	public void setSession(Map<String, Object> session){
		this.ss = session;
	}

	public Map<String, Object> getOut() {
		return out;
	}

	public void setOut(Map<String, Object> out) {
		this.out = out;
	}
	
}
