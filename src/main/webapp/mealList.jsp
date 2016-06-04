<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
<%--@elvariable id="mealList" type="java.util.List"--%>
<c:forEach items="${mealList}" var="meal">
    <c:set var="date" value="${meal.dateTime}"/>
    <c:set var="dateTime" value="${fn:replace(date,'T',' ')}" />
    <tr <c:if test = "${meal.exceed eq true}">style="color:red"</c:if> <c:if test = "${meal.exceed ne true}">style="color:green"</c:if>>
        <td><c:out value="${dateTime}"/></td>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
    </tr>
</c:forEach>
</table>
</body>
</html>
