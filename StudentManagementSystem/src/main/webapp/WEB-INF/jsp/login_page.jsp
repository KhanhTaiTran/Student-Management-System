<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
	<title>Login - Student Management System</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-MfXo3Y5mZb6YFf2e6Xq0sK5p3Q5p6G9K5e6m3Z6l9Q" crossorigin="anonymous">
	<style>
		body { background-color: #f4f6f9; }
		.login-card { max-width: 420px; margin: 6rem auto; }
	</style>
</head>
<body>
	<div class="container">
		<div class="card login-card shadow-sm">
			<div class="card-body">
				<h4 class="card-title text-center mb-4">Sign in</h4>

				<c:if test="${not empty requestScope.error}">
					<div class="alert alert-danger" role="alert">${requestScope.error}</div>
				</c:if>
				<c:if test="${not empty requestScope.success}">
					<div class="alert alert-success" role="alert">${requestScope.success}</div>
				</c:if>

				<form action="${pageContext.request.contextPath}/login" method="post" novalidate>
					<div class="form-group">
						<label for="username">Username</label>
						<input type="text" class="form-control" id="username" name="username" value="${param.username}" required autofocus>
					</div>

					<div class="form-group">
						<label for="password">Password</label>
						<input type="password" class="form-control" id="password" name="password" required>
					</div>

					<div class="form-group form-check">
						<input type="checkbox" class="form-check-input" id="remember" name="remember">
						<label class="form-check-label" for="remember">Remember me</label>
					</div>

					<button type="submit" class="btn btn-primary btn-block">Sign In</button>
				</form>

				<div class="mt-3 text-center small">
					<a href="${pageContext.request.contextPath}/signup">Create account</a> |
					<a href="${pageContext.request.contextPath}/forgot-password">Forgot password?</a>
				</div>

				<div class="mt-3 text-center small">
					<a href="${pageContext.request.contextPath}/">Back to Home</a>
				</div>
			</div>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-LtrjvnR4/Jqs6e9gk6a6e3Y6b6b6q3x2Z3Yy5q6t" crossorigin="anonymous"></script>
</body>
</html>

