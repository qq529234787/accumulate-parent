<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2015/5/13
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Single File Upload</h1>
<form method="post" enctype="multipart/form-data" action="singleUpload">
  Upload File: <input type="file" name="file">
  <br /><br />
  Description: <input type="text" name="loanId"/>
  <br/><br/><input type="submit" value="Upload">
</form>
</body>
</html>
