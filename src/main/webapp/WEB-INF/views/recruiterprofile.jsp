<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:out value="Welcome ${user.name}"></c:out><br/><br/>
<a href="rechome.htm">Go to Home Page</a><br/><br/>


<h2> My Profile </h2><br/>

<form:form method="POST" modelAttribute="recruiter">
<h4> About me </h4><br/>
Name: <form:input path="name"/><form:errors path="name"/><br/>
Email: <form:input path="email"/><form:errors path="email"/><br/>
Phone: <form:input  type="number" path="phone"/><form:errors path="phone"/><br/>
Job Title: <form:input path="jobTitle"/><form:errors path="jobTitle"/><br/>
Company: <form:input path="company"/><form:errors path="company"/><br/>

<input type="submit" value="Save"/>
</form:form>

</body>
</html>