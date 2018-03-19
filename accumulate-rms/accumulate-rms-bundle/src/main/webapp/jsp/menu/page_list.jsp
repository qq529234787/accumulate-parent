<%--
  Created by IntelliJ IDEA.
  User: ming
  Date: 2016/4/9
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@	taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>page menu</title>
    <link rel="stylesheet" href="/resources/js/jquery-pagination/css/pagination.css"/>
    <script type="text/javascript" src="/resources/js/jquery-1.7.2.js"/>
    <script type="text/javascript" src="/resources/js/jquery-pagination/js/jquery.pagination.js"/>
    <script type="text/javascript" src="/resources/js/common/util.js"/>
</head>
<body>

<div class="container">
    <div id="search_bar" class="mt10">
        <div class="box">
            <div class="box_border">
                <div class="box_top"><b class="pl15">搜索</b></div>
                <div class="box_center pt10 pb10">
                    <form id="queryMenuForm" method="post" action="${pageContext.request.contextPath}/menu/queryMenuList">
                    <input type="hidden" id="menuPageNo" name="pageNo" value="${menuSearch.pageNo}" />
                    <input type="hidden" id="parentId" name="parentId" value="${adminMenu.menuId}">
                    <table class="form_table" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td>菜单名称</td>
                            <td>
                                <input id="menu_name" name="menuName" type="text" class="input-text lh25" value="${menuSearch.menuName}"/>
                            </td>
                            <td>菜单链接</td>
                            <td>
                                <input id="menu_link" name="menuLink" type="text" class="input-text lh25" value="${menuSearch.menuLink}"/>
                            </td>
                            <td>权限码</td>
                            <td>
                                <input id="menu_code" name="code" type="text" class="input-text lh25" value="${menuSearch.code}"/>
                            </td>
                        </tr>
                    </table>
                    </form>
                </div>
                <div class="box_bottom pb5 pt5 pr10" style="border-top:1px solid #dadada;">
                    <div class="search_bar_btn" style="text-align:right;">
                        <input type="button" class="btn btn82 btn_search" onclick="queryMenus(1);return false;" value="查询">
                        <input type="button" class="btn btn82 btn_res" onclick="resetQueryForm();return false;" value="重置">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="button" class="mt10">
        <input type="button" name="button" onclick="addMenu('${menuSearch.parentId}','${adminMenu.menuLevel}');" class="btn btn82 btn_add" value="新增">
        <c:if test="${menuSearch.parentId>0}">
            <input type="button" value="返回上一级" onclick="goBack('${adminMenu.parentId}');" class="ext_btn">
        </c:if>
    </div>
    <c:if test="${menuSearch.parentId>0}">
        <div id="search_bar_" class="mt10">
            <div class="box_border">
                <div class="box_bottom pb5 pt5 pr10" style="border-top:1px solid #dadada;">
                    <div class="search_bar_btn" >
                        <span style="padding-left: 20px;"><b>上级菜单名称：</b>${adminMenu.menuName}</span>
                        <span style="padding-left: 50px;"><b>菜单链接：</b>${adminMenu.menuLink}</span>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div id="table" class="mt10">
        <div class="box span10 oh">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="list_table">
                <tr>
                    <th width="3%">编号</th>
                    <th width="8%">名称</th>
                    <th width="27%">链接</th>
                    <th width="15%">权限码</th>
                    <th width="3%">排序号</th>
                    <th width="5%">菜单级别</th>
                    <th width="8%">创建时间</th>
                    <th width="8%">修改时间</th>
                    <th width="17%">操作</th>
                </tr>
                <c:forEach items="${pagination.items}" var="menu">
                    <tr class="tr">
                        <td class="td_center">${menu.menuId}</td>
                        <td class="td_center">${menu.menuName}</td>
                        <td>${menu.menuLink}</td>
                        <td>${menu.code}</td>
                        <td class="td_center">${menu.seq}</td>
                        <td class="td_center">
                            <c:choose>
                                <c:when test="${menu.menuLevel == 1}">
                                    一级菜单
                                </c:when>
                                <c:when test="${menu.menuLevel == 2}">
                                    二级菜单
                                </c:when>
                                <c:otherwise>
                                    功能菜单
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="td_center">${menu.created}</td>
                        <td class="td_center">${menu.modifyed}</td>
                        <td class="td_center">
                            <c:if test="${menu.menuLevel < 3}">
                                <a href="${pageContext.request.contextPath}/menu/queryMenuList?parentId=${menu.menuId})">
                                    <input type="button" name="button" class="btn btn82 btn_add" value="子菜单">
                                </a>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/menu/editMenuInit?menuId=${menu.menuId})">
                                <input type="button" name="button" class="btn btn82 btn_add" value="编辑">
                            </a>
                            <a href="javascript:void(0);" onclick="delMenu(${menu.menuId});">
                                <input type="button" name="button" class="btn btn82 btn_del" value="删除">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div id="menuPage" class="pagination"></div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript">
    $(document).ready(function () {

        $("#menu_page").pagination("${pagination.totalCount}", {
            next_text: "下一页",
            prev_text: "上一页",
            link_to: "javascript:void(0)",
            current_page: '${menuSearch.pageNo}' - 1,
            num_display_entries: 8,
            items_per_page: '${menuSearch.pageSize}',
            num_edge_entries: 1,
            num_display_entries: 5,
            callback: function (page, jq) {
                var pageNo = page + 1;
                queryMenus(pageNo);
            }
        });

        /**
         * 查询用户列表
         */
        function queryMenus(pageNo) {
            $('#menuPageNo').val(pageNo);
            $("#queryMenuForm")[0].submit();
        }

        /**
         * 重置查询表单
         */
        function resetQueryForm(){
            $('#queryMenuForm input[name!="parentId"]').val("");
        }

        function addMenu(menuId,menuLevel){
            if(isNull(menuLevel)){
                menuLevel = 0;
            }
            window.location.href = '${pageContext.request.contextPath}/menu/addMenuInit?menuId=' + menuId + '&menuLevel=' + menuLevel;
        }

        function goBack(menuId){
            window.location.href = '${pageContext.request.contextPath}/menu/queryMenuList?parentId=' + menuId;
        }

        function delMenu(id){
            if(confirm("确定要删除吗？")){
                var parentId = $("#parentId").val();
                if(parentId == undefined){
                    parentId = 0;
                }
                jQuery.ajax({
                    url: '${pageContext.request.contextPath}/menu/delMenu',
                    data: "menuId=" + id,
                    type: 'post',
                    cache: false,
                    async: false,
                    success: function (retMsg) {
                        if(retMsg == "1"){
                            alert("删除成功");
                            queryMenus($('#menuPageNo').val());
                        }else if (retMsg == "2"){
                            alert("该菜单包含子菜单，不允许删除");
                        }else {
                            alert("删除失败");
                        }
                    },
                    error: function (request, errMsg, errorObj) {
                        alert("网络异常，请稍后再试！");
                    }
                });
            }
        }
        

    });

</script>
</html>
