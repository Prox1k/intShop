<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="orders" scope="request" type="java.util.List<mate.academy.internetshop.model.Order>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
</head>
<body>
<h2>ALL ORDERS LIST:</h2>
<table border="1">
    <tr>
        <th>Order ID</th>
        <th>Items</th>
        <th>DELETE ORDER</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.orderId}" />
            </td>
            <td>
                <c:out value="${order.items}" />
            </td>
            <td>
                <a href="/intShop_war_exploded/servlet/deleteOrder?order_id=${order.orderId}" />DELETE ORDER</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p></p>
<button type="button" onclick="location.href ='/intShop_war_exploded/servlet/mainMenu'">BACK TO HOME</button>
</body>
</html>
