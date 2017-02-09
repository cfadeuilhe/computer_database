<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="springForm"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="springTags" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<springTags:message code="computer.name" var="tradName"></springTags:message>
<springTags:message code="computer.date.placeholder"
	var="tradDatePlaceholder"></springTags:message>
<springTags:message code="computer.add" var="tradAdd"></springTags:message>

<body>
	<myTag:header tradTitle="${tradTitle}"></myTag:header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<springTags:message code="computer.addComputer" />
					</h1>
					<springForm:form action="addComputer" method="POST"
						id="addComputerForm" modelAttribute="computerDto">
						<fieldset>
							<div class="form-group">
								<springForm:label path="name">
									<springTags:message code="computer.name" />
								</springForm:label>
								<springForm:input type="text" path="name" class="form-control"
									id="computerName" placeholder="${tradName }" name="name" />
								<springForm:errors path="name" cssClass="alert alert-danger"
									element="div" />
							</div>
							<div class="form-group">
								<springForm:label path="introducedDate">
									<springTags:message code="computer.introducedDate" />
								</springForm:label>
								<springForm:input type="date" path="introducedDate"
									class="form-control" id="introduced"
									placeholder="${tradDatePlaceholder }" name="introduced" />
								<springForm:errors path="introducedDate"
									cssClass="alert alert-danger" element="div" />
							</div>

							<div class="form-group">
								<springForm:label path="discontinuedDate">
									<springTags:message code="computer.discontinuedDate" />
								</springForm:label>
								<springForm:input type="date" path="discontinuedDate"
									class="form-control" id="discontinued"
									placeholder="${tradDatePlaceholder}" name="discontinued"/>
							</div>

							<div class="form-group">
								<springForm:label path="companyId">
									<springTags:message code="computer.company" />
								</springForm:label>
								<springForm:select class="form-control" path="companyId"
									id="companyId">
<%--									<springForm:option value="0">--</springForm:option>	--%> 
									<springForm:options itemValue="id" itemLabel="name"
										items="${companyList}"></springForm:options>
								</springForm:select>
							</div>

						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="${tradAdd}" class="btn btn-primary">
							<springTags:message code="computer.or" />
							<a href="dashboard" class="btn btn-default"><springTags:message
									code="computer.cancel" /></a>
						</div>
					</springForm:form>
				</div>
			</div>
		</div>
	</section>

	<script src="/Cdb/js/addComputer.js"></script>

</body>
</html>