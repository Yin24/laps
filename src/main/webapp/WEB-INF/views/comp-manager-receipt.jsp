<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3>Log Overtime Receipt</h3>

		<span>Success. Confirmed ${ totalApproved } hours of overtime.</span>
		<span>Success. Rejected ${ totalRejected } hours of overtime.</span>
<c:choose>
	<c:when test="${ wasEdit eq true }">
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>