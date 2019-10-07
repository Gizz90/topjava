<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table cellpadding="6" border="3">
    <thead>
    <tr>
        <th width="200">Date/Time</th>
        <th width="200">Description</th>
        <th width="200">Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${pageContext.request.getAttribute(\"meals\") != null}">
        <c:forEach var="meal" items="${pageContext.request.getAttribute(\"meals\")}">
            <tr style="color:${meal.excess ? "red" : "green"}">
                <td align="center">
                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" type="both"
                                   var="parsedDateTime"/>
                    <fmt:formatDate value="${parsedDateTime}" pattern="yyyy.MM.dd HH:mm"/>
                </td>
                <td align="center">${meal.description}</td>
                <td align="center">${meal.calories}</td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
</body>
</html>