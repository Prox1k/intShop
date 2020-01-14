<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Item adding</title>
</head>
<body>

<h2>ITEM ADDING</h2>
<form action="/intShop_war_exploded/servlet/addItem" method="post">

    <label for="item_name"><b>Item name</b></label>
    <input type="text" name="item_name" required>
    <p></p>
    <label for="item_price"><b>Item price</b></label>
    <input type="text" name="item_price" required>

    <p><button type="submit" class="additembtn">Add item</button></p>
    <button type="button" onclick="location.href ='/intShop_war_exploded/servlet/mainMenu'">BACK TO HOME</button>
</form>
</body>
</html>
