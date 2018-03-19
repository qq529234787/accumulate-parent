<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
    <title>转让标列表</title>
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <meta name="author" content="" />
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <link rel="stylesheet" href="/resources/css/list.css" type="text/css"/>
    <script type="text/javascript" src="/resources/js/jquery-1.6.4.js"></script>
    <script language="javascript" type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
</head>
<script type="text/javascript">
    function topage(pageNow) {
        window.location.href = "/transferOrigin/transferlist?pageNo=" + pageNow+"&startime=${startime}&endtime=${endtime}";
    }
    function upinfo(uri){
        window.location=uri;
    }

    $(function(){

        $(".testclass").live("click",function(){

//            console.log($(this).parent().find('.uploadfile').val());
            if($(this).parent().find('.uploadfile').val().length <= 0) {
                if (confirm("请先选择文件！")) {
                    return false;
                }
            }else {
                var oMyForm = new FormData();
                var loanId = $(this).parent().find(".upload-loanid").val();
                oMyForm.append("loanId",loanId);
                oMyForm.append("file", $(this).parent().find("#addressFile")[0].files[0]);
                var $msg = $(this).parent().find(".upload-msg");
                $.ajax({
                    url:"/fileupload/singleUpload",
                    data:oMyForm,
                    type:"post",
                    processData: false,  // 不处理发送的数据
                    contentType: false,   // 不设置Content-Type请求头
                    success:function(res){
                        if(res==1) {
                            $msg.text("上传成功！");
                        }else{
                            $msg.text("上传失败！");
                        }
                    }
                });
            }
        });
        //tranfer-loanid
        $(".tranfer").click(function(){

            var $btn = $(this);
            var loanId = $(this).parent().find(".tranfer-loanid").val();
            var $msg = $(this).parent().find(".transfer-msg");
            $.ajax({
                url: "/transferOrigin/insertIntoTransfer",
                data:{"loanId":loanId},
                type: "post",
                success: function(res){
                    if(res==1) {
                        $msg.text("恭喜你成功转到积木！！！");
                        $btn.remove();
                    }else{
                        $msg.text("转标失败！！！");
                    }

                }

            });
        });


    });


</script>

<body>
<div class="content"  style="min-width:800px;">
<div class="content_box">
    <div class="infolist">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" id="usertab"  class="mt7">

            <div class="list_date fright">
                <form action="/transferOrigin/transferlist">
                    <input type="button" value="清空" class="btn" style="float:right" onclick="javascript:upinfo('/transferOrigin/transferlist')"/>
                    <input type="submit" value="搜索" class="btn" style="float:right"/>
                    <div  style="float: right;">
                        贷款开始时间：<input type="text" name="startime" id="startime" class="Wdate" style="width:90px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}',readOnly:true})"  value="${startime}"/>
                        至 <input type="text" name="endtime" id="endtime" class="Wdate" style="width:90px" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startime\')}',readOnly:true})" value="${endtime}"/>
                    </div>
                </form>
            </div>

            <tr class="line_g yanse" style="  background: #7D9BFC;">
                <td align="center"  width="150"><strong>贷款编号</strong></td>
                <td align="center"  width="150"><strong>借款人ID</strong></td>
                <td align="center"   width="150"><strong>投资人ID</strong></td>
                <td align="center"  width="150"><strong>贷款开始时间</strong></td>
                <td align="center"  width="150"><strong>金额</strong></td>
                <td align="center"  width="150"><strong>还款方式</strong></td>
                <td align="center"  width="150"><strong>期数</strong></td>
                <td align="center"  width="150"><strong>利率</strong></td>
                <td  align="center"><strong>操作</strong></td>
                <c:choose>
                <c:when test="${fn:length(listtransfer) == 0 }">
            <tr class="line_g hover_bg">
                <td colspan="9"  align="center">暂无数据！</td>
            </tr>
            </c:when>
            <c:otherwise>
                <tbody id="bbsTab">
                <c:forEach var="lgu" items="${listtransfer}" varStatus="i">
                    <tr class="line_g hover_bg" id="${lgu.loanId}">
                        <td align="center">${lgu.loanId}</td>
                        <td align="center">${lgu.borrowerId}</td>
                        <td align="center">${lgu.investorId}</td>
                        <td align="center"><fmt:formatDate value="${lgu.creditDate}" pattern="yyyy-MM-dd"/></td>
                        <td align="center">${lgu.creditAmt}</td>
                        <td align="center">${lgu.repayMethodId}</td>
                        <td align="center">${lgu.period}</td>
                        <td align="center">${lgu.apr}</td>
                        <td  align="center">
                                <div>
                                <c:choose>
                                <c:when test="${ lgu.uploadFlag == 1}">
                                <font color="blue"> 已上传</font>&nbsp;
                                </c:when>
                                </c:choose>
                                    <input  class="uploadfile" type="file"  name="file"  dataType="Require"  id="addressFile" />
                                    <input class="upload-loanid" type="hidden" value="${lgu.loanId}"/>
                                    <input class="testclass" value="上传"  type="button"/><span class="upload-msg"></span>
                                </div>
                            <div>
                                <input class="tranfer-loanid" type="hidden" value="${lgu.loanId}"/>
                                <input class="tranfer" type='submit' value="转到积木" /><span class="transfer-msg"></span>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </c:otherwise>
            </c:choose>
            <tr class="title">
                <td height="28" align="right" valign="middle" colspan="9">
                    共&nbsp;<span id="totalNum">${total}</span>&nbsp;条&nbsp;&nbsp;每页&nbsp;${pageSize }&nbsp;条&nbsp;&nbsp;第&nbsp;${pageNo }/${pageTotal }&nbsp;页&nbsp;&nbsp;<a href=javascript:topage(1);" onclick="topage(1);">首页</a>&nbsp;
                    <c:choose><c:when test="${pageNo>1}"><a href='javascript:topage("${pageNo-1 }");' onclick='topage("${pageNo-1}");'>上一页</a></c:when><c:otherwise>上一页</c:otherwise></c:choose>&nbsp;
                    <c:choose><c:when test="${pageTotal>pageNo}"> <a href='javascript:topage("${pageNo+1 }");' onclick='topage("${pageNo+1}");'>下一页</a></c:when> <c:otherwise>下一页</c:otherwise></c:choose>&nbsp;
                    <a href='javascript:topage("${pageTotal}");' onclick='topage("${pageTotal}");'>尾页</a>&nbsp;
                    跳转：<select name="pageNo" style="width:40px" onchange="topage(this.value)" ><c:forEach   varStatus="i" begin="1" end="${pageTotal}"> <option value="${i.index}"  <c:if test="${pageNo==i.index}">selected</c:if> />${i.index }</option></c:forEach><c:if test="${pageTotal==0}"><option value="1">1</option></c:if></select>
                </td>
            </tr>
        </table>
    </div>
</div>
</div>
</body>
</html>
