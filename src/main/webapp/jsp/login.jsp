<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content='"我要"管理系统'>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="author" content="Snowm">
<meta http-equiv="X-UA-Compatible" content="IE=EDGE" />
<meta http-equiv="cache-control" content="no-cache,private">
<link rel="shortcut icon" href="/admin/resources/images/add/luoke.png" type="image/png">
<title>倮克科技 - 登录</title>
<link href="/admin/resources/css/style.default.css" rel="stylesheet">
<link href="/admin/resources/css/main.css" rel="stylesheet">

<link rel="shortcut icon" href="resources/images/favicon.ico">
<script src="http://apps.bdimg.com/libs/jquery/1.9.1/jquery.min.js"></script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE8+ -->
<!--[if gte IE 8]><script src="js/lib/jquery/fileupload/cors/jquery.xdr-transport.js"></script><![endif]-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>
<body class="signin">
	<c:set var="login_url">
		<c:url value="/admin/login" />
	</c:set>
	<section id="main_container" >
  
	    <div class="signinpanel">
	        
	        <div class="row">
	            
	            <div class="col-md-7">
	                
	                <div class="signin-info">
	                    <div class="logopanel">
	                        <h1><span>[</span> 倮克科技  <span>]</span></h1>
	                    </div><!-- logopanel -->
	                
	                    <div class="mb20"></div>
	                
	                    <h5><strong>欢迎进入倮克科技统一登录界面</strong></h5>
	                    <ul>
	                    <!--     <li><i class="fa fa-arrow-circle-o-right mr5"></i> Fully Responsive Layout</li>
	                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> HTML5/CSS3 Valid</li>
	                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> Retina Ready</li>
	                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> WYSIWYG CKEditor</li>
	                        <li><i class="fa fa-arrow-circle-o-right mr5"></i> and much more...</li> -->
	                    </ul>
	                    <div class="mb20"></div>
	                    <!--  <strong>Not a member? <a href="#!">注册</a></strong>-->
	                </div><!-- signin0-info -->
	            
	            </div><!-- col-sm-7 -->
	            
	            <div class="col-md-5">
	                
	                <form action="${login_url}" method="POST">
	                    <h4 class="nomargin text-center">登 录</h4>
	                
	                    <input type="text" class="form-control uname" id="username" name="username" placeholder="用户名" autocomplete="off"/>
	                    <input type="password" class="form-control pword" id="password" name="password" type="password" value="" placeholder="密 码" autocomplete="off" />
	                    <!-- <a href=""><small>忘记密码?</small></a> -->
	                    <button class="btn btn-success btn-block" type="submit" >登录</button>
	                    
	                </form>
	            </div><!-- col-sm-5 -->
	            
	        </div><!-- row -->
	        
	        <div class="signup-footer">
	            <div class="">
	                 
	            </div>
	            <div class="pull-right">
	                &copy;Created By : 倮克科技研发部
	            </div>
	        </div>
	        
	    </div><!-- signin -->
	  
	</section>
</body>
</html>