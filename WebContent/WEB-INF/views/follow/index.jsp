<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:choose>

            <c:when test="${follow_null != null}">
                <h2>フォローしている従業員はいません</h2>
            </c:when>

            <c:otherwise>
                <h3>【フォローしている従業員の日報　一覧】</h3>
                <table id="report_list">
                    <tbody>
                        <tr>
                            <th class="report_name">氏名</th>
                            <th class="report_date">日付</th>
                            <th class="report_title">タイトル</th>
                            <th class="report_action">操作</th>
                        </tr>
                        <c:forEach var="follow_report" items="${follow_reports}" varStatus="status">
                            <!-- ２３行目は色分け、row0もしくはrow1になる、statusが１から１ずつ増えていく -->
                            <tr class="row${status.count % 2}">
                                <td class="report_name"><c:out value="${follow_report.employee.name}" /></td>
                                <td class="report_date"><fmt:formatDate value='${follow_report.report_date}' pattern='yyyy-MM-dd' /></td>
                                <td class="report_title">${follow_report.title}</td>
                                <td class="report_action"><a href="<c:url value='/reports/show?id=${follow_report.id}' />">詳細を見る</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div id="pagination">
                    （全 ${follow_reports_count} 件）<br />
                    <c:forEach var="i" begin="1" end="${((follow_reports_count - 1) / 15) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:otherwise>

        </c:choose>
    </c:param>
</c:import>