<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>
<jsp:useBean id="greeting" scope="request" type="java.lang.String"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
Hello, ${greeting}, Welcome to the All Users page!

Users:
<table border="1">
    <tr>
        <th>ID</th>
        <th>Login</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.userId}" />
            </td>
            <td>
                <c:out value="${user.login}" />
            </td>
            <td>
                <c:out value="${user.name}" />
            </td>
            <td>
                <c:out value="${user.surname}" />
            </td>
            <td>
                <a href="/intShop_war_exploded/servlet/deleteUser?user_id=${user.userId}" />DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p></p>
<button type="button" onclick="location.href ='/intShop_war_exploded/servlet/mainMenu'">BACK TO HOME</button>

</body>
</html>
