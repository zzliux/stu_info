layui.use(['form', 'layer', 'element'], function() {
	var form = layui.form();
	var layer = layui.layer;
	
	//监听提交
	form.on('submit', function(data) {
		var subData = data.field;
		subData.fun = 'login';
		layer.load(2);
		$.ajax({
			url: '/stu_info/api/login.action',
			method: 'post',
			dataType: 'json',
			data: subData,
			success: function(data){
				layer.closeAll();
				if(data.err == 0){
					layer.msg(data.msg + " 2秒后自动跳转");
					setTimeout(function(){
						if(data.userType == 0)
							location.href = "/stu_info/student.html";
						else
							location.href = "/stu_info/admin.html";
					}, 2000);
				}else{
					layer.msg(data.msg);
				}
			},
			error: function(){
				layer.closeAll();
				layer.msg("网络错误");
			}
		})
		return false;
	});
});
