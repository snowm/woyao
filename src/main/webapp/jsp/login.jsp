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
<link rel="stylesheet" type="text/css" href="resources/css/main.css" />

<link rel="shortcut icon" href="resources/images/favicon.ico">
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
	<c:set var="login_url">
		<c:url value="/admin/login" />
	</c:set>
	<div class="container" id="main_container" style="width:300px;">
		<div class="row">
			<div class="col-sm-12">
				<form action="${login_url }" method="POST" class="form-horizontal" style="margin-top:120px;">
					<div class="form-group">
						<label class=" col-xs-12 col-sm-12" for="username">用户名:</label>
						<div class="col-xs-12 col-sm-12">
						    <input type="text" style="display: none;" autocomplete="off" >
							<input class="form-control" id="username" name="username" type="text" value="" placeholder="用户名" autocomplete="off" >
						</div>
					</div>
					<div class="form-group">
						<label class=" col-xs-12 col-sm-12" for="password">密 码:</label>
						<div class="col-xs-12 col-sm-12">
						    <input type="password" style="display: none;" autocomplete="off" >
							<input class="form-control" id="password" name="password" type="password" value="" placeholder="密 码" autocomplete="off" >
						</div>
					</div>
					<div class="form-group">
						<label class=" col-xs-12 col-sm-12">&nbsp;</label>
						<div class="col-xs-12 col-sm-12">
							<button class="btn btn-success" style="width:100%"">登 录</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>