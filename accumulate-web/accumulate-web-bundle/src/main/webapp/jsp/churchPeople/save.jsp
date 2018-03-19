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
  <title>名单</title>
    <link rel="stylesheet" type="text/css" href="/resources/frame/js/jquery-pagination/css/pagination.css"/>
    <script type="text/javascript" src="/resources/frame/js/jquery-1.6.4.js"></script>
    <script type="text/javascript" src="/resources/frame/js/jquery-pagination/js/jquery.pagination.js"></script>
</head>
<body>
    <form id="searchForm" method="post" action="/churchPeople/list">
        <input id="id" name="id" value="${churchPeople.id}" type="hidden"/>
        <table cellspacing="1" border="1">
            <tbody>
                <tr>
                    <td>姓名：</td>
                    <td><input id="name" name="name" type="text" value="${churchPeople.name}"/></td>
                    <td>手机号</td>
                    <td><input id="mobile" name="mobile" type="text" value="${churchPeople.mobile}"/></td>
                </tr>
                <tr>
                    <td>性别：</td>
                    <td>
                        <select id="gender" name="gender">
                            <c:forEach items="${GenderEnumArr}" var="gender" varStatus="sta">
                                <option value="${gender.status}" <c:if test="${churchPeople.gender == gender.status}">selected</c:if>>${gender.value}</option>
                            </c:forEach>
                        </select>

                    </td>
                    <td>生日：</td>
                    <td><input id="birthday" name="birthday" type="text" value="${churchPeople.birthday}"/></td>
                </tr>
                <tr>
                    <td>排序：</td>
                    <td><input id="sort" name="sort" type="text" value="${churchPeople.sort}"/></td>
                    <td>分堂：</td>
                    <td>
                        <select id="parishId" name="parishId">
                            <option value="${churchParish.id}">${churchParish.name}</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea id="remark" name="remark" >${churchPeople.remark}</textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <a href="javascript:void(0);" onclick="save();">保存</a>
                        <a href="/churchPeople/list">取消</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>

  <div id="listPage" class="pagination"></div>
</body>

<script type="text/javascript">


    function save() {
        var dataObj = {};
        dataObj.id = $("#id").val();
        dataObj.name = $("#name").val();
        dataObj.mobile = $("#mobile").val();
        dataObj.gender = $("#gender").val();
        //dataObj.birthday = $("#birthday").val();
        dataObj.sort = $("#sort").val();
        dataObj.parishId = $("#parishId").val();
        dataObj.remark = $("#remark").val();
        if (!dataObj.name) {
            alert("姓名必填");
            return false;
        }

        var url = "/churchPeople/add";
        if(dataObj.id && dataObj.id>0){
            url = "/churchPeople/update";
        }
        $.ajax({
            type: "POST",
            url: url,
            data: dataObj,
            dataType: "json",
            async: false,
            success: function (data) {
                var resultObj = eval(data);
                if (resultObj.code == "true") {
                    alert("操作成功");
                    window.location.href = "/churchPeople/list";
                } else {
                    alert("操作失败. " + resultObj.message);
                }
            }
        });
    }

</script>
</html>


