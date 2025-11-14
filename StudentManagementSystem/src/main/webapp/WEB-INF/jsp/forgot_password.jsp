<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Forgot Password</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style> .container { max-width: 520px; margin-top: 4rem; } </style>
</head>
<body>
<div class="container">
    <h3>Reset Password</h3>

    <c:if test="${not empty requestScope.error}">
        <div class="alert alert-danger">${requestScope.error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/forgot-password">
        <div class="form-group">
            <label>Registered Email</label>
            <input class="form-control" type="email" name="email" required />
        </div>
        <div class="form-group">
            <label>New Password</label>
            <input class="form-control" type="password" name="new_password" required />
        </div>
        <button class="btn btn-primary" type="submit">Reset Password</button>
        <a class="btn btn-link" href="${pageContext.request.contextPath}/login">Sign In</a>
    </form>
</div>
</body>
</html>
