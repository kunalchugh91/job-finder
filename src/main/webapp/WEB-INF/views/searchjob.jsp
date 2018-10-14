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
<a href="userhome.htm">Go to Home Page</a><br/><br/>

<form method="post" action="searchjob.htm">

Keyword: <input type="text" name="keyword"/><br/>
Job Category: <select name="category">
<c:forEach var="cat" items="${categories}">
<option value="${cat.category}">${cat.category}</option>
</c:forEach>
</select><br/>
<input type="submit" value="Search" name="search"/><br/>
<input type="submit" value="Next" name="next">
<input type="submit" value="Previous" name="prev">
</form>

<br/><br/> 

<c:if test="${results > 0}">
<c:forEach items="${jobsearchlist}" var="jobsearch" varStatus="loop">

<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<form action="${contextPath}/apply.htm" method="GET">

JobID: <c:out value="${jobsearch.jobID}"/><br/>
Job Title: <c:out value="${jobsearch.jobTitle}"/><br/>
Job Description:<br/>
<textarea rows="5" cols="50" readonly="readonly">${jobsearch.jobDescription}</textarea><br/>
Company: <c:out value="${jobsearch.company}"/><br/>
Location: <c:out value="${jobsearch.location}"/><br/>
Job Category: <c:out value="${jobsearch.jobCategory}"/><br/>
Approximate Salary: <c:out value="${jobsearch.currency} ${jobsearch.rangeAmt} ${jobsearch.term}"/>
<c:if test="${jobsearch.plusCommission} = true"> <c:out value=" Plus Commision"/> </c:if> <br/>
Recruiter Name: <c:out value="${jobsearch.name}"/><br/>
Recruiter Phone: <c:out value="${jobsearch.phone}"/><br/>
Recruiter Email: <c:out value="${jobsearch.email}"/><br/>
<input type="hidden" name="jobID" value="${jobsearch.jobID}"/>

<input type="submit" value="Apply"/>  <c:out value="${jobsearch.hasApplied}"></c:out>

<hr><br/>
</form>
</c:forEach>
</c:if>



</body>
</html>