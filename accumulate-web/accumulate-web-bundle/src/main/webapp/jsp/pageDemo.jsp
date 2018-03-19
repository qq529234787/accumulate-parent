<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/8/14
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>page demo</title>
</head>
<body>
  <table cellspacing="1" border="1">
    <thead>
      <tr style="height: 30px;">
        <td style="width: 50px;text-align: center">序号</td>
        <td style="width: 300px;text-align: center">名称</td>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${pageView.recordList}" var="item" varStatus="status" >
        <tr style="height: 30px;">
          <td>${status.index+1}</td>
          <td>${item.name}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <div class="pageView">
      <!--
      <a href="#">&lt;上一页</a>
      <a href="#" class="pageon">1</a>
      <a href="#">2</a>
      <a href="#">3</a>
      <a href="#">下一页&gt;</a>
      -->
      <form action="${pageContext.request.contextPath }/list" method="post"  id="form_page">
          <input type="hidden" name="pageNo" id="pageNo" value="${pageView.currentPage }"/>
          <input type="hidden" name="pageSize" value="${pageView.pageSize }"/>
      </form>
      <%@ include file="common/page_view.jsp" %>
  </div>
</body>
</html>


