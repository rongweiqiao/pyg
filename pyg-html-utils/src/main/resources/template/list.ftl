<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

    <table>
        <tr>
            <td>序号</td>
            <td>名字</td>
            <td>年龄</td>
        </tr>
        <#list peopleList as peopele>
        <tr>
            <td>${peopele_index}</td>
            <td>${peopele.name}</td>
            <td>${peopele.age}</td>
        </tr>
        </#list>
    </table>
</body>
</html>