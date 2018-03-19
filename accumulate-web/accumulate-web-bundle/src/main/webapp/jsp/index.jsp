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
      <%--
      <li><em><a href="#" id="header_tab2">目录2</a></em></li>
      <li><em><a href="#" id="header_tab3">目录3</a></em></li>
      --%>
    </ul>
    <div class="navbd"></div>
  </div>
</div>
<div id="leftmenu" class="menu">
  <ul id="menu_index">
    <li><a target="main" href="/churchParish/list">堂点管理</a></li>
    <li><a target="main" href="/churchPeople/list">名单管理</a></li>
    <li><a target="main" href="/churchParishLesson/list">敬拜日期</a></li>
  </ul>
  <ul id="menu_tab1">
    <%--<li><a target="main" href="/feedback/master/serverList">用户意见反馈</a></li>--%>
  </ul>
  <ul id="menu_tab2">
  </ul>
</div>
<div id="mainFrameContainer">
  <iframe id="mainFrame" name="main" frameborder="0"></iframe>
</div>
</body>
</html>