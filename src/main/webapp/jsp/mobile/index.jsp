<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content='"我要"管理系统'>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="author" content="Snowm">
<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
<meta http-equiv="cache-control" content="no-cache,private">
<title>"我要"管理系统</title>
<!-- <link rel="stylesheet" type="text/css" href="resources/css/main.css" />
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap/dataTables/dataTables.bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap/dataTables/select.bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap/dataTables/rowReorder.bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="resources/css/bootstrap/dataTables/responsive.bootstrap.min.css" />

<link rel="shortcut icon" href="resources/images/favicon.ico">
<script data-main="resources/js/base/index_main" src="resources/js/lib/requirejs/require.js"></script>  -->
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE8+ -->
<!--[if gte IE 8]><script src="js/lib/jquery/fileupload/cors/jquery.xdr-transport.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top header">
		<div class="container-fluid">
			<div class="navbar-header">
		        <button type="button" data-toggle="collapse" data-target="#main_navbar" class="navbar-toggle">
		        	<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
		        	<span class="icon-bar"></span>
		        	<span class="icon-bar"></span>
		        </button>
				<a class="navbar-brand" href="./">"我要"管理系统</a>
			</div>
			<div id="main_navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="#" id="index-a">首页</a></li>
					
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li id="system-manage" class="dropdown" data-toggle="tooltip"
						title="系统管理" data-placement="bottom"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false"><span
							class="glyphicon glyphicon-cog"></span><span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#user-manage" id="user-manage-a">用户管理</a></li>
							<li><a href="javascript:;" id="system-config-a">系统设置</a></li>
						</ul></li>
					<li class="dropdown" data-toggle="tooltip"
						title="个人管理" data-placement="bottom"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false"><span
							class="glyphicon glyphicon-user"></span><span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li data-toggle="tooltip" title="注销" ><a href="<c:url value='/logout' />">注销</a></li>
						</ul></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</nav>

	<div class="container" id="main_container">
		<div id="modals-container"></div>
	</div>
	<!-- /container -->
	<script>
		var socket = new WebSocket('ws://localhost:8080/chat/websck'); 
		// 打开Socket 
		socket.onopen = function(event) { 
		  // 发送一个初始化消息
		  socket.send('I am the client and I\'m listening!'); 

		  // 监听消息
		  socket.onmessage = function(event) { 
		    console.log('Client received a message',event); 
		  }; 

		  // 监听Socket的关闭
		  socket.onclose = function(event) { 
		    console.log('Client notified socket has closed',event); 
		  }; 
		};

	</script>
</body>
</html>