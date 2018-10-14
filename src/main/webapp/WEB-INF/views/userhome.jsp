<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Home</title>
</head>
<body>
<c:out value="Welcome ${user.name}"></c:out><br/><br/>
<a href="logout.htm">Logout</a><br/><br/>

<a href="profile.htm">Update My Profile</a><br/><br/>
<a href="searchjob.htm">Search Jobs</a><br/><br/>

<h2>My Applied Jobs</h2><br/><br/>
<table border="1">
<c:forEach items="${jobapplications}" var="ja">
<tr>
<td>${ja.jobTitle}</td>
<td>${ja.company}</td>
<td>${ja.applicationDate}</td>
<td>${ja.decisionString}</td>
</tr>
</c:forEach>
</table>

</body>
</html>