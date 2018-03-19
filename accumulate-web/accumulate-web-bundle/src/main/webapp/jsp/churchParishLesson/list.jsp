<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/8/14
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>名单</title>
    <link rel="stylesheet" type="text/css" href="/resources/frame/js/jquery-pagination/css/pagination.css"/>
    <script type="text/javascript" src="/resources/frame/js/jquery-1.6.4.js"></script>
    <script type="text/javascript" src="/resources/frame/js/jquery-pagination/js/jquery.pagination.js"></script>
</head>
<body>
    <form id="searchForm" method="post" action="/churchParishLesson/list">
        <input id="pageNo" name="pageNo" value="${lessonQuery.pageNo}" type="hidden"/>
    </form>
  <table cellspacing="1" border="1">
    <thead>
      <tr style="height: 30px;">
        <td style="width: 50px;text-align: center">id</td>
        <td style="width: 100px;text-align: center">所属堂</td>
        <td style="width: 100px;text-align: center">敬拜日期</td>
        <td style="width: 200px;text-align: center">开始时间</td>
        <td style="width: 200px;text-align: center">结束时间</td>
        <td style="width: 200px;text-align: center">操作</td>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${parishLessons}" var="item" varStatus="status" >
        <tr style="height: 30px;">
          <td>${item.id}</td>
          <td>${item.parishId}</td>
          <td><fmt:formatDate value="${item.worshipDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
          <td><fmt:formatDate value="${item.start}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
          <td><fmt:formatDate value="${item.end}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
          <td>
              <a href="/churchParishLesson/toEdit?id=${item.id}">修改</a>
              |
              <a href="/prLessonPeople/toSignView?lessonId=${item.id}">签到情况</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <div id="listPage" class="pagination"></div>
</body>

<script type="text/javascript">
    $("#listPage").pagination('${total}', {
        next_text : "下一页",
        prev_text : "上一页",
        link_to : "javascript:void(0)",
        current_page : '${churchPeopleQuery.pageNo-1}',
        num_display_entries : 8,
        items_per_page : '${churchPeopleQuery.pageSize}',
        num_edge_entries : 1,
        callback:function(page,jq){
            var pageNo = page + 1;
            querylist(pageNo);
        }
    });

    /**
     * 查询列表
     */
    function querylist(pageNo) {
        if (checkQueryForm()) {
            $('#pageNo').val(pageNo);
            $("#searchForm")[0].submit();
        }
    }

    function checkQueryForm() {
        return true;
    }

    function addObj(){
        window.location.href = "/churchParishLesson/toAdd";
    }


</script>
</html>


