<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#include "assign.ftl">
    <#assign name="张三">
    <#assign user={"name":"张三","age":18}>
</head>
<body>
${name},${user.name},${user.age}
</body>
</html>