<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报警</title>
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
   <link href="../../assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/><!-- 字体图标 -->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/ElegantIcons/style.css"><!-- 字体图标 -->
    <link rel="stylesheet" type="text/css"  href="../../assets/plugins/simple-line-icons/simple-line-icons.min.css"><!-- 字体图标 -->
    <link rel="stylesheet"  type="text/css" href="../../css/bootstrap.css"><!-- base -->
    <link rel="stylesheet" type="text/css" href="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/><!-- table -->
    <link rel="stylesheet" type="text/css" href="../../assets/css/components.css"><!-- BASE -->
    <link rel="stylesheet" href="../../assets/css/plugins.css"><!-- BASE -->
    <link rel="stylesheet" type="text/css"  href="../../css/main.css"/><!-- 自定义 -->
    
    <link rel="stylesheet" href="../../css/bootstrap.css">
    <link rel="stylesheet" href="../../asset/plugins/ElegantIcons/style.css"><!--字体图标-->
    <link rel="stylesheet" href="../../asset/plugins/simple-line-icons/simple-line-icons.min.css"><!--字体图标-->
    <link rel="stylesheet" href="../../css/components.css"><!---->
    <link rel="stylesheet" href="../../css/main.css">
    <link rel="stylesheet" href="../../js/jqueryPagation/jquery.pagination.css">
</head>

<body onload="alarmOnload('all');">
<div class="o_warpper" >
    <!-- BEGIN HEADER -->
    <div class="o_header">
        <a href="javascript:void(0)" class="top_back" onclick="window.location.href='../../web/pageController/toIndex'"><span class="icon-arrow-left"></span>报警</a>
        <ul class="o_topnav">
            <li class="active">
                <a href="javascript:void(0)" onclick="toMsg('all');"><i class="icon-list"></i>全部<span class="badge badge-warning"></span></a>
            </li>
            <li >
                <a href="javascript:void(0)" onclick="toMsg('alarm');"><i class="icon-bell"></i>报警<span class="badge badge-danger"></span></a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="toMsg('thing');"><i class="icon-note"></i>事件<span class="badge badge-success"></span></a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="toMsg('fault');"><i class="icon-ban"></i>故障<span class="badge badge-primary"></span></a>
            </li>
        </ul>
        <div class="top_right">
            <a class="btn_lock"><span class="icon-lock"></span></a>
            <a class="top_user">Andy</a>
            <a class="top_logout" onclick="opLogout();">注销</a>
        </div>
    </div>
    <!-- END HEADER -->
    <div class="o_main">
        <div class="o_maincon">
		    <!-- start main map -->
			    <div class="main_box" style="height:73%">
			        <div class="alarm_table" style="margin-top: -6px">
			            <table class="table table-bordered table-hover dataTable" id="table2">
			                <thead>
			                   <tr>
			                       <th style="width: 243px;">设备名称</th>
			                       <th style="width: 243px;">设备编号</th>
			                       <th style="width: 243px;">报警类型</th>
			                       <th style="width: 256px;">地址</th>
			                       <th style="width: 120px;">时间</th>
			                       <th style="width: 109px;">操作</th>
			                   </tr>
			                </thead>
			                <tbody id="AllMsg" style="overflow: auto">
			                   
			                </tbody>
			            </table>
			            <div id="pagationDiv"></div>
			        </div>
			    </div>
		<!-- start foot -->
		     <div class="main_foot" style="height:27%">
	            <table class="table_one table table-hover table-striped dataTable">
					 <thead>
			            <tr>
			                <th style="width:3%"></th>
			                <th style="width:20%">设备名称</th>
			                <th style="width:20%">设备编号</th>
			                <th style="width:20%">报警类型</th>
			                <th style="width:20%">地址</th>
			                <th style="width:20%">时间</th>
			            </tr>
	            	</thead>
	            </table>
            	<div style="overflow:auto;height:76%">
		            <table class="table_one table table-bordered table-hover table-striped dataTable"  id="table1">
				            <tbody  id="tbAllMsg">
				            		
				            </tbody>
		            </table>
            	</div>
		    </div>
    <!-- END foot -->
		</div>
	</div>
</div>
	<script src="../../js/jquery.min.js"></script>
	<script src="../../js/jqueryPagation/jquery.pagination-1.2.7.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
    <script src="../../js/check.js"></script>
	<script type="text/javascript" src="../../js/alarm/alarm.js"></script>
	<script type="text/javascript" src="../../js/global.js"></script>
    <script src="../../js/normal.js"></script>
    <script src="../../js/layer/layer.js"></script>
	<!-- <script src="../../js/table.js"></script> -->
	<!-- <script type="text/javascript" src="../../assets/plugins/datatables/media/js/jquery.dataTables.min.js"></script>表格插件
	<script type="text/javascript" src="../../assets/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>表格插件 -->
</body>
</html>