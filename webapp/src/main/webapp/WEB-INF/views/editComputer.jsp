<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="springForm"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="springTags" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><c:out value="Computer Database"></c:out></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="/Cdb/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="/Cdb/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="/Cdb/css/main.css" rel="stylesheet" media="screen">
</head>

<!-- Spring translation references -->
<springTags:message code="computer.title" var="tradTitle"></springTags:message>
<springTags:message code="computer.editComputer" var="tradEditComputer"></springTags:message>
<springTags:message code="computer.edit" var="tradEdit"></springTags:message>
<springTags:message code="computer.name" var="tradName"></springTags:message>
<springTags:message code="computer.introducedDate" var="tradIntroducedDate"></springTags:message>
<springTags:message code="computer.discontinuedDate" var="tradDiscontinuedDate"></springTags:message>
<springTags:message code="computer.date.placeholder" var="tradDatePlaceholder"></springTags:message>
<springTags:message code="computer.company" var="tradCompany"></springTags:message>
<springTags:message code="computer.add" var="tradAdd"></springTags:message>
<springTags:message code="computer.or" var="tradOrelse"></springTags:message>
<springTags:message code="computer.cancel" var="tradCancel"></springTags:message>

<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a href="login?logout" class="pull-right" style="padding-top: 7px;"> <span
				class="glyphicon glyphicon-log-out btn btn-danger"></span>
			</a> 
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
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						<c:out value="id: ${computerToEdit.id }"></c:out>
					</div>
					<h1><c:out value="${tradEditComputer }"></c:out></h1>
					<springForm:form action="editComputer" method="POST"
						id="editComputer" modelAttribute="computerDto">
						<input type="hidden" name="id" value="${computerToEdit.id }"
							id="id" /> <input type="hidden" name="page"
							value="${pageNumber }" id="page" />
						<fieldset>
							<div class="form-group">
								<springForm:label path="name">
									<c:out value="${tradName}"></c:out>
								</springForm:label>
								<springForm:input type="text" path="name" class="form-control"
									id="name" placeholder="${tradName}" />
								<springForm:errors path="name" cssClass="alert alert-danger"
									element="div" />
							</div>

							<div class="form-group">
								<springForm:label path="introducedDate">
									<c:out value="${tradIntroducedDate}"></c:out>
								</springForm:label>
								<springForm:input type="date" path="introducedDate"
									class="form-control" id="introduced"
									placeholder="${tradDatePlaceholder}" />
								<springForm:errors path="introducedDate" cssClass="alert alert-danger"
									element="div" />
							</div>
							
							<div class="form-group">
								<springForm:label path="discontinuedDate">
									<c:out value="${discontinuedDate}"></c:out>
								</springForm:label>
								<springForm:input type="date" path="discontinuedDate"
									class="form-control" id="discontinued"
									placeholder="${tradDatePlaceholder}" />
							</div>

							<div class="form-group">
								<label for="companyId"><c:out value="${tradCompany}"></c:out></label> 
								<springForm:select
									class="form-control" path="companyId" id="companyId">
									<springForm:option value="0">--</springForm:option>
									<springForm:options itemValue="id" itemLabel = "name" items="${companyList}"></springForm:options>
								</springForm:select>
								
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="${tradEdit}" class="btn btn-primary">
							<c:out value="${tradOrelse}"></c:out> <a href="dashboard" class="btn btn-default"><c:out value="${tradCancel}"></c:out></a>
						</div>
					</springForm:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>