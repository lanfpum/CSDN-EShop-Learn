<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>login.jsp</title>
  </head>
  <body>
    <form action="<c:url value='/doLogin' />" method="post">
      Username : <input type="text" name="name"><br>
      Password : <input type="password" name="password"><br>
      <input type="submit" />
    </form>
  </body>
</html>
