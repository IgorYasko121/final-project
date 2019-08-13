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
        <form action="update" method="post">
            <input type="hidden" name="command" value="update_track"/>
            <input type="hidden" name="track_id" value="<c:out value="${track_id}"/>"/>
            <div class="form-group">
                <label for="trackName"><fmt:message key="label.trackName"/></label>
                <input class="form-control" id="trackName" type="text" name="track_name" value="" required="required" pattern="^[a-zA-Z0-9 -]{4,20}$"/>
                <small class="form-text text-muted"><fmt:message key="input.fieldNameInfo"/></small>
            </div>
            <div class="form-group">
                <label for="trackGenre"><fmt:message key="label.trackGenre"/></label>
                <input class="form-control" id="trackGenre" type="text" name="genre" value="" required="required" pattern="^[A-Z]{4,20}"/>
                <small class="form-text text-muted"><fmt:message key="input.genreFieldInfo"/></small>
            </div>
            <div class="form-group">
                <label for="trackSinger"><fmt:message key="label.trackSinger"/></label>
                <input class="form-control" id="trackSinger" type="text" name="singer" value="" required="required" pattern="^[a-zA-Z0-9 -]{4,20}$"/>
                <small class="form-text text-muted"><fmt:message key="input.fieldNameInfo"/></small>
            </div>
            <br/>
            ${errorLoginPassMessage}
            <br/>
            <input class="btn btn-secondary" type="submit" name="submit" value="<fmt:message key="label.update"/>"/>
        </form>
    </div>
</body>
</html>
