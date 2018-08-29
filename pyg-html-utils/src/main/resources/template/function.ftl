<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#assign name="张三" message="欢迎来到我的世界">
</head>
<body>
共${peopleList?size}条
<#assign user=people?eval>
${user.name},${user.age}
当前日期:${date?date}
当前时间:${date?time}
当前日期加时间:${date?datetime}
日期格式化:${date?string("yyyy年MM月dd日 hh时mm分ss秒")}
</body>
</html>