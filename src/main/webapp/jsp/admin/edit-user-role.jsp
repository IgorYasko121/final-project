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
            <a class="navbar-brand" href="admin"><fmt:message key="label.backPage"/></a>
        </nav>
    </header>
        <div class="container-fluid">
            <h5><fmt:message key="label.edit"/></h5><br/>
            <form name="updateForm" method="post" action="update">
                <input type="hidden" name="command" value="update_user_role"/>
                <input type="hidden" name="user_id" value="<c:out value="${user_id}"/>"/>
                <div class="form-group">
                    <label for="role"><fmt:message key="label.role"/></label>
                    <input class="form-control" id="role" type="text" name="role" value="" required="required" pattern="USER|GUEST"/>
                    <small class="form-text text-muted"><fmt:message key="input.roleinfo"/></small>
                </div>
                <br/>
                ${errorLoginPassMessage}
                <br/>
                <input class="btn btn-secondary" type="submit" name="submit" value="<fmt:message key="label.update"/>"/>
            </form>
        </div>
    </body>
</html>
