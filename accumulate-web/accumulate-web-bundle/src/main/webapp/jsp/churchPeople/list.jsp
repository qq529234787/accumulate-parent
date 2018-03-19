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
    <form id="searchForm" method="post" action="/churchPeople/list">
        <input id="pageNo" name="pageNo" value="${churchPeopleQuery.pageNo}" type="hidden"/>
        <input id="parishId" name="parishId" value="${churchPeopleQuery.parishId}" type="hidden"/>
        <table cellspacing="1" border="1">
            <tbody>
                <tr>
                    <td>姓名：</td>
                    <td><input id="name" name="name" type="text" value="${churchPeopleQuery.name}"/></td>
                    <td>状态</td>
                    <td>
                        <select id="status" name="status">
                            <option value="1" <c:if test="${churchPeopleQuery.status == 1}">selected</c:if>>正常</option>
                            <option value="0" <c:if test="${churchPeopleQuery.status == 0}">selected</c:if>>无效</option>
                            <option value="-1" <c:if test="${churchPeopleQuery.status == -1}">selected</c:if>>删除</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <a href="javascript:void(0);" class="" onclick="querylist(1);">查询</a>
                        <a href="javascript:void(0);" class="" onclick="addObj(${churchPeopleQuery.parishId});">新增</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
  <table cellspacing="1" border="1">
    <thead>
      <tr style="height: 30px;">
        <td style="width: 50px;text-align: center">序号</td>
        <td style="width: 100px;text-align: center">姓名</td>
        <td style="width: 100px;text-align: center">性别</td>
        <td style="width: 200px;text-align: center">手机</td>
        <td style="width: 200px;text-align: center">生日</td>
        <td style="width: 50px;text-align: center">排序</td>
        <td style="width: 100px;text-align: center">头像</td>
        <td style="width: 100px;text-align: center">所属堂</td>
        <td style="width: 200px;text-align: center">备注</td>
        <td style="width: 200px;text-align: center">操作</td>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${churchPeoples}" var="item" varStatus="status" >
        <tr style="height: 30px;">
          <td>${churchPeopleQuery.startRow+status.index+1}</td>
          <td>${item.name}</td>
          <td>
              <c:forEach items="${GenderEnumArr}" var="gender" varStatus="sta">
                  <c:if test="${item.gender == gender.status}">
                    ${gender.value}
                  </c:if>
              </c:forEach>

          </td>
          <td>${item.mobile}</td>
          <td><fmt:formatDate value="${item.birthday}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
          <td>${item.sort}</td>
          <td>
              <c:if test="${item.headPortrait != null}">
                  <img src="/churchPeople/writeImage?id=${item.id}" width="100" height="80"/>
              </c:if>
          </td>
          <td>${item.parishId}</td>
          <td>${item.remark}</td>
          <td>
              <a href="/churchPeople/toEdit?id=${item.id}">修改</a>
              |
              <c:choose>
                  <c:when test="${churchPeopleQuery.status == 1}">
                      <a href="#" onclick="changeStatus(${item.id}, 0)">设为无效</a>
                  </c:when>
                  <c:otherwise>
                      <a href="#" onclick="changeStatus(${item.id}, 1)">恢复</a>
                  </c:otherwise>
              </c:choose>
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

    function addObj(parishId){
        if(!parishId){
            parishId = 1;
        }
        window.location.href = "/churchPeople/toAdd?parishId="+parishId;
    }

    function changeStatus(id, status) {
        if (!id) {
            alert("id不正确");
            return false;
        }
        if (confirm("确定删除id:" + id)) {
            $.ajax({
                type: "POST",
                url: "/churchPeople/changeStatus",
                data: {"id":id, "status":status},
                dataType: "json",
                async: false,
                success: function (data) {
                    var resultObj = eval(data);
                    if (resultObj.code == "true") {
                        alert("操作成功");
                        $("#searchForm")[0].submit();
                    } else {
                        alert("操作失败. " + resultObj.message);
                    }
                }
            });
        }
    }


</script>
</html>


