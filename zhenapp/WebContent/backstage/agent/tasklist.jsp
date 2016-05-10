<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<meta name="viewport" content="width=device-width, initial-scale=1">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>积分明细</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/bootstrap/css/myPage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/backstage/agent/pagematter/amazeui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/backstage/agent/pagematter/admin.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/backstage/agent/pagematter/lanyunying.css" />
<script src="${pageContext.request.contextPath}/backstage/agent/pagematter/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/backstage/agent/pagematter/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/backstage/agent/pagematter/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath}/backstage/agent/pagematter/amazeui.min.js"></script>
<script src="${pageContext.request.contextPath}/backstage/agent/pagematter/lanyunying.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/backstage/agent/pagematter/default.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/backstage/agent/pagematter/kindeditor.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/backstage/agent/pagematter/zh_CN.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/jqPaginator.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/myPage.js" type="text/javascript"></script>
</head>
<div class="am-cf admin-main">
      <!-- sidebar start -->
      <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
        <div class=" admin-offcanvas-bar">
          <ul class="am-list admin-sidebar-list">
            <li><a href="/admin"><span class="am-icon-home"></span> 首页</a></li>
            <li class="admin-parent"><a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span
                class="am-icon-user"></span>用户管理 <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
              <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-nav">
                <li><a href="/admin/user" class="am-cf"><span class="am-icon-list"></span>用户列表</a></li>
                <li><a href="/admin/user/listRecharge"><span class="am-icon-usd"></span>充值记录</a></li>
                <li><a href="/admin/user/listCoin"><span class="am-icon-money"></span>资金记录</a></li>
              </ul></li>
            <li><a href="/admin/user/taskList"><span class="am-icon-pencil-square-o"></span>订单查询</a></li>
            <li><a href="/admin/combo"><span class="am-icon-cubes"></span> 套餐信息</a></li>

            
            
              <li><a href="/admin/sysconf"><span class="am-icon-puzzle-piece"></span>系统配置</a></li>
              <li><a href="/admin/proxy/setting"><span class="am-icon-puzzle-piece"></span>设置登录页面</a></li>
              <li><a href="/admin/proxy/points"><span class="am-icon-heart"></span>剩余积分</a></li>
              <li><a href="/admin/stat"><span class="am-icon-bar-chart"></span>数据统计</a></li>
            

          </ul>
        </div>
      </div>
 <!-- sidebar end -->
      <div id="module-head"></div>
      
  <meta name="viewport" content="width=device-width, initial-scale=1">

<div class="admin-content">
  <div class="am-alert am-alert-danger" id="my-alert" style="display: none">
    <p>开始日期应小于结束日期！</p>
  </div>

  <div class="am-cf am-padding">
    <div class="am-fl am-cf">
      <strong class="am-text-primary am-text-lg">我的订单 </strong>
    </div>
  </div>

  <div class="am-g" id="module-head" style="margin-bottom: 10px;">
    <div class="am-u-sm-12 am-u-md-3">
      <button type="button" class="am-btn am-btn-default" id="my-start">
        <span class="am-icon-calendar"></span>开始日期
      </button>
      <span id="my-startDate"></span>
    </div>

    <div class="am-u-sm-12 am-u-md-3">
      <button type="button" class="am-btn am-btn-default am-margin-right" id="my-end">
        <span class="am-icon-calendar"></span>结束日期
      </button>
      <span id="my-endDate"></span>
    </div>

    <div class="am-u-sm-12 am-u-md-6">
      <form class="am-form-inline" role="form">
        <div class="am-form-group">
          <input type="text" id="nick" class="am-form-field am-input-sm" value="" placeholder="用户名">
        </div>
 		<div class="am-form-group">
          <input type="text" id="nid" class="am-form-field am-input-sm" value="" placeholder="宝贝id">
        </div>
        <div class="am-form-group">
          <input type="text" id="fid" class="am-form-field am-input-sm" value="" placeholder="任务ID">
        </div>
        <div class="am-form-group">
          <select name="type" id="ktype">
          	<option value="FLOW">流量</option>
            <option value="ZTCFLOW">直通车</option>
          </select>
        </div>
        <button class="am-btn am-btn-default" id="search" type="button">搜索</button>
      </form>
    </div>

  </div>

  <div class="am-g">
    <div class="am-u-sm-12">
      <form class="am-form">
        <table class="am-table am-table-striped am-table-hover" style="font-size: 1.4rem;">
          <thead>
            <tr class="am-success">
              <th>任务ID</th>
              <th>订单ID</th>
              <th>宝贝ID</th>
              <th>任务类型</th>
              <th>用户名</th>
              <th>关键词</th>
              <th>访问数</th>
              <th>收藏数</th>
              <th>加购物车数</th>
              <th>完成访问数</th>
              <th>完成收藏数</th>
              <th>完成加购物车数</th>
              <th>失败数</th>
              <th>发布时间</th>
              <th>任务状态</th>
               <th>查询状态</th>
               <th>详情</th>
               <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${tTaskInfoCustomlist}" var="list">
            	<tr data-id="${list.taskpk }">
                <td>${list.taskpk }</td>
                <td>${list.taskid}</td>
                <td>${list.taskkeynum}</td>
                <td>${list.tasktype }</td>
                <td>${list.createuser }</td>
                <td>${list.taskkeyword}</td>
                <td>${list.flowcount }</td>
                <td>${list.collectioncount }</td>
                <td>${list.shoppingcount }</td>
                <td>${list.flowcount }</td>
                <td>${list.collectioncount }</td>
                <td>${list.shoppingcount }</td>
                <td class="font-red">0</td>
                <td>${list.createtime }</td>
                <td>进行中 </td>
                <td>进行中</td>
				<td>正常</td>
				<td>
				  <a data-id="729934073681674240" class="am-badge am-badge-primary shutdownTaks">终止任务</a>					
				</td>
              </tr>
            </c:forEach>
              
          </tbody>
        </table>
        <div>
			<ul class="pagination" id="pagination">
			</ul>
			<input type="hidden" id="PageCount" runat="server" value="${total}"/>
			<input type="hidden" id="PageSize" runat="server" value="10" />
			<input type="hidden" id="countindex" runat="server" value="10"/>
			<!--设置最多显示的页码数 可以手动设置 默认为7-->
			<input type="hidden" id="visiblePages" runat="server" value="12" />
		  </div>
      </form>
    </div>
  </div>

</div>

<script>
  $(function() {
    var startDate = new Date(2015, 12, 20);
    var endDate = new Date(2016, 11, 25);
    var $alert = $('#my-alert');
    $('#my-start').datepicker().on('changeDate.datepicker.amui', function(event) {
      if (event.date.valueOf() > endDate.valueOf()) {
        $alert.find('p').text('开始日期应小于结束日期！').end().show();
      } else {
        $alert.hide();
        startDate = new Date(event.date);
        $('#my-startDate').text($('#my-start').data('date'));
      }
      $(this).datepicker('close');
    });

    $('#my-end').datepicker().on('changeDate.datepicker.amui', function(event) {
      if (event.date.valueOf() < startDate.valueOf()) {
        $alert.find('p').text('结束日期应大于开始日期！').end().show();
      } else {
        $alert.hide();
        endDate = new Date(event.date);
        $('#my-endDate').text($('#my-end').data('date'));
      }
      $(this).datepicker('close');
    });

    $("#search").click(
        function() {
          var startTime = $("#my-startDate").text();
          var entTime = $("#my-endDate").text();
          var fid = $("#fid").val();
          var nid = $("#nid").val();
          location.href = "/admin/user/taskList?page=1&startTime=" + startTime + "&endTime=" + entTime + "&fid=" + fid
              + "&nick=" + $("#nick").val()+"&nid="+nid+"&type="+$("#ktype").val();
        });
    
    $(document)
	.on(
			"click",
			".shutdownTaks",
			function() {
				if (confirm("您确定要终止该订单吗？")) {
					$
							.ajax({
								type : "post",
								url : "/admin/user/terminateTask",
								data : {
									id : $(this).attr("data-id")
								},
								success : function(resp) {
									if (resp && resp.ec == 0) {
										Message
												.info('终止成功：',
														false);
										setTimeout(
												function() {
													window.location.href = '/admin/user/taskList?page=1';
												}, 2000);

									} else {
										Message
												.error(resp.em,
														true);
									}

								},
								error : function() {
									Message.error(resp.em, true);
								}
							});
				}
			});
  });
  
  var index = Number("${pagenum}");
	if (index.length < 1) {
		index = 1;
	}
	function loadpage() {
		var myPageCount = parseInt($("#PageCount").val());
		var myPageSize = parseInt($("#PageSize").val());
		var countindex = myPageCount % myPageSize > 0 ? (myPageCount / myPageSize) + 1
				: (myPageCount / myPageSize);
		if(countindex==0){
			countindex=1;
		}
		$("#countindex").val(countindex);
		$.jqPaginator('#pagination',
						{
							totalPages : parseInt($("#countindex").val()),
							visiblePages : parseInt($("#visiblePages").val()),
							currentPage : index,
							first : '<li class="first"><a href="${pageContext.request.contextPath}/task/responsetaskmanageagent?page=1">首页</a></li>',
							prev : '<li class="prev"><a href="javascript:;">上一页</a></li>',
							next : '<li class="next"><a href="javascript:;">下一页</a></li>',
							last : '<li class="last"><a href="javascript:;">末页</a></li>',
							page : '<li class="page"><a href="javascript:;">{{page}}</a></li>',
							onPageChange : function(num, type) {
								if (type == "change") {
									//exeData(num, type);
									window.location.href = "${pageContext.request.contextPath}/task/responsetaskmanageagent?page=" + num;
								}
							}
						});
	}
</script>

    </div>
  
  <a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu"
    data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
  <footer>
    <hr>
    <p class="am-padding-left">Copyright (c) 2015 zhenapp.cn Inc. All Rights. 浙ICP备140452118号-5</p>
  </footer>

</body>
</html>