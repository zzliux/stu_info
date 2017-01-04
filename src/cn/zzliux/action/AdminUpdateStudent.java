package cn.zzliux.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.zzliux.util.Db;

import com.opensymphony.xwork2.ActionContext;

public class AdminUpdateStudent {
	private Map<String, Object> out = new HashMap<String, Object>();
	
	public String execute(){
		ActionContext context = ActionContext.getContext();  
		HttpServletRequest req = (HttpServletRequest)context.get(ServletActionContext.HTTP_REQUEST);
		try{
			if(req.getParameter("stu_delete") != null && req.getParameter("stu_delete").equals("on")){
				// 删除学生信息
				if(Db.deleteStudentById(Integer.parseInt(req.getParameter("stu_id")))){
					this.out.put("err", 0);
					this.out.put("msg", "删除成功！");
				}else{
					this.out.put("err", 1);
					this.out.put("msg", "未知错误");
				}
			}else{
				// 更新学生信息
				int    id          = Integer.parseInt(req.getParameter("stu_id"));
				int    age         = Integer.parseInt(req.getParameter("stu_age"));
				int    sex         = Integer.parseInt(req.getParameter("stu_sex"));
				String name        = req.getParameter("stu_name");
				String location    = req.getParameter("stu_location");
				String className   = req.getParameter("stu_className");
				String phoneNumber = req.getParameter("stu_phoneNumber");
				if(Db.updateStudent(id, name, sex, age, className, phoneNumber, location)){
					this.out.put("err", 0);
					this.out.put("msg", "修改成功！");
				}else{
					this.out.put("err", 1);
					this.out.put("msg", "未知错误");
				}
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
