<%--
  Created by IntelliJ IDEA.
  User: hubin
  Date: 2018/8/31
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试单点登录--品优购系统1</title>
</head>
<body>
<h1>

  您好！ 欢迎您 :  <%= request.getRemoteUser()%> 测试单点登录品优购系统2
    <a href="http://134.175.21.206:9099/cas/logout">退出登录</a>

</h1>
</body>
</html>
