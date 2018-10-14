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

<h2> Create Job </h2><br/>

<form:form method="POST" modelAttribute="job">

Job Title: <form:input path="jobTitle"/><form:errors path="jobTitle"/><br/>
Location:     <form:input path="location"/><form:errors path="location"/><br/>
Employment Type: <form:input path="employmentType"/><form:errors path="employmentType"/><br/>
Job Description: <br/><form:textarea path="jobDescription" rows="5" cols="50"/>
                      <form:errors path="jobDescription"/><br/>
Job category: <form:select path="jc" required="required">
<c:forEach var="cat" items="${categories}">
<form:option value="${cat.category}">${cat.category}</form:option>
</c:forEach>
</form:select><br/>
Approximate Salary: <form:input type="number" path="rangeAmt"/><form:errors path="rangeAmt"/><br/>
Currency: <form:input path="currency"/><form:errors path="currency"/><br/>
Term: <form:input path="term"/><form:errors path="term"/><br/>
Plus Commission: <form:checkbox path="plusCommission"/><form:errors path="plusCommission"/><br/>

<input type="submit" value="Save"/>
</form:form>

</body>
</html>