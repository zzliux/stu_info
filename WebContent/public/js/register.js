layui.use(['layer', 'form', 'element'], function(){
	var layer = layui.layer;
	var form = layui.form();
	form.on('submit', function(form_data){
		if(form_data.field.stu_password != form_data.field.stu_password_confirm){
			layer.msg('两次密码不一致', {icon: 5});
			return false;
		}
		layer.load(2);
		$.ajax({
			url: '/stu_info/api/registerStudent.action',
			dataType: 'json',
			method: 'post',
			data: form_data.field,
			success: function(data){
				layer.closeAll();
				if(data.err == 0){
					layer.msg(data.msg + ' 2秒后跳转至登录页面');
					setTimeout(function(){
						location.href = "/stu_info/login.html"
					}, 2000);
				}else{
					layer.msg(data.msg);
				}
			},
			error: function(){
				layer.closeAll();
				layer.msg('未知错误');
			}
		});
		return false;
	});
});