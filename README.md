### 运行前要做的事
- 将项目根目录下的stu_manage.sql导入至MySQL
- 配置好/src/hibernate.cfg.xml相关MySQL的用户和密码
- 管理员账号密码都是zzliux

### url说明
- /stu_info/index.html 首页，会根据登录状态判断跳转直管理员后台还是学生后台或者是登录页
- /stu_info/login.html 管理员/学生登录页
- /stu_info/register.html 学生注册
- /stu_info/admin.html 管理员后台
- /stu_info/student.html 学生后台

### 补充
本项目没有使用JSP，前端全html，所有交互使用AJAX，后端使用struts编写action，使用的struts的JSON扩展自动将Map对象转JSON输出，hibernate的数据交互层，

### 依赖
- 前端
	- [JQuery](http://jquery.com/)
	- [Layui](http://www.layui.com/)
	- [ECharts](http://echarts.baidu.com/)
- 后端
	- Hibernate
	- Struts