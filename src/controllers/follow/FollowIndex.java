package controllers.follow;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowIndex
 */
@WebServlet("/follow/index")
public class FollowIndex extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowIndex() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        List<Follow> follow_employees = em.createNamedQuery("getMyFollowEmployees", Follow.class)
                                           .setParameter("employee", login_employee)
                                           .getResultList();

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }


        List<Report> follow_reports = em.createNamedQuery("getMyAllReports", Report.class)
                                         .setParameter("employee", follow_employees)
                                         .setFirstResult(15 * (page - 1))
                                         .setMaxResults(15)
                                         .getResultList();


        long follow_reports_count = (long)em.createNamedQuery("getMyReportsCount", Long.class)
                                     .setParameter("employee", follow_employees)
                                     .getSingleResult();

        em.close();

        request.setAttribute("follow_reports", follow_reports);
        request.setAttribute("follow_reports_count", follow_reports_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/follow/index.jsp");
        rd.forward(request, response);
    }
}
