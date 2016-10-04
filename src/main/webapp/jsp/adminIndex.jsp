<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="/admin/resources/images/add/logo0.png" type="image/png">
    <title>倮克后台管理系统</title>
    <link href="/admin/resources/css/style.default.css" rel="stylesheet">
</head>
<script data-main="/admin/resources/js/main.js" src="/admin/resources/js/plugin/require.js"></script>
<body ms-controller="root">
<!-- Preloader -->
<div id="preloader">
    <div id="status"><i class="fa fa-spinner fa-spin"></i></div>
</div>
<!-- Preloader -->
<section>
    <div class="leftpanel">
        <div class="logopanel">
            <h1 class="">倮克管理系统</h1>
        </div><!-- logopanel -->
        <div class="leftpanelinner">
            <!-- This is only visible to small devices -->
            <div class="visible-xs hidden-sm hidden-md hidden-lg">
                <div class="media userlogged">
                    <img alt="" src="/admin/resources/images/photos/loggeduser.png" class="media-object">
                    <div class="media-body">
                        <h4>admin</h4>
                        <span>""</span>
                    </div>
                </div>
                <h5 class="sidebartitle actitle">Account</h5>
                <ul class="nav nav-pills nav-stacked nav-bracket mb30">
                    <li><a href="profile.html"><i class="glyphicon glyphicon-user"></i> 个人中心</a></li>
                    <li><a href="#"><i class="glyphicon glyphicon-cog"></i> 设 置</a></li>
                    <li><a href="#"><i class="glyphicon glyphicon-question-sign"></i> 帮 助</a></li>
                    <li><a href="signin.html"><i class="glyphicon glyphicon-log-out"></i> 注 销</a></li>
                </ul>
            </div>
            <h5 class="sidebartitle">Navigation</h5>
            <ul class="nav nav-pills nav-stacked nav-bracket">
                <li class="active"><a href="#!/home"><i class="fa fa-home"></i> <span>首 页</span></a></li>
                <li><h5 class="sidebartitle">我要APP</h5></li>
                <li class="nav-parent"><a href=""><i class="fa fa-fire"></i> <span>商户管理</span></a>
                    <ul class="children">
                        <li><a href="#!/business-manage"><i class="fa fa-caret-right"></i> 商户查询</a></li>
                    </ul>
                </li>
                <li class="nav-parent"><a href=""><i class="fa fa-users"></i> <span>用户管理</span></a>
                    <ul class="children">
                        <li><a href="#!"><i class="fa fa-caret-right"></i> 用户查询</a></li>
                    </ul>
                </li>
                <li class="nav-parent"><a href=""><i class="fa fa-comments"></i> <span>聊天管理</span></a>
                    <ul class="children">
                        <li><a href="#!"><i class="fa fa-caret-right"></i> 查询聊天记录</a></li>
                    </ul>
                </li>
            </ul>

        </div><!-- leftpanelinner -->
    </div><!-- leftpanel -->

    <div class="mainpanel">

        <div class="headerbar">

        </div><!-- headerbar -->
        <div class="headerbar">

            <a class="menutoggle"><i class="fa fa-bars"></i></a>

            <div class="header-right">
                <ul class="headermenu">
                    <li>
                        <div class="btn-group">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                <img src="/admin/resources/images/photos/loggeduser.png" alt="" />
                                Admin
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-usermenu pull-right">
                                <li><a href="profile.html"><i class="glyphicon glyphicon-user"></i> 个人中心</a></li>
                                <li><a href="#"><i class="glyphicon glyphicon-cog"></i> 设 置</a></li>
                                <li><a href="#"><i class="glyphicon glyphicon-question-sign"></i> 帮 助</a></li>
                                <li><a href="signin.html"><i class="glyphicon glyphicon-log-out"></i> 注 销</a></li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div><!-- header-right -->
        </div><!-- headerbar -->
        <div class="contentpanel" ms-include-src="content" style="padding: 1px">


        </div><!-- contentpanel -->
    </div><!-- mainpanel -->
</section>
</body>
</html>