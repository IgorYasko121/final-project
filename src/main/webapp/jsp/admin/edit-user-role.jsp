<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <a class="navbar-brand" href="controller?command=guest"><fmt:message key="label.guestPage"/></a>
            <form action="controller" method="post">
                <input type="hidden" name="command" value="language">
                <input type="hidden" name="page" value="main">
                <select name="locale" onchange="submit()">
                    <option value="ru_RU" ${locale == 'ru_RU' ? 'selected' : ''}>Русский</option>
                    <option value="en_US" ${locale == 'en_US' ? 'selected' : ''}>English</option>
                    <option value="ru_BY" ${locale == 'ru_BY' ? 'selected' : ''}>Белорусский</option>
                </select>
            </form>
        </nav>
    </header>
        <div class="container-fluid">
            <h5><fmt:message key="label.edit"/></h5><br/>
            <form name="updateForm" method="post" action="update">
                <input type="hidden" name="command" value="update_user_role"/>
                <input type="hidden" name="user_id" value="<c:out value="${user_id}"/>"/>
                Role:<br/>
                <input type="text" name="role" value="" required="required" pattern="user|admin|guest" />
                <br/>
                ${errorLoginPassMessage}
                <br/>
                <input type="submit" value="Update"/>
            </form>
        </div>
    </body>
</html>
