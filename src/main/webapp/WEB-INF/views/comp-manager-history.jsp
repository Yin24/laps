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

<h3>${ viewStaff.name }'s Overtime History</h3>

<table class="table">
	<thead>
		<tr>
			<th>ID</th>
			<th>Logged hrs</th>
			<th>Claimed hrs</th>
			<th>Date of overtime</th>
			<th>Approved?</th>
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
			</tr>
		</c:forEach>
		<tr>
			<th></th>
			<th>Total: ${ totalLoggedHours }</th>
			<th>Total: ${ totalClaimedHours }</th>
			<th></th>
			<th></th>
		</tr>
	</tbody>
</table>

<c:url var="editUnclaimed" value="/manager/comp/approve/${ viewStaff.staffId }">
<%-- 	<c:param name="edit" value="1" /> --%>
</c:url>

<a href="${ editUnclaimed }" class="btn btn-success">Go to Approved or Reject overtimes</a>

