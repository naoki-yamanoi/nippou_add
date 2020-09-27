package controllers.follow;

import java.io.IOException;

import javax.persistence.EntityManager;
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
 * Servlet implementation class FollowUnServlet
 */
@WebServlet("/follow/un")
public class FollowUnServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowUnServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        Employee e = r.getEmployee();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        Follow remove_f = em.createNamedQuery("removeFollowEmployee", Follow.class)
                .setParameter("remove_employee", e)
                .setParameter("login_employee", login_employee)
                .getSingleResult();

        em.getTransaction().begin();
        em.remove(remove_f);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "フォローを解除しました。");

        response.sendRedirect(request.getContextPath() + "/reports/index");
    }

}
