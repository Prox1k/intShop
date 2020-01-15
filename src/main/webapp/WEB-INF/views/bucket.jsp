<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="bucket" scope="request" type="mate.academy.internetshop.model.Bucket"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bucket</title>
</head>
<body>
<h2>ITEMS IN YOUR BUCKET:</h2>
<table border="1">
    <tr>
        <th>Item Name</th>
        <th>Item Price</th>
        <th>DELETE ITEM</th>
    </tr>
    <c:forEach var="item" items="${bucket.items}">
        <tr>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
            <a href="/intShop_war_exploded/servlet/deleteItem?item_id=${item.itemId}" />DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p></p>
<button type="button" onclick="location.href ='/intShop_war_exploded/servlet/complete'">CHECKOUT</button>
<p></p>
<button type="button" onclick="location.href ='/intShop_war_exploded/servlet/mainMenu'">BACK TO HOME</button>
</body>
</html>
