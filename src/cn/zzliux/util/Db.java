package cn.zzliux.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import cn.zzliux.entity.Student;
import cn.zzliux.entity.User;

/**
 * @author zzliux
 *
 */
public class Db {
	private static SessionFactory sf = new Configuration().configure().buildSessionFactory();

	/**
	 * 插入一个新的Student
	 * @param id 学号
	 * @param name 姓名
	 * @param sex 性别 1男,2女
	 * @param age 年龄
	 * @param className 班级
	 * @param phoneNumber 手机号
	 * @param location 住址
	 * @param passowrd 密码
	 * @return boolean true插入成功,false失败
	 */
	public static boolean insertStudent(int id, String name, int sex, int age, String className, String phoneNumber, String location, String password){
		Transaction tr = null;
		Session ss = null;
		boolean ret = true;
		try {
			ss = sf.openSession();
			// 开启事务
			tr = ss.beginTransaction();
			// 执行SQL，预编译绑定参数
			ss.createSQLQuery("INSERT INTO `student`(`id`, `name`, `sex`, `age`, `class_name`, `phone_number`, `location`) VALUES(?, ?, ?, ?, ?, ?, ?)")
					.setParameter(0, id).setParameter(1, name).setParameter(2, sex).setParameter(3, age).setParameter(4, className)
					.setParameter(5, phoneNumber).setParameter(6, location).executeUpdate();
			ret = Db.insertUser(0, String.valueOf(id), password);
			// 提交事务
			tr.commit();
		}catch(Exception e){
//			e.printStackTrace();
			ret = false;
			if(ss != null){
				ss.getTransaction().rollback();
			}
		}finally{
			if(ss != null && ss.isOpen()){
				ss.close();
			}
		}
		return ret;
	}
	
	/**
	 * 通过学号查询Student
	 * @param id 学号
	 * @return Student对象
	 */
	@SuppressWarnings("unchecked")
	public static Student getStudentById(int id){
		Transaction tr = null;
		Session ss = null;
		List<Student> ret = null;
		try{
			ss = sf.openSession();
			tr = ss.beginTransaction();
			ret = (List<Student>)ss.createSQLQuery("SELECT * FROM `student` WHERE `id` = ? LIMIT 1")
					.addEntity(Student.class).setParameter(0, id).list();
			tr.commit();
		}catch(Exception e){
//			e.printStackTrace();
			return null;
		}
		if(ret.size() == 0) return null;
		return ret.get(0);
	}
	
	/**
	 * @param startPos 开始位置
	 * @param maxLength 查询长度,若该项为0，则表示查询全部数据
	 * @return List<Student>集合
	 */
	@SuppressWarnings("unchecked")
	public static List<Student> getStudents(int startPos, int maxLength){
		Transaction tr = null;
		Session ss = null;
		List<Student> ret = null;
		try{
			ss = sf.openSession();
			tr = ss.beginTransaction();
			Query q = null;
			if(maxLength == 0){
				q = ss.createSQLQuery("SELECT * FROM `student`").addEntity(Student.class);
			}else{
				q = ss.createSQLQuery("SELECT * FROM `student` LIMIT ?,?").addEntity(Student.class).setParameter(0, startPos).setParameter(1, maxLength);
			}
			ret = (List<Student>)q.list();
			tr.commit();
		}catch(Exception e){
//			e.printStackTrace();
			return null;
		}
		return ret;
	}
	
	/**
	 * 查询所有Student
	 * @return List<Student> 
	 */
	public static List<Student> getStudents(){
		return Db.getStudents(0, 0);
	}
	
	/**
	 * 修改Student信息
	 * @param id 学号(主键，通过该字段修改其它数据)
	 * @param name 姓名
	 * @param sex 性别 1男,2女
	 * @param age 年龄
	 * @param className 班级
	 * @param phoneName 手机号
	 * @param location 住址
	 * @return boolean true修改成功,false失败
	 */
	public static boolean updateStudent(int id, String name, int sex, int age, String className, String phoneName, String location){
		Transaction tr = null;
		Session ss = null;
		boolean ret = true;
		try {
			ss = sf.openSession();
			tr = ss.beginTransaction();
			Query q = ss.createSQLQuery("UPDATE `student` SET name=?, sex=?, age=?, class_name=?, phone_number=?, location=? WHERE `id` = ?")
					.setParameter(0, name).setParameter(1, sex).setParameter(2, age).setParameter(3, className)
					.setParameter(4, phoneName).setParameter(5, location).setParameter(6, id);
			q.executeUpdate();
			tr.commit();
		}catch(Exception e){
//			e.printStackTrace();
			ret = false;
			if(ss != null){
				ss.getTransaction().rollback();
			}
		}finally{
			if(ss != null && ss.isOpen()){
				ss.close();
			}
		}
		return ret;
	}
	
	/**
	 * @param type 用户类型，0普通用户，1管理员
	 * @param name 用户名，普通用户学号为用户名
	 * @param password 密码 md5(pass+salt)
	 * @return boolean true插入成功, false插入失败
	 */
	public static boolean insertUser(int type, String name, String password){
		// 用 md5消息摘要 来做加密
		String savePassword = Md5.toMd5(new StringBuffer(password).append("605e9615ac4b50b7ab009d11e22ebc3c").toString());
		Transaction tr = null;
		Session ss = null;
		boolean ret = true;
		try {
			ss = sf.openSession();
			// 开启事务
			tr = ss.beginTransaction();
			// 执行SQL，预编译绑定参数
			Query q = ss.createSQLQuery("INSERT INTO `user`(`user_type`, `name`, `password`) VALUES(?, ?, ?)")
					.setParameter(0, type).setParameter(1, name).setParameter(2, savePassword);
			q.executeUpdate();
			// 提交事务
			tr.commit();
		}catch(Exception e){
//			e.printStackTrace();
			ret = false;
			if(ss != null){
				ss.getTransaction().rollback();
			}
		}finally{
			if(ss != null && ss.isOpen()){
				ss.close();
			}
		}
		return ret;
	}
	
	/**
	 * 登录查询
	 * @param name
	 * @param password
	 * @return int 0普通用户，1管理员，2用户或密码错误
	 */
	@SuppressWarnings("unchecked")
	public static int checkUserPassword(String name, String password){
		String savePassword = Md5.toMd5(new StringBuffer(password).append("605e9615ac4b50b7ab009d11e22ebc3c").toString());
		Transaction tr = null;
		Session ss = null;
		List<User> ret = null;
		try{
			ss = sf.openSession();
			tr = ss.beginTransaction();
			ret = (List<User>)ss.createSQLQuery("SELECT * FROM `user` WHERE `name`=? AND `password`=? LIMIT 1")
					.addEntity(User.class).setParameter(0, name).setParameter(1, savePassword).list();
			tr.commit();
		}catch(Exception e){
//			e.printStackTrace();
			return 2;
		}
		if(ret.size() == 0) return 2;
		return ret.get(0).getUserType();
	}
	
	public static boolean deleteStudentById(int id){
		boolean ret = true;
		Transaction tr = null;
		Session ss = null;
		try{
			ss = sf.openSession();
			// 开启事务
			tr = ss.beginTransaction();
			// 执行SQL，预编译绑定参数
			ss.createSQLQuery("DELETE FROM `student` WHERE `id`=?")
			.setParameter(0, id).executeUpdate();
			ss.createSQLQuery("DELETE FROM `user` WHERE `name`=?")
			.setParameter(0, String.valueOf(id)).executeUpdate();
			// 提交事务
			tr.commit();
		}catch(Exception e){
//			e.printStackTrace();
			ret = false;
			if(ss != null){
				ss.getTransaction().rollback();
			}
		}finally{
			if(ss != null && ss.isOpen()){
				ss.close();
			}
		}
		return ret;
	}
	
	/**
	 * 修改密码
	 * @param name 用户名
	 * @param password 老密码
	 * @param newPassword 新密码 
	 * @return boolean true修改成功, false修改失败
	 */
	public static boolean changeUserPassword(String name, String password, String newPassword){
		if(Db.checkUserPassword(name, password) == 2){
			return false;
		}
		return Db.changeUserPasswordForce(name, newPassword);
	}
	/**
	 * 强行修改密码
	 * @param name 用户名
	 * @param password 老密码
	 * @param newPassword 新密码 
	 * @return boolean true修改成功, false修改失败
	 */
	public static boolean changeUserPasswordForce(String name, String newPassword){
		Transaction tr = null;
		Session ss = null;
		boolean ret = true;
		String savePassword = Md5.toMd5(new StringBuffer(newPassword).append("605e9615ac4b50b7ab009d11e22ebc3c").toString());
		try {
			ss = sf.openSession();
			tr = ss.beginTransaction();
			ss.createSQLQuery("UPDATE `user` SET  password=? WHERE `name` = ?")
				.setParameter(0, savePassword).setParameter(1, name).executeUpdate();
			tr.commit();
		}catch(Exception e){
//			e.printStackTrace();
			ret = false;
			if(ss != null){
				ss.getTransaction().rollback();
			}
		}finally{
			if(ss != null && ss.isOpen()){
				ss.close();
			}
		}
		return ret;
	}
	
	public static void main(String[] args) {
		// 测试用
		// 插入
//		boolean tmpRes = Db.insertStudent(23454, "张三", 1, 19, "软件1403", "13012342234", "长沙", "23514");
//		System.out.println(tmpRes);
		
		// 通过学号查询
//		System.out.println(Db.getStudentById(1));
		
		// 查询student到集合中
//		System.out.println(Db.getStudents().get(1).getId());
		
		// 修改学生信息
//		System.out.println(Db.updateStudent(111, "李四", 2, 20, "软件1504", "15333662333", "株洲"));
//		System.out.println(Db.getStudentById(111).getName());
		
		// 插入管理员
//		System.out.println(Db.insertUser(1, "zzliux", "admin123"));
		
		// 登录查询
//		System.out.println(Db.checkUserPassword("zzliux", "admin123"));
//		System.out.println(Db.checkUserPassword("13521", "13521"));
//		System.out.println(Db.checkUserPassword("13521", "1521"));
		
		// 删除学生
//		System.out.println(Db.deleteStudentById(23454));
	}
}
