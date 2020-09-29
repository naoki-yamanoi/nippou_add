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
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        List<Report> approval_reports = null;
        long approval_reports_count = 0;
        try {
            approval_reports = em.createNamedQuery("getApprovalReports", Report.class)
                                               .setFirstResult(10 * (page - 1))
                                               .setMaxResults(10)
                                               .getResultList();

            approval_reports_count = (long)em.createNamedQuery("getApprovalReportsCount", Long.class)
                                                   .getSingleResult();

        } catch(NoResultException no_e) {
            request.setAttribute("null_error", "承認されたレポートはありません");
        }

        em.close();

        request.setAttribute("approval_reports", approval_reports);
        request.setAttribute("approval_reports_count", approval_reports_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/approval/index.jsp");
        rd.forward(request, response);
    }
}
