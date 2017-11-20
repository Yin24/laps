<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%-- <%@ page session="false" %> --%>

<link
	href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'
	rel='stylesheet'
	integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u'
	crossorigin='anonymous'>
<link href="../css/leonard-styles.css" rel="STYLESHEET" type="text/css">

<!-- <div class='container'> -->
<%-- <form:form method="POST" commandName="monthSelect"
		action="${pageContext.request.contextPath}/staff/movement.html">

		<form:select path="monthPicker">
			<c:forEach items="${ allMonthNames }" var="month" varStatus="idx">
				<form:option value="${ idx.index }">${ month }</form:option>
			</c:forEach>
		</form:select>

	</form:form> --%>

<form method="POST"
	action="${pageContext.request.contextPath}/staff/movement.html">

	<span>For month of </span> <select name="monthPicker"
		onchange="this.form.submit()">
		<c:forEach items="${ allMonthNames }" var="month" varStatus="idx">
			<c:choose>
				<c:when test="${ idx.index == picked }">
					<option value="${ idx.index }" selected>${ month }</option>
				</c:when>
				<c:otherwise>
					<option value="${ idx.index }">${ month }</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
	<span> ${ year }</span>
</form>

<%-- 	<select>
		<c:forEach items="${ allMonthNames }" var="month" varStatus="idx">
			<option value="${ idx.index }">${ month }</option>
		</c:forEach>
	</select>
 --%>

<span>Staff on leave:</span>
<ul>
	<c:forEach items="${ allLeave }" var="leave">
	
		<c:choose>
			<c:when test='${ leave.leaveType == 1 }'>
				<c:set var="typeStyle" value="success" />
			</c:when>
			<c:when test='${ leave.leaveType == 2 }'>
				<c:set var="typeStyle" value="warning" />
			</c:when>
			<c:otherwise>
				<c:set var="typeStyle" value="info" />
			</c:otherwise>
			
		</c:choose>
		<li>(LeaveID ${ leave.leaveId }) ${ leave.staffMember.name } <span class='label label-${ typeStyle }'>${ leave.leaveTypeModel.leaveName }</span></li>
	</c:forEach>
	<c:if test="${ allLeave.size() == 0 }">
		<li class='alert alert-info alert-auto'>No staff on leave</li>
	</c:if>
</ul>

<!-- </div> -->