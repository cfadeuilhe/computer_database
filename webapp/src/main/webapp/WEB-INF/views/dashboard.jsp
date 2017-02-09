<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="springForm"
	uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="springTags" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="secTags"
	uri="http://www.springframework.org/security/tags"%>
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
</head>

<!-- Spring translation references -->
<springTags:message code="computer.title" var="tradTitle"></springTags:message>
<springTags:message code="computer.computersFound"
	var="tradComputersFound"></springTags:message>
<springTags:message code="computer.searchName" var="tradSearchName" />
<springTags:message code="computer.filterByName" var="tradFilterByName" />
<springTags:message code="computer.addComputer" var="tradAddComputer" />
<springTags:message code="computer.editComputer" var="tradEditComputer" />
<springTags:message code="computer.edit" var="tradEdit" />
<springTags:message code="computer.name" var="tradName" />
<springTags:message code="computer.introducedDate"
	var="tradIntroducedDate" />
<springTags:message code="computer.discontinuedDate"
	var="tradDiscontinuedDate" />
<springTags:message code="computer.company" var="tradCompany" />
<springTags:message code="computer.add" var="tradAdd" />
<springTags:message code="computer.cancel" var="tradCancel" />

<springTags:message code="computer.delete" var="tradDel" />
<springTags:message code="computer.view" var="tradView" />

<c:set value="${pageContext.response.locale}" var="localeCode" />

<script>
	var del = '${tradDel}'
</script>
<script>
	var view = '${tradView}'
</script>
<body>
	<myTag:header tradTitle="${tradTitle}"></myTag:header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${count} ${tradComputersFound }"></c:out>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="" method="GET" class="form-inline">

						<input type="hidden" name="page" value="${page.currentPage }" />
						<input type="hidden" name="limit" value="${page.pageSize }" /> <input
							type="search" id="searchbox" name="search" class="form-control"
							placeholder="${tradSearchName }" value="${page.search }" /> <input
							type="hidden" name="order" value="${page.order }" /> <input
							type="submit" id="searchsubmit" value="${tradFilterByName }"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<secTags:authorize access="hasRole('Admin')">
						<a class="btn btn-danger" id="editComputer" href="#"
							onclick="$.fn.toggleEditMode();"><c:out value="${tradDel }"></c:out></a>
					</secTags:authorize>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">
						<c:out value="${tradAddComputer }"></c:out>
					</a>
					<springForm:form id="deleteForm" action="deleteComputer"
						method="POST">
						<input type="hidden" name="selection" value="" />
					</springForm:form>
				</div>

			</div>
		</div>

		<form id="orderForm" action="dashboard" method="GET">
			<input type="hidden" name="order" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<input type="hidden" name="orderType" value="before" id="orderType" />
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o"></i>
							</a>
						</span></th>
						<th class="orderBy" id="computerName"><c:out
								value="${tradName }"></c:out>
							<div style="float: right">

								<myTag:link cssClass="fa fa-arrow-down"
									currentPage="${page.currentPage}" searchValue="${page.search }"
									pageSize="${page.pageSize }" target="dashboard"
									computerOrder="name.asc" language="${localeCode }"></myTag:link>

								<myTag:link cssClass="fa fa-arrow-up"
									currentPage="${page.currentPage}" searchValue="${page.search }"
									pageSize="${page.pageSize }" target="dashboard"
									computerOrder="name.desc" language="${localeCode }"></myTag:link>

							</div></th>
						<th class="orderBy" id="introDateOrder"><c:out
								value="${tradIntroducedDate }"></c:out>
							<div style="float: right">

								<myTag:link cssClass="fa fa-arrow-down"
									currentPage="${page.currentPage}" searchValue="${page.search }"
									pageSize="${page.pageSize }" target="dashboard"
									computerOrder="introduced.asc" language="${localeCode }"></myTag:link>
								<myTag:link cssClass="fa fa-arrow-up"
									currentPage="${page.currentPage}" searchValue="${page.search }"
									pageSize="${page.pageSize }" target="dashboard"
									computerOrder="introduced.desc" language="${localeCode }"></myTag:link>

							</div></th>
						<!-- Table header for Discontinued Date -->
						<th class="orderBy" id="discoDateOrder"><c:out
								value="${tradDiscontinuedDate }"></c:out>
							<div style="float: right">

								<myTag:link cssClass="fa fa-arrow-down"
									currentPage="${page.currentPage}" searchValue="${page.search }"
									pageSize="${page.pageSize }" target="dashboard"
									computerOrder="discontinued.asc" language="${localeCode }"></myTag:link>
								<myTag:link cssClass="fa fa-arrow-up"
									currentPage="${page.currentPage}" searchValue="${page.search }"
									pageSize="${page.pageSize }" target="dashboard"
									computerOrder="discontinued.desc" language="${localeCode }"></myTag:link>

							</div></th>
						<!-- Table header for Company -->
						<th class="orderBy" id="companyNameOrder"><c:out
								value="${tradCompany }"></c:out>
							<div style="float: right">

								<myTag:link cssClass="fa fa-arrow-down"
									currentPage="${page.currentPage}" searchValue="${page.search }"
									pageSize="${page.pageSize }" target="dashboard"
									computerOrder="company.asc" language="${localeCode }"></myTag:link>
								<myTag:link cssClass="fa fa-arrow-up"
									currentPage="${page.currentPage}" searchValue="${page.search }"
									pageSize="${page.pageSize }" target="dashboard"
									computerOrder="company.desc" language="${localeCode }"></myTag:link>

							</div></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->

				<tbody id="results">
					<c:forEach items="${page.computerList}" var="item">

						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value=${item.id }></td>
							<td><a
								href="editComputer?id=${item.id }&computerName=${item.name }&
								introduced=${item.introducedDate}&discontinued=${item.discontinuedDate}&
								companyId=${item.company.id}&page=${page.currentPage}&limit=${page.pageSize}&
								search=${page.search}&order=${page.order}"
								onclick="">${item.name}</a></td>
							<td><c:out value="${item.introducedDate}"></c:out></td>
							<td><c:out value="${item.discontinuedDate}"></c:out></td>
							<td><c:out value="${item.company.name}"></c:out></td>
						</tr>

					</c:forEach>
				</tbody>

			</table>
		</div>
	</section>


	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<myTag:pagination currentPage="${page.currentPage }"
					pageSize="${page.pageSize }" searchValue="${page.search }"
					pageCount="${page.pageCount }" computerOrder="${page.order }"
					language="${localeCode }">
				</myTag:pagination>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<c:if test="${page.pageSize == 10 }">
					<springTags:message code="btn.primary" var="Tenclass" />
				</c:if>
				<c:if test="${page.pageSize != 10 }">
					<springTags:message code="btn.default" var="Tenclass" />
				</c:if>
				<c:if test="${page.pageSize == 50 }">
					<springTags:message code="btn.primary" var="Fiftyclass" />
				</c:if>
				<c:if test="${page.pageSize != 50 }">
					<springTags:message code="btn.default" var="Fiftyclass" />
				</c:if>
				<c:if test="${page.pageSize == 100 }">
					<springTags:message code="btn.primary" var="Hundredclass" />
				</c:if>
				<c:if test="${page.pageSize != 100 }">
					<springTags:message code="btn.default" var="Hundredclass" />
				</c:if>
				
				<myTag:link cssClass="${Tenclass }"
					currentPage="${page.currentPage}" searchValue="${page.search }"
					pageSize="10" target="dashboard" text="10"
					computerOrder="${page.order }" language="${localeCode }"></myTag:link>
				<myTag:link cssClass="${Fiftyclass }"
					currentPage="${page.currentPage}" searchValue="${page.search }"
					pageSize="50" target="dashboard" text="50"
					computerOrder="${page.order }" language="${localeCode }"></myTag:link>
				<myTag:link cssClass="${Hundredclass }"
					currentPage="${page.currentPage}" searchValue="${page.search }"
					pageSize="100" target="dashboard" text="100"
					computerOrder="${page.order }" language="${localeCode }"></myTag:link>
			</div>
		</div>
	</footer>

	<script type="text/javascript" src="/Cdb/js/jquery.min.js"></script>
	<script type="text/javascript" src="/Cdb/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/Cdb/js/dashboard.js"></script>
	<script type="text/javascript" src="js/jquery.i18n.properties-min.js"></script>


</body>
</html>
