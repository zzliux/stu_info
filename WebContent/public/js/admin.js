layui.use(['form', 'layer', 'element'], function(){
	var student = {};
	var layer = layui.layer;
	var form = layui.form();
	var element = layui.element();
	layer.load(2);
	
	element.on('tab(admin-tab)', function(ele_data){
		switch(ele_data.index){
			case 0: renderStudentListTab(); break;
			case 1: renderStudentStatisticTab(); break;
		}
	});
	$('#stu_add').on('click', function(){
		layer.open({
			title:'添加学生信息',
			type: 1,
			area: '500px',
			content: '<form class="layui-form" style="margin:30px"><div class="layui-form-item"><label class="layui-form-label">学号</label><div class="layui-input-block"><input type="number" name="stu_id" placeholder="学号（添加后不可修改）" required lay-verify="number" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">姓名</label><div class="layui-input-block"><input type="text" name="stu_name" placeholder="姓名" required lay-verify="required" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">年龄</label><div class="layui-input-block"><input type="number" name="stu_age" placeholder="年龄" required lay-verify="required" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">班级</label><div class="layui-input-inline" id="class-input"><input type="text" name="stu_className" placeholder="班级" required lay-verify="required" autocomplete="off" class="layui-input"></div><a class="layui-btn" id="change-class-input-type" href="javascript:;">切换至选择</a></div><div class="layui-form-item"><label class="layui-form-label">手机号</label><div class="layui-input-block"><input type="number" name="stu_phoneNumber" placeholder="手机号码" required lay-verify="phone" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">住址</label><div class="layui-input-block"><input type="text" name="stu_location" placeholder="家庭住址" required lay-verify="required" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">性别</label><div class="layui-input-block"><input type="radio" name="stu_sex" value="1" title="男" checked><input type="radio" name="stu_sex" value="2" title="女"></div></div><div class="layui-form-item"><label class="layui-form-label"></label><div class="layui-form-mid layui-word-aux">初始密码为学号，且管理员不可修改</div></div><div class="layui-form-item"><div class="layui-input-block"><button class="layui-btn" lay-submit lay-filter="sub-add">添加学生</button></div></div></form>'
		});
		form.render();
		$('#change-class-input-type').unbind('click');
		$('#change-class-input-type').bind('click', function(){
			if($(this).html() == '切换至选择'){
				console.log(student);
				var __class = {};
				for(var key in student){
					__class[student[key].className] = true;
				}
				var selectHtml = '<select name="stu_className" lay-verify="required"><option value=""></option>';
				for(var key in __class){
					selectHtml += '<option value="'+key+'">'+key+'</option>';
				}
				selectHtml += '<select>';
				$('#class-input').html(selectHtml);
				$(this).html('切换至输入');
			}else{
				$('#class-input').html('<input type="text" name="stu_className" placeholder="班级" required lay-verify="required" autocomplete="off" class="layui-input">');
				$(this).html('切换至选择');
			}
			form.render();
		});
		
		form.on('submit(sub-add)', function(form_data){
			layer.load(2);
			var newStu = {};
			for(var key in form_data.field){
				newStu[key.substr(4)] = form_data.field[key];
			}
			newStu.sex = newStu.sex==1?'男':(newStu.sex==2?'女':'未知');
			$.ajax({
				url: '/stu_info/api/adminAddStudent.action',
				method: 'post',
				dataType: 'json',
				data: form_data.field,
				success: function(data){
					student[newStu.id] = newStu;
					if(data.err == 0){
						// 添加学生后重新渲染学生列表
						renderStudentListTab();
					}
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
	});
	
	$.ajax({
		url: '/stu_info/api/checkLogin.action',
		method: 'post',
		dataType: 'json',
		data: {
			fun: 'checkLogin'
		},
		success: function(data){
			layer.closeAll();
			if(data.err != 0 || data.userType != 1){
				location.href = '/stu_info/login.html';
			}
			renderStudentListTab();
		},
		error: function(){
			layer.closeAll();
			layer.msg('未知错误', {time:0});
		}
	});
	function renderStudentStatisticTab(){
		$('#stu_statistic').html('');
		layer.load(2);
		getAllStudents(function(stu_data){
			layer.closeAll();
			var list = stu_data.students;
			var stData = {__sex:{}, __age:{}, __class:{}};
			for(var i=0; i<list.length; i++){
				list[i].sex = list[i].sex==1?'男':(list[i].sex==2?'女':'未知');
				list[i].age = list[i].age + '岁';
				// 性别
				stData.__sex[list[i].sex] = stData.__sex[list[i].sex]? stData.__sex[list[i].sex]+1:1;
				// 年龄
				stData.__age[list[i].age] = stData.__age[list[i].age]? stData.__age[list[i].age]+1:1;
				// 班级
				stData.__class[list[i].className] = stData.__class[list[i].className]? stData.__class[list[i].className]+1:1;
			}
			var chData = {
				__sex: [],
				__age: [],
				__class: []
			};
			for(key1 in stData){
				for(key2 in stData[key1]){
					chData[key1].push({
						value: stData[key1][key2],
						name:key2
					});
				}
			}
			
			$('#stu_statistic').height(600);
			var chart = echarts.init($('#stu_statistic')[0]);
			var option = {
				title:[{
					text: '学生数据统计',
					x:'center'
				},{
					text: '性别',
					top: '53%',
					left:'23%'
				},{
					text: '年龄',
					top: '53%',
					left:'73%'
				},{
					text: '班级',
					top: '93%',
					left: '48%'
				}],
				tooltip : {
					trigger: 'item',
					formatter: "{a} <br/>{b} : {c}个 ({d}%)"
				},
				calculable : true,
				series : [{
					name:'性别',
					type:'pie',
					radius : [30, 110],
					center : ['25%', '30%'],
					roseType : 'area',
					data: chData.__sex
				},{
					name:'年龄',
					type:'pie',
					radius : [30, 110],
					center : ['75%', '30%'],
					roseType : 'area',
					data: chData.__age
				},{
					name:'班级',
					type:'pie',
					radius : [30, 110],
					center : ['50%', '70%'],
					roseType : 'area',
					data: chData.__class
				}]
			};
			chart.setOption(option);
		});
	}
	function renderStudentListTab(){
		$('#stu_list').html('');
		layer.load(2);
		getAllStudents(function(data){
			layer.closeAll();
			var s = data.students;
			var repHtml = '<table class="layui-table" id="stu_table"><thead><tr><th>学号</th><th>姓名</th><th>性别</th><th>年龄</th><th>班级</th><th>手机号</th><th>住址</th></tr></thead>';
			for(var i=0; i<s.length; i++){
				s[i].sex = s[i].sex==1?'男':(s[i].sex==2?'女':'未知');
				repHtml += '<tr class="admin_col_stu_info" id="stu_'+s[i].id+'" stu-id="'+s[i].id+'"><td>'+s[i].id+'</td><td>'+s[i].name+'</td><td>'+s[i].sex+'</td><td>'+s[i].age+'</td><td>'+s[i].className+'</td><td>'+s[i].phoneNumber+'</td><td>'+s[i].location+'</td></tr>';
			}
			repHtml += '</table>';
			$('#stu_list').html(repHtml);
			$('.admin_col_stu_info').on('click',function(e){
				var id = $(this).attr('stu-id');
				layer.open({
					title:'修改学生信息',
					type: 1,
					area: '500px',
					content: '<form class="layui-form" style="margin:30px"><div class="layui-form-item"><label class="layui-form-label">学号</label><div class="layui-input-block"><input type="number" name="stu_id" value="'+student[id].id+'" required lay-verify="number" readonly="readonly" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">姓名</label><div class="layui-input-block"><input type="text" name="stu_name" value="'+student[id].name+'" required lay-verify="required" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">年龄</label><div class="layui-input-block"><input type="number" name="stu_age" value="'+student[id].age+'" required lay-verify="required" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">班级</label><div class="layui-input-inline" id="class-input"><input type="text" name="stu_className" value="'+student[id].className+'" required lay-verify="required" autocomplete="off" class="layui-input"></div><a class="layui-btn" id="change-class-input-type" href="javascript:;">切换至选择</a></div><div class="layui-form-item"><label class="layui-form-label">手机号</label><div class="layui-input-block"><input type="number" name="stu_phoneNumber" value="'+student[id].phoneNumber+'" required lay-verify="phone" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">住址</label><div class="layui-input-block"><input type="text" name="stu_location" value="'+student[id].location+'" required lay-verify="required" autocomplete="off" class="layui-input"></div></div><div class="layui-form-item"><label class="layui-form-label">性别</label><div class="layui-input-block"><input type="radio" name="stu_sex" value="1" title="男" '+(student[id].sex=='男'?'checked':'')+'><input type="radio" name="stu_sex" value="2" title="女" '+(student[id].sex=='女'?'checked':'')+'></div></div><div class="layui-form-item"><label class="layui-form-label"></label><div class="layui-input-block"><input type="checkbox" name="stu_delete" title="勾选该项表示删除该学生信息"></div></div><div class="layui-form-item"><div class="layui-input-block"><button class="layui-btn" lay-submit lay-filter="sub-change">提交修改</button></div></div></form>'
				});
				// 表单更新渲染
				form.render();
				
				$('#change-class-input-type').unbind('click');
				$('#change-class-input-type').bind('click', function(){
					if($(this).html() == '切换至选择'){
						var __class = {};
						for(var key in student){
							__class[student[key].className] = true;
						}
						var selectHtml = '<select name="stu_className" lay-verify="required"><option value=""></option>';
						for(var key in __class){
							selectHtml += '<option value="'+key+'"'+(key==student[id].className?'selected':'')+'>'+key+'</option>';
						}
						selectHtml += '<select>';
						$('#class-input').html(selectHtml);
						$(this).html('切换至输入');
					}else{
						$('#class-input').html('<input type="text" name="stu_className" value="'+student[id].className+'" placeholder="班级" required lay-verify="required" autocomplete="off" class="layui-input">');
						$(this).html('切换至选择');
					}
					form.render();
				});
				
				/* 更新学生信息 */
				form.on('submit(sub-change)', function(form_data){
					layer.load(2);
					$.ajax({
						url: '/stu_info/api/adminUpdateStudent.action',
						method: 'post',
						dataType: 'json',
						data: form_data.field,
						success: function(data){
							layer.closeAll();
							if(data.err == 0){
								// 更须学生信息后重新渲染学生列表
								renderStudentListTab();
							}
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
		});
	}
	
	function getAllStudents(callback){
		$.ajax({
			url: '/stu_info/api/adminListStudents.action',
			method: 'post',
			dataType: 'json',
			data: {
				fun: 'listStudents'
			},
			success: function(data){
				if(data.err != 0){
					layer.closeAll();
					layer.msg(data.msg);
					return ;
				}
				var s = data.students;
				student = {};
				for(var i=0; i<s.length; i++){
					student[s[i].id] = s[i];
				}
				callback(data);
			},
			error: function(){
				layer.closeAll();
				layer.msg('未知错误', {time:0});
			}
		});
	}
});
