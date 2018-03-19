<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@	taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title></title>
</head>
<body>
    <c:forEach items="${books}" var="book">
        <div>
            <span>${book.title}</span>
            <span>${book.price}</span>
        </div>
    </c:forEach>
</body>
</html>
