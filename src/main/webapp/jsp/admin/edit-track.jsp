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
        <form action="update" method="post">
            <input type="hidden" name="command" value="update_track"/>
            <input type="hidden" name="track_id" value="<c:out value="${track_id}"/>"/>
            <div class="form-group">
                <label for="trackName"><fmt:message key="label.trackName"/></label>
                <input class="form-control" id="trackName" type="text" name="track_name" value="" required="required" pattern="^[a-zA-Z0-9 -]{3,30}$"/>
            </div>
            <div class="form-group">
                <label for="trackGenre"><fmt:message key="label.trackGenre"/></label>
                <input class="form-control" id="trackGenre" type="text" name="genre" value="" required="required" pattern="^[A-Z]{3,20}"/>
            </div>
            <div class="form-group">
                <label for="trackSinger"><fmt:message key="label.trackSinger"/></label>
                <input class="form-control" id="trackSinger" type="text" name="singer" value="" required="required" pattern="^[a-zA-Z0-9 -]{3,30}$"/>
            </div>
            <br/>
            ${errorLoginPassMessage}
            <br/>
            <input type="submit" value="Update"/>
        </form>
    </div>
</body>
</html>
