<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<style>
    .exceed {
        color: red;
    }

    .notexceed {
        color: green
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table border="1">
    <thead>
    <tr>
        <th>Description</th>
        <th>Date&Time</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr class="<c:out value="${meal.exceed ? 'exceed' : 'notexceed'}" />">
            <td><c:out value="${meal.description}"/></td>
            <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
            <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
            <td><c:out value="${meal.calories}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
