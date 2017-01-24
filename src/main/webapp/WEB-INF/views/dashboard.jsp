<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>
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
</head>

<!-- Spring translation references -->
<springTags:message code="computer.title" var="title"></springTags:message>
<springTags:message code="computer.computersFound" var="computersFound"></springTags:message>
<springTags:message code="computer.searchName" var="searchName"></springTags:message>
<springTags:message code="computer.filterByName" var="filterByName"></springTags:message>
<springTags:message code="computer.addComputer" var="addComputer"></springTags:message>
<springTags:message code="computer.editComputer" var="editComputer"></springTags:message>
<springTags:message code="computer.edit" var="edit"></springTags:message>
<springTags:message code="computer.name" var="name"></springTags:message>
<springTags:message code="computer.introducedDate" var="introducedDate"></springTags:message>
<springTags:message code="computer.discontinuedDate" var="discontinuedDate"></springTags:message>
<springTags:message code="computer.company" var="company"></springTags:message>
<springTags:message code="computer.add" var="add"></springTags:message>
<springTags:message code="computer.or" var="or"></springTags:message>
<springTags:message code="computer.cancel" var="cancel"></springTags:message>

<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <c:out value="${title}"></c:out>
			</a>
			<span style="float: right"> <a href="?language=en">English</a>|<a href="?language=fr">Français</a></span>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${count} ${computersFound }"></c:out>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="${searchName }"
							value="${page.search }" /> <input type="submit"
							id="searchsubmit" value="${filterByName }"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">
						<c:out value="${addComputer }"></c:out>
					</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><c:out value="${edit }"></c:out></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="deleteComputer" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<input type="hidden" name="order" value="before" id="order" />
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
						<th class="orderBy" id="computerName"><c:out value="${name }"></c:out><a
							href="#" id="orderBy" onclick="$.fn.orderBy('computerName');">
								<i id="orderArrow" class="fa fa-arrow-down"></i>
						</a></th>
						<th><c:out value="${introducedDate }"></c:out></th>
						<!-- Table header for Discontinued Date -->
						<th><c:out value="${discontinuedDate }"></c:out></th>
						<!-- Table header for Company -->
						<th><c:out value="${company }"></c:out></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->

				<tbody id="results">
					<c:forEach items="${page.computerList}" var="item">

						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value=${item.id }></td>
							<td><a
								href="editComputer?id=${item.id }&computerName=${item.name }&introduced=${item.introducedDate}&discontinued=${item.discontinuedDate}&companyId=${item.company.id}&page=${page.currentPage}"
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
					pageCount="${page.pageCount }">
				</myTag:pagination>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<myTag:link cssClass="btn btn-default"
					currentPage="${page.currentPage}" searchValue="${page.search }"
					pageSize="10" target="dashboard" text="10"></myTag:link>
				<myTag:link cssClass="btn btn-default"
					currentPage="${page.currentPage}" searchValue="${page.search }"
					pageSize="50" target="dashboard" text="50"></myTag:link>
				<myTag:link cssClass="btn btn-default"
					currentPage="${page.currentPage}" searchValue="${page.search }"
					pageSize="100" target="dashboard" text="100"></myTag:link>
			</div>
		</div>
	</footer>

	<script src="/Cdb/js/jquery.min.js"></script>
	<script src="/Cdb/js/bootstrap.min.js"></script>
	<script src="/Cdb/js/dashboard.js"></script>

</body>
</html>
