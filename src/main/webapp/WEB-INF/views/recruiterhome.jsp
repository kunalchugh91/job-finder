<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recruiter Home</title>
</head>
<body>
<c:out value="Welcome ${user.name}"></c:out><br/><br/>
<a href="logout.htm">Logout</a><br/><br/>


<a href="recprofile.htm">My profile</a><br/>
<a href="createjob.htm">Create Job</a><br/>
<br/>

<h3>My Jobs</h3><br/><br/>

<table border="1">

<c:forEach var="j" items="${joblist}">
<tr>
<td>${j.jobID}</td>
<td>${j.jobTitle}</td>
<td>${j.location}</td>
<td><a href="viewcandidates.htm?jobID=${j.jobID}">View Candidates</a></td>
</tr>
</c:forEach>

</table>





</body>
</html>