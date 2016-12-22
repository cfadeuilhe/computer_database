<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>
<%@ attribute name="pageNumber" required="true" type="java.lang.Integer"
	description="Page number"%>
<%@ attribute name="nbElementsPerPage" required="true"
	type="java.lang.Integer" description="Number of computer per page"%>
<%@ attribute name="maxPageNumber" required="true"
	type="java.lang.Integer" description="Number of pages"%>


<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<ul class="pagination">


			<c:choose>
				<c:when test="${pageNumber > 1}">
					<li><myTag:link pageNumber="1" pageSize="${nbElementsPerPage}"
							target="dashboard" text="&larr;"></myTag:link></li>
					<li><myTag:link pageNumber="${pageNumber-1}"
							pageSize="${nbElementsPerPage}" target="dashboard" text="&laquo;"></myTag:link>
					</li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><myTag:link pageNumber="1"
							pageSize="${nbElementsPerPage}" target="dashboard" text="&larr;"></myTag:link>
					<li class="disabled"><myTag:link pageNumber="${pageNumber-1}"
							pageSize="${nbElementsPerPage}" target="dashboard" text="&laquo;"></myTag:link>
				</c:otherwise>
			</c:choose>



			<c:forEach begin="${pageNumber-1}" end="${pageNumber+1}" var="item"
				varStatus="i">

				<c:if test="${item >= 1}">
					<c:if test="${item <= maxPageNumber}">
						<li><myTag:link pageNumber="${item}"
								pageSize="${nbElementsPerPage}" target="dashboard"
								text="${item}"></myTag:link></li>
					</c:if>
				</c:if>
			</c:forEach>




			<c:choose>
				<c:when test="${pageNumber < maxPageNumber}">
					<li><myTag:link pageNumber="${pageNumber+1}"
							pageSize="${nbElementsPerPage}" target="dashboard" text="&raquo;"></myTag:link></li>

					<li><myTag:link pageNumber="${maxPageNumber}"
							pageSize="${nbElementsPerPage}" target="dashboard" text="&rarr;"></myTag:link></li>
				</c:when>
				<c:otherwise>
					<li class="disabled"><myTag:link pageNumber="${pageNumber+1}"
							pageSize="${nbElementsPerPage}" target="dashboard" text="&raquo;"></myTag:link>
					</li>

					<li class="disabled"><myTag:link pageNumber="${maxPageNumber}"
							pageSize="${nbElementsPerPage}" target="dashboard" text="&rarr;"></myTag:link></li>

				</c:otherwise>
			</c:choose>




		</ul>

		<div class="btn-group btn-group-sm pull-right" role="group">
			<myTag:link cssClass="btn btn-default" pageNumber="${pageNumber}"
				pageSize="10" target="dashboard" text="10"></myTag:link>
			<myTag:link cssClass="btn btn-default" pageNumber="${pageNumber}"
				pageSize="50" target="dashboard" text="50"></myTag:link>
			<myTag:link cssClass="btn btn-default" pageNumber="${pageNumber}"
				pageSize="100" target="dashboard" text="100"></myTag:link>
		</div>
	</div>
</footer>