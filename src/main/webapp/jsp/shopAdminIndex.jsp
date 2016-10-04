<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="/shopAdmin/resources/images/logo0.png" type="image/png">
    <title>woyao</title>
    <!-- <link href="/shopAdmin/resources/plugin/bootstrap-3.3.0-dist/dist/css/bootstrap.css" rel="stylesheet"> -->
    <link href="/shopAdmin/resources/css/application-ui.css" rel="stylesheet">
    <link href="/shopAdmin/resources/css/font-awesome.min.css" rel="stylesheet">
    <link href="/shopAdmin/resources/css/main.css" rel="stylesheet">
</head>
<script data-main="/shopAdmin/resources/js/main.js" src="/shopAdmin/resources/js/plugin/require.js"></script>
<body ms-controller="root">


<div id='wrapper'>
    <!-- Sidebar -->
    <section id='sidebar'>
        <i class='icon-align-justify icon-large' id='toggle'></i>
        <ul id='dock'>
            <li class='active launcher'>
                <i class='fa fa-home'></i>
                <a href="#!/home">首页</a>
            </li>
            <li class='launcher'>
                <i class='fa fa-wrench'></i>
                <a href="#!/setting">设置</a>
            </li>
            <li class='launcher'>
                <i class='fa fa-star'></i>
                <a href="#!/setting">收入明细</a>
            </li>
            <li class='launcher'>
                <i class='fa fa-gift'></i>
                <a href="tables.html">礼物管理</a>
            </li>
        </ul>
        <div data-toggle='tooltip' id='beaker' title='Made by lab2023'></div>
    </section>
    <!-- Tools -->
    <section id='tools'>
        <ul class='breadcrumb' id='breadcrumb'>
            <li class='title'>Tables</li>
            <li><a href="#">Lorem</a></li>
            <li class='active'><a href="#">ipsum</a></li>
        </ul>
        <div id='toolbar'>
            <ul class='nav navbar-nav pull-right'>
                <li class='dropdown'>
                    <a class='dropdown-toggle' data-toggle='dropdown' href='#'>
                        <i class='icon-envelope'></i>
                        Messages
                        <span class='badge'>5</span>
                        <b class='caret'></b>
                    </a>
                    <ul class='dropdown-menu'>
                        <li>
                            <a href='#'>New message</a>
                        </li>
                        <li>
                            <a href='#'>Inbox</a>
                        </li>
                        <li>
                            <a href='#'>Out box</a>
                        </li>
                        <li>
                            <a href='#'>Trash</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href='#'>
                        <i class='icon-cog'></i>
                        Settings
                    </a>
                </li>
                <li class='dropdown user'>
                    <a class='dropdown-toggle' data-toggle='dropdown' href='#'>
                        <i class='icon-user'></i>
                        <strong>John DOE</strong>
                        <img class="img-rounded" src="http://placehold.it/20x20/ccc/777" />
                        <b class='caret'></b>
                    </a>
                    <ul class='dropdown-menu'>
                        <li>
                            <a href='#'>Edit Profile</a>
                        </li>
                        <li class='divider'></li>
                        <li>
                            <a href="/">Sign out</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </section>
    <!-- Content -->
    <div id='content' ms-include-src="content">
    </div>
</div>
</body>
</html>