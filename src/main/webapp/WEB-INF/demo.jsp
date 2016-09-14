<html>
  <head>
    <title>Demo Sheets Web App</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  <body>Name, Major<br>
    <c:forEach items="${values}" var="row">
        ${row[0]},${row[4]}<br>
    </c:forEach>
  </body>
</html>
