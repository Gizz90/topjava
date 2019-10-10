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
<table cellpadding="2" cols="3" border="3">
    <thead>
    <tr>
        <th width="200">Date / Time</th>
        <th width="100">Description</th>
        <th width="100">Calories</th>
        <th colspan="2">Action</th>
    </tr>
    </thead>
    <tbody>

    <c:if test="${meals != null}">
        <c:forEach var="meal" items="${meals}">
            <tr style="color:${meal.excess ? "red" : "green"}">
                <td align="center">${dtf.format(meal.dateTime)}</td>
                <td align="center">${meal.description}</td>
                <td align="center">${meal.calories}</td>
                <td>
                    <form action="meals" method="get">
                        <input hidden name="action" value="edit">
                        <input hidden name="id" value="${meal.id}">
                        <input type="submit" value="EDIT">
                    </form>
                </td>
                <td valign="center">
                    <form action="meals" method="get">
                        <input hidden name="action" value="delete">
                        <input hidden name="id" value="${meal.id}">
                        <input type="submit" value="DELETE">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
<form action=meals method=get>
    <input hidden name="action" value="add">
    <input type=submit value=ADD>
</form>
</body>
</html>