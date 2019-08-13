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
<header>
    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand" href="${pageContext.request.contextPath}"><fmt:message key="label.registerPage"/></a>
    </nav>
</header>
    <div class="container-fluid">
        <h1><fmt:message key="label.error"/></h1>
        <br/>
        Request from : ${pageContext.errorData.requestURI} is failed <br/>
        <br/>
        Servlet name or type: ${pageContext.errorData.servletName} <br/>
        <br/>
        Status code: ${pageContext.errorData.statusCode} <br/>
        <br/>
        Exception: ${pageContext.errorData.throwable} <br/>
        <br/>
    </div>
</body>
</html>
