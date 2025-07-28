<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Invalidate the current session (JSP already has 'session')
    if (session != null) {
        session.invalidate();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Logged Out - Food Delivery App</title>
    <link rel="stylesheet" type="text/css" href="css/auth.css">
</head>
<body>

<div class="auth-container">
    <h2>You have been logged out</h2>
    <p>Thank you for using the Food Delivery App.</p>

    <div class="link-box">
        <a href="login.jsp">Sign In Again</a> |
        <a href="home.jsp">Go to Home</a>
    </div>
</div>

</body>
</html>
