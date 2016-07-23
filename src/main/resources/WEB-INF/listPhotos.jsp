<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
<div align="center">
    <h1>Photo ids: </h1>
<form action="/delete_selected" method="post">
    <c:forEach items="${list_photos}" var="p">
        <input type="checkbox" name="toDelete[]" value="${p.getKey()}" ><c:out value="${p.getKey()}"/><br>
    </c:forEach><br>
    <input type="submit" value="Delete selected">
</form>
<p align="center"><a href="/">Return to main</a></p>
</div>
</body>
</html>
