package cn.zzliux.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

public class CheckLogin implements SessionAware {
	private Map<String, Object> out = new HashMap<String, Object>();
	private Map<String, Object> ss;

	public String execute(){
		Object userType = this.ss.get("userType");
		if(userType == null){
			this.out.put("err", 1);
			this.out.put("msg", "你没有登录哦~");
		}else{
			this.out.put("err", 0);
			this.out.put("userType", userType);
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
