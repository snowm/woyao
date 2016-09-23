<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload</title>
</head>
<body>
	<c:set var="upload_url">
		<c:url value="/uploadx" />
	</c:set>
	<form action="${upload_url }" method="POST" enctype="multipart/form-data">
		<input type="file" name="uploadFile" /><br/>
		msg: <input type="text" name="msg"/> <br/>
		csrf:<input type="text" name="${_csrf.parameterName}" value="${_csrf.token}" style="width:300px" /><br/>
		<button>upload</button>
	</form>
</body>
</html>