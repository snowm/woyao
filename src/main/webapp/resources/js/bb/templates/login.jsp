<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="login_url">
	<c:url value="/login" />
</c:set>
<form action="${login_url }" method="POST">
	username:<input type="text" name="username" value="" />
	<p/> 
	password:<input
		type="password" name="password" value="" /> <input type="hidden"
		name="${_csrf.parameterName}" value="${_csrf.token}" />
	<button>Login</button>
</form>