<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>


<!DOCTYPE html>
<html>
<head>

<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css"
	type="text/css" rel="stylesheet" />
<link
	href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css"
	type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript"
	src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"
	src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>	
	
	

<script type="text/javascript">


	$(function(){
		
		 	$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			}); 
			
			
			
			
			$("#saveBtn").click(function() {
				$.ajax({
					
					url:"workbench/user/finduser.do",
					type:"get",
					dataType:"json",
					success:function (data){
						/* data 返回一个个user用户  n
							取出用户的姓名
							添加到下拉列表中
							默认值为登陆者
						*/
						var html = "<option></option>";
						$.each(data,function (i,n){
							html += "<option value='"+n.id+"'>"+n.name+"</option>";
						})
						
						$("#create-Owner").html(html);
						
						
						var id = "${user.id}";
						$("#create-Owner").val(id);
						
						$("#createActivityModal").modal("show");
						
						
					}
					
				});
				
				
			})
			
			
			
			//添加
			$("#addBtn").click(function() {
					$.ajax({
							
							url:"workbench/activity/add.do",
							data:{
								"owner":$.trim($("#create-Owner").val()),
								"name":$.trim($("#create-name").val()),
								"startDate":$.trim($("#create-endDate").val()),
								"endDate":$.trim($("#create-endDate").val()),
								"cost":$.trim($("#create-cost").val()),
								"description":$.trim($("#create-description").val())
							},
							
							type:"post",
							dataType:"json",
							
							success:function (data){
								if(data.success){
									$("#addForm")[0].reset();
									pageList($("#activityPage").bs_pagination('getOption', 'currentPage'),$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
									$("#createActivityModal").modal("hide");
									
								}else {
									alert("添加失败");
								}
							}
							
							
					});
						
			})
			
			pageList(1,2);
			
			//查询
			$("#selectBtn").click(function() {
				//为隐藏域表单赋值
				$("#hidden-name").val($.trim($("#select-name").val()));
				$("#hidden-owner").val($.trim($("#select-owner").val()));
				$("#hidden-startDate").val($.trim($("#select-startDate").val()));
				$("#hidden-endDate").val($.trim($("#select-endDate").val())); 
				
				pageList(1,2);
				
			})
			
			
			
			
			$("#updataBtn").click(function() {
				var $ck = $(":checkbox[name='ck']:checked");
				if($ck.length==0){
					alert("请选择要修改的目标");
					return false;
				}else if ($ck.length>1) {
					alert("只能修改一条记录");
					return false;
				}else{
					
					 var id = $ck.val();
					
					$.ajax({
						
						url:"workbench/activity/finduUserAndActivity.do",
						data:{
							"id":id
						},
						type:"get",
						dataType:"json",
						success:function(data){
							//需要返回一个用户的集合，一个activity
							
							var html = "<option></option>";
							$.each(data.userList,function (i,n){
								html += "<option value='"+n.id+"'>"+n.name+"</option>";
							})
							
							$("#edit-owner").html(html);
				
							$("#edit-id").val(data.a.id);
							$("#edit-owner").val(data.a.owner);
							$("#edit-name").val(data.a.name);
							$("#edit-startDate").val(data.a.startDate);
							$("#edit-endDate").val(data.a.endDate);
							$("#edit-cost").val(data.a.cost);
							$("#edit-description").val(data.a.description);
							
							$("#editActivityModal").modal("show");
							
						}
						
						
					}); 
					
				}
			})
			
			
			
			$("#updatesubBtn").click(function() {
				$.ajax({
					
					url:"wrokbench/activity/update.do",
					data:{
						"id":$.trim($("#edit-id").val()),
						"owner":$.trim($("#edit-owner").val()),
						"name":$.trim($("#edit-name").val()),
						"startDate":$.trim($("#edit-startDate").val()),
						"endDate":$.trim($("#edit-endDate").val()),
						"cost":$.trim($("#edit-cost").val()),
						"description":$.trim($("#edit-description").val())
					},
					type:"post",
					dataType:"json",
					success:function(data){
						//返回成功或失败
						//true   false
						
						if(data.success){
							pageList($("#activityPage").bs_pagination('getOption', 'currentPage'),$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
											
							$("#editActivityModal").modal("hide");
						}else{
							alert("更新失败");
						}
						
					}
					
					
				})
			})
			
			
			
			
			
			$("#deleteBtn").click(function() {
				var $ck = $(":checkbox[name='ck']:checked");
				if($ck.length==0){
					alert("请选择要删除的记录");
				}else{
					if(confirm("确认删除吗?")){
						
						var parm ="";
						
						for (var i = 0; i < $ck.length; i++) {
							parm += "id="+$($ck[i]).val();
							
							if(i<$ck.length-1){
								parm += "&";
							}
						}
						
						
						$.ajax({
							url:"workbench/activity/delete.do",
							data:parm,
							type:"post",
							dataType:"json",
							success:function(data){
								if(data.success){
									pageList($("#activityPage").bs_pagination('getOption', 'currentPage'),$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
									$("#ckbo").prop("checked",false);
									
								}else{
									alert("删除失败！！！");
								}
							}
							
						})
						
						
					}
				}
			})
			
			
			
			
			$("#ckbo").click(function() {
				$("input[name=ck]").prop("checked",this.checked);
			})
			
			//选择框反选
			$("#activityBody").on("click",$("input[name=ck]"),function(){
				
				$("#ckbo").prop("checked",$("input[name=ck]").size()==$("input[name=ck]:checked").size());
			
			})
			
			
	
		
	});
	
	
	
	function pageList(pageNo,pageSize) {
		
		$("#select-name").val($.trim($("#hidden-name").val()));
		$("#select-owner").val($.trim($("#hidden-owner").val()));
		$("#select-startDate").val($.trim($("#hidden-startDate").val()));
		$("#select-endDate").val($.trim($("#hidden-endDate").val()));
		 
		
		$.ajax({
					
				url:"workbench/activity/pageList.do",
				data:{
					"pageNo":pageNo,
					"pageSize":pageSize,
					"name":$.trim($("#select-name").val()),
					"owner":$.trim($("#select-owner").val()),
					"startDate":$.trim($("#select-startDate").val()),
					"endDate":$.trim($("#select-endDate").val())
				},
				
				type:"get",
				dataType:"json",
				success:function (data){
					
					var html = "";
					
					$.each(data.list,function(i,n){
						
						html += '<tr class="active">';
						html += '<td><input type="checkbox" name="ck" value="'+n.id+'"/></td>';
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id='+n.id+'\';">'+n.name+'</a></td>';
	                    html += '<td>'+n.owner+'</td>';
						html += '<td>'+n.startDate+'</td>';
						html += '<td>'+n.endDate+'</td>';
						html += '</tr>';
						
					
					})
					
					$("#activityBody").html(html);
					
					
					var totalPages = data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
					
					//使用bootstrap的分页插件展现分页信息
					$("#activityPage").bs_pagination({
						currentPage: pageNo, // 页码
						rowsPerPage: pageSize, // 每页显示的记录条数
						maxRowsPerPage: 20, // 每页最多显示的记录条数
						totalPages: totalPages, // 总页数
						totalRows: data.total, // 总记录条数

						visiblePageLinks: 3, // 显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true, 
						
						onChangePage : function(event, data){
							pageList(data.currentPage , data.rowsPerPage);
							$("#ckbo").prop("checked",false);
						}
				   });
					
					
					
				}
					
					
		});
		
	}
	
	
</script>
</head>
<body>

	
	<input type="hidden" id="hidden-name">
	<input type="hidden" id="hidden-owner">
	<input type="hidden" id="hidden-startDate">
	<input type="hidden" id="hidden-endDate">
	

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form" id="addForm">

						<div class="form-group">
							<label for="create-marketActivityOwner"
								class="col-sm-2 control-label">所有者<span
								style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-Owner">

								</select>
							</div>
							<label for="create-marketActivityName"
								class="col-sm-2 control-label">名称<span
								style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control"
									id="create-name">
							</div>
						</div>

						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始时间</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate">
							</div>
						</div>
						<div class="form-group">

							<label for="create-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-cost">
							</div>
						</div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal"
						id="addBtn">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id">
						<div class="form-group">
							<label for="edit-marketActivityOwner"
								class="col-sm-2 control-label">所有者<span
								style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
									
								</select>
							</div>
							<label for="edit-marketActivityName"
								class="col-sm-2 control-label">名称<span
								style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control"
									id="edit-name">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startDate"
									>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endDate"
									>
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost"
									>
							</div>
						</div>

						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="updatesubBtn">更新</button>
				</div>
			</div>
		</div>
	</div>




	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div
		style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute; top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form"
					style="position: relative; top: 8%; left: 5px;">

					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">名称</div>
							<input class="form-control" type="text" id="select-name">
						</div>
					</div>

					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">所有者</div>
							<input class="form-control" type="text" id="select-owner">
						</div>
					</div>


					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">开始日期</div>
							<input class="form-control" type="text" id="select-startDate"/>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">结束日期</div>
							<input class="form-control" type="text" id="select-endDate">
						</div>
					</div>

					<button type="button" class="btn btn-default" id="selectBtn">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar"
				style="background-color: #F7F7F7; height: 50px; position: relative; top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
					<button type="button" id="saveBtn" class="btn btn-primary"
						data-toggle="modal" data-target="#createActivityModal">
						<span class="glyphicon glyphicon-plus"></span> 创建
					</button>
					<button type="button" id="updataBtn" class="btn btn-default"
						data-toggle="modal" data-target="#editActivityModal">
						<span class="glyphicon glyphicon-pencil"></span> 修改
					</button>
					<button type="button" id="deleteBtn" class="btn btn-danger">
						<span class="glyphicon glyphicon-minus"></span> 删除
					</button>
				</div>

			</div>
			<div style="position: relative; top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="ckbo" /></td>
							<td>名称</td>
							<td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<!-- <tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr> -->
					
						
					</tbody>
				</table>
			</div>
			<div style="height: 50px; position: relative;top: 30px;">
			<div id="activityPage"></div>

		</div>

	</div>
</body>
</html>