<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>Sign Up</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style> .container { max-width: 520px; margin-top: 4rem; } </style>
</head>
<body>
<div class="container">
    <h3>Create an account</h3>

    <c:if test="${not empty requestScope.error}">
        <div class="alert alert-danger">${requestScope.error}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/signup">
        <div class="form-group">
            <label>Username</label>
            <input class="form-control" name="username" required />
        </div>
        <div class="form-group">
            <label>Email</label>
            <input class="form-control" type="email" name="email" required />
        </div>
        <div class="form-group">
            <label>Password</label>
            <input class="form-control" type="password" name="password" required />
        </div>
        <button class="btn btn-primary" type="submit">Sign Up</button>
        <a class="btn btn-link" href="${pageContext.request.contextPath}/login">Sign In</a>
    </form>
</div>
</body>
</html>
