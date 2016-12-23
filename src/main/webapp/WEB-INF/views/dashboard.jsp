<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="/computerdatabase/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="/computerdatabase/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="/computerdatabase/css/main.css" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${completeList.size()} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
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
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->

				<tbody id="results">
					<c:forEach items="${page.computerList}" var="item">

						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="0"></td>
							<td><a href="editComputer" onclick="$.fn.toggleEditMode();">${item.name}</a></td>
							<td>${item.introducedDate}</td>
							<td>${item.discontinuedDate}</td>
							<td>${item.company.name}</td>
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
					pageSize="${page.pageSize }"
					pageCount="${page.pageCount }">
				</myTag:pagination>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<myTag:link cssClass="btn btn-default" currentPage="${page.currentPage}"
					pageSize="10" target="dashboard" text="10"></myTag:link>
				<myTag:link cssClass="btn btn-default" currentPage="${page.currentPage}"
					pageSize="50" target="dashboard" text="50"></myTag:link>
				<myTag:link cssClass="btn btn-default" currentPage="${page.currentPage}"
					pageSize="100" target="dashboard" text="100"></myTag:link>
			</div>
		</div>
	</footer>

	<script src="/computerdatabase/js/jquery.min.js"></script>
	<script src="/computerdatabase/js/bootstrap.min.js"></script>
	<script src="/computerdatabase/js/dashboard.js"></script>

</body>
</html>