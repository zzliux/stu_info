package cn.zzliux.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;

import cn.zzliux.util.Db;

public class Login implements SessionAware {
	private Map<String, Object> out = new HashMap<String, Object>();
	private Map<String, Object> ss;

	public String execute(){
		ActionContext context = ActionContext.getContext();  
		HttpServletRequest req = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
		try{
			int res = Db.checkUserPassword(req.getParameter("user_name"), req.getParameter("password"));
			if(res == 2){
				this.out.put("err", 1);
				this.out.put("msg", "用户名或密码错误！");
				this.ss.clear();
			}else if(res == 0){
				this.out.put("err", 0);
				this.out.put("userType", 0);
				this.out.put("msg", "普通用户登录成功！");
				this.ss.put("userType", 0);
				this.ss.put("userName", req.getParameter("user_name"));
			}else if(res == 1){
				this.out.put("err", 0);
				this.out.put("userType", 1);
				this.out.put("msg", "管理员登录成功！");
				this.ss.put("userType", 1);
				this.ss.put("userName", req.getParameter("user_name"));
			}
		}catch(Exception e){
//			e.printStackTrace();
		}
		
		return "success";
	}

	public Map<String, Object> getOut() {
		return out;
	}
	public void setOut(Map<String, Object> out) {
		this.out = out;
	}
	public void setSession(Map<String, Object> session) {
		this.ss = session;
	}
}
