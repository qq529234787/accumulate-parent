<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2016/4/24
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@	taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Title</title>
    <style>
        a {
            color: blue;
        }
    </style>
</head>
<body>
    <div class="side" style="max-height: 1000px;overflow-y: auto;">
        <div id="menu_list" class="sideMenu" style="margin:0 auto;margin-bottom: 30px;">
            <c:forEach var="menu" items="${menuTree}">
                <h3 id="h3_${menu.menuId}">${menu.menuName}</h3>
                <ul style="display: none;">
                    <c:forEach var="subMenu" items="${menu.subMenu}">
                        <li rho="menu_li" val="${subMenu.menuId}">
                            <a href="${subMenu.menuLink}" target="main"
                               onclick="selectMenu(this,${menu.menuId},${subMenu.menuId});">${subMenu.menuName}</a>
                        </li>
                    </c:forEach>
                </ul>
            </c:forEach>
        </div>
    </div>
</body>

<script type="text/javascript">
    function selectMenu(obj, menuId, menu2Id) {
        //$("#hereArea").html($("#h3_" + menuId).html() + " - " + $(obj).html());//显示当前位置
        var menuLi = $('#menu_list li[rho="menu_li"]');
        $.each(menuLi, function (index, menu) {
            if ($(menu).attr("val") == menu2Id) {
                $(menu).addClass("on");
            } else {
                $(menu).removeClass("on");
            }
        });
    }
    $(document).ready(function () {
        if ($(".side").height() < $(".sideMenu").height()) {
            $(".side").attr("style", "overflow-y:auto;");
        }
        $("#menu_list h3").on("click", function () {
            $("#menu_list ul").hide("fast");
            if ($(this).next("ul").is(":hidden")) {
                $(this).next("ul").show("fast");
            }else {
                $(this).next("ul").hide("fast");
            }
        });
    });
</script>
</html>
