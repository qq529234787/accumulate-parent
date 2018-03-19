<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="${assets}/resources/frame/css/common.css" rel="stylesheet">
  <script type="text/javascript" src="${assets}/resources/frame/js/jquery.js"></script>
  <script type="text/javascript" src="${assets}/resources/frame/js/common.js"></script>
  <link href="${assets}/resources/frame/css/index.css" rel="stylesheet">
  <script type="text/javascript" src="${assets}/resources/frame/js/index.js"></script>
  <title>jiuxian master</title>
</head>
<body>
<div class="mainhd">
  <div><img alt="logo" class="none"></div>
  <div class="uinfo">
    <p>您好, <em>${sessionScope.loginUser.userName}</em>
      [ <a href="${pageContext.request.contextPath}/exit" >退出</a> ]</p>
  </div>
  <div class="nav">
    <ul id="topmenu">
      <li class="navon">
        <em><a href="#" id="header_index">首页</a></em>
      </li>
      <%--<li><em><a href="#" id="header_tab1">目录1</a></em></li>
      <li><em><a href="#" id="header_tab2">目录2</a></em></li>
      <li><em><a href="#" id="header_tab3">目录3</a></em></li>--%>
    </ul>
    <div class="navbd"></div>
  </div>
</div>
<div id="leftmenu" class="menu">
  <c:forEach var="menu" items="${menus}" varStatus="status">
    <ul id="menu_index">
      <h3 id="h3_${menu.menuId}">${menu.menuName}</h3>
        <%--<li><a target="main" href="/welcomeRemark/welcomeManage">欢迎词设置</a></li>--%>
        <c:forEach var="subMenu" items="${menu.subMenu}" varStatus="subStatus">
          <li rho="menu_li" val="${subMenu.menuId}">
            <a target="main" href="$!{subMenu.menuLink}"
               onclick="selectMenu(this,$menu.menuId,$subMenu.menuId);">$!{subMenu.menuName}</a>
          </li>
        </c:forEach>
      <li><a target="main" href="/autoReply/autoReplyManage">自动回复设置</a></li>
    </ul>
  </c:forEach>

  <ul id="menu_tab1">
    <li><a target="main" href="/feedback/master/serverList">用户意见反馈</a></li>
  </ul>
  <ul id="menu_tab2">
  </ul>
</div>
<div id="mainFrameContainer">
  <iframe id="mainFrame" name="main" frameborder="0"></iframe>
</div>
</body>
</html>