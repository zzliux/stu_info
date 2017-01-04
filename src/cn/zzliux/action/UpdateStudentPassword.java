package cn.zzliux.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;

import cn.zzliux.util.Db;

public class UpdateStudentPassword implements SessionAware{
	private Map<String, Object> out = new HashMap<String, Object>();
	private Map<String, Object> ss;
	
	public String execute(){
		ActionContext context = ActionContext.getContext();  
		HttpServletRequest req = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
		try{
			String name        = (String) ss.get("userName");
			String oldPassword = req.getParameter("stu_pass_old");
			String newPassword = req.getParameter("stu_pass_new");
			if(Db.changeUserPassword(name, oldPassword, newPassword)){
				this.out.put("err", 0);
				this.out.put("msg", "修改成功");
			}else{
				this.out.put("err", 1);
				this.out.put("msg", "旧密码错误");
			}
			
		}catch(Exception e){
//			e.printStackTrace();
			this.out.put("err", 1);
			this.out.put("msg", "未知错误");
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
