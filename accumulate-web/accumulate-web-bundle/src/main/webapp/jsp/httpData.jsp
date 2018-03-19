<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/10/14
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>upload</title>
  <script type="text/javascript" src="/resources/js/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="/resources/js/http.js"></script>
  <script type="text/javascript">
    var __CONTEXT_PATH = "http://localhost:8080";
    var __API_HOST = "http://library.mall.autohome.com.cn";


    function getAllTags() {
        try {
            var data = {
                _appid:"mall",
            }
            var requestData = urlToSpringMvcGeneralQueryByData(__API_HOST + "/tags/getAllTags",data,true);
            console.log(requestData.data);
            $.ajax({
                type: "GET",
                url: requestData.url,
                data: requestData.data,
                dataType:requestData.dataType,
                async:true,
                success: function(resultData){
                    if(resultData){
                        $("#result").val(JSON.stringify(resultData));
                    }
                },
                error:function(){
                    console.log("getAllTags fail");
                }
            });
        } catch(e) {
            console.log(e);
        }
    }

    $(document).ready(function(){


    });
  </script>
</head>
<body>
  <h1>HttpDataController测试页面</h1>
  <div id="newUpload1">
    <input type="button" value="ajax" onclick="getAllTags()">
    <textarea id="result" style="width: 800px;height: 500px;background: ghostwhite"></textarea>
  </div>
</body>
</html>
