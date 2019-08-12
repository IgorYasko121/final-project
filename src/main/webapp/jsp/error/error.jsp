<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="pagecontent"/>
<html>
    <head>
        <title><fmt:message key="label.title"/></title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
<body>
    <div class="container-fluid">
        <h1>Error</h1>
        <br/>
        Request from ${pageContext.errorData.requestURI} is failed <br/>
        <br/>
        Servlet name or type: ${pageContext.errorData.servletName} <br/>
        <br/>
        Status code: ${pageContext.errorData.statusCode} <br/>
        <br/>
        Exception: ${pageContext.errorData.throwable} <br/>
        <br/>
        Message from exception: ${pageContext.exception.message} <br/>
    </div>
    <div class="container-fluid">
        <h3>Main page</h3>
        <a href="${pageContext.request.contextPath}">Main page</a>
        <hr/>
    </div>
</body>
</html>
