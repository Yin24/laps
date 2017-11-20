<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link
	href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'
	rel='stylesheet'
	integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u'
	crossorigin='anonymous'>
<link href="../css/leonard-styles.css" rel="STYLESHEET" type="text/css">

<h3>Log Overtime History</h3>

<c:choose>
	<c:when test="${ hide eq true }">
		<c:set var="btnText">Show claimed</c:set>
		<c:set var="urlParam">0</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="btnText">Hide claimed</c:set>
		<c:set var="urlParam">1</c:set>
	</c:otherwise>
</c:choose>

<c:url var="toggleClaimed" value="history">
	<c:param name="hideClaimed" value="${ urlParam }" />
</c:url>

<a href="${ toggleClaimed }" class="btn btn-primary">${ btnText }</a>


<table class="table">
	<thead>
		<tr>
			<th>ID</th>
			<th>Logged hrs</th>
			<th>Claimed hrs</th>
			<th>Date of overtime</th>
			<th>Approved?</th>
			<th>Pending?</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${overtimes}" var="overtime" varStatus="idx">
			<c:set var="fmtDate">
				<fmt:formatDate value="${ overtime.date }" pattern="dd/MM/yyyy" />
			</c:set>
			<tr>
				<td>${ overtime.id }</td>
				<td>${ overtime.loggedHours }</td>
				<td>${ overtime.claimedHours }</td>
				<td>${ fmtDate }</td>
				<td>
				<c:choose>
					<c:when test="${ overtime.approved eq true }">
						Approved
					</c:when>
					<c:otherwise>
						Not approved
					</c:otherwise>
				</c:choose>
				</td>
				<td>
				<c:choose>
					<c:when test="${ overtime.wasConfirmed eq false }">
						Pending
					</c:when>
				</c:choose>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<th></th>
			<th>Total: ${ totalLoggedHours }</th>
			<th>Total: ${ totalClaimedHours }</th>
			<th></th>
			<th></th>
			<th></th>
		</tr>
	</tbody>
</table>

<c:url var="editUnclaimed" value="loghours">
	<c:param name="edit" value="1" />
</c:url>

<a href="${ editUnclaimed }" class="btn btn-warning">Edit unclaimed
	overtimes</a>

