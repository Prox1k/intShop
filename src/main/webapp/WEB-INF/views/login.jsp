<%--
  Created by IntelliJ IDEA.
  User: Vadim
  Date: 15.01.2020
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h2>LOGIN</h2>
<div>${errorMsg}</div>
<form action="/intShop_war_exploded/login" method="post">
    <div class="container">
        <h1>Login</h1>
        <p>Please fill in this to sign in.</p>
        <hr>

        <label for="login"><b>Login</b></label>
        <input type="text" placeholder="Enter login" name="login" required>

        <label for="psw"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" required>
        </hr>
        <p></p>
        <button type="submit" class="loginbtn">Login</button>
    </div>

    <div class="container signin">
        <p>Don't have an account? <a href="/intShop_war_exploded/registration">Sign up</a>.</p>
    </div>
    <p><a class="selected" href="/intShop_war_exploded/servlet/mainMenu">BACH HOME</a></p>
</form>
</body>
</html>
