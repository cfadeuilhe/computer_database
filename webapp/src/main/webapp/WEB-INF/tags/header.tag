<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="tradTitle" required="true" type="java.lang.String"
	description="Title for the header"%>


<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a href="javascript:formSubmit()" class="pull-right" style="padding-top: 7px;">
			<img border="10" alt="French"
			src="http://www.aratimegarecharge.in/common/bootstrap/img/Logout.png"
			width="33" height="35">
		</a> <a class="navbar-brand" href="dashboard"> <c:out
				value="${tradTitle}"></c:out>
		</a> <span style="float: right; padding-right: 30px; padding-top: 7px;">
			<a href="?language=en"><img border="10" alt="French"
				src="https://cdn2.iconfinder.com/data/icons/world-flag-icons/128/Flag_of_United_Kingdom.png"
				width="35" height="35"></a>|<a href="?language=fr"><img
				border="10" alt="English"
				src="https://cdn2.iconfinder.com/data/icons/world-flag-icons/128/Flag_of_France.png"
				width="35" height="35"></a>
		</span>
	</div>
	
	<form action="j_spring_security_logout" method="post" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>

			<script>
				function formSubmit() {
					document.getElementById("logoutForm").submit();
				}
			</script>
			
</header>
