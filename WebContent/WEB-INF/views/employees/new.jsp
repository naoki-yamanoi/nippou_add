<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>従業員　新規登録ページ</h2>

        <form method="POST" action="<c:url value='/employees/create' />">
            <c:import url="_form.jsp" />
        </form>

        <p class="click_btn"><a href="<c:url value='/employees/index' />">一覧に戻る</a></p>
    </c:param>
</c:import>