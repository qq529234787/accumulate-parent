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
  <title>签到情况</title>
    <script type="text/javascript" src="/resources/frame/js/jquery-1.6.4.js"></script>
</head>
<body>
  <div>
      <div style="text-align: center;">
          <label style="color: black;font-family: Arial;font-size: larger">
              <fmt:formatDate value="${lesson.worshipDate}" pattern="yyyy-MM-dd"/>
              &nbsp;
              签到情况
          </label>
      </div>
      <div>
      <c:forEach items="${prLessonPeopleList}" var="item" varStatus="status">
          <div style="height: 180px;width: 150px;float: left">
              <p style="text-align: center">
                  <img src="/churchPeople/writeImage?id=${item.peopleId}" width="120" height="100"/>
              </p>
              <p style="color: ${item.isSign() ? 'blue' : 'red'};text-align: center">
                <label onclick="signName(${lessonPeopleQuery.lessonId},${item.peopleId},'${item.peopleName}')">${item.peopleName}</label>
              </p>
          </div>
      </c:forEach>
      </div>
  </div>

</body>

<script type="text/javascript">

    function signName(lessonId, peopleId, peopleName) {
        if (!lessonId || !peopleId) {
            alert("参数不正确");
            return false;
        }

        $.ajax({
            type: "POST",
            url: "/prLessonPeople/getByLessonPeople",
            data: {"lessonId":lessonId, "peopleId":peopleId},
            dataType: "json",
            async: false,
            success: function (data) {
                var resultObj = eval(data);
                if (resultObj.code == "true") {
                    if(confirm("[ "+peopleName+" ] 确定取消签到？")){
                        insertOrDelete(lessonId, peopleId, false);
                    }

                } else {
                    if(confirm("[ "+peopleName+" ] 确定签到？")){
                        insertOrDelete(lessonId, peopleId, true);
                    }
                }
            }
        });

    }

    function insertOrDelete(lessonId, peopleId, flag){
        var url = "/prLessonPeople/insert";
        if(!flag){
            url = "/prLessonPeople/delete";
        }

        $.ajax({
            type: "POST",
            url: url,
            data: {"lessonId":lessonId, "peopleId":peopleId},
            dataType: "json",
            async: false,
            success: function (data) {
                var resultObj = eval(data);
                if (resultObj.code == "true") {
                    alert("操作成功");
                    window.location.reload();
                } else {
                    alert("操作失败. " + resultObj.message);
                    window.location.reload();
                }
            }
        });
    }


</script>
</html>


