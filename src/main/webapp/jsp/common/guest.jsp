<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${ not empty locale ? locale : pageContext.request.locale }" scope="session" />
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
        <form action="controller" method="post">
            <input type="hidden" name="command" value="language">
            <input type="hidden" name="page" value="guest">
            <select name="locale" onchange="submit()">
                <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
                <option value="en_US" ${locale == 'en_US' ? 'selected' : ''}>English</option>
                <option value="ru_BY" ${locale == 'ru_BY' ? 'selected' : ''}>Белорусский</option>
            </select>
        </form>
    </nav>
</header>
    <div class="container-fluid">
        <h5><fmt:message key="label.greetingGuest"/></h5>
        <br/>
    </div>
    <div class="container-fluid">
        <ctg:track-list tracks="${ sessionScope.tracks }" />
    </div>

</body>
    <footer class="page-footer font-small bg-light pt-4">
        <div class="footer-copyright text-center py-3"><fmt:message key="label.footer"/>
            <a href="${pageContext.request.contextPath}"><fmt:message key="label.login"/></a>
        </div>
    </footer>
</html>
