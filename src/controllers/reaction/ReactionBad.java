package controllers.reaction;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Reaction;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReactionBad
 */
@WebServlet("/reaction/bad")
public class ReactionBad extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReactionBad() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));
        r.countBad();

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        Reaction reac = new Reaction();
        reac.setReaction_employee(login_employee);
        reac.setReaction_report(r);

        em.getTransaction().begin();
        em.persist(reac);
        em.getTransaction().commit();

        em.close();

        request.getSession().setAttribute("flush", "Badしました。");

        response.sendRedirect(request.getContextPath() + "/reports/index");
    }

}
