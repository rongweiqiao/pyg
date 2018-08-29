<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
${num?c}
<#if aaa??>
    aaa变量存在
    <#else >
    变量不存在
</#if>
${aaa!"666"}
${aaa?default("888")}
${bbb?default("999")}
</body>
</html>