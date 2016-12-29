<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>
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
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <c:out
					value="Application - Computer
				Database">
				</c:out>
			</a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${count} Computers found"></c:out>
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name"
							value="${page.search }" /> <input type="submit"
							id="searchsubmit" value="Filter by name" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><c:out
							value="Add Computer"></c:out></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><c:out
							value="Edit"></c:out></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href="#&orderby=yes" onclick=""><c:out
									value="Computer Name"></c:out></a></th>
						<th><c:out value="Introduced date"></c:out></th>
						<!-- Table header for Discontinued Date -->
						<th><c:out value="Discontinued date"></c:out></th>
						<!-- Table header for Company -->
						<th><c:out value="Company"></c:out></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->

				<tbody id="results">
					<c:forEach items="${page.computerList}" var="item">

						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value=${item.id }></td>
							<td><a
								href="editComputer?id=${item.id }&computerName=${item.name }&introduced=${item.introducedDate}&discontinued=${item.discontinuedDate}&companyId=${item.company.id}"
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