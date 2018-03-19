
<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title></title>
</head>
<script type="text/javascript" src="/resources/js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/resources/plugin/validator.js"></script>
<body style="min-width:1200px; min-height:700px;">
<script type="application/javascript">
    var aaa= "${script}";
    if(aaa.valueOf().length > 0) {
        alert(aaa);
    }

</script>

        <form action="/transferOrigin/login"  method="post" target="_parent" onsubmit="return Validator.Validate(this,2)">
            <table width="230" border="0" cellpadding="0" cellspacing="0" align="center">
                <tr>
                    <td width="126"><h4>用户名</h4></td>
                    <td width="104">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="text" class="login_text" name="username" dataType="Require" msg="用户名不能为空" style="display: inline;" value=""/>
                    </td>
                </tr>
                <tr>
                    <td><h4>登录密码</h4></td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="password" class="login_password"  name="password" dataType="Require" msg="密码不能为空" style="display: inline;"  value=""/>
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <input type="submit"  value="登陆" class="login_btn" />
                    </td>
                </tr>
            </table>
        </form>

</body>
</html>
