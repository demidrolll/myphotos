<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>myphotos.com</title>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <jsp:include page="../fragment/styles.jsp"/>
</head>
<body id="top">
<jsp:include page="../fragment/header.jsp"/>
<jsp:include page="${currentPage}"/>
<jsp:include page="../fragment/footer.jsp"/>
<jsp:include page="../fragment/scripts.jsp"/>
</body>
</html>
