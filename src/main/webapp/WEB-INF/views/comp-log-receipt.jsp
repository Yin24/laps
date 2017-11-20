<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h3>Log Overtime Receipt</h3>

<c:choose>
	<c:when test="${ wasEdit eq true }">
		<span>Success. Updated ${ totalHours } hours of overtime.</span>
	</c:when>
	<c:otherwise>
		<span>Success. Logged ${ totalHours } hours of overtime.</span>
	</c:otherwise>
</c:choose>