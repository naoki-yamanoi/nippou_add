package controllers.approval;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ApprovalIndex
 */
@WebServlet("/approval/index")
public class ApprovalIndex extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApprovalIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        List<Report> not_approval_reports = null;
        long not_approval_reports_count = 0;
        try {
            not_approval_reports = em.createNamedQuery("getNotApprovalReports", Report.class)
                                               .setFirstResult(10 * (page - 1))
                                               .setMaxResults(10)
                                               .getResultList();

            not_approval_reports_count = (long)em.createNamedQuery("getNotApprovalReportsCount", Long.class)
                                                   .getSingleResult();

        } catch(NoResultException no_e) {
            request.setAttribute("not_approval", "未承認のレポートはありません");
        }

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        em.close();

        request.setAttribute("not_approval_reports", not_approval_reports);
        request.setAttribute("not_approval_reports_count", not_approval_reports_count);
        request.setAttribute("page", page);
        request.setAttribute("login_employee", login_employee);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approval/index.jsp");
        rd.forward(request, response);
    }
}
