<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="https://s3.amazonaws.com/codecademy-content/projects/bootstrap.min.css">
    <link rel="stylesheet" href="style.css">
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
</head>
<body>
<div class="header">
    <h2>
        <a href="index.html">Home</a>
    </h2>
    <h2><strong>Meal list</strong></h2>
</div>
<div class="jumbotron">
    <div class="row">
        <div class="col-md-7">
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
<%--@elvariable id="meals" type="java.util.List"--%>
<c:forEach items="${meals}" var="meal">
    <c:set var="date" value="${meal.dateTime}"/>
    <c:set var="dateTime" value="${fn:replace(date,'T',' ')}" />
    <tr <c:if test = "${meal.exceed eq true}">style="color:red"</c:if> <c:if test = "${meal.exceed ne true}">style="color:green"</c:if>>
        <td><c:out value="${meal.id}"/></td>
        <td><c:out value="${dateTime}"/></td>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
        <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
        <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>
</tr>
</c:forEach>
    </tbody>
</table>
            </div>
        <div class="col-md-5">
            <form method="POST" action='meals' name="frmFilterMeal">
            <table>
                <thead>
                    <tr>
                        <th colspan=2>Filter meals</th>
                    </tr>
                </thead>
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
        </div>
        </div>
    </div>
<div class="footer">
    <c:set var="check" value="${meal}"/>
    <p id="addMeal"><strong><c:if test="${check eq null}">Add</c:if><c:if test="${check ne null}">Update</c:if> meal</strong></p>
    <form method="POST" action='meals' name="frmAddMeal">
        <input id="id"
               type="text" name="mealId"
               value="<c:out value="${meal.id}" />" /> <br />
        <div class="row">
            <div class="col-md-4  meal_input">
        Date(yyyy-mmm-dd hh:mm) :
                </div>
                <div class="col-md-4  meal_input">
        Description :
                    </div>
                    <div class="col-md-4  meal_input">
        Calories :
                        </div>
            </div>
        <div class="row">
            <div class="col-md-4  meal_input">
                <input id="dateTime"
                       type="text" name="dateTime"
                       value="<c:out value="${fn:replace(meal.dateTime,'T',' ')}" />" /> <br />
            </div>
            <div class="col-md-4  meal_input">
                <input id="description"
                       type="text" name="description"
                       value="<c:out value="${meal.description}" />" /> <br />
            </div>
            <div class="col-md-4  meal_input">
                <input id="calories"
                       type="text" name="calories"
                       value="<c:out value="${meal.calories}" />" /> <br />
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <p class="wrong_date"></p>
            </div>
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <p class="wrong_calories"></p>
            </div>
        </div>
        <input class="btn submit" type="submit" value="Submit" />
    </form>
</div>
<script>
    var main = function() {
        $('form').submit(function () {
            if($("#startDate").val() === "" && $("#endDate").val() === "")
            {
                var dateTime = $("#dateTime").val();
                var description = $("#description").val();
                var calories = $("#calories").val();
                if (dateTime === "" || description === "" || calories === "")
                    return false;
                if (!calories.match("^[0-9]+$")) {
                    $(".wrong_calories").text("Enter integer value");
                    $("#calories").focus();
                    return false;
                }
                else {
                    $(".wrong_calories").text("");
                }
                var dateTest = /^\d{4}-(0[1-9]|1[0-2])-([0-2]\d|3[01]) (0\d|1\d|2[0-3]):[0-5]\d$/;
                if (!dateTest.test(dateTime)) {
                    $(".wrong_date").text("Incorrect date format");
                    $("#dateTime").focus();
                    return false;
                }
                else {
                    $(".wrong_date").text("");
                }
            }
        });

    };
    $(document).ready(main);
</script>
</body>
</html>
