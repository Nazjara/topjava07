<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h3>Meal list</h3>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <%--@elvariable id="mealList" type="java.util.List"--%>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<section>
    <h4>Meal filter</h4>
    <form method="POST" action='meals' name="frmFilterMeal">
        <table>
            <tbody>
            <tr>
                <td class="dateFilter">Start date</td>
                <td class="dateFilter">End date</td>
            </tr>
            <tr>
                <td><input id="startDate"
                           type="datetime-local" name="startDate"
                           value="" /> <br /></td>
                <td><input id="endDate"
                           type="datetime-local" name="endDate"
                           value="" /> <br /></td>
            </tr>
            <tr>
                <td id = "filter_submit" colspan=2><input class="btn submit filter" type="submit" value="Filter" /></td>
            </tr>
            </tbody>
        </table>
    </form>
</section>
</body>
</html>