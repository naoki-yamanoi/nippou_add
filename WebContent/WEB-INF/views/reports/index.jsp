<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>日報　一覧</h2>
        <table id="report_list">
            <tbody>
                <tr>
                    <th class="report_name">氏名</th>
                    <th class="report_date">日付</th>
                    <th class="report_title">タイトル</th>
                    <th class="report_action">操作</th>
                    <th class="report_name">承認者</th>
                    <th class="report_action">リアクション</th>
                </tr>
                <c:forEach var="approval_report" items="${approval_reports}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="report_name"><c:out value="${approval_report.employee.name}" /></td>
                        <td class="report_date"><fmt:formatDate value='${approval_report.report_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="report_title">${approval_report.title}</td>
                        <td class="report_action"><a href="<c:url value='/reports/show?id=${approval_report.id}' />">詳細を見る</a></td>
                        <td class="report_name"><c:out value="${approval_report.approval_employee.name}" /></td>
                        <td class="report_action">
                        <c:choose>
                            <c:when test="${reactions.size() == 0}">
                                <a href="<c:url value='/reaction/good?id=${approval_report.id}' />">Good! +<c:out value="${approval_report.reaction_good}" /></a>
                                <a href="<c:url value='/reaction/bad?id=${approval_report.id}' />">Bad -<c:out value="${approval_report.reaction_bad}" /></a>
                            </c:when>
                            <c:otherwise>
                                <c:set var="stopFlag" value="0"/>
                                <c:forEach var="reaction" items="${reactions}" varStatus="status">
                                    <c:if test="${stopFlag == 0}">
                                        <c:if test="${reaction.reaction_report.id == approval_report.id}">
                                            <c:set var="stopFlag" value="1"/>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${stopFlag == 1}">
                                        <a class="disabled" href="<c:url value='/reaction/good?id=${approval_report.id}' />">Good! +<c:out value="${approval_report.reaction_good}" /></a>
                                        <a class="disabled" href="<c:url value='/reaction/bad?id=${approval_report.id}' />">Bad -<c:out value="${approval_report.reaction_bad}" /></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value='/reaction/good?id=${approval_report.id}' />">Good! +<c:out value="${approval_report.reaction_good}" /></a>
                                        <a href="<c:url value='/reaction/bad?id=${approval_report.id}' />">Bad -<c:out value="${approval_report.reaction_bad}" /></a>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>

<%--                             <c:otherwise>
                                <c:set var="stopFlag" value="0"/>
                                <c:forEach var="reaction" items="${reactions}" varStatus="status">
                                    <c:if test="${stopFlag == 0}">
                                        <c:choose>
                                            <c:when test="${reaction.reaction_report.id == approval_report.id}">
                                                <a class="disabled" href="<c:url value='/reaction/good?id=${approval_report.id}' />">Good! +<c:out value="${approval_report.reaction_good}" /></a>
                                                <a class="disabled" href="<c:url value='/reaction/bad?id=${approval_report.id}' />">Bad -<c:out value="${approval_report.reaction_bad}" /></a>
                                                <c:set var="stopFlag" value="1"/>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value='/reaction/good?id=${approval_report.id}' />">Good! +<c:out value="${approval_report.reaction_good}" /></a>
                                                <a href="<c:url value='/reaction/bad?id=${approval_report.id}' />">Bad -<c:out value="${approval_report.reaction_bad}" /></a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </c:forEach>
                            </c:otherwise>
 --%>                        </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${approval_reports_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((approval_reports_count - 1) / 10) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/reports/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p class="click_btn"><a href="<c:url value='/reports/new' />">新規日報の登録</a></p>

    </c:param>
</c:import>