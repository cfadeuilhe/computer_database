<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"><c:out
					value="Application - Computer Database"></c:out></a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><c:out value="Computer name"></c:out></label>
								<c:if test="${computerWrong.name != null }">
									<input type="text" name="computerName" class="form-control"
										id="computerName" value=${computerWrong.name }>
								</c:if>
								<c:if test="${computerWrong.name == null }">
									<input type="text" name="computerName" class="form-control"
										id="computerName" placeholder="Computer name">
								</c:if>
							</div>
							<c:if test="${errors.containsKey('NoName') }">
								<p>
									<font color="red"><c:out value="The name is mandatory"></c:out></font>
								</p>
							</c:if>
							<c:if test="${errors.containsKey('NameTooShort') }">
								<p>
									<font color="red"><c:out
											value="The name must contain more than 2 characters"></c:out></font>
								</p>
							</c:if>
							<div class="form-group">
								<label for="introduced"><c:out value="Introduced date"></c:out></label>
								<input type="date" name="introduced" class="form-control"
									id="introduced" placeholder="Introduced date"
									value="${computerWrong.introducedDate }">
							</div>

							<c:if test="${errors.containsKey('DateIntroFormatError') }">
								<p>
									<font color="red"><c:out
											value="The introduced date format is wrong"></c:out></font>
								</p>
							</c:if>

							<div class="form-group">
								<label for="discontinued"><c:out
										value="Discontinued date"></c:out></label> <input type="date"
									name="discontinued" class="form-control" id="discontinued"
									placeholder="Discontinued date"
									value="${computerWrong.discontinuedDate }">
							</div>

							<c:if test="${errors.containsKey('DateIntroFormatError') }">
								<p>
									<font color="red"><c:out
											value="The discontinued date format is wrong"></c:out></font>
								</p>
							</c:if>

							<c:if test="${errors.containsKey('DateOrderError') }">
								<p>
									<font color="red"><c:out
											value="The introduced date must be before the discontinued date"></c:out></font>
								</p>
							</c:if>

							<div class="form-group">
								<label for="companyId"><c:out value="Company"></c:out></label> <select
									class="form-control" name="companyId" id="companyId">
									<option value=0><c:out value="--"></c:out></option>
									<c:if test="${computerWrong.companyId != null }">
										<c:if test="${computerWrong.companyId == item.id}">
											<c:forEach items="${companyList}" var="item">
												<option selected value=${item.id }>${item.name}</option>
											</c:forEach>
										</c:if>
										<c:if test="${computerWrong.companyId != item.id}">
											<c:forEach items="${companyList}" var="item">
												<option value=${item.id }>${item.name}</option>
											</c:forEach>
										</c:if>
									</c:if>
									<c:if test="${computerWrong.companyId == null }">
										<c:forEach items="${companyList}" var="item">
											<option value=${item.id }>${item.name}</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>