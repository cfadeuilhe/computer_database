<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>
<%@ attribute name="currentPage" required="true"
	type="java.lang.Integer" description="Page number"%>
<%@ attribute name="pageSize" required="true" type="java.lang.Integer"
	description="Number of computer per page"%>
<%@ attribute name="searchValue" required="false"
	type="java.lang.String" description="User's search value"%>
<%@ attribute name="computerOrder" required="false"
	type="java.lang.String" description="Order to sort computers"%>
<%@ attribute name="pageCount" required="true" type="java.lang.Integer"
	description="Number of pages"%>
<%@ attribute name="language" required="false" type="java.lang.String"
	description="web page language"%>

<c:choose>
	<c:when test="${currentPage > 1}">
		<li><myTag:link cssClass="First" currentPage="1"
				searchValue="${page.search }" pageSize="${pageSize}"
				target="dashboard" text="&larr;" computerOrder="${computerOrder }"
				language="${language}"></myTag:link></li>
		<li><myTag:link cssClass="Previous"
				currentPage="${currentPage-1}" searchValue="${page.search }"
				pageSize="${pageSize}" target="dashboard" text="&laquo;"
				computerOrder="${computerOrder }" language="${language}"></myTag:link>
		</li>
	</c:when>
	<c:otherwise>
		<li class="disabled"><myTag:link cssClass="First" currentPage="1"
				searchValue="${page.search }" pageSize="${pageSize}"
				target="dashboard" text="&larr;" computerOrder="${computerOrder }"></myTag:link>
		<li class="disabled"><myTag:link cssClass="Previous"
				currentPage="${currentPage-1}" searchValue="${page.search }"
				pageSize="${pageSize}" target="dashboard" text="&laquo;"
				computerOrder="${computerOrder }"></myTag:link>
	</c:otherwise>
</c:choose>

<c:forEach begin="${currentPage-1}" end="${currentPage+1}" var="item"
	varStatus="i">

	<c:if test="${item >= 1}">
		<c:if test="${item <= pageCount}">
			<li>
				<myTag:link currentPage="${item}"
					searchValue="${page.search }" pageSize="${pageSize}"
					target="dashboard" text="${item}" computerOrder="${computerOrder }"
					language="${language}">
				</myTag:link>
			</li>
		</c:if>
	</c:if>
</c:forEach>

<c:choose>
	<c:when test="${currentPage < pageCount}">
		<li><myTag:link cssClass="Next" currentPage="${currentPage+1}"
				searchValue="${page.search }" pageSize="${pageSize}"
				target="dashboard" text="&raquo;" computerOrder="${computerOrder }"
				language="${language}"></myTag:link></li>

		<li><myTag:link cssClass="Last" currentPage="${pageCount}"
				searchValue="${page.search }" pageSize="${pageSize}"
				target="dashboard" text="&rarr;" computerOrder="${computerOrder }"
				language="${language}"></myTag:link></li>
	</c:when>
	<c:otherwise>
		<li class="disabled"><myTag:link cssClass="Next"
				currentPage="${currentPage+1}" searchValue="${page.search }"
				pageSize="${pageSize}" target="dashboard" text="&raquo;"
				computerOrder="${computerOrder }" language="${language}"></myTag:link>
		</li>

		<li class="disabled"><myTag:link cssClass="Last"
				currentPage="${pageCount}" searchValue="${page.search }"
				pageSize="${pageSize}" target="dashboard" text="&rarr;"
				computerOrder="${computerOrder }" language="${language}"></myTag:link></li>

	</c:otherwise>
</c:choose>
