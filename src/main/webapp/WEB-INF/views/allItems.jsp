<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All items</title>
</head>
<body>
<h2>ALL ITEMS LIST:</h2>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>ADD TO BUCKET</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="/intShop_war_exploded/servlet/addItemToBucket?item_id=${item.itemId}" />ADD</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p></p>
<button type="button" onclick="location.href ='/intShop_war_exploded/servlet/mainMenu'">BACK TO HOME</button>

</body>
</html>
