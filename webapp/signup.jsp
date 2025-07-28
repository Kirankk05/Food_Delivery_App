<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register - Food Delivery App</title>
    <link rel="stylesheet" type="text/css" href="css/auth.css">
</head>
<body>

<div class="auth-container">
    <h2>Create Account</h2>

    <% 
        String errorMsg = request.getParameter("error");
        if (errorMsg != null) {
    %>
        <div class="error"><%= errorMsg %></div>
    <% 
        } 
    %>

    <form action="RegisterServlet" method="post">
        <label>Username</label>
        <input type="text" name="username" required placeholder="Enter your name">

        <label>Email</label>
        <input type="email" name="email" required placeholder="Enter your email">

        <label>Phone Number</label>
        <input type="text" name="phone" required placeholder="Enter your phone number">

        <label>Password</label>
        <input type="password" name="password" required placeholder="Create a password">

        <input type="submit" value="Sign Up">
    </form>

    <div class="link-box">
        Already have an account? <a href="login.jsp">Sign In</a>
    </div>
</div>

</body>
</html>
