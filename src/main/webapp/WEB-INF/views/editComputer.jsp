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
<springTags:message code="computer.title" var="title"></springTags:message>
<springTags:message code="computer.editComputer" var="editComputer"></springTags:message>
<springTags:message code="computer.edit" var="edit"></springTags:message>
<springTags:message code="computer.name" var="name"></springTags:message>
<springTags:message code="computer.introducedDate" var="introducedDate"></springTags:message>
<springTags:message code="computer.discontinuedDate" var="discontinuedDate"></springTags:message>
<springTags:message code="computer.company" var="company"></springTags:message>
<springTags:message code="computer.add" var="add"></springTags:message>
<springTags:message code="computer.or" var="orelse"></springTags:message>
<springTags:message code="computer.cancel" var="cancel"></springTags:message>

<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><c:out value="${title}"></c:out></a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						<c:out value="id: ${computerToEdit.id }"></c:out>
					</div>
					<h1><c:out value="${editComputer }"></c:out></h1>
					<springForm:form action="editComputer" method="POST"
						id="editComputer" modelAttribute="computerDto">
						<input type="hidden" name="id" value="${computerToEdit.id }"
							id="id" /> <input type="hidden" name="page"
							value="${pageNumber }" id="page" />
						<fieldset>
							<div class="form-group">
								<springForm:label path="name">
									<c:out value="${name}"></c:out>
								</springForm:label>
								<springForm:input type="text" path="name" class="form-control"
									id="computerName" placeholder="${name}" />
								<springForm:errors path="name" cssClass="alert alert-danger"
									element="div" />
							</div>

							<div class="form-group">
								<springForm:label path="introducedDate">
									<c:out value="${introducedDate}"></c:out>
								</springForm:label>
								<springForm:input type="date" path="introducedDate"
									class="form-control" id="introduced"
									placeholder="${introducedDate}" />
								<springForm:errors path="introducedDate" cssClass="alert alert-danger"
									element="div" />
							</div>
							
							<div class="form-group">
								<springForm:label path="discontinuedDate">
									<c:out value="${discontinuedDate}"></c:out>
								</springForm:label>
								<springForm:input type="date" path="discontinuedDate"
									class="form-control" id="discontinued"
									placeholder="${discontinuedDate}" />
							</div>

							<div class="form-group">
								<label for="companyId"><c:out value="${company}"></c:out></label> 
								<springForm:select
									class="form-control" path="companyId" id="companyId">
									<springForm:option value="0">--</springForm:option>
									<springForm:options itemValue="id" itemLabel = "name" items="${companyList}"></springForm:options>
								</springForm:select>
								
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="${edit}" class="btn btn-primary">
							<c:out value="${orelse}"></c:out> <a href="dashboard" class="btn btn-default"><c:out value="${cancel}"></c:out></a>
						</div>
					</springForm:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>