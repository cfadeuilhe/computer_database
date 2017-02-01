<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="springTags" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><c:out value="Computer Database"></c:out></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="/Cdb/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/Cdb/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="/Cdb/css/main.css" rel="stylesheet" media="screen">

<!-- Setting up messages for i18m -->
<springTags:message code="computer.title" var="tradTitle"/>
<springTags:message code="login.username" var="username" />
<springTags:message code="login.password" var="password" />
<springTags:message code="login.signin" var="signin" />

</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <c:out
					value="${tradTitle}"></c:out>
			</a> <span style="float: right; padding-right:10px; padding-top:7px;"> <a href="?language=en"><img border="10" alt="French"
					src="https://cdn2.iconfinder.com/data/icons/world-flag-icons/128/Flag_of_United_Kingdom.png"
					width="35" height="35"></a>|<a
				href="?language=fr"><img
					border="10" alt="English"
					src="https://cdn2.iconfinder.com/data/icons/world-flag-icons/128/Flag_of_France.png"
					width="35" height="35"></a></span>
		</div>
	</header>

	<section id="main">


		<div class="col-md-4 col-md-offset-4" style="text-align: center">

			<!-- Message when loggout -->
			<c:if test="${not empty logout}">
				<div class="alert alert-success">
					<springTags:message code="${logout}" text="Logout successful" />
				</div>
			</c:if>

			<br /> <br /> <i class="fa fa-user fa-5x"></i> <br /> <br />

			<!-- Loggin Form -->
			<form name='loginForm'
				action="<c:url value='j_spring_security_check' />" method='POST'>
				<div class="form-group">
					<input class="form-control" placeholder="${username}" type="text"
						name="username" /> <br /> <input class="form-control"
						placeholder="${password}" type="password" name="password" /> <br />

					<!-- Message when Wrong credentials -->
					<c:if test="${not empty error}">
						<div class="alert alert-danger">
							<springTags:message code="${error}" text="Error login" />
						</div>
					</c:if>
					<button type="submit" class="btn btn-success" name="submit">${signin}</button>
				</div>

				<!-- csrf -->
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</div>
	</section>

	<script src="/Cdb/js/jquery.min.js"></script>
	<script src="/Cdb/js/bootstrap.min.js"></script>
	<script src="/Cdb/js/dashboard.js"></script>

</body>
</html>