package cn.zzliux.interceptor;

import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AdminInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	// 输出JSON数据用的变量
	private Map<String, Object> out = new HashMap<String, Object>();
	private ActionContext ctx;
	private Map<String, Object> ss;

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		this.ctx = actionInvocation.getInvocationContext();
		this.ss = this.ctx.getSession();
		if(this.ss.get("userType")!=null && this.ss.get("userType").equals(1)){
			return actionInvocation.invoke();  
		}else{
			this.out.put("err", 1);
			this.out.put("msg", "你没有管理员权限哦~");
			this.ctx.put("errorOut", this.out);
			return "error";
		}
	}
}
