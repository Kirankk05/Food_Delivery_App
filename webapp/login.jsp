<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign In - Food Delivery App</title>
    <link rel="stylesheet" type="text/css" href="css/auth.css">
</head>
<body>

<div class="auth-container">
    <h2>Sign In</h2>

    <% 
        String errorMsg = (String) request.getAttribute("errorMessage");
        if (errorMsg != null) {
    %>
        <div class="error"><%= errorMsg %></div>
    <% 
        } 
    %>

    <form action="login" method="post">
        <label>Email</label>
        <input type="email" name="email" required placeholder="Enter your email">

        <label>Password</label>
        <input type="password" name="password" required placeholder="Enter your password">

        <input type="submit" value="Sign In">
    </form>

    <div class="link-box">
        Don't have an account? <a href="signup.jsp">Sign Up</a>
    </div>
</div>

</body>
</html>
