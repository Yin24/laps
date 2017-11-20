<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Leave Type</title>
</head>
<body>
<form action="ManageLeave" method="POST">

<center> 

<h1>Manage Leave Type</h1>

<h2>
            <a href="/new">Add New Leave Type</a>
            &nbsp;&nbsp;&nbsp;
            <a href="/list">List All Books</a>
             
        </h2>

</center>

<div align="center">
	<table border="1" cellpadding="3">
	<caption><h2>List of Leave Types</h2></caption>
	<tr>
	<th>Leave Type</th>
	<th>Classification</th>
	</tr>
		<c:forEach items="${leave_type}" var="leave_type">
			<tr>
			    <td><c:out value="${leave_type}" /></td>
			    <td><c:out value="${leave_name}" /></td>
			    <td>
			    <a href="/edit?leave_type=<c:out value='${leave_type.leave_type}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/delete?leave_type=<c:out value='${leave_type.leave_type}' />">Delete</a>   
                        </td>
			</tr>
		</c:forEach>
		</table>
	</div>
	</form>
</body>
</html>