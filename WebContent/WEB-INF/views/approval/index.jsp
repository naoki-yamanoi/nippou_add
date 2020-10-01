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
        <c:choose>
            <c:when test="${not_approval_reports != null}">
                <h2>日報　一覧</h2>
                <table id="report_list">
                    <tbody>
                        <tr>
                            <th class="report_name">氏名</th>
                            <th class="report_date">日付</th>
                            <th class="report_title">タイトル</th>
                            <th class="report_action">操作</th>
                            <th class="report_action">承認</th>
                        </tr>
                        <c:forEach var="report" items="${not_approval_reports}" varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="report_name"><c:out value="${report.employee.name}" /></td>
                                <td class="report_date"><fmt:formatDate value='${report.report_date}' pattern='yyyy-MM-dd' /></td>
                                <td class="report_title">${report.title}</td>
                                <td class="report_action"><a href="<c:url value='/reports/show?id=${report.id}' />">詳細を見る</a></td>
                                <c:choose>
                                    <c:when test="${report.employee.name != login_employee.name}">
                                        <td class="report_action"><a href="<c:url value='/approval/permit?id=${report.id}' />">承認する</a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="report_action">未承認</td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div id="pagination">
                    （全 ${not_approval_reports_count} 件）<br />
                    <c:forEach var="i" begin="1" end="${((not_approval_reports_count - 1) / 10) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/approval/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <h2><c:out value="${not_approval}"></c:out></h2>
            </c:otherwise>
        </c:choose>
    </c:param>
</c:import>