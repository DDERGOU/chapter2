<%--
  Created by IntelliJ IDEA.
  User: ZDD
  Date: 2017/5/13
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="BASE" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>客户-列表</title>
    <link href="${BASE}/frame/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
    <div class="container">
        <div class="jumbotron">
            <h1>Hello Smart Framework</h1>
            <p>架构探险·从零开始写Java Web框架</p>
            <p><a class="btn btn-primary btn-lg" href="#" role="button">点赞</a></p>
        </div>
        <div class="bs-example" data-example-id="hoverable-table">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>客户名称</th>
                    <th>联系人</th>
                    <th>电话号码</th>
                    <th>邮箱地址</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="customer" items="${customerList}">
                    <tr>
                        <th scope="row">${customer.name}</th>
                        <td>${customer.contact}</td>
                        <td>${customer.telephone}</td>
                        <td>@${customer.email}</td>
                        <td>
                            <a class="btn btn-link" href="#">编辑</a>
                            <a class="btn btn-link" href="#">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script src="${BASE}/frame/jquery/js/jquery.js"></script>
    <script src="${BASE}/frame/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
