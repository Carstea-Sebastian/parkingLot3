<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Users">

    <h1> Users </h1>
    <form method="POST" action="${pageContext.request.contextPath}/Users">

        <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/AddUser">Add User</a>
        </c:if>

        <button type="submit" class="btn btn-secondary btn-lg">Invoice</button>

        <div class="container text-center mt-3">
            <div class="row fw-bold border-bottom">
                <div class="col-1">Select</div>
                <div class="col">Username</div>
                <div class="col">Email</div>
                <div class="col-2">Actions</div>
            </div>

            <c:forEach var="user" items="${users}">
                <div class="row border-bottom py-2 align-items-center">
                    <div class="col-1">
                        <input type="checkbox" name="user_ids" value="${user.id}" />
                    </div>
                    <div class="col">
                            ${user.username}
                    </div>
                    <div class="col">
                            ${user.email}
                    </div>
                    <div class="col-2">
                        <c:if test="${pageContext.request.isUserInRole('WRITE_USERS')}">
                            <a href="${pageContext.request.contextPath}/EditUser?id=${user.id}"
                               class="btn btn-sm btn-outline-primary">
                                Edit
                            </a>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
    </form>

    <c:if test="${not empty invoices}">
        <div class="mt-4 p-3 bg-light border rounded">
            <h2>Invoices Generated For:</h2>
            <ul>
                <c:forEach var="username" items="${invoices}">
                    <li>${username}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

</t:pageTemplate>