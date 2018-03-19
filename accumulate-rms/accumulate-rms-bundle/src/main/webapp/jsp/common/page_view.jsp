<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${pageView.currentPage != 1 }">
	<a href="javascript:currPage('1');" id="first">首页</a>
	<a href="javascript:currPage('${pageView.currentPage-1 }');" id="prev">&lt;上一页</a>
</c:if>

<c:forEach begin="${pageView.startPageIndex }" end="${pageView.endPageIndex }" var="index">
	<c:choose>
		<c:when test="${index == pageView.currentPage}">
			<span style="color: darkgrey">${index }</span>
		</c:when>
		<c:otherwise>
			<a href="#" class="" onclick="currPage(${index });">${index }</a>
		</c:otherwise>
	</c:choose>
</c:forEach>
<c:if test="${pageView.endPageIndex != pageView.totalPage }">
	<span>...</span>
</c:if>

<c:if test="${pageView.currentPage < pageView.totalPage }">
	<a href="javascript:currPage('${pageView.currentPage+1 }');" id="next">下一页&gt;</a>
	<a href="javascript:currPage('${pageView.totalPage }');" id="end">末页</a>
</c:if>
<script type="text/javascript">
	//分页
	function currPage(page) {
		document.getElementById("pageNo").value = page;
		document.getElementById("form_page").submit();
	}
</script>