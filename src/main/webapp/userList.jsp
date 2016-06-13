<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>User list</h2>
<section>
<%--@elvariable id="userList" type="java.util.List"--%>
<c:forEach items="${userList}" var="user">
<%--<jsp:useBean id="user" type="ru.javawebinar.topjava.model.User" scope="request"/>--%>
<form method="post" action="users">
    <input type="hidden" name="id" value="${user.id}">
    <input type="hidden" name="name" value="${user.name}">
    <p>${user.id}. ${user.name}&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit">Save</button></p>
</form>
</c:forEach>
</section>
</body>
</html>
