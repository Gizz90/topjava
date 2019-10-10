<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${meal == null ? 'Add' : "Edit"}</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>

<h2>${meal == null ? 'Add' : "Edit"}</h2>
<form method="post" action="meals">
    <table cellpadding="2" cols="3" border="3">
        <thead>
        <tr>
            <th width="200">Date / Time</th>
            <th width="100">Description</th>
            <th width="100">Calories</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td align="center" hidden>${meal.id}
                <input name="id" type="hidden" value="${meal.id}">
            </td>
            <td align="center">
                <input name="date" value="${meal.dateTime.toLocalDate()}" type="date">
                <input name="time" value="${meal.dateTime.toLocalTime()}" type="time">
            </td>
            <td align="center">
                <input name="description" value="${meal.description}">
            </td>
            <td align="center">
                <input name="calories" value="${meal.calories}" type="number">
            </td>
            <td><input type="submit" value="CONFIRM"></td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>