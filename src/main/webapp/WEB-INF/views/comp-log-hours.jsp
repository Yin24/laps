<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="STYLESHEET" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-ui.theme.css" />
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
<script>
	$(document).ready(function() {
		$(".datepicker").datepicker({
			dateFormat : "dd/mm/yy"
		});
		
		$(".hourText").each(function() {
			$(this).keyup(function(){
				updateTotalHours();
			});
			$(this).click(function(){
				if (this.value == "0") {
					this.value = "";
				} 
			});
		});
		updateTotalHours();
	});
	function updateTotalHours() {
		var sum = 0;
		$(".hourText").each(function() {
			if (!isNaN(this.value) && this.value.length != 0) {
				sum += parseFloat(this.value);
			}
		});
		$("#totalHours").html(sum);
	}
</script>

<h3>Log Overtime Hours <c:if test="${ edit eq true }">
		<span class="alert alert-warning">Editing only unclaimed</span>
	</c:if> </h3>
<form:form method="POST" modelAttribute="logHours"
	action="${pageContext.request.contextPath}/staff/comp/loghours.html">
<%-- 	Entries: ${ fn:length(logHours.overtimes) } --%>
	<p style="color:red; font-size:1em;">${ valError }</p>
<%-- 	<form:errors path="loggedHoursZeroOrNull" cssStyle="color: red;" /> --%>
	<table class="table">
		<thead>
			<tr>
				<th>Hours</th>
				<th>Date of overtime</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${logHours.overtimes}" var="overtime"
				varStatus="idx">
				<c:set var="fmtDate">
					<fmt:formatDate value="${ overtime.date }" pattern="dd/MM/yyyy" />
				</c:set>
				<input name="overtimes[${idx.index}].id" type="hidden"
					value="${overtime.id}" />
				<input name="overtimes[${idx.index}].approved" type="hidden"
					value="${overtime.approved}" />
				<input name="overtimes[${idx.index}].wasConfirmed" type="hidden"
					value="${overtime.wasConfirmed}" />
				<tr>
					<td><input name="overtimes[${idx.index}].loggedHours"
						class="hourText form-control" value="${overtime.loggedHours}" /></td>
					<td><input class="datepicker form-control"
						name="overtimes[${idx.index}].date" value="${ fmtDate }" /></td>
					<td>
					<c:choose>
						<c:when test="${ (idx.index + 1) eq fn:length(logHours.overtimes)
						&& fn:length(logHours.overtimes) > 1
						&& not edit eq true }">
							<input type="submit" name="delRow" value="Del Row" class="btn btn-warning" />
						</c:when>
						<c:when test="${ edit eq true }">
							<a href="${pageContext.request.contextPath}/staff/comp/delete/${ overtime.id }" class="btn btn-danger btn-sm">Delete</a>
						</c:when>
					</c:choose>
<%-- 					<c:if --%>
<%-- 							test="${ (idx.index + 1) eq fn:length(logHours.overtimes) --%>
<%-- 						&& fn:length(logHours.overtimes) > 1 --%>
<%-- 						&& not edit eq true }"> --%>
<%-- 						</c:if> --%>
						</td>
				</tr>
			</c:forEach>
			<tr>
				<th>Total: <span id="totalHours"></span></th>
				<th></th>
				<th></th>
			</tr>
		</tbody>
	</table>
	<c:if test="${ fn:length(logHours.overtimes) > 0 }">
		<input type="submit" name="save" value="Save" class="btn btn-success" />
	</c:if>
	
	<c:choose>
		<c:when test="${ not edit eq true }">
			<c:if test="${ fn:length(logHours.overtimes) < 5 }">
				<input type="submit" name="addRow" value="Add Row" class="btn btn-info" />
			</c:if>
		</c:when>
		<c:otherwise>
			<a href="${pageContext.request.contextPath}/staff/comp/history" class="btn btn-default">Cancel</a>
		</c:otherwise>
	</c:choose>
					
	<input type="hidden" name="edit" value="${ edit }" />

</form:form>