<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="target" required="true" type="java.lang.String" description="URL of the page we want to go to" %>
<%@ attribute name="text" required="false" type="java.lang.String" description="text of the link" %>
<%@ attribute name="pageSize" required="true" type="java.lang.String" description="Number of computer shown per page" %>
<%@ attribute name="searchValue" required="false" type="java.lang.String" description="User's search value" %>
<%@ attribute name="computerOrder" required="false" type="java.lang.String" description="Order to sort computers" %>
<%@ attribute name="currentPage" required="true" type="java.lang.String" description="Number of the page we want to go to" %>
<%@ attribute name="cssClass" required="false" type="java.lang.String" description="The classes that we want to add" %>
<%@ attribute name="language" required="false" type="java.lang.String" description="Locale language" %>

<c:choose>
	<c:when test="${target != '#'}">
		<a class="${cssClass}" href="${target}?page=${currentPage}&limit=${pageSize}&search=${searchValue}&order=${computerOrder }&language=${language}">${text}</a>
	</c:when>
	<c:otherwise>
		<a class="${cssClass}" href="#">${text}</a>
	</c:otherwise>
</c:choose>