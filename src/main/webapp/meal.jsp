<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h4>Edit meal</h4>
<hr>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form action="meals" method="post">
    <input type="hidden" name="id" value="${meal.id}">
    <label for="dateTime">DateTime</label>
    <input type="datetime-local" value="${meal.dateTime}" name="dateTime" id="dateTime"><br>
    <label for="description">Description</label>
    <input type="text" value="${meal.description}" name="description" id="description"><br>
    <label for="calories">Calories</label>
    <input type="number" value="${meal.calories}" name="calories" id="calories"><br>

    <button type="submit">Submit</button>
    <button type="button" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
