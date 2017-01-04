layui.use(['form', 'layer', 'element'], function(){
	var layer = layui.layer;
	var form = layui.form();
	layer.load(2);
	$.ajax({
		url: '/stu_info/api/getStudentInfo.action',
		method: 'get',
		dataType: 'json',
		success: function(data){
			layer.closeAll();
			if(data.err == 0){
				$('#stu_id').val(data.stu.id);
				$('#stu_name').val(data.stu.name);
				$('#stu_age').val(data.stu.age);
				$('#stu_className').val(data.stu.className);
				$('#stu_phoneNumber').val(data.stu.phoneNumber);
				$('#stu_location').val(data.stu.location);
				$('#stu_sex_'+data.stu.sex).prop('checked', 'checked');
				form.render();
			}else{
				layer.msg(data.msg);
			}
		},
		error: function(){
			layer.closeAll();
			layer.msg('网络错误', {time:0});
		}
	});
	form.on('submit(base)', function(form_data){
		layer.load(2);
		$.ajax({
			url:'/stu_info/api/updateStudentInfo.action',
			method: 'post',
			dataType: 'json',
			data: form_data.field,
			success: function(data){
				layer.closeAll();
				layer.msg(data.msg);
			},
			error: function(){
				layer.closeAll();
				layer.msg('未知错误');
			}
		});
		return false;
	});
	form.on('submit(pass)', function(form_data){
		if(form_data.field.stu_pass_new != form_data.field.stu_pass_new_confirm){
			layer.msg('两次新密码不一致', {icon: 5});
			return false;
		}
		layer.load(2);
		$.ajax({
			url:'/stu_info/api/updateStudentPassword.action',
			method: 'post',
			dataType: 'json',
			data: form_data.field,
			success: function(data){
				layer.closeAll();
				layer.msg(data.msg);
			},
			error: function(){
				layer.closeAll();
				layer.msg('未知错误');
			}
		});
		return false;
	})
});