layui.use(['layer'], function(){
	var layer = layui.layer;
	var layerLoadId = layer.load(2);
	$.ajax({
		url: '/stu_info/api/checkLogin.action',
		method: 'post',
		dataType: 'json',
		data: {
			fun: 'checkLogin'
		},
		success: function(data){
			if(data.err == 0){
				if(data.userType == 0){
					location.href = '/stu_info/student.html';
				}else if(data.userType == 1){
					location.href = '/stu_info/admin.html';
				}else{
					location.href = '/stu_info/login.html';
				}
			}else{
				location.href = '/stu_info/login.html';
			}
		},
		error: function(){
			layer.close(layerLoadId);
			layer.msg('未知错误', {time: 0});
		}
	});
});